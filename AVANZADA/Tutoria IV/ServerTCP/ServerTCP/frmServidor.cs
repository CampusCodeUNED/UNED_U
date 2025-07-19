using System;
using System.Drawing;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Windows.Forms;

namespace ServerTCP
{
    public partial class frmServidor : Form
    {
        TcpListener tcpListener;
        Thread subprocesoEscuchaClientes;
        bool servidorIniciado;

        public frmServidor()
        {
            InitializeComponent();

            lblEstado.ForeColor = Color.Red;
            btnDetener.Enabled = false;
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


        private void ComunicacionCliente(object cliente) {
            TcpClient tcCliente = (TcpClient)cliente;
            NetworkStream clienteStream = tcCliente.GetStream();
            ASCIIEncoding encoder = new ASCIIEncoding();
            
            byte[] buffer = new byte[4096];
            int bytesLeidos;

            while (servidorIniciado)
            {

                bytesLeidos = 0;

                try
                {
                    /*Esta sección se bloquea hasta que el cliente envíe unmensaje*/
                    bytesLeidos = clienteStream.Read(buffer, 0, buffer.Length);
                }
                catch 
                {
                    //Ocurrió un error en el socket 
                    break;
                }
                if (bytesLeidos == 0) {
                    //El cliente se desconectó del servidor 
                    break;
                }

                //Mensaje recibido correctamente 
                string mensaje;
                mensaje = encoder.GetString(buffer, 0, bytesLeidos);
                MessageBox.Show(string.Format("El cliente {0} se ha conectado!",mensaje), "Cliente conectado", MessageBoxButtons.OK, MessageBoxIcon.Information);               
            }

            tcCliente.Close();
        }       

        private void btnIniciar_Click(object sender, EventArgs e)
        {
            IPAddress local = IPAddress.Parse("127.0.0.1");
            tcpListener = new TcpListener(local, 30000);
            subprocesoEscuchaClientes = new Thread(new ThreadStart(EscucharClientes));
            subprocesoEscuchaClientes.Start();
            subprocesoEscuchaClientes.IsBackground = true;

            servidorIniciado = true;
            lblEstado.Text = "Escuchando clientes... en (127.0.0.1, 30000)";
            lblEstado.ForeColor = Color.Green;
            btnIniciar.Enabled = false;
            btnDetener.Enabled = true;            
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

        private void groupBox1_Enter(object sender, EventArgs e)
        {

        }

        private void lblEstado_Click(object sender, EventArgs e)
        {

        }

        private void label1_Click(object sender, EventArgs e)
        {

        }
    }
}
