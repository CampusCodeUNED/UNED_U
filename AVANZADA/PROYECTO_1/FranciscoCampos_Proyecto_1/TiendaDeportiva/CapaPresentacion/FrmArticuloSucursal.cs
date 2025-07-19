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
    public partial class FrmArticuloSucursal : Form
    {
        private CLN_Sucursal clnSucursal; // Clase para la lógica de sucursales
        private CLN_Articulo clnArticulo; // Clase para la lógica de artículos
        private CLN_ArticuloSucursal clnArticuloxSucursal; // Clase para la lógica de artículos por sucursal

        public FrmArticuloSucursal()
        {
            InitializeComponent();

            // Configuración de los DataGridView
            ConfigurarDataGridViewArticulos();
            ConfigurarDataGridViewArticulosxSucursal();

            // Inicialización de las clases
            clnSucursal = CLN_Sucursal.Instancia();
            clnArticulo = CLN_Articulo.Instancia();
            clnArticuloxSucursal = CLN_ArticuloSucursal.Instancia();

            // Cargar sucursales y artículos
            CargarSucursales();
            CargarArticulos();
            ActualizarGridArticulosxSucursal();
        }

        private void ConfigurarDataGridViewArticulosxSucursal()
        {
            // Configuración del DataGridView para artículos por sucursal
            dataGridViewArticulosxSucursal.Columns.Clear();
            dataGridViewArticulosxSucursal.Columns.Add("Sucursal", "Sucursal");
            dataGridViewArticulosxSucursal.Columns.Add("Articulo", "Artículo");
            dataGridViewArticulosxSucursal.Columns.Add("Cantidad", "Cantidad");

            dataGridViewArticulosxSucursal.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill;
        }

        private void ConfigurarDataGridViewArticulos()
        {
            // Configuración del DataGridView para artículos
            dgvArticulos.Columns.Clear();
            dgvArticulos.Columns.Add("Id", "ID");
            dgvArticulos.Columns.Add("Descripcion", "Descripción");
            dgvArticulos.Columns.Add("Categoria", "Categoría");
            dgvArticulos.Columns.Add("Marca", "Marca");

            dgvArticulos.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill;
        }

        private void CargarSucursales()
        {
            // Cargar las sucursales activas en el ComboBox
            cmbSucursales.Items.Clear();
            var sucursales = clnSucursal.ObtenerSucursalesActivas();
            foreach (var sucursal in sucursales)
            {
                if (sucursal != null)
                {
                    cmbSucursales.Items.Add(sucursal);
                }
            }

            // Comprobar si hay sucursales activas
            if (cmbSucursales.Items.Count == 0)
            {
                MessageBox.Show("No hay sucursales activas disponibles.");
                cmbSucursales.Enabled = false;
            }
        }

        private void CargarArticulos()
        {
            // Cargar los artículos activos en el DataGridView
            dgvArticulos.Rows.Clear();
            var articulos = clnArticulo.ObtenerArticulosActivos();
            foreach (var articulo in articulos)
            {
                if (articulo != null)
                {
                    dgvArticulos.Rows.Add(articulo.Id, articulo.Descripcion, articulo.CategoriaArticulo.NombreCategoria, articulo.Marca);
                }
            }

            // Comprobar si hay artículos activos
            if (dgvArticulos.Rows.Count == 0)
            {
                MessageBox.Show("No hay artículos activos disponibles.");
                dgvArticulos.Enabled = false;
            }
        }

        private void btnAgregarArticuloSucursal_Click(object sender, EventArgs e)
        {
            // Manejar la adición de artículos a sucursales
            try
            {
                if (cmbSucursales.SelectedItem == null)
                {
                    MessageBox.Show("Debe seleccionar una sucursal.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }

                if (dgvArticulos.SelectedRows.Count == 0)
                {
                    MessageBox.Show("Debe seleccionar al menos un artículo.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }

                // Validar la cantidad ingresada
                int cantidad;
                if (!int.TryParse(txtCantidad.Text, out cantidad) || cantidad <= 0)
                {
                    MessageBox.Show("Debe ingresar una cantidad válida mayor a cero.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }

                var sucursalSeleccionada = (Sucursal)cmbSucursales.SelectedItem;

                foreach (DataGridViewRow row in dgvArticulos.SelectedRows)
                {
                    int idArticulo = Convert.ToInt32(row.Cells[0].Value);
                    Articulo articuloSeleccionado = clnArticulo.ObtenerArticuloPorId(idArticulo);

                    if (articuloSeleccionado != null)
                    {
                        // Verificar si el artículo ya está asociado a la sucursal
                        if (clnArticuloxSucursal.ExisteArticuloxSucursal(sucursalSeleccionada.Id, articuloSeleccionado.Id))
                        {
                            MessageBox.Show($"El artículo {articuloSeleccionado.Descripcion} ya está asociado a la sucursal {sucursalSeleccionada.Nombre}.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                            return;
                        }
                        else
                        {
                            // Agregar el artículo a la sucursal
                            clnArticuloxSucursal.AgregarArticuloxSucursal(sucursalSeleccionada, articuloSeleccionado, cantidad);
                        }
                    }
                }

                MessageBox.Show("Artículos registrados correctamente.", "Información", MessageBoxButtons.OK, MessageBoxIcon.Information);
                ActualizarGridArticulosxSucursal();
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void ActualizarGridArticulosxSucursal()
        {
            // Actualizar el DataGridView de artículos por sucursal
            dataGridViewArticulosxSucursal.Rows.Clear();
            var listaArticulosXSucursal = clnArticuloxSucursal.ObtenerArticulosXSucursal();

            foreach (var item in listaArticulosXSucursal)
            {
                if (item != null)
                {
                    dataGridViewArticulosxSucursal.Rows.Add(item.Sucursal.Nombre, item.Articulo.Descripcion, item.Cantidad);
                }
            }
        }
    }
}

