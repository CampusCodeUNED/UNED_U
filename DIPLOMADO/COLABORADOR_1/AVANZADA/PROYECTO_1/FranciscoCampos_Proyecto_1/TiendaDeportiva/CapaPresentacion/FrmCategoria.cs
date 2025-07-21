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
    public partial class FrmCategoria : Form
    {
        private CLN_Categoria CLN_Categoria; // Clase para la lógica de categorías

        public FrmCategoria()
        {
            InitializeComponent();
            CLN_Categoria = CLN_Categoria.Instancia(); // Inicializar la clase de lógica de categoría
            ConfigurarDataGridView(); // Configurar el DataGridView
            CargarCategorias(); // Cargar categorías en el DataGridView
        }

        private void btnAgregarCategoria_Click(object sender, EventArgs e)
        {
            // Manejar la adición de una nueva categoría
            try
            {
                // Validar que el campo Id no esté vacío
                if (string.IsNullOrWhiteSpace(txtIdCategoria.Text))
                {
                    MessageBox.Show("El campo Id de categoría es obligatorio.", "Error de validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return;
                }

                // Validar que el campo Nombre no esté vacío
                if (string.IsNullOrWhiteSpace(txtNombreCategoria.Text))
                {
                    MessageBox.Show("El campo Nombre de categoría es obligatorio.", "Error de validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return;
                }

                // Validar que el campo Descripción no esté vacío
                if (string.IsNullOrWhiteSpace(txtDescripcion.Text))
                {
                    MessageBox.Show("El campo Descripción es obligatorio.", "Error de validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return;
                }

                // Validar que el Id sea un número entero
                if (!int.TryParse(txtIdCategoria.Text, out int id))
                {
                    MessageBox.Show("El campo Id de categoría debe ser un número entero.", "Error de validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return;
                }

                // Comprobar si ya existe una categoría con ese Id
                if (CLN_Categoria.ExisteIdCategoria(id))
                {
                    MessageBox.Show("Ya existe una categoria con ese Id.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return;
                }

                // Comprobar si ya existe una categoría con ese nombre
                if (CLN_Categoria.ExisteNombreCategoria(txtNombreCategoria.Text))
                {
                    MessageBox.Show("Ya existe una categoria con ese nombre.", "Validación", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return;
                }

                // Crear una nueva categoría
                string nombre = txtNombreCategoria.Text;
                string descripcion = txtDescripcion.Text;
                Categoria nueva_categoria = new Categoria(id, nombre, descripcion);

                // Agregar la nueva categoría
                CLN_Categoria.AgregarCategoria(nueva_categoria);

                // Limpiar los campos de entrada
                txtIdCategoria.Clear();
                txtNombreCategoria.Clear();
                txtDescripcion.Clear();

                MessageBox.Show("El registro se ha ingresado correctamente.");
                CargarCategorias(); // Recargar las categorías
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error: {ex.Message}");
            }
        }

        private void CargarCategorias()
        {
            // Limpiar el DataGridView y cargar las categorías
            dataGridViewCategorias.Rows.Clear();
            var categorias = CLN_Categoria.ObtenerCategorias();

            foreach (var categoria in categorias)
            {
                if (categoria != null)
                {
                    // Agregar cada categoría al DataGridView
                    dataGridViewCategorias.Rows.Add(categoria.IdCategoria, categoria.NombreCategoria, categoria.Descripcion);
                }
            }
        }

        private void ConfigurarDataGridView()
        {
            // Configurar el DataGridView para mostrar categorías
            dataGridViewCategorias.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill;
            dataGridViewCategorias.AutoSizeRowsMode = DataGridViewAutoSizeRowsMode.AllCells;
            dataGridViewCategorias.AllowUserToResizeColumns = false;
            dataGridViewCategorias.AllowUserToResizeRows = false;
            dataGridViewCategorias.ReadOnly = true;

            dataGridViewCategorias.Columns.Clear(); // Limpiar columnas existentes
            dataGridViewCategorias.Columns.Add("IdCategoria", "ID"); // Agregar columna de ID
            dataGridViewCategorias.Columns.Add("NombreCategoria", "Nombre"); // Agregar columna de Nombre
            dataGridViewCategorias.Columns.Add("Descripcion", "Descripción"); // Agregar columna de Descripción
        }
    }
}
