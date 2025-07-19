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
using System.Collections.Generic;
using System.Globalization;
using System.Windows.Forms;
using TiendaDeportivaCliente.Entidades;

namespace TiendaDeportivaCliente.Interfaz
{
    public partial class FrmConsultaReserva : Form
    {
        private ClienteTcp _clienteTcp;// Cliente TCP para comunicación con el servidor
        private int _identificacionCliente;// Identificación del cliente que realiza la consulta
        public FrmConsultaReserva(ClienteTcp clienteTcp, int identificacionCliente)
        {
            InitializeComponent();// Inicializa los componentes de la interfaz
            this.StartPosition = FormStartPosition.CenterScreen;// Centra la ventana en la pantalla
            this.MaximizeBox = false;// Deshabilita el botón de maximizar
            this.FormBorderStyle = FormBorderStyle.FixedSingle;// Deshabilita la opción de cambiar el tamaño de la ventana

            _clienteTcp = clienteTcp;// Asigna el cliente TCP recibido como parámetro
            _identificacionCliente = identificacionCliente;// Asigna la identificación del cliente
        }
        // Evento para consultar todas las reservas del cliente
        private void button2_Click(object sender, EventArgs e)
        {
            string mensaje = $"CONSULTAR_RESERVAS:{_identificacionCliente}";// Mensaje para consultar las reservas
            string respuesta = _clienteTcp.EnviarMensaje(mensaje);// Envía el mensaje al servidor
            MostrarReservas(respuesta);// Muestra las reservas en el DataGridView
        }
        // Evento para consultar una reserva específica mediante su ID
        private void button1_Click(object sender, EventArgs e)
        {    // Valida que el ID sea un número entero
            if (int.TryParse(txtIdReserva.Text, out int idReserva))
            {
                string mensaje = $"CONSULTAR_RESERVA:{_identificacionCliente},{idReserva}";// Mensaje para consultar una reserva
                string respuesta = _clienteTcp.EnviarMensaje(mensaje);// Envía el mensaje al servidor
                MostrarReservas(respuesta);// Muestra la reserva especificada en el DataGridView
            }
            else
            {
                MessageBox.Show("Por favor, ingrese un ID de reserva válido.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
        // Método para mostrar las reservas en la tabla
        private void MostrarReservas(string respuesta)
        {
            if (respuesta.StartsWith("OK:"))
            {// Procesa las reservas recibidas
                List<Reserva> reservas = ParsearReservas(respuesta.Substring(3));
                dgvReservas.DataSource = reservas;// Asigna la lista de reservas al DataGridView
                // Ajusta el tamaño de las columnas para ocupar todo el espacio disponible
                foreach (DataGridViewColumn column in dgvReservas.Columns)
                {
                    column.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
                }
            }
            else
            {
                MessageBox.Show("Error al consultar reservas: " + respuesta, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        // Método para convertir los datos de reservas recibidos en objetos Reserva
        private List<Reserva> ParsearReservas(string datosReservas)
        {// Lista de reservas a retornar
            List<Reserva> reservas = new List<Reserva>();
            string[] registros = datosReservas.Split('|');// Divide cada registro de reserva

            foreach (string registro in registros)
            {
                string[] campos = registro.Split(',');// Divide cada campo dentro del registro
                if (campos.Length == 4)// Verifica que se tengan los campos esperados
                {
                    try
                    {// Crea un objeto Reserva con los datos del registro
                        reservas.Add(new Reserva
                        {
                            Id = int.Parse(campos[0]),// ID de la reserva
                            Sucursal = campos[1],
                            Articulo = campos[2],
                            FechaReserva = DateTime.ParseExact(campos[3], "yyyy-MM-dd HH:mm:ss", CultureInfo.InvariantCulture)// Fecha de la reserva
                        });
                    }
                    catch (FormatException ex)
                    {
                        MessageBox.Show($"Error al procesar la reserva: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }
                }
                else
                {
                    MessageBox.Show("Formato de datos inválido recibido del servidor.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
            return reservas;// Retorna la lista de reservas
        }
    }
}
