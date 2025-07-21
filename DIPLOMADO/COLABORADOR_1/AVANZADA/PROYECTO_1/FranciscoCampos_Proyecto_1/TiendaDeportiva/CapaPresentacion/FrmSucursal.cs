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
    public partial class FrmSucursal : Form
    {
        private CLN_Sucursal cln_Sucursal; // Clase lógica de negocio para sucursales
        private CLN_Administrador cln_Administrador; // Clase lógica de negocio para administradores

        public FrmSucursal()
        {
            cln_Sucursal = CLN_Sucursal.Instancia(); // Inicializa la clase de lógica de negocio para sucursales
            cln_Administrador = CLN_Administrador.Instancia(); // Inicializa la clase de lógica de negocio para administradores
            InitializeComponent(); // Inicializa los componentes de la forma
            ConfigurarDataGridView(); // Configura el DataGridView
            CargarSucursales(); // Carga las sucursales en el DataGridView
            CargarAdministradores(); // Carga los administradores en el combo box
        }

        private void ConfigurarDataGridView()
        {
            dgvSucursales.AllowUserToResizeColumns = false; // Desactiva el redimensionamiento de columnas
            dgvSucursales.AllowUserToResizeRows = false; // Desactiva el redimensionamiento de filas
            dgvSucursales.ReadOnly = true; // Hace el DataGridView de solo lectura

            dgvSucursales.Columns.Clear(); // Limpia las columnas existentes
            dgvSucursales.Columns.Add("IdSucursal", "ID Sucursal"); // Agrega columna para ID de sucursal
            dgvSucursales.Columns.Add("Nombre", "Nombre"); // Agrega columna para nombre
            dgvSucursales.Columns.Add("Administrador", "Administrador"); // Agrega columna para administrador
            dgvSucursales.Columns.Add("Direccion", "Dirección"); // Agrega columna para dirección
            dgvSucursales.Columns.Add("Telefono", "Teléfono"); // Agrega columna para teléfono
            dgvSucursales.Columns.Add("Activo", "Activo"); // Agrega columna para estado activo
        }

        private void CargarSucursales()
        {
            dgvSucursales.Rows.Clear(); // Limpia las filas del DataGridView

            var sucursales = cln_Sucursal.ObtenerSucursales(); // Obtiene la lista de sucursales
            foreach (var sucursal in sucursales)
            {
                if (sucursal != null) // Verifica que la sucursal no sea nula
                {
                    // Agrega la sucursal al DataGridView
                    dgvSucursales.Rows.Add(sucursal.Id, sucursal.Nombre,
                        sucursal.Administrador.Nombre,
                        sucursal.Direccion, sucursal.Telefono,
                        sucursal.Activo ? "Sí" : "No");
                }
            }
        }

        private void CargarAdministradores()
        {
            try
            {
                cmbAdministradores.Items.Clear(); // Limpia los elementos del combo box
                var administradores = cln_Administrador.ObtenerAdministradores(); // Obtiene la lista de administradores

                foreach (var admin in administradores)
                {
                    if (admin != null) // Verifica que el administrador no sea nulo
                    {
                        cmbAdministradores.Items.Add(admin); // Agrega el administrador al combo box
                    }
                }

                cmbAdministradores.DisplayMember = "Nombre"; // Establece la propiedad para mostrar
                cmbAdministradores.ValueMember = "Identificacion"; // Establece la propiedad para el valor
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error al cargar las categorías: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void btnAgregarSucursal_Click(object sender, EventArgs e)
        {
            try
            {
                // Validaciones para los campos del formulario
                if (string.IsNullOrWhiteSpace(txtId.Text))
                {
                    MessageBox.Show("El campo 'ID Sucursal' es obligatorio.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    txtId.Focus();
                    return;
                }

                if (!int.TryParse(txtId.Text, out int idSucursal))
                {
                    MessageBox.Show("El ID Sucursal debe ser un número válido.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    txtId.Focus();
                    return;
                }

                if (string.IsNullOrWhiteSpace(txtNombre.Text))
                {
                    MessageBox.Show("El campo 'Nombre' es obligatorio.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    txtNombre.Focus();
                    return;
                }

                if (cmbAdministradores.SelectedIndex == -1)
                {
                    MessageBox.Show("Por favor, seleccione un administrador.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    cmbAdministradores.Focus();
                    return;
                }

                if (string.IsNullOrWhiteSpace(txtDireccion.Text))
                {
                    MessageBox.Show("El campo 'Dirección' es obligatorio.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    txtDireccion.Focus();
                    return;
                }

                if (string.IsNullOrWhiteSpace(txtTelefono.Text))
                {
                    MessageBox.Show("El campo 'Teléfono' es obligatorio.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    txtTelefono.Focus();
                    return;
                }

                if (cmbActivo.SelectedIndex == -1)
                {
                    MessageBox.Show("Por favor, seleccione si la sucursal está activa.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    cmbActivo.Focus();
                    return;
                }

                // Validaciones para existencia de ID y nombre
                if (cln_Sucursal.ExisteIdSucursal(idSucursal))
                {
                    MessageBox.Show("Ya existe una sucursal con ese Id.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return;
                }

                if (cln_Sucursal.ExisteNombreSucursal(txtNombre.Text))
                {
                    MessageBox.Show("Ya existe una sucursal con ese nombre.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return;
                }

                // Obtiene datos del formulario para crear la nueva sucursal
                string nombre = txtNombre.Text;
                Administrador administrador = (Administrador)cmbAdministradores.SelectedItem; // Obtiene el administrador seleccionado
                string direccion = txtDireccion.Text;
                string telefono = txtTelefono.Text;
                bool activo = cmbActivo.SelectedItem.ToString() == "Sí"; // Verifica si está activo

                // Crea y agrega la nueva sucursal
                Sucursal nuevaSucursal = new Sucursal(idSucursal, nombre, administrador, direccion, telefono, activo);
                cln_Sucursal.AgregarSucursal(nuevaSucursal);

                // Limpia los campos del formulario
                txtId.Clear();
                txtNombre.Clear();
                txtDireccion.Clear();
                txtTelefono.Clear();
                cmbAdministradores.SelectedIndex = -1; // Resetea el combo box
                cmbActivo.SelectedIndex = -1; // Resetea el combo box

                MessageBox.Show("La sucursal se ha agregado correctamente.");
                CargarSucursales(); // Recarga la lista de sucursales
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error: {ex.Message}"); // Manejo de excepciones
            }
        }
    }
}
