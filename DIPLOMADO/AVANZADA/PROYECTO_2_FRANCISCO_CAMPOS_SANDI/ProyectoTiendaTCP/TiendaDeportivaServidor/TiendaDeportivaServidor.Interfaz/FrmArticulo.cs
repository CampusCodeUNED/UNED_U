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
    public partial class FrmArticulo : Form
    {
        private readonly LN_Articulo _lnArticulo;
        private readonly LN_Categoria _lnCategoria;
        public FrmArticulo()
        {
            InitializeComponent();//Inicializar los componentes del formulario
            this.StartPosition = FormStartPosition.CenterScreen;//Centrar el formulario en la pantalla
            this.MaximizeBox = false;//Deshabilitar el botón de maximizar
            this.FormBorderStyle = FormBorderStyle.FixedSingle;//

            _lnArticulo = new LN_Articulo();
            _lnCategoria = new LN_Categoria();
        }

        private void FrmArticulo_Load(object sender, EventArgs e)
        {
            // Cargar las categorías de productos en el combo box
            List<Categoria> categorias = _lnCategoria.ObtenerCategorias();
            cmbCategoria.DataSource = categorias;
            cmbCategoria.DisplayMember = "Nombre";
            cmbCategoria.ValueMember = "Id";

            // Agregar opciones de activación para el artículo
            cmbActivo.Items.Add("Sí");
            cmbActivo.Items.Add("No");

            // Cargar los artículos existentes al iniciar el formulario
            CargarArticulos();
        }

        private void CargarArticulos()
        {
            // Consultar los artículos desde la base de datos y mostrarlos en el DataGridView
            List<Articulo> articulos = _lnArticulo.ObtenerArticulos();

            // Limpiar los datos previos
            dataGridArticulos.DataSource = null;
            dataGridArticulos.DataSource = articulos;

            // Limpiar las columnas del DataGridView y agregar las nuevas
            dataGridArticulos.Columns.Clear();
            dataGridArticulos.Columns.Add("Id", "ID");
            dataGridArticulos.Columns["Id"].DataPropertyName = "Id";

            dataGridArticulos.Columns.Add("Descripcion", "Descripción");
            dataGridArticulos.Columns["Descripcion"].DataPropertyName = "Descripcion";

            dataGridArticulos.Columns.Add("CategoriaNombre", "Categoría");
            dataGridArticulos.Columns["CategoriaNombre"].DataPropertyName = "CategoriaNombre";

            dataGridArticulos.Columns.Add("Marca", "Marca");
            dataGridArticulos.Columns["Marca"].DataPropertyName = "Marca";

            dataGridArticulos.Columns.Add("ActivoTexto", "Activo");
            dataGridArticulos.Columns["ActivoTexto"].DataPropertyName = "ActivoTexto";

            // Ajustar el tamaño de las columnas
            foreach (DataGridViewColumn column in dataGridArticulos.Columns)
            {
                column.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            }
        }

        private void LimpiarCampos()
        {
            // Limpiar los campos de entrada de datos
            txtIdArticulo.Clear();
            txtDescripcion.Clear();
            txtMarca.Clear();
            cmbCategoria.SelectedIndex = -1;
            cmbActivo.SelectedIndex = -1;
        }

        private void btnAgregarArticulo_Click_1(object sender, EventArgs e)
        {
            // Validar los datos de entrada antes de registrar el artículo
            if (!int.TryParse(txtIdArticulo.Text, out int idArticulo))
            {
                MessageBox.Show("Ingrese un ID de artículo válido (solo números).", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                txtIdArticulo.Focus();
                return;
            }

            if (string.IsNullOrWhiteSpace(txtDescripcion.Text))
            {
                MessageBox.Show("Ingrese una descripción válida para el artículo.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                txtDescripcion.Focus();
                return;
            }

            if (string.IsNullOrWhiteSpace(txtMarca.Text))
            {
                MessageBox.Show("Ingrese una marca válida para el artículo.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                txtMarca.Focus();
                return;
            }

            if (cmbCategoria.SelectedItem == null)
            {
                MessageBox.Show("Seleccione una categoría para el artículo.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                cmbCategoria.Focus();
                return;
            }

            if (cmbActivo.SelectedItem == null)
            {
                MessageBox.Show("Seleccione si el artículo está activo o no.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                cmbActivo.Focus();
                return;
            }

            // Crear el objeto Artículo con los datos validados
            Articulo articulo = new Articulo
            {
                Id = idArticulo,
                Descripcion = txtDescripcion.Text,
                Marca = txtMarca.Text,
                Categoria = (Categoria)cmbCategoria.SelectedItem,
                Activo = cmbActivo.SelectedItem.ToString() == "Sí"
            };

            // Registrar el artículo en la base de datos
            bool registrado = _lnArticulo.RegistrarArticulo(articulo);

            // Mostrar mensaje de éxito o error dependiendo de si el artículo fue registrado correctamente
            if (registrado)
            {
                MessageBox.Show("Artículo registrado exitosamente.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
                CargarArticulos();
                LimpiarCampos();
            }
            else
            {
                MessageBox.Show("El ID del artículo o la combinación de descripción y marca ya existen. Ingrese otros valores.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    }
}
