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
    public partial class FrmArticuloSucursal : Form
    {
        private readonly LN_Articulo _lnArticulo;
        private readonly LN_Sucursal _lnSucursal;
        private readonly LN_ArticuloSucursal _lnArticuloSucursal;

        public FrmArticuloSucursal()
        {
            InitializeComponent(); // Inicializa los componentes de la interfaz
            this.StartPosition = FormStartPosition.CenterScreen; // Centra el formulario en la pantalla
            this.MaximizeBox = false; // Desactiva el botón de maximizar
            this.FormBorderStyle = FormBorderStyle.FixedSingle; // Establece un borde fijo

            _lnArticulo = new LN_Articulo();
            _lnSucursal = new LN_Sucursal();
            _lnArticuloSucursal = new LN_ArticuloSucursal();
        }

        private void FrmArticuloSucursal_Load(object sender, EventArgs e)
        {
            // Carga las sucursales activas y las asigna al comboBox de sucursales
            List<Sucursal> sucursales = _lnSucursal.ObtenerSucursales();
            cmbSucursal.DataSource = sucursales.FindAll(s => s.Activo);
            cmbSucursal.DisplayMember = "Nombre";
            cmbSucursal.ValueMember = "Id";

            // Carga los artículos activos y los asigna al DataGrid
            List<Articulo> articulos = _lnArticulo.ObtenerArticulos();
            dataGridArticulos.DataSource = articulos.FindAll(a => a.Activo);
            ConfigurarColumnasDataGrid(); // Configura las columnas del DataGrid

            // Carga los artículos asociados a sucursales
            CargarArticulosPorSucursal();
        }

        private void ConfigurarColumnasDataGrid()
        {
            // Limpia las columnas existentes
            dataGridArticulos.Columns.Clear();

            // Añade las columnas necesarias al DataGrid
            dataGridArticulos.Columns.Add("Id", "ID");
            dataGridArticulos.Columns["Id"].DataPropertyName = "Id";

            dataGridArticulos.Columns.Add("Descripcion", "Descripción");
            dataGridArticulos.Columns["Descripcion"].DataPropertyName = "Descripcion";

            dataGridArticulos.Columns.Add("CategoriaNombre", "Categoría");
            dataGridArticulos.Columns["CategoriaNombre"].DataPropertyName = "CategoriaNombre";

            dataGridArticulos.Columns.Add("Marca", "Marca");
            dataGridArticulos.Columns["Marca"].DataPropertyName = "Marca";

            // Ajusta el tamaño de las columnas al contenido
            foreach (DataGridViewColumn column in dataGridArticulos.Columns)
            {
                column.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            }
        }

        private void CargarArticulosPorSucursal()
        {
            // Consulta los artículos por sucursal y los carga en el DataGrid
            List<ArticuloSucursal> articulosPorSucursal = _lnArticuloSucursal.ObtenerArticulosPorSucursal();
            dataGridArticulosSucursal.DataSource = null;
            dataGridArticulosSucursal.DataSource = articulosPorSucursal;

            // Limpia y configura las columnas del DataGrid
            dataGridArticulosSucursal.Columns.Clear();

            dataGridArticulosSucursal.Columns.Add("NombreSucursal", "Sucursal");
            dataGridArticulosSucursal.Columns["NombreSucursal"].DataPropertyName = "NombreSucursal";

            dataGridArticulosSucursal.Columns.Add("DescripcionArticulo", "Artículo");
            dataGridArticulosSucursal.Columns["DescripcionArticulo"].DataPropertyName = "DescripcionArticulo";

            dataGridArticulosSucursal.Columns.Add("Cantidad", "Cantidad");
            dataGridArticulosSucursal.Columns["Cantidad"].DataPropertyName = "Cantidad";

            // Ajusta el tamaño de las columnas al contenido
            foreach (DataGridViewColumn column in dataGridArticulosSucursal.Columns)
            {
                column.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            }
        }

        private void btnAgregarArticuloSucursal_Click(object sender, EventArgs e)
        {
            // Valida que se haya seleccionado una sucursal y al menos un artículo
            if (cmbSucursal.SelectedItem == null || dataGridArticulos.SelectedRows.Count == 0)
            {
                MessageBox.Show("Por favor, seleccione una sucursal y al menos un artículo.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            // Valida que la cantidad ingresada sea un número entero mayor que cero
            if (!int.TryParse(txtCantidad.Text, out int cantidad) || cantidad <= 0)
            {
                MessageBox.Show("La cantidad debe ser un número entero mayor a cero.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            Sucursal sucursal = (Sucursal)cmbSucursal.SelectedItem; // Obtiene la sucursal seleccionada

            // Recorre los artículos seleccionados y los asocia a la sucursal
            foreach (DataGridViewRow row in dataGridArticulos.SelectedRows)
            {
                Articulo articulo = (Articulo)row.DataBoundItem;

                ArticuloSucursal articuloSucursal = new ArticuloSucursal
                {
                    Sucursal = sucursal,
                    Articulo = articulo,
                    Cantidad = cantidad
                };

                // Intenta registrar el artículo asociado a la sucursal
                bool registrado = _lnArticuloSucursal.RegistrarArticulosPorSucursal(articuloSucursal);

                if (!registrado)
                {
                    MessageBox.Show($"El artículo {articulo.Descripcion} ya está asociado a la sucursal.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }
            }

            // Actualiza la lista de artículos asociados a sucursales
            CargarArticulosPorSucursal();
            MessageBox.Show("Artículos asociados exitosamente.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
            LimpiarCampos(); // Limpia los campos después de agregar
        }

        private void LimpiarCampos()
        {
            // Limpia la selección y los campos de texto
            cmbSucursal.SelectedIndex = -1;
            txtCantidad.Clear();
            dataGridArticulos.ClearSelection();
        }
    }
}
