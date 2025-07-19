namespace BibiliotecaCliente.Interfaz
{
    partial class frmAgregarAutor
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.grbCrearLibro = new System.Windows.Forms.GroupBox();
            this.txtSegundoApellido = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.txtPrimerApellido = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.btnCrearAutor = new System.Windows.Forms.Button();
            this.txtNombre = new System.Windows.Forms.TextBox();
            this.lblTitulo = new System.Windows.Forms.Label();
            this.txtIdAutor = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.grbCrearLibro.SuspendLayout();
            this.SuspendLayout();
            // 
            // grbCrearLibro
            // 
            this.grbCrearLibro.Controls.Add(this.txtSegundoApellido);
            this.grbCrearLibro.Controls.Add(this.label3);
            this.grbCrearLibro.Controls.Add(this.txtPrimerApellido);
            this.grbCrearLibro.Controls.Add(this.label2);
            this.grbCrearLibro.Controls.Add(this.btnCrearAutor);
            this.grbCrearLibro.Controls.Add(this.txtNombre);
            this.grbCrearLibro.Controls.Add(this.lblTitulo);
            this.grbCrearLibro.Controls.Add(this.txtIdAutor);
            this.grbCrearLibro.Controls.Add(this.label1);
            this.grbCrearLibro.Location = new System.Drawing.Point(15, 12);
            this.grbCrearLibro.Name = "grbCrearLibro";
            this.grbCrearLibro.Size = new System.Drawing.Size(401, 279);
            this.grbCrearLibro.TabIndex = 3;
            this.grbCrearLibro.TabStop = false;
            this.grbCrearLibro.Text = "Crear un nuevo libro";
            // 
            // txtSegundoApellido
            // 
            this.txtSegundoApellido.Location = new System.Drawing.Point(19, 161);
            this.txtSegundoApellido.Name = "txtSegundoApellido";
            this.txtSegundoApellido.Size = new System.Drawing.Size(365, 20);
            this.txtSegundoApellido.TabIndex = 4;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(23, 145);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(90, 13);
            this.label3.TabIndex = 12;
            this.label3.Text = "Segundo Apellido";
            // 
            // txtPrimerApellido
            // 
            this.txtPrimerApellido.Location = new System.Drawing.Point(19, 112);
            this.txtPrimerApellido.Name = "txtPrimerApellido";
            this.txtPrimerApellido.Size = new System.Drawing.Size(365, 20);
            this.txtPrimerApellido.TabIndex = 3;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(23, 96);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(76, 13);
            this.label2.TabIndex = 10;
            this.label2.Text = "Primer Apellido";
            // 
            // btnCrearAutor
            // 
            this.btnCrearAutor.Location = new System.Drawing.Point(159, 211);
            this.btnCrearAutor.Name = "btnCrearAutor";
            this.btnCrearAutor.Size = new System.Drawing.Size(75, 23);
            this.btnCrearAutor.TabIndex = 5;
            this.btnCrearAutor.Text = "Crear Autor";
            this.btnCrearAutor.UseVisualStyleBackColor = true;
            this.btnCrearAutor.Click += new System.EventHandler(this.btnCrearAutor_Click);
            // 
            // txtNombre
            // 
            this.txtNombre.AcceptsReturn = true;
            this.txtNombre.Location = new System.Drawing.Point(159, 55);
            this.txtNombre.Name = "txtNombre";
            this.txtNombre.Size = new System.Drawing.Size(225, 20);
            this.txtNombre.TabIndex = 2;
            // 
            // lblTitulo
            // 
            this.lblTitulo.AutoSize = true;
            this.lblTitulo.Location = new System.Drawing.Point(163, 39);
            this.lblTitulo.Name = "lblTitulo";
            this.lblTitulo.Size = new System.Drawing.Size(44, 13);
            this.lblTitulo.TabIndex = 3;
            this.lblTitulo.Text = "Nombre";
            // 
            // txtIdAutor
            // 
            this.txtIdAutor.Location = new System.Drawing.Point(19, 55);
            this.txtIdAutor.Name = "txtIdAutor";
            this.txtIdAutor.Size = new System.Drawing.Size(121, 20);
            this.txtIdAutor.TabIndex = 1;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(16, 39);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(65, 13);
            this.label1.TabIndex = 1;
            this.label1.Text = "Identificador";
            // 
            // frmAgregarAutor
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(433, 311);
            this.Controls.Add(this.grbCrearLibro);
            this.Name = "frmAgregarAutor";
            this.Text = "Agregar autor";
            this.grbCrearLibro.ResumeLayout(false);
            this.grbCrearLibro.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.GroupBox grbCrearLibro;
        private System.Windows.Forms.TextBox txtSegundoApellido;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox txtPrimerApellido;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Button btnCrearAutor;
        private System.Windows.Forms.TextBox txtNombre;
        private System.Windows.Forms.Label lblTitulo;
        private System.Windows.Forms.TextBox txtIdAutor;
        private System.Windows.Forms.Label label1;
    }
}