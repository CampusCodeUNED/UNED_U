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
    public partial class FrmAdministrador : Form
    {
        private readonly LN_Administrador _lnAdministrador;

        public FrmAdministrador()
        {
            InitializeComponent();
            this.StartPosition = FormStartPosition.CenterScreen; // Centra la ventana en la pantalla
            this.MaximizeBox = false; // Deshabilita el botón de maximizar
            this.FormBorderStyle = FormBorderStyle.FixedSingle; // Establece el borde de la ventana como fijo

            _lnAdministrador = new LN_Administrador();
        }

        private void FrmAdministrador_Load(object sender, EventArgs e)
        {
            CargarAdministradores(); // Carga la lista de administradores al cargar el formulario
            dtpFechaNacimiento.MaxDate = DateTime.Now.AddYears(-18); // Establece la fecha máxima de nacimiento para validar mayor de edad
        }

        private void CargarAdministradores()
        {
            // Obtiene la lista de administradores de la base de datos
            List<Administrador> administradores = _lnAdministrador.ObtenerAdministradores();
            dataGridAdministradores.DataSource = administradores; // Asigna la lista al DataGridView
            foreach (DataGridViewColumn column in dataGridAdministradores.Columns)
            {
                column.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill; // Ajusta el tamaño de las columnas
            }
        }

        private void btnAgregarAdministrador_Click(object sender, EventArgs e)
        {
            // Validación de la identificación del administrador
            if (!int.TryParse(txtIdentificacion.Text, out int identificacion))
            {
                MessageBox.Show("Ingrese una identificación válida (solo números).", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                txtIdentificacion.Focus(); // Focaliza el campo de identificación
                return;
            }

            // Validación del nombre del administrador
            if (string.IsNullOrWhiteSpace(txtNombre.Text))
            {
                MessageBox.Show("Ingrese un nombre válido.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                txtNombre.Focus(); // Focaliza el campo de nombre
                return;
            }

            // Validación del primer apellido
            if (string.IsNullOrWhiteSpace(txtApellido1.Text))
            {
                MessageBox.Show("Ingrese un primer apellido válido.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                txtApellido1.Focus(); // Focaliza el campo de primer apellido
                return;
            }

            // Validación del segundo apellido
            if (string.IsNullOrWhiteSpace(txtApellido2.Text))
            {
                MessageBox.Show("Ingrese un segundo apellido válido.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                txtApellido2.Focus(); // Focaliza el campo de segundo apellido
                return;
            }

            // Validación de la fecha de nacimiento
            DateTime fechaNacimiento = dtpFechaNacimiento.Value;
            DateTime fechaLimite = DateTime.Now.AddYears(-18); // Establece límite para mayor de edad
            if (fechaNacimiento > fechaLimite)
            {
                MessageBox.Show("El administrador debe ser mayor de 18 años.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                dtpFechaNacimiento.Focus(); // Focaliza el campo de fecha de nacimiento
                return;
            }

            // Validación de la fecha de ingreso
            DateTime fechaIngreso = dtpFechaIngreso.Value;
            if (fechaIngreso < fechaNacimiento)
            {
                MessageBox.Show("La fecha de ingreso no puede ser anterior a la fecha de nacimiento.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                dtpFechaIngreso.Focus(); // Focaliza el campo de fecha de ingreso
                return;
            }

            // Creación del objeto Administrador
            Administrador administrador = new Administrador
            {
                Identificacion = identificacion,
                Nombre = txtNombre.Text,
                Apellido1 = txtApellido1.Text,
                Apellido2 = txtApellido2.Text,
                FechaNacimiento = fechaNacimiento,
                FechaIngreso = fechaIngreso
            };

            // Registra al administrador en la base de datos
            bool registrado = _lnAdministrador.RegistrarAdministrador(administrador);

            if (registrado)
            {
                MessageBox.Show("Administrador registrado exitosamente.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
                CargarAdministradores(); // Recarga la lista de administradores
                LimpiarCampos(); // Limpia los campos del formulario
            }
            else
            {
                MessageBox.Show("El administrador ya existe: verifique la identificación o nombre completo.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void LimpiarCampos()
        {
            // Limpia los campos del formulario después de registrar un nuevo administrador
            txtIdentificacion.Clear();
            txtNombre.Clear();
            txtApellido1.Clear();
            txtApellido2.Clear();
            dtpFechaIngreso.Value = DateTime.Today; // Establece la fecha de ingreso a la fecha actual
        }

    }
}
