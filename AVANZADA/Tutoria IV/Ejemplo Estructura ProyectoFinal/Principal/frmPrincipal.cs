using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using ServidorTCP;
using ClienteTCP;

namespace Principal
{
    public partial class frmPrincipal : Form
    {
        bool ServidorIniciado = false;
        public frmPrincipal()
        {
            InitializeComponent();
        }

        private void btnIniciarServidor_Click(object sender, EventArgs e)
        {
            if (!ServidorIniciado)
            {
                ServidorIniciado = true;
                frmServidor servidor = new frmServidor();
                servidor.Show();

            }
            else
            {
                MessageBox.Show("No se puede inciair mas de un servidor");
            }

        }

        private void btnIniciarCliete_Click(object sender, EventArgs e)
        {
            if (ServidorIniciado)
            {
                frmCliente cliente = new frmCliente();
                cliente.Show();

            }
            else
            {
                MessageBox.Show("No hay un servidor iniciado");
            }
            
        }
    }
}
