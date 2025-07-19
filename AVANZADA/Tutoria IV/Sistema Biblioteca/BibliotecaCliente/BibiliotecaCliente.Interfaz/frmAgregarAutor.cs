using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using BibiliotecaServidor.Entidades;

namespace BibiliotecaCliente.Interfaz
{
    public partial class frmAgregarAutor : Form
    {
        public frmAgregarAutor()
        {
            InitializeComponent();
        }

        private void btnCrearAutor_Click(object sender, EventArgs e)
        {
            if (!(txtIdAutor.Text.Equals(string.Empty) || txtNombre.Text.Equals(string.Empty)
                || txtPrimerApellido.Text.Equals(string.Empty) || txtSegundoApellido.Text.Equals(string.Empty)))
            {
                try
                {
                    //Se crea una nueva instancia del objeto Autor
                    Autor nuevoAutor = new Autor();

                    //Se realiza la asignacion de los valores en las propiedades correspondientes
                    nuevoAutor.IdAutor = txtIdAutor.Text;
                    nuevoAutor.Nombre = txtNombre.Text;
                    nuevoAutor.PrimerApellido = txtPrimerApellido.Text;
                    nuevoAutor.SegundoApellido = txtSegundoApellido.Text;

                    //Se agrega el nuevo Autor mediante la conexión tcp              
                    bool agregaAutor = ClienteTCP.AgregarAutor(nuevoAutor);                    

                    if (agregaAutor)
                    {
                        //Se limpian los TextBox para un nuevo ingreso de información
                        txtIdAutor.Clear();
                        txtNombre.Clear();
                        txtPrimerApellido.Clear();
                        txtSegundoApellido.Clear();

                        MessageBox.Show("Autor agregado correctamente", "Información", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    }                   

                }
                catch (Exception ex)
                {

                    MessageBox.Show("Ha ocurrido un error al crear el Autor " + ex.Message, "Atención", MessageBoxButtons.OK, MessageBoxIcon.Error);
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
