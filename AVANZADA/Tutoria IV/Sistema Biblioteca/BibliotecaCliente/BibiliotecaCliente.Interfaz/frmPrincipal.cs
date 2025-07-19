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
    public partial class frmPrincipal : Form
    {
        bool clienteConectado = false;
        public frmPrincipal()
        {
            InitializeComponent();
            lblEstado.ForeColor = Color.Red;
            btnDesconectar.Enabled = false;
            btnConectar.Enabled = true;
        }

        private void btnConectar_Click(object sender, EventArgs e)
        {
            if (!(txtIdentificador.Text.Equals(string.Empty)))
            {
                if (ClienteTCP.Conectar(txtIdentificador.Text))
                {
                    lblEstado.Text = "Conectado al servidor... en (127.0.0.1, 30000)";
                    lblEstado.ForeColor = Color.Green;
                    clienteConectado = true;
                    btnConectar.Enabled = false;
                    btnDesconectar.Enabled = true;
                    txtIdentificador.ReadOnly = true;
                }
                else
                {
                    MessageBox.Show("Verifique que el servidor esté escuchando clientes...", "No es posible conectarse", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                }
            }
            else
            {                
                MessageBox.Show("Debe ingresar el identificador del cliente", "Atención", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }           
        }

        private void btnDesconectar_Click(object sender, EventArgs e)
        {
            ClienteTCP.Desconectar(txtIdentificador.Text);

            lblEstado.Text = "Desconectado";
            lblEstado.ForeColor = Color.Red;
            btnDesconectar.Enabled = false;
            btnConectar.Enabled = true;
            clienteConectado = false;
            txtIdentificador.ReadOnly = false;
        }

        private void btnAgregarAutor_Click(object sender, EventArgs e)
        {
            if (clienteConectado)
            {
                frmAgregarAutor formularioAgregarAutor = new frmAgregarAutor();
                formularioAgregarAutor.StartPosition = FormStartPosition.CenterScreen;
                formularioAgregarAutor.ShowDialog();
            }
            else
            {
                MessageBox.Show("Debe conectarse primero con el servidor", "No es posible realizar la acción solicitada", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }
          
        }

        private void btnAgregarLibro_Click(object sender, EventArgs e)
        {
            if (clienteConectado)
            {
                frmAgregarLibro formularioAgregarLibro = new frmAgregarLibro();
                formularioAgregarLibro.StartPosition = FormStartPosition.CenterScreen;
                formularioAgregarLibro.ShowDialog();               
            }
            else
            {
                MessageBox.Show("Debe conectarse primero con el servidor", "No es posible realizar la acción solicitada", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }            
        }

        private void btnConsultarLibros_Click(object sender, EventArgs e)
        {
            if (clienteConectado)
            {
                frmConsultarLibros formularioConsultarLibros = new frmConsultarLibros();
                formularioConsultarLibros.StartPosition = FormStartPosition.CenterScreen;
                formularioConsultarLibros.ShowDialog();
            }
            else
            {
                MessageBox.Show("Debe conectarse primero con el servidor", "No es posible realizar la acción solicitada", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }            
        }
    }
}
