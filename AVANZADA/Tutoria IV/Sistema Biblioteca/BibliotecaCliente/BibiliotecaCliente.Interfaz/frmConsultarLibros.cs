using BibiliotecaServidor.Entidades;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace BibiliotecaCliente.Interfaz
{
    public partial class frmConsultarLibros : Form
    {
        public frmConsultarLibros()
        {
            InitializeComponent();

            try
            {
                List<Autor> listaAutores = new List<Autor>();
                listaAutores = ClienteTCP.ObtenerAutores();// Se obtienen los autores mediante la conexión tcp

                cmbAutores.DataSource = listaAutores;
                cmbAutores.DisplayMember = "Nombre";
            }
            catch (Exception)
            {
                MessageBox.Show("Ha ocurrido un eror al realizar la acción solicitada, revise que el estado del servidor", "Atención", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }


        }

        private void cmbAutores_SelectedIndexChanged(object sender, EventArgs e)
        {
            try
            {
                // Se realiza la convsersión del item seleccionado al tipo autor
                Autor autorSeleccionado = (Autor)cmbAutores.SelectedItem;
                //Se obtienen los libros mediante la conexión tcp
                dgvLibros.DataSource = ClienteTCP.ObtenerLibrosDeAutor(autorSeleccionado.IdAutor);
            }
            catch (InvalidCastException)
            {

                MessageBox.Show("Ha ocurrido un eror al convertir el objeto seleccionado", "Conversión de datos", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            catch (Exception)
            {
                MessageBox.Show("Ha ocurrido un eror al realizar la acción solicitada, revise que el estado del servidor", "Atención", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    }
}
