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
    public partial class frmAgregarLibro : Form
    {
        public frmAgregarLibro()
        {
            InitializeComponent();

            //Se cargan los autores en el combobox
            cmbAutores.DataSource = ClienteTCP.ObtenerAutores();//Se invoca el método de la conexión tcp
            cmbAutores.DisplayMember = "Nombre";
        }

        private void btnCrearLibro_Click(object sender, EventArgs e)
        {
            if (!(txtISBN.Text.Equals(string.Empty) || txtTitulo.Text.Equals(string.Empty) || txtDescripcion.Text.Equals(string.Empty)
              || txtNumeroEdicion.Text.Equals(string.Empty) || cmbAutores.SelectedItem == null))
            {
                try
                {
                    //Se crea una nueva instancia del objeto Libro
                    Libro nuevoLibro = new Libro();

                    //Se realiza la asignacion de los valores en las propiedades correspondientes
                    nuevoLibro.ISBN = txtISBN.Text;
                    nuevoLibro.Titulo = txtTitulo.Text;
                    nuevoLibro.Descripcion = txtDescripcion.Text;
                    nuevoLibro.NumeroEdicion = Convert.ToInt32(txtNumeroEdicion.Text);
                    nuevoLibro.IdAutor = ((Autor)cmbAutores.SelectedItem).IdAutor;

                    //Se agrega el nuevo libro mediante la conexión tcp
                    bool agregalibro = ClienteTCP.AgregarLibro(nuevoLibro);

                    if (agregalibro)
                    {
                        //Se limpian los TextBox para un nuevo ingreso de información
                        txtISBN.Clear();
                        txtTitulo.Clear();
                        txtDescripcion.Clear();
                        txtNumeroEdicion.Clear();

                        MessageBox.Show("Libro agregado correctamente", "Información", MessageBoxButtons.OK, MessageBoxIcon.Information);

                    }
                }
                catch (FormatException)
                {
                    MessageBox.Show("El número de edición no pueden ser caracteres", "Atención", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                }
                catch (Exception ex)
                {

                    MessageBox.Show("Ha ocurrido un error al crear el libro " + ex.Message, "Atención", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }

            }
            else
            {
                //En caso de que los datos no estén completos se muestra un mensaje de advertencia
                //Se debe tener cuidado que no parezca un mensaje de error, dado que no es un error
                MessageBox.Show("Debe completar todos los campos!", "Atención", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }
        }
    }
}
