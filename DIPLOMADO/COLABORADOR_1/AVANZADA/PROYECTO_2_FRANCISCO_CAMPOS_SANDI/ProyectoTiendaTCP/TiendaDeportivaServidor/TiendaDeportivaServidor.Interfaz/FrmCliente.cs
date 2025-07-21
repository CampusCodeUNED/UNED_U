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
using System.Windows.Forms;
using TiendaDeportivaServidor.Datos;
using TiendaDeportivaServidor.Entidades;
using TiendaDeportivaServidor.LogicaNegocio;

namespace TiendaDeportivaServidor.Interfaz
{
    public partial class FrmCliente : Form
    {
        private readonly LN_Cliente _lnCliente;

        public FrmCliente()
        {
            InitializeComponent();
            this.StartPosition = FormStartPosition.CenterScreen; // Centra la ventana en la pantalla
            this.MaximizeBox = false; // Desactiva la opción de maximizar la ventana
            this.FormBorderStyle = FormBorderStyle.FixedSingle; // Establece que la ventana no podrá cambiar de tamaño

            _lnCliente = new LN_Cliente(); // Inicializa la conexión a la base de datos
        }

        private void FrmCliente_Load(object sender, EventArgs e)
        {
            cmbActivo.Items.Add("Sí"); // Agrega la opción "Sí" al combo box
            cmbActivo.Items.Add("No"); // Agrega la opción "No" al combo box

            CargarClientes(); // Carga la lista de clientes en el DataGrid
            dtpFechaNacimiento.MaxDate = DateTime.Now.AddYears(-18); // Establece la fecha máxima para la fecha de nacimiento (mayores de 18 años)
        }

        private void CargarClientes()
        {
            List<Cliente> clientes = _lnCliente.ObtenerClientes(); // Consulta todos los clientes de la base de datos
            dataGridClientes.DataSource = clientes; // Asigna la lista de clientes al DataGrid

            // Limpia las columnas actuales en el DataGrid
            dataGridClientes.Columns.Clear();

            // Agrega columnas personalizadas al DataGrid
            dataGridClientes.Columns.Add("Identificacion", "Identificación");
            dataGridClientes.Columns["Identificacion"].DataPropertyName = "Identificacion";

            dataGridClientes.Columns.Add("Nombre", "Nombre");
            dataGridClientes.Columns["Nombre"].DataPropertyName = "Nombre";

            dataGridClientes.Columns.Add("Apellido1", "Primer Apellido");
            dataGridClientes.Columns["Apellido1"].DataPropertyName = "Apellido1";

            dataGridClientes.Columns.Add("Apellido2", "Segundo Apellido");
            dataGridClientes.Columns["Apellido2"].DataPropertyName = "Apellido2";

            dataGridClientes.Columns.Add("FechaNacimiento", "Fecha de Nacimiento");
            dataGridClientes.Columns["FechaNacimiento"].DataPropertyName = "FechaNacimiento";

            dataGridClientes.Columns.Add("ActivoTexto", "Activo");
            dataGridClientes.Columns["ActivoTexto"].DataPropertyName = "ActivoTexto";

            // Ajusta automáticamente el tamaño de las columnas
            foreach (DataGridViewColumn column in dataGridClientes.Columns)
            {
                column.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill; // Ajusta el tamaño de las columnas al espacio disponible
            }
        }

        private void btnAgregarCliente_Click(object sender, EventArgs e)
        {
            // Verifica si la identificación es un número válido
            if (!int.TryParse(txtIdentificacion.Text, out int identificacion))
            {
                MessageBox.Show("Ingrese una identificación válida (solo números).", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                txtIdentificacion.Focus(); // Enfoca el campo de identificación
                return; // Sale del método si la validación falla
            }

            // Verifica si el nombre del cliente no está vacío
            if (string.IsNullOrWhiteSpace(txtNombre.Text))
            {
                MessageBox.Show("Ingrese un nombre válido para el cliente.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                txtNombre.Focus(); // Enfoca el campo de nombre
                return;
            }

            // Verifica si el primer apellido del cliente no está vacío
            if (string.IsNullOrWhiteSpace(txtApellido1.Text))
            {
                MessageBox.Show("Ingrese el primer apellido del cliente.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                txtApellido1.Focus(); // Enfoca el campo de primer apellido
                return;
            }

            // Verifica si el segundo apellido del cliente no está vacío
            if (string.IsNullOrWhiteSpace(txtApellido2.Text))
            {
                MessageBox.Show("Ingrese el segundo apellido del cliente.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                txtApellido2.Focus(); // Enfoca el campo de segundo apellido
                return;
            }

            // Verifica si la fecha de nacimiento es válida (mayor de 18 años)
            DateTime fechaNacimiento = dtpFechaNacimiento.Value;
            DateTime fechaLimite = DateTime.Now.AddYears(-18);
            if (fechaNacimiento > fechaLimite)
            {
                MessageBox.Show("El cliente debe ser mayor de 18 años.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                dtpFechaNacimiento.Focus(); // Enfoca el campo de fecha de nacimiento
                return;
            }

            // Verifica si el cliente ha seleccionado si está activo
            if (cmbActivo.SelectedItem == null)
            {
                MessageBox.Show("Seleccione si el cliente está activo.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                cmbActivo.Focus(); // Enfoca el combo box de estado activo
                return;
            }

            // Crea una nueva instancia de la clase Cliente con los datos ingresados
            Cliente cliente = new Cliente
            {
                Identificacion = identificacion,
                Nombre = txtNombre.Text,
                Apellido1 = txtApellido1.Text,
                Apellido2 = txtApellido2.Text,
                FechaNacimiento = fechaNacimiento,
                Activo = cmbActivo.SelectedItem.ToString() == "Sí" // Convierte la selección de "Sí"/"No" a un valor booleano
            };

            // Intenta registrar al cliente en la base de datos
            bool registrado = _lnCliente.RegistrarCliente(cliente);

            if (registrado)
            {
                MessageBox.Show("Cliente registrado exitosamente.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
                CargarClientes(); // Recarga la lista de clientes en el DataGrid
                LimpiarCampos(); // Limpia los campos de entrada
            }
            else
            {
                MessageBox.Show("El cliente ya existe: verifique la identificación o el nombre completo.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        // Limpia los campos del formulario
        private void LimpiarCampos()
        {
            txtIdentificacion.Clear(); // Limpia el campo de identificación
            txtNombre.Clear(); // Limpia el campo de nombre
            txtApellido1.Clear(); // Limpia el campo de primer apellido
            txtApellido2.Clear(); // Limpia el campo de segundo apellido
            cmbActivo.SelectedIndex = -1; // Limpia la selección del combo box de estado activo
        }
    }
}
