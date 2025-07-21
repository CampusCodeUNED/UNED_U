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
    public partial class FrmArticulo : Form
    {
        private CLN_Articulo cln_Articulo; // Instancia de la lógica de negocio para artículos
        private CLN_Categoria cln_Categoria; // Instancia de la lógica de negocio para categorías

        public FrmArticulo()
        {
            InitializeComponent();
            cln_Articulo = CLN_Articulo.Instancia(); // Inicializa la lógica de negocio de artículos
            cln_Categoria = CLN_Categoria.Instancia(); // Inicializa la lógica de negocio de categorías
            ConfigurarDataGridView(); // Configura el DataGridView
            CargarArticulos(); // Carga los artículos existentes
            CargarCategoriasEnComboBox(); // Carga las categorías en el ComboBox
        }

        private void btnAgregarArticulo_Click(object sender, EventArgs e)
        {
            try
            {
                // Validaciones de campos
                if (string.IsNullOrWhiteSpace(txtIdArticulo.Text))
                {
                    MessageBox.Show("El campo Id de artículo es obligatorio.", "Error de validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return; // Sale si el Id está vacío
                }
                if (string.IsNullOrWhiteSpace(txtDescripcion.Text))
                {
                    MessageBox.Show("El campo Descripción es obligatorio.", "Error de validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return; // Sale si la descripción está vacía
                }
                if (string.IsNullOrWhiteSpace(txtMarca.Text))
                {
                    MessageBox.Show("El campo Marca es obligatorio.", "Error de validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return; // Sale si la marca está vacía
                }
                if (cmbCategorias.SelectedIndex == -1)
                {
                    MessageBox.Show("Debe seleccionar una categoría para el artículo.", "Error de validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return; // Sale si no se selecciona una categoría
                }
                if (cmbActivo.SelectedIndex == -1)
                {
                    MessageBox.Show("Debe seleccionar si el artículo está activo.", "Error de validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return; // Sale si no se selecciona el estado activo
                }

                // Validación del Id como número entero
                if (!int.TryParse(txtIdArticulo.Text, out int id))
                {
                    MessageBox.Show("El campo Id de artículo debe ser un número entero.", "Error de validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return; // Sale si el Id no es un número entero
                }

                // Verifica si el Id o la descripción ya existen
                if (cln_Articulo.ExisteIdArticulo(id))
                {
                    MessageBox.Show("Ya existe un articulo con ese Id.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return; // Sale si el Id ya existe
                }

                if (cln_Articulo.ExisteDescripcionArticulo(txtDescripcion.Text))
                {
                    MessageBox.Show("Ya existe un articulo con esa descripción.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return; // Sale si la descripción ya existe
                }

                // Obtiene los valores de los campos
                string descripcion = txtDescripcion.Text;
                string marca = txtMarca.Text;
                Categoria categoriaSeleccionada = (Categoria)cmbCategorias.SelectedItem; // Obtiene la categoría seleccionada
                bool activo = cmbActivo.SelectedItem.ToString() == "Sí"; // Obtiene el estado activo

                // Agrega el artículo
                cln_Articulo.AgregarArticulo(id, descripcion, categoriaSeleccionada, marca, activo);

                // Limpia los campos
                txtIdArticulo.Clear();
                txtDescripcion.Clear();
                txtMarca.Clear();
                cmbCategorias.SelectedIndex = -1;
                cmbActivo.SelectedIndex = -1;

                MessageBox.Show("El registro del artículo se ha ingresado correctamente."); // Mensaje de éxito

                CargarArticulos(); // Recarga los artículos
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Ha ocurrido un error inesperado: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error); // Manejo de errores
            }
        }

        private void ConfigurarDataGridView()
        {
            // Configuración del DataGridView
            dgvArticulos.AllowUserToResizeColumns = false; // No permite redimensionar columnas
            dgvArticulos.AllowUserToResizeRows = false; // No permite redimensionar filas
            dgvArticulos.ReadOnly = true; // Establece como solo lectura

            dgvArticulos.Columns.Clear(); // Limpia columnas existentes
            dgvArticulos.Columns.Add("IdArticulo", "ID"); // Agrega columna ID
            dgvArticulos.Columns.Add("Descripcion", "Descripción"); // Agrega columna Descripción
            dgvArticulos.Columns.Add("Categoria", "Categoría"); // Agrega columna Categoría
            dgvArticulos.Columns.Add("Marca", "Marca"); // Agrega columna Marca
            dgvArticulos.Columns.Add("Activo", "Activo"); // Agrega columna Activo
        }

        private void CargarArticulos()
        {
            dgvArticulos.Rows.Clear(); // Limpia filas existentes
            var articulos = cln_Articulo.ObtenerArticulos(); // Obtiene la lista de artículos

            // Agrega artículos al DataGridView
            foreach (var articulo in articulos)
            {
                if (articulo != null)
                {
                    dgvArticulos.Rows.Add(articulo.Id, articulo.Descripcion, articulo.CategoriaArticulo.NombreCategoria, articulo.Marca, articulo.Activo ? "Sí" : "No");
                }
            }
        }

        private void CargarCategoriasEnComboBox()
        {
            try
            {
                cmbCategorias.Items.Clear(); // Limpia categorías existentes
                var categorias = cln_Categoria.ObtenerCategorias(); // Obtiene la lista de categorías

                // Agrega categorías al ComboBox
                foreach (var categoria in categorias)
                {
                    if (categoria != null)
                    {
                        cmbCategorias.Items.Add(categoria);
                    }
                }

                cmbCategorias.DisplayMember = "NombreCategoria"; // Establece el nombre a mostrar
                cmbCategorias.ValueMember = "IdCategoria"; // Establece el valor a usar
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error al cargar las categorías: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error); // Manejo de errores
            }
        }
    }
}
