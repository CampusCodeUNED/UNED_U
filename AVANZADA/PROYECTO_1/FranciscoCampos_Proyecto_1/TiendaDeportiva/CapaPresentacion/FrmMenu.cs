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
using System.Windows.Forms;

namespace TiendaDeportiva.CapaPresentacion
{
    public partial class FrmMenu : Form
    {
        public FrmMenu()
        {
            InitializeComponent(); // Inicializa los componentes de la forma
            this.StartPosition = FormStartPosition.CenterScreen; // Centra la ventana en la pantalla
            this.MaximizeBox = false; // Desactiva el botón de maximizar
        }

        // Método para abrir un formulario en el panel principal.
        private void abrirForm(object form)
        {
            // Si ya hay un formulario en el panel, lo elimina
            if (this.panel2.Controls.Count > 0)
            {
                this.panel2.Controls.RemoveAt(0);
            }

            // Configuración del nuevo formulario
            Form form1 = form as Form; // Convierte el objeto a Form
            form1.TopLevel = false; // Establece el formulario como no principal
            form1.FormBorderStyle = FormBorderStyle.None; // Elimina el borde del formulario
            form1.Dock = DockStyle.Fill; // Hace que el formulario ocupe todo el panel
            this.panel2.Controls.Add(form1); // Agrega el formulario al panel
            this.panel2.Tag = form1; // Guarda el formulario en el tag del panel
            form1.Show(); // Muestra el formulario
        }

        // Eventos para los botones que abren diferentes formularios
        private void btnCategorias_Click(object sender, System.EventArgs e)
        {
            abrirForm(new FrmCategoria()); // Abre el formulario de categorías
        }

        private void btnArticulos_Click(object sender, System.EventArgs e)
        {
            abrirForm(new FrmArticulo()); // Abre el formulario de artículos
        }

        private void btnAdministradores_Click(object sender, System.EventArgs e)
        {
            abrirForm(new FrmAdministrador()); // Abre el formulario de administradores
        }

        private void BtnSucursales_Click(object sender, System.EventArgs e)
        {
            abrirForm(new FrmSucursal()); // Abre el formulario de sucursales
        }

        private void btnClientes_Click(object sender, System.EventArgs e)
        {
            abrirForm(new FrmCliente()); // Abre el formulario de clientes
        }

        private void btnArticulosSucursal_Click(object sender, System.EventArgs e)
        {
            abrirForm(new FrmArticuloSucursal()); // Abre el formulario de artículos por sucursal
        }
    }
}

