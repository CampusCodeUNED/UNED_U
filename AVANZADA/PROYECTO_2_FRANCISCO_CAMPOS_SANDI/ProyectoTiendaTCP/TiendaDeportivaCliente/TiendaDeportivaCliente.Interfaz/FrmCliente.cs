/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso: Programación avanzada
Código: 00830
Proyecto #2: Tienda deportiva
Tutor: Juan Ramírez Valladares
Grupo: 09
Estudiante: Francisco Campos Sandi
Cédula: 114750560
III Cuatrimestre 2024
*/
using System;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace TiendaDeportivaCliente.Interfaz
{
    public partial class FrmCliente : Form
    {
        private ClienteTcp _clienteTcp;// Cliente TCP para manejar la conexión
        private bool _conectado = false;// Estado de la conexión
        public FrmCliente()
        {
            InitializeComponent();// Inicializa los componentes del formulario
            this.StartPosition = FormStartPosition.CenterScreen;// Centra el formulario en la pantalla
            this.MaximizeBox = false;// Deshabilita el botón de maximizar
            this.FormBorderStyle = FormBorderStyle.FixedSingle;// Deshabilita la opción de cambiar el tamaño del formulario

            _clienteTcp = new ClienteTcp();// Crea una instancia del cliente TCP
            btnDesconectar.Enabled = false;// Desactiva el botón de desconexión al inicio
            btnReservaArticulo.Enabled = false;// Desactiva el botón de reserva
            btnConsultarReserva.Enabled = false;// Desactiva el botón de consulta de reservas
        }
        // Evento click del botón de conexión
        private async void btnConectarse_ClickAsync(object sender, EventArgs e)
        {// Verifica si el campo de identificación está vacío
            if (string.IsNullOrWhiteSpace(txtIdentificacion.Text))
            {
                MessageBox.Show("Ingrese su identificación.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            // Intenta conectar al servidor
            _conectado = _clienteTcp.Conectar("127.0.0.1", 14100); 

            if (_conectado) // Si la conexión es exitosa, valida el cliente
            {
                await Task.Run(() => ValidarCliente());// Ejecuta el método en un hilo aparte
            }
            else
            {
                MessageBox.Show("No se pudo conectar al servidor.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
        // Método para validar el cliente
        private void ValidarCliente()
        {// Envía un mensaje al servidor para validar el cliente
            string respuesta = _clienteTcp.EnviarMensaje($"VALIDAR_CLIENTE:{txtIdentificacion.Text}");
            // Si la respuesta es positiva, muestra el nombre del cliente y habilita botones
            if (respuesta.StartsWith("OK"))
            {
                string nombreCliente = respuesta.Substring(3);// Extrae el nombre del cliente de la respuesta
                this.Invoke(new Action(() => {
                    lblNombreCliente.Text = $"Cliente: {nombreCliente}"; // Muestra el nombre del cliente
                    btnReservaArticulo.Enabled = true;// Habilita el botón de reserva
                    btnConsultarReserva.Enabled = true;// Habilita el botón de consulta de reservas
                    MessageBox.Show("Conexión exitosa y cliente validado.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);// Muestra un mensaje de éxito

                    btnConectarse.Enabled = false;// Desactiva el botón de conexión
                    btnDesconectar.Enabled = true;// Habilita el botón de desconexión
                    txtIdentificacion.Enabled = false;// Desactiva el campo de identificación
                }));
            }
            else
            {// Si la respuesta no es válida, muestra un mensaje de error y desconecta al cliente
                this.Invoke(new Action(() => {
                    MessageBox.Show("Identificación no válida o cliente inactivo.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    _clienteTcp.Desconectar();// Desconecta al cliente
                }));
            }
        }
        // Método para desconectar al cliente
        private void btnDesconectar_Click(object sender, EventArgs e)
        {
            _clienteTcp.Desconectar();// Desconecta al cliente
            lblNombreCliente.Text = "Cliente: ";// Limpia el nombre del cliente
            btnReservaArticulo.Enabled = false;// Desactiva el botón de reserva
            btnConsultarReserva.Enabled = false;// Desactiva el botón de consulta de reservas
            btnConectarse.Enabled = true;// Habilita el botón de conexión
            btnDesconectar.Enabled = false;// Desactiva el botón de desconexión
            txtIdentificacion.Enabled = true;// Habilita el campo de identificación
            MessageBox.Show("Desconectado del servidor.", "Información", MessageBoxButtons.OK, MessageBoxIcon.Information);// Muestra un mensaje de información
        }
        // Método para reservar un artículo
        private void btnReservaArticulo_Click(object sender, EventArgs e)
        {// Crea una instancia de la ventana de reserva de artículo y la muestra
            FrmReservaArticulo frmReservaArticulo = new FrmReservaArticulo(_clienteTcp, int.Parse(txtIdentificacion.Text));
            frmReservaArticulo.ShowDialog();// Muestra la ventana de reserva de artículo
        }
        // Método para consultar reservas
        private void btnConsultarReserva_Click(object sender, EventArgs e)
        {// Crea una instancia de la ventana de consulta de reservas y la muestra
            FrmConsultaReserva frmConsultaReserva = new FrmConsultaReserva(_clienteTcp, int.Parse(txtIdentificacion.Text));
            frmConsultaReserva.ShowDialog();// Muestra la ventana de consulta de reservas
        }
    }
}
