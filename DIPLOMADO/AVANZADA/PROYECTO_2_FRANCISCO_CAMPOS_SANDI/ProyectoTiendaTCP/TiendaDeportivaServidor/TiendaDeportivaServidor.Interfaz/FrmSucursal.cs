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
    public partial class FrmSucursal : Form
    {
        private readonly LN_Sucursal _lnSucursal;//Se crea un objeto de la clase LN_Sucursal
        private readonly LN_Administrador _lnAdministrador;//Se crea un objeto de la clase LN_Administrador

        public FrmSucursal()
        {
            InitializeComponent();
            this.StartPosition = FormStartPosition.CenterScreen;//Se centra la ventana en la pantalla
            this.MaximizeBox = false;//Se deshabilita el botón maximizar
            this.FormBorderStyle = FormBorderStyle.FixedSingle;//Se deshabilita el cambio de tamaño de la ventana   

            _lnSucursal = new LN_Sucursal();//Se instancia el objeto _lnSucursal
            _lnAdministrador = new LN_Administrador();//Se instancia el objeto _lnAdministrador

        }

        private void FrmSucursal_Load(object sender, EventArgs e)
        {
            List<Administrador> administradores = _lnAdministrador.ObtenerAdministradores();//Se obtiene la lista de administradores
            cmbAdministrador.DataSource = administradores;//Se asigna la lista de administradores al combobox
            cmbAdministrador.DisplayMember = "Nombre";//Se muestra el nombre del administrador
            cmbAdministrador.ValueMember = "Identificacion";//Se asigna el valor de la identificación del administrador

            cmbActivo.Items.Add("Sí");//Se agrega la opción "Sí" al combobox
            cmbActivo.Items.Add("No");//Se agrega la opción "No" al combobox

            CargarSucursales();//Se cargan las sucursales en el datagridview
        }
        //Método para cargar las sucursales en el datagridview
        private void CargarSucursales()
        {
            List<Sucursal> sucursales = _lnSucursal.ObtenerSucursales();//Se obtiene la lista de sucursales
            dataGridSucursales.DataSource = sucursales;//Se asigna la lista de sucursales al datagridview

            dataGridSucursales.Columns.Clear();//Limpia las columnas 

            dataGridSucursales.Columns.Add("Id", "ID");//Se agrega la columna ID al datagridview
            dataGridSucursales.Columns["Id"].DataPropertyName = "Id";//Se asigna el valor de la propiedad Id

            dataGridSucursales.Columns.Add("Nombre", "Nombre");//Se agrega la columna Nombre al datagridview
            dataGridSucursales.Columns["Nombre"].DataPropertyName = "Nombre";//Se asigna el valor de la propiedad Nombre

            dataGridSucursales.Columns.Add("NombreAdministrador", "Administrador");//Se agrega la columna Administrador al datagridview
            dataGridSucursales.Columns["NombreAdministrador"].DataPropertyName = "NombreAdministrador";//Se asigna el valor de la propiedad NombreAdministrador

            dataGridSucursales.Columns.Add("Direccion", "Dirección");//Se agrega la columna Dirección al datagridview
            dataGridSucursales.Columns["Direccion"].DataPropertyName = "Direccion";//Se asigna el valor de la propiedad Dirección

            dataGridSucursales.Columns.Add("Telefono", "Teléfono");//Se agrega la columna Teléfono al datagridview
            dataGridSucursales.Columns["Telefono"].DataPropertyName = "Telefono";//Se asigna el valor de la propiedad Teléfono

            dataGridSucursales.Columns.Add("ActivoTexto", "Activo");//Se agrega la columna Activo al datagridview
            dataGridSucursales.Columns["ActivoTexto"].DataPropertyName = "ActivoTexto";//Se asigna el valor de la propiedad ActivoTexto

            foreach (DataGridViewColumn column in dataGridSucursales.Columns)//Se ajusta el tamaño de las columnas
            {
                column.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            }
        }

        private void btnAgregarSucursal_Click(object sender, EventArgs e)
        {
            if (!int.TryParse(txtIdSucursal.Text, out int idSucursal))//Se valida que el ID de la sucursal sea un número
            {
                MessageBox.Show("Ingrese un ID de sucursal válido (solo números).", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                txtIdSucursal.Focus();//Se enfoca el campo ID de la sucursal
                return;
            }

            if (string.IsNullOrWhiteSpace(txtNombre.Text))//Se valida que el nombre de la sucursal no esté vacío
            {
                MessageBox.Show("Ingrese un nombre válido para la sucursal.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                txtNombre.Focus();
                return;
            }

            if (string.IsNullOrWhiteSpace(txtDireccion.Text))//Se valida que la dirección de la sucursal no esté vacía
            {
                MessageBox.Show("Ingrese una dirección válida para la sucursal.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                txtDireccion.Focus();
                return;
            }

            if (!long.TryParse(txtTelefono.Text, out long telefono) || txtTelefono.Text.Length != 8)//Se valida que el teléfono de la sucursal sea un número de 8 dígitos
            {
                MessageBox.Show("Ingrese un número de teléfono válido (8 dígitos).", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                txtTelefono.Focus();
                return;
            }

            if (cmbAdministrador.SelectedItem == null)//Se valida que se haya seleccionado un administrador para la sucursal
            {
                MessageBox.Show("Seleccione un administrador para la sucursal.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                cmbAdministrador.Focus();
                return;
            }

            if (cmbActivo.SelectedItem == null)//Se valida que se haya seleccionado si la sucursal está activa
            {
                MessageBox.Show("Seleccione si la sucursal está activa.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                cmbActivo.Focus();
                return;
            }
            // Crear instancia de Sucursal con datos ingresados
            Sucursal sucursal = new Sucursal
            {
                Id = idSucursal,
                Nombre = txtNombre.Text,
                Direccion = txtDireccion.Text,
                Telefono = txtTelefono.Text,
                Administrador = (Administrador)cmbAdministrador.SelectedItem,//Se asigna el administrador seleccionado
                Activo = cmbActivo.SelectedItem.ToString() == "Sí"
            };
            // Registro de sucursal y mensaje de éxito/error
            bool registrado = _lnSucursal.RegistrarSucursal(sucursal);

            if (registrado)
            {
                MessageBox.Show("Sucursal registrada exitosamente.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
                CargarSucursales();//Se cargan las sucursales en el datagridview
                LimpiarCampos();//Se limpian los campos
            }
            else
            {
                MessageBox.Show("La sucursal ya existe: verifique el ID o nombre.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
        //Método para limpiar los campos
        private void LimpiarCampos()
        {
            txtIdSucursal.Clear();
            txtNombre.Clear();
            txtDireccion.Clear();
            txtTelefono.Clear();
            cmbAdministrador.SelectedIndex = -1; //Reiniciar selección de Administrador
            cmbActivo.SelectedIndex = -1;//Reiniciar selección de Activo
        }
    }
}
