/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso: Programación avanzada
Código: 00830
Proyecto #1: Tienda deportiva
Tutor: Juan Ramírez Valladares
Grupo: 09
Estudiante: Francisco Campos Sandi
Cédula: 114750560
III Cuatrimestre 2024
*/
using System;
using System.Windows.Forms;
using TiendaDeportiva.CapaAccesoDatos;
using TiendaDeportiva.CapaEntidades;
using TiendaDeportiva.CapaLogicaNegocio;

namespace TiendaDeportiva.CapaPresentacion
{
    public partial class FrmCliente : Form
    {
        private CLN_Cliente cln_Cliente; // Clase para la lógica de clientes

        public FrmCliente()
        {
            InitializeComponent(); // Inicializa los componentes de la forma
            cln_Cliente = CLN_Cliente.Instancia(); // Inicializa la clase de lógica de cliente
            ConfigurarDataGridView(); // Configurar el DataGridView
            CargarClientes(); // Cargar clientes en el DataGridView
            dtpFechaNacimiento.MaxDate = DateTime.Now.AddYears(-18); // Establece la fecha máxima para el nacimiento
        }

        private void ConfigurarDataGridView()
        {
            // Configuración del DataGridView para mostrar clientes
            dgvClientes.AllowUserToResizeColumns = false;
            dgvClientes.AllowUserToResizeRows = false;
            dgvClientes.ReadOnly = true;

            // Agregar columnas al DataGridView
            dgvClientes.Columns.Add("Identificacion", "Identificación");
            dgvClientes.Columns.Add("Nombre", "Nombre");
            dgvClientes.Columns.Add("PrimerApellido", "Primer Apellido");
            dgvClientes.Columns.Add("SegundoApellido", "Segundo Apellido");
            dgvClientes.Columns.Add("FechaNacimiento", "Fecha de Nacimiento");
            dgvClientes.Columns.Add("Activo", "Activo");
        }

        private void CargarClientes()
        {
            // Limpiar el DataGridView y cargar los clientes
            dgvClientes.Rows.Clear();
            var clientes = cln_Cliente.ObtenerClientes(); // Obtener lista de clientes
            foreach (var cliente in clientes)
            {
                if (cliente != null)
                {
                    // Agregar cada cliente al DataGridView
                    dgvClientes.Rows.Add(cliente.Identificacion, cliente.Nombre,
                        cliente.PrimerApellido, cliente.SegundoApellido,
                        cliente.FechaNacimiento.ToShortDateString(),
                        cliente.Activo ? "Sí" : "No");
                }
            }
        }

        private void btnAgregarCliente_Click(object sender, EventArgs e)
        {
            // Manejar la adición de un nuevo cliente
            try
            {
                // Validar que el campo Identificación no esté vacío
                if (string.IsNullOrWhiteSpace(txtIdentificacion.Text))
                {
                    MessageBox.Show("El campo 'Identificación' es obligatorio.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    txtIdentificacion.Focus();
                    return;
                }

                // Validar que la identificación sea un número válido
                if (!int.TryParse(txtIdentificacion.Text, out int identificacion))
                {
                    MessageBox.Show("La identificación debe ser un número válido.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    txtIdentificacion.Focus();
                    return;
                }

                // Validar que el campo Nombre no esté vacío
                if (string.IsNullOrWhiteSpace(txtNombre.Text))
                {
                    MessageBox.Show("El campo 'Nombre' es obligatorio.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    txtNombre.Focus();
                    return;
                }

                // Validar que el campo Primer Apellido no esté vacío
                if (string.IsNullOrWhiteSpace(txtPrimerApellido.Text))
                {
                    MessageBox.Show("El campo 'Primer Apellido' es obligatorio.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    txtPrimerApellido.Focus();
                    return;
                }

                // Validar que el campo Segundo Apellido no esté vacío
                if (string.IsNullOrWhiteSpace(txtSegundoApellido.Text))
                {
                    MessageBox.Show("El campo 'Segundo Apellido' es obligatorio.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    txtSegundoApellido.Focus();
                    return;
                }

                // Validar que se seleccione si el cliente está activo
                if (cmbActivo.SelectedIndex == -1)
                {
                    MessageBox.Show("Por favor, seleccione si el cliente está activo.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    cmbActivo.Focus();
                    return;
                }

                // Validar la fecha de nacimiento
                DateTime fechaNacimiento = dtpFechaNacimiento.Value;
                if (fechaNacimiento >= DateTime.Today)
                {
                    MessageBox.Show("La fecha de nacimiento no puede ser hoy ni en el futuro.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    dtpFechaNacimiento.Focus();
                    return;
                }

                // Comprobar si ya existe un cliente con esa identificación
                if (cln_Cliente.ExisteCliente(identificacion))
                {
                    MessageBox.Show("Ya existe un cliente con esa identificación.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return;
                }

                // Crear un nuevo cliente
                string nombre = txtNombre.Text;
                string primerApellido = txtPrimerApellido.Text;
                string segundoApellido = txtSegundoApellido.Text;
                bool activo = cmbActivo.SelectedItem.ToString() == "Sí";

                Cliente nuevoCliente = new Cliente(identificacion, nombre, primerApellido, segundoApellido, fechaNacimiento, activo);
                cln_Cliente.AgregarCliente(nuevoCliente); // Agregar el cliente a la lógica de negocio

                // Limpiar los campos de entrada
                txtIdentificacion.Clear();
                txtNombre.Clear();
                txtPrimerApellido.Clear();
                txtSegundoApellido.Clear();
                cmbActivo.SelectedIndex = -1;

                MessageBox.Show("El cliente se ha agregado correctamente.");
                CargarClientes(); // Recargar los clientes en el DataGridView
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error: {ex.Message}"); // Mostrar error en caso de excepción
            }
        }

        private void label4_Click(object sender, EventArgs e)
        {
            
        }

        private void dgvClientes_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            
        }
    }
}

