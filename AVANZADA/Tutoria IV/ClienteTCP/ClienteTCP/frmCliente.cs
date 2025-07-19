using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Net;
using System.Net.Sockets;

namespace ClienteTCP
{
    public partial class frmCliente : Form
    {

        bool clienteConectado = false;
        TcpClient cliente;

        public frmCliente()
        {
            InitializeComponent();
            lblEstado.ForeColor = Color.Red;
            btnDesconectar.Enabled = false;
            btnConectar.Enabled = true;
        }
       
        private void IniciarCliente() {
            if (!(txtIdentificador.Text.Equals(string.Empty)))
            {
                try
                {
                    IPAddress ipServidor = IPAddress.Parse("127.0.0.1");
                    cliente = new TcpClient();
                    IPEndPoint serverEndPoint = new IPEndPoint(ipServidor, 30000);

                    cliente.Connect(serverEndPoint);
                    NetworkStream clientStream = cliente.GetStream();
                    ASCIIEncoding encoder = new ASCIIEncoding();

                    byte[] buffer = encoder.GetBytes(txtIdentificador.Text);//buffer obtiene los bytes del mensaje a enviar
                    clientStream.Write(buffer, 0, buffer.Length);
                    clientStream.Flush();//se envia los bytes al servidor

                    lblEstado.Text = "Conectado al servidor... en (127.0.0.1, 30000)";
                    lblEstado.ForeColor = Color.Green;
                    clienteConectado = true;
                    btnConectar.Enabled = false;
                    btnDesconectar.Enabled = true;
                    txtIdentificador.ReadOnly = true;
                }
                catch (SocketException)
                {

                    MessageBox.Show("Verifique que el servidor esté escuchando clientes...", "No es posible conectarse", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                }

            }
            else
            {
                MessageBox.Show("Debe ingresar el identificador del cliente", "Atención", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }

        }

        private void btnConectar_Click(object sender, EventArgs e)
        {
            IniciarCliente();
        }

        private void btnDesconectar_Click(object sender, EventArgs e)
        {
            //Se cierra la conexión del cliente
            cliente.Close();

            lblEstado.Text = "Desconectado";
            lblEstado.ForeColor = Color.Red;
            btnDesconectar.Enabled = false;
            btnConectar.Enabled = true;
            clienteConectado = false;
            txtIdentificador.ReadOnly = false;
        }
    }
}
