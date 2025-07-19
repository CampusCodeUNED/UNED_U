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
using TiendaDeportiva.CapaEntidades;
using TiendaDeportiva.CapaLogicaNegocio;

namespace TiendaDeportiva.CapaPresentacion
{
    public partial class FrmAdministrador : Form
    {
        private CLN_Administrador cln_Administrador; // Lógica de negocio para administradores

        public FrmAdministrador()
        {
            InitializeComponent();
            cln_Administrador = CLN_Administrador.Instancia(); // Inicializa la instancia Singleton
            ConfigurarDataGridView(); // Configura el DataGridView
            CargarAdministradores(); // Carga los administradores existentes

            // Establece la fecha máxima de nacimiento en 18 años atrás
            dtpFechaNacimiento.MaxDate = DateTime.Now.AddYears(-18);
        }

        private void ConfigurarDataGridView()
        {
            // Configuración del DataGridView
            dgvAdministradores.AllowUserToResizeColumns = false;
            dgvAdministradores.AllowUserToResizeRows = false;
            dgvAdministradores.ReadOnly = true;

            // Limpia columnas anteriores y agrega nuevas
            dgvAdministradores.Columns.Clear();
            dgvAdministradores.Columns.Add("Identificacion", "Identificación");
            dgvAdministradores.Columns.Add("Nombre", "Nombre");
            dgvAdministradores.Columns.Add("PrimerApellido", "Primer Apellido");
            dgvAdministradores.Columns.Add("SegundoApellido", "Segundo Apellido");
            dgvAdministradores.Columns.Add("FechaNacimiento", "Fecha de Nacimiento");
            dgvAdministradores.Columns.Add("FechaIngreso", "Fecha de Ingreso");
        }

        private void CargarAdministradores()
        {
            try
            {
                dgvAdministradores.Rows.Clear(); // Limpia las filas existentes
                var administradores = cln_Administrador.ObtenerAdministradores(); // Obtiene la lista de administradores

                foreach (var administrador in administradores)
                {
                    if (administrador != null)
                    {
                        // Agrega cada administrador al DataGridView
                        dgvAdministradores.Rows.Add(
                            administrador.Identificacion,
                            administrador.Nombre,
                            administrador.PrimerApellido,
                            administrador.SegundoApellido,
                            administrador.FechaNacimiento.ToShortDateString(),
                            administrador.FechaIngreso.ToShortDateString()
                        );
                    }
                }
            }
            catch (Exception ex)
            {
                // Muestra un mensaje de error en caso de excepciones
                MessageBox.Show($"Error al cargar administradores: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void btnAgregarAdministrador_Click(object sender, EventArgs e)
        {
            try
            {
                // Validación de campos obligatorios
                if (string.IsNullOrWhiteSpace(txtIdentificacion.Text))
                {
                    MessageBox.Show("El campo 'Identificación' es obligatorio.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    txtIdentificacion.Focus();
                    return;
                }

                // Validación de tipo numérico
                if (!int.TryParse(txtIdentificacion.Text, out int identificacion))
                {
                    MessageBox.Show("La identificación debe ser un número válido.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    txtIdentificacion.Focus();
                    return;
                }

                // Validación de campos obligatorios
                if (string.IsNullOrWhiteSpace(txtNombre.Text))
                {
                    MessageBox.Show("El campo 'Nombre' es obligatorio.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    txtNombre.Focus();
                    return;
                }

                if (string.IsNullOrWhiteSpace(txtPrimerApellido.Text))
                {
                    MessageBox.Show("El campo 'Primer Apellido' es obligatorio.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    txtPrimerApellido.Focus();
                    return;
                }

                if (string.IsNullOrWhiteSpace(txtSegundoApellido.Text))
                {
                    MessageBox.Show("El campo 'Segundo Apellido' es obligatorio.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    txtSegundoApellido.Focus();
                    return;
                }

                // Validación de fechas
                DateTime fechaNacimiento = dtpFechaNacimiento.Value;
                DateTime fechaIngreso = dtpFechaIngreso.Value;

                if (fechaNacimiento >= DateTime.Today)
                {
                    MessageBox.Show("La fecha de nacimiento no puede ser hoy ni en el futuro.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    dtpFechaNacimiento.Focus();
                    return;
                }

                if (fechaIngreso < fechaNacimiento)
                {
                    MessageBox.Show("La fecha de ingreso no puede ser anterior a la fecha de nacimiento.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    dtpFechaIngreso.Focus();
                    return;
                }

                // Verificación de existencia del administrador
                if (cln_Administrador.ExisteAdministrador(identificacion))
                {
                    MessageBox.Show("Ya existe un administrador con esa identificación.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return;
                }

                // Creación del nuevo administrador y registro
                string nombre = txtNombre.Text;
                string primerApellido = txtPrimerApellido.Text;
                string segundoApellido = txtSegundoApellido.Text;

                Administrador administrador = new Administrador(identificacion, nombre, primerApellido, segundoApellido, fechaNacimiento, fechaIngreso);
                cln_Administrador.AgregarAdministrador(administrador);

                MessageBox.Show("Administrador registrado correctamente.", "Información", MessageBoxButtons.OK, MessageBoxIcon.Information);

                // Limpieza de campos
                txtIdentificacion.Clear();
                txtNombre.Clear();
                txtPrimerApellido.Clear();
                txtSegundoApellido.Clear();

                // Recarga de administradores
                CargarAdministradores();
            }
            catch (FormatException)
            {
                MessageBox.Show("El formato de algunos campos es incorrecto. Por favor, verifique.", "Error de formato", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    }
}

