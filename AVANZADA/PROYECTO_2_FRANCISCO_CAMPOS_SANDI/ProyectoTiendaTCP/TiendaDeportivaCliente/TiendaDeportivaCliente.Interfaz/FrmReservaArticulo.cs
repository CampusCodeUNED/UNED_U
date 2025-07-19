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
using System.Collections.Generic;//importamos la libreria para usar listas
using System.Windows.Forms;
using TiendaDeportivaCliente.Entidades;

namespace TiendaDeportivaCliente.Interfaz
{
    public partial class FrmReservaArticulo : Form
    {
        private ClienteTcp _clienteTcp;
        private int _identificacionCliente;

        public FrmReservaArticulo(ClienteTcp clienteTcp, int identificacionCliente)
        {
            InitializeComponent();//inicializamos los componentes del formulario
            this.StartPosition = FormStartPosition.CenterScreen;//centramos el formulario
            this.MaximizeBox = false;//deshabilitamos el boton maximizar
            this.FormBorderStyle = FormBorderStyle.FixedSingle;//deshabilitamos el cambio de tamaño del formulario

            _clienteTcp = clienteTcp;//asignamos el clienteTcp
            _identificacionCliente = identificacionCliente;//asignamos la identificacion del cliente

            CargarSucursales();//cargamos las sucursales
        }
        //Método para cargar las sucursales
        private void CargarSucursales()
        {// Envía solicitud para obtener la lista de sucursales activas desde el servidor
            string respuesta = _clienteTcp.EnviarMensaje("CONSULTAR_SUCURSALES_ACTIVAS");
            // Analiza la respuesta y obtiene una lista de sucursales
            List<Sucursal> sucursales = ParseSucursales(respuesta);//parseamos las sucursales
            // Configura el combo box para mostrar las sucursales activas
            cmbSucursales.DataSource = sucursales;
            cmbSucursales.DisplayMember = "Nombre";
            cmbSucursales.ValueMember = "Id";
            // Configura el DataGridView para mostrar los artículos disponibles
            foreach (DataGridViewColumn column in dgvArticulos.Columns)
            {// Ajusta el ancho de las columnas al contenido
                column.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            }
        }
        //Método para parsear las sucursales
        private List<Sucursal> ParseSucursales(string respuesta)
        {// Inicializa una lista para almacenar las sucursales procesadas
            List<Sucursal> sucursales = new List<Sucursal>();
            // Verifica que la respuesta tenga el formato esperado
            if (respuesta.StartsWith("OK:"))
            {   // Divide la respuesta en datos individuales de sucursales
                string[] datosSucursales = respuesta.Substring(3).Split('|');
                foreach (var dato in datosSucursales)
                {
                    string[] campos = dato.Split(',');// Divide cada dato de sucursal en sus respectivos campos

                    if (campos.Length >= 5)// Verifica que haya suficientes campos antes de procesar
                    {
                        try
                        {
                            sucursales.Add(new Sucursal
                            {
                                Id = int.Parse(campos[0]),// Convierte el ID de sucursal a entero
                                Nombre = campos[1],// Obtiene el nombre de la sucursal
                                Direccion = campos[2],// Obtiene la dirección de la sucursal
                                Telefono = campos[3],// Obtiene el teléfono de la sucursal
                                Activo = bool.Parse(campos[4])// Convierte el estado de la sucursal a booleano
                            });
                        }
                        catch (FormatException ex)
                        {
                            MessageBox.Show("Error al convertir datos de la sucursal: " + ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        }
                    }
                    else
                    {
                        MessageBox.Show("La respuesta del servidor contiene una sucursal con datos incompletos.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }
                }
            }
            else
            {
                MessageBox.Show("Error en la respuesta del servidor: " + respuesta, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }

            return sucursales;//retornamos la lista de sucursales
        }
        //Método para cargar los artículos disponibles
        private void CargarArticulosDisponibles(int idSucursal)
        {// Envía solicitud para obtener la lista de artículos disponibles en la sucursal
            string respuesta = _clienteTcp.EnviarMensaje($"CONSULTAR_ARTICULOS_SUCURSAL:{idSucursal}");
            // Analiza la respuesta y obtiene una lista de artículos disponibles
            List<ArticuloSucursal> articulos = ParseArticulos(respuesta);
            // Configura el DataGridView para mostrar los artículos disponibles
            if (dgvArticulos.Columns.Count == 0)
            {
                dgvArticulos.AutoGenerateColumns = false;
                // Agrega columnas a la tabla de artículos con nombres y propiedades específicas
                dgvArticulos.Columns.Add(new DataGridViewTextBoxColumn
                {
                    DataPropertyName = "IdArticulo",
                    HeaderText = "ID Artículo",
                    Name = "IdArticulo",
                    ReadOnly = true
                });
                dgvArticulos.Columns.Add(new DataGridViewTextBoxColumn
                {
                    DataPropertyName = "DescripcionArticulo",
                    HeaderText = "Descripción",
                    Name = "DescripcionArticulo",
                    ReadOnly = true
                });
                dgvArticulos.Columns.Add(new DataGridViewTextBoxColumn
                {
                    DataPropertyName = "MarcaArticulo",
                    HeaderText = "Marca",
                    Name = "MarcaArticulo",
                    ReadOnly = true
                });
                dgvArticulos.Columns.Add(new DataGridViewTextBoxColumn
                {
                    DataPropertyName = "Cantidad",
                    HeaderText = "Cantidad Disponible",
                    Name = "Cantidad",
                    ReadOnly = true
                });
            }

            dgvArticulos.DataSource = articulos;//asignamos la lista de articulos al DataGridView


        }
        //Método para parsear los artículos
        private List<ArticuloSucursal> ParseArticulos(string respuesta)
        {// Inicializa una lista para almacenar los artículos procesados
            List<ArticuloSucursal> articulos = new List<ArticuloSucursal>();
            // Verifica que la respuesta tenga el formato esperado
            if (respuesta.StartsWith("OK:"))
            {    // Divide la respuesta en datos individuales de artículo
                string[] datosArticulos = respuesta.Substring(3).Split('|');
                foreach (var dato in datosArticulos)
                {   // Divide cada dato de artículo en sus respectivos campos
                    string[] campos = dato.Split(',');
                    articulos.Add(new ArticuloSucursal//Crea un objeto ArticuloSucursal con los datos del articulo
                    {
                        Articulo = new Articulo
                        {
                            Id = int.Parse(campos[0]),// Convierte el ID de artículo a entero
                            Descripcion = campos[1],// Obtiene la descripción del artículo
                            Marca = campos[2],// Obtiene la marca del artículo
                            Activo = bool.Parse(campos[3])// Convierte el estado del artículo a booleano
                        },
                        Cantidad = int.Parse(campos[4])// Convierte la cantidad de artículo a entero
                    });
                }
            }
            return articulos;//retornamos la lista de articulos
        }
        //Evento para seleccionar una sucursal
        private void cboSucursales_SelectedIndexChanged(object sender, System.EventArgs e)
        {// Verifica que se haya seleccionado una sucursal
            if (cmbSucursales.SelectedItem != null)
            {// Obtiene la sucursal seleccionada
                Sucursal sucursalSeleccionada = (Sucursal)cmbSucursales.SelectedItem;
                CargarArticulosDisponibles(sucursalSeleccionada.Id);//cargamos los articulos disponibles
            }
        }
        //Evento para reservar un artículo
        private void btnReservarArticulo_Click(object sender, EventArgs e)
        {// Verifica que se haya seleccionado una sucursal y un artículo
            if (cmbSucursales.SelectedItem == null || dgvArticulos.SelectedRows.Count == 0)
            {
                MessageBox.Show("Seleccione una sucursal y un artículo.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            // Obtiene la sucursal y el artículo seleccionados
            Sucursal sucursalSeleccionada = (Sucursal)cmbSucursales.SelectedItem;
            ArticuloSucursal articuloSeleccionado = (ArticuloSucursal)dgvArticulos.SelectedRows[0].DataBoundItem;
            // Verifica que haya suficiente cantidad disponible para reservar
            if (articuloSeleccionado.Cantidad < 1)
            {
                MessageBox.Show("No hay suficiente cantidad disponible para realizar la reserva.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            // Obtiene la fecha de reserva seleccionada
            DateTime fechaReserva = dtpFechaReserva.Value;
            // Envía solicitud para realizar la reserva del artículo seleccionado
            string mensajeReserva = $"REALIZAR_RESERVA:{sucursalSeleccionada.Id},{articuloSeleccionado.Articulo.Id},{_identificacionCliente},{fechaReserva:yyyy-MM-dd HH:mm:ss}";
            // Obtiene la respuesta del servidor
            string respuesta = _clienteTcp.EnviarMensaje(mensajeReserva);
            // Muestra un mensaje de éxito o error según la respuesta
            if (respuesta == "OK:Reserva exitosa")
            {
                MessageBox.Show("Reserva realizada con éxito.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
                CargarArticulosDisponibles(sucursalSeleccionada.Id);
            }
            else
            {
                MessageBox.Show("Error al realizar la reserva. " + respuesta, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }


    }
}
