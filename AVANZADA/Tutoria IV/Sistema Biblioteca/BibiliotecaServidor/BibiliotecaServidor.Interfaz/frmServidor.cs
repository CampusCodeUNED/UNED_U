using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using BibiliotecaServidor.Entidades;
using Newtonsoft.Json;
using System.IO;
using BibiliotecaServidor.Datos;


namespace BibiliotecaServidor.Interfaz
{
    public partial class frmServidor : Form
    {
        TcpListener tcpListener;
        Thread subprocesoEscuchaClientes;
        EscribirEnTextboxDelegado modificarTextotxtBitacora;
        ModoficarListBoxDelegado modificarListBoxClientes;
        DatosBiblioteca accesoDatos;
        //StreamWriter servidorStreamWriter;
        bool servidorIniciado;

        public frmServidor()
        {
            InitializeComponent();
            accesoDatos = new DatosBiblioteca();
            modificarTextotxtBitacora = new EscribirEnTextboxDelegado(EscribirEnTextbox);
            modificarListBoxClientes = new ModoficarListBoxDelegado(ModificarListBox);
            lblEstado.ForeColor = Color.Red;
            btnDetener.Enabled = false;
        }

        //Delegado, necesario para modificar controles de la interfaz gráfica desde un subproceso
        private delegate void EscribirEnTextboxDelegado(string texto);
        private delegate void ModoficarListBoxDelegado(string texto, bool agregar);

        //Método utilizado por el delegado para modificar la interfaz gráfica desde un subproceso
        private void EscribirEnTextbox(string texto)
        {
            txtBitacora.AppendText(DateTime.Now.ToString() + " - " + texto);
            txtBitacora.AppendText(Environment.NewLine);
        }

        //Método utilizado por el delegado para modificar la interfaz gráfica desde un subproceso
        private void ModificarListBox(string texto, bool agregar)
        {
            if (agregar)
            {
                lstClientesConectados.Items.Add(texto);
            }
            else
            {
                lstClientesConectados.Items.Remove(texto);
            }
                  
        }

        private void btnIniciar_Click(object sender, EventArgs e)
        {
            IPAddress local = IPAddress.Parse("127.0.0.1");
            tcpListener = new TcpListener(local, 30000);
            servidorIniciado = true;  

            subprocesoEscuchaClientes = new Thread(new ThreadStart(EscucharClientes));
            subprocesoEscuchaClientes.Start();
            subprocesoEscuchaClientes.IsBackground = true; 
            lblEstado.Text = "Escuchando clientes... en (127.0.0.1, 30000)";
            lblEstado.ForeColor = Color.Green;
            btnIniciar.Enabled = false;
            btnDetener.Enabled = true;

           txtBitacora.Text = "Servidor iniciado... en (127.0.0.1, 30000)";
           txtBitacora.AppendText(Environment.NewLine);
        }

        private void EscucharClientes()
        {
            tcpListener.Start();
            while (servidorIniciado)
            {              
                //Se bloquea hasta que un cliente se haya conectado al servidor 
                TcpClient client = tcpListener.AcceptTcpClient();
                /*Se crea un nuevo hilo para manejar la comunicación con los clientes que se conectan al servidor*/
                Thread clientThread = new Thread(new ParameterizedThreadStart(ComunicacionCliente));
                clientThread.Start(client);                           
            }
        }

        private void ComunicacionCliente(object cliente)
        {           
            TcpClient tcCliente = (TcpClient)cliente;
            StreamReader reader = new StreamReader(tcCliente.GetStream());
            StreamWriter servidorStreamWriter = new StreamWriter(tcCliente.GetStream());//El StreamWriter debe ser único por subproceso y por cliente por eso se pasa por referencia

            while (servidorIniciado)
            {               

                try
                {
                    /*Esta sección se bloquea hasta que el cliente envíe unmensaje*/
                    var mensaje = reader.ReadLine();
                    MensajeSocket<object> mensajeRecibido = JsonConvert.DeserializeObject<MensajeSocket<object>>(mensaje);//Se deserializa el objeto recibido mediante json
                    SeleccionarMetodo(mensajeRecibido.Metodo, mensaje, ref servidorStreamWriter);
                }
                catch(Exception e)
                {
                    //Ocurrió un error en el socket 
                    break;
                }                
            }

            tcCliente.Close();
        }

        private void btnDetener_Click(object sender, EventArgs e)
        {
            servidorIniciado = false; 
            tcpListener.Stop();
            subprocesoEscuchaClientes.Abort();            
            

            lblEstado.ForeColor = Color.Red;
            lblEstado.Text = "Sin iniciar";
            btnIniciar.Enabled = true;
            btnDetener.Enabled = false;
        }


        /// <summary>
        /// Método donde se procesa el mensaje recibido para seleccionar el método que está solicitanto el cliente
        /// </summary>
        /// <param name="pMetodo">Nombre del método que se debe invocar</param>
        /// <param name="pMensaje">Mensaje enviado por el cliente</param>
        public void SeleccionarMetodo(string pMetodo, string pMensaje, ref StreamWriter servidorStreamWriter)
        {
            switch (pMetodo)
            {
                case "Conectar":
                    MensajeSocket<string> mensajeConectar = JsonConvert.DeserializeObject<MensajeSocket<string>>(pMensaje);// Se deserializa el objeto recibido mediante json
                    Conectar(mensajeConectar.Entidad);
                    break;
                case "AgregarAutor":
                    MensajeSocket<Autor> mensajeCrearAutor = JsonConvert.DeserializeObject<MensajeSocket<Autor>>(pMensaje);// Se deserializa el objeto recibido mediante json
                    AgregarAutor(mensajeCrearAutor.Entidad);
                    break;
                case "AgregarLibro":
                    MensajeSocket<Libro> mensajeCrearLibro = JsonConvert.DeserializeObject<MensajeSocket<Libro>>(pMensaje);//Se deserializa el objeto recibido mediante json
                    AgregarLibro(mensajeCrearLibro.Entidad);
                    break;
                case "ObtenerAutores":                    
                    ObtenerAutores(ref servidorStreamWriter);
                    break;
                case "ObtenerLibrosDeAutor":
                    MensajeSocket<string> mensajeObtenerLibrosDeAutor = JsonConvert.DeserializeObject<MensajeSocket<string>>(pMensaje);//Se deserializa el objeto recibido mediante json
                    ObtenerLibrosDeAutor(mensajeObtenerLibrosDeAutor.Entidad, ref servidorStreamWriter);
                    break;
                case "Desconectar":
                    MensajeSocket<string> mensajeDesconectar = JsonConvert.DeserializeObject<MensajeSocket<string>>(pMensaje);//Se deserializa el objeto recibido mediante json
                    Desconectar(mensajeDesconectar.Entidad);
                    break;
                default:
                    break;
            }

        }

        private void Desconectar(string pIdentificadorCliente)
        {
            txtBitacora.Invoke(modificarTextotxtBitacora, new object[] { pIdentificadorCliente + " se ha desconectado!" });
            lstClientesConectados.Invoke(modificarListBoxClientes, new object[] { pIdentificadorCliente, false });
        }

        private void AgregarLibro(Libro pNuevoLibro)
        {
            accesoDatos.AgregarLibro(pNuevoLibro);
        }

        private void ObtenerLibrosDeAutor(string pIdAutor, ref StreamWriter servidorStreamWriter)
        {
            List<Libro> listaLibros = new List<Libro>();

            listaLibros = accesoDatos.ObtenerLibrosDeAutor(pIdAutor);

            servidorStreamWriter.WriteLine(JsonConvert.SerializeObject(listaLibros));
            servidorStreamWriter.Flush();
        }

        private void ObtenerAutores(ref StreamWriter servidorStreamWriter)
        {
            List<Autor> listaAutores = new List<Autor>();            

            listaAutores = accesoDatos.ObtenerAutores();          

            servidorStreamWriter.WriteLine(JsonConvert.SerializeObject(listaAutores));
            servidorStreamWriter.Flush();
        }

        private void AgregarAutor(Autor pNuevoAutor)
        {
            accesoDatos.AgregarAutor(pNuevoAutor);
        }

        private void Conectar(string pIdentificadorCliente)
        {
            txtBitacora.Invoke(modificarTextotxtBitacora, new object[] { pIdentificadorCliente + " se ha conectado..." });
            lstClientesConectados.Invoke(modificarListBoxClientes, new object[] { pIdentificadorCliente , true});

        }

        private void frmServidor_Load(object sender, EventArgs e)
        {

        }
    }
}
