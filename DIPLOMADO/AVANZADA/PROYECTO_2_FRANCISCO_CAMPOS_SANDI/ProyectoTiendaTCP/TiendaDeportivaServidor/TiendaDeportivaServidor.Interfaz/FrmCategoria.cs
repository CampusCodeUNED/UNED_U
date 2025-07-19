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
using System.Collections.Generic;
using System.Windows.Forms;
using TiendaDeportivaServidor.Datos;
using TiendaDeportivaServidor.Entidades;
using TiendaDeportivaServidor.LogicaNegocio;

namespace TiendaDeportivaServidor.Interfaz
{
    public partial class FrmCategoria : Form
    {
        private readonly LN_Categoria _lnCategoria;

        public FrmCategoria()
        {
            InitializeComponent();
            this.StartPosition = FormStartPosition.CenterScreen; // Centra la ventana en la pantalla
            this.MaximizeBox = false; // Desactiva la opción de maximizar la ventana
            this.FormBorderStyle = FormBorderStyle.FixedSingle; // Establece que la ventana no podrá cambiar de tamaño

            _lnCategoria = new LN_Categoria();//Instanciar la clase Database
        }

        private void FrmCategoria_Load(object sender, System.EventArgs e)
        {
            CargarCategorias(); // Carga las categorías desde la base de datos al DataGrid
        }

        private void CargarCategorias()
        {
            List<Categoria> categorias = _lnCategoria.ObtenerCategorias(); // Consulta las categorías de la base de datos
            dataGridCategorias.DataSource = categorias; // Establece la fuente de datos del DataGrid con las categorías

            foreach (DataGridViewColumn column in dataGridCategorias.Columns)
            {
                column.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill; // Ajusta el tamaño de las columnas para llenar el espacio
            }
        }

        private void btnAgregarCategoria_Click(object sender, System.EventArgs e)
        {
            // Verifica si el ID de la categoría es un número válido
            if (!int.TryParse(txtIdCategoria.Text, out int idCategoria))
            {
                MessageBox.Show("Ingrese un ID de categoría válido (solo números).", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                txtIdCategoria.Focus(); // Enfoca el campo de ID de categoría
                return; // Sale del método si la validación falla
            }

            // Verifica si el nombre de la categoría no está vacío
            if (string.IsNullOrWhiteSpace(txtNombre.Text))
            {
                MessageBox.Show("Ingrese un nombre válido para la categoría.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                txtNombre.Focus(); // Enfoca el campo de nombre de la categoría
                return;
            }

            // Verifica si la descripción de la categoría no está vacía
            if (string.IsNullOrWhiteSpace(txtDescripcion.Text))
            {
                MessageBox.Show("Ingrese una descripción válida para la categoría.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                txtDescripcion.Focus(); // Enfoca el campo de descripción de la categoría
                return;
            }

            // Crea una nueva instancia de la clase Categoria con los valores ingresados
            Categoria categoria = new Categoria
            {
                Id = idCategoria,
                Nombre = txtNombre.Text,
                Descripcion = txtDescripcion.Text
            };

            // Intenta registrar la nueva categoría en la base de datos
            bool registrado = _lnCategoria.RegistrarCategoria(categoria);

            if (registrado)
            {
                MessageBox.Show("Categoría registrada exitosamente.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
                CargarCategorias(); // Recarga las categorías en el DataGrid
                LimpiarCampos(); // Limpia los campos de entrada
            }
            else
            {
                MessageBox.Show("El ID o el nombre de la categoría ya existe. Ingrese otro ID o nombre.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        // Limpia los campos de entrada de la interfaz
        private void LimpiarCampos()
        {
            txtIdCategoria.Clear(); 
            txtNombre.Clear(); 
            txtDescripcion.Clear(); 
        }
    }
}
