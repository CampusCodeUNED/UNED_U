namespace BibiliotecaCliente.Interfaz
{
    partial class frmAgregarLibro
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
            this.label4 = new System.Windows.Forms.Label();
            this.cmbAutores = new System.Windows.Forms.ComboBox();
            this.btnCrearLibro = new System.Windows.Forms.Button();
            this.txtNumeroEdicion = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.txtDescripcion = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.txtTitulo = new System.Windows.Forms.TextBox();
            this.lblTitulo = new System.Windows.Forms.Label();
            this.txtISBN = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.grbCrearLibro.SuspendLayout();
            this.SuspendLayout();
            // 
            // grbCrearLibro
            // 
            this.grbCrearLibro.Controls.Add(this.label4);
            this.grbCrearLibro.Controls.Add(this.cmbAutores);
            this.grbCrearLibro.Controls.Add(this.btnCrearLibro);
            this.grbCrearLibro.Controls.Add(this.txtNumeroEdicion);
            this.grbCrearLibro.Controls.Add(this.label3);
            this.grbCrearLibro.Controls.Add(this.txtDescripcion);
            this.grbCrearLibro.Controls.Add(this.label2);
            this.grbCrearLibro.Controls.Add(this.txtTitulo);
            this.grbCrearLibro.Controls.Add(this.lblTitulo);
            this.grbCrearLibro.Controls.Add(this.txtISBN);
            this.grbCrearLibro.Controls.Add(this.label1);
            this.grbCrearLibro.Location = new System.Drawing.Point(12, 12);
            this.grbCrearLibro.Name = "grbCrearLibro";
            this.grbCrearLibro.Size = new System.Drawing.Size(401, 412);
            this.grbCrearLibro.TabIndex = 2;
            this.grbCrearLibro.TabStop = false;
            this.grbCrearLibro.Text = "Crear un nuevo libro";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(18, 29);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(32, 13);
            this.label4.TabIndex = 11;
            this.label4.Text = "Autor";
            // 
            // cmbAutores
            // 
            this.cmbAutores.FormattingEnabled = true;
            this.cmbAutores.Location = new System.Drawing.Point(21, 45);
            this.cmbAutores.Name = "cmbAutores";
            this.cmbAutores.Size = new System.Drawing.Size(121, 21);
            this.cmbAutores.TabIndex = 10;
            // 
            // btnCrearLibro
            // 
            this.btnCrearLibro.Location = new System.Drawing.Point(147, 370);
            this.btnCrearLibro.Name = "btnCrearLibro";
            this.btnCrearLibro.Size = new System.Drawing.Size(75, 23);
            this.btnCrearLibro.TabIndex = 9;
            this.btnCrearLibro.Text = "Crear Libro";
            this.btnCrearLibro.UseVisualStyleBackColor = true;
            this.btnCrearLibro.Click += new System.EventHandler(this.btnCrearLibro_Click);
            // 
            // txtNumeroEdicion
            // 
            this.txtNumeroEdicion.Location = new System.Drawing.Point(21, 300);
            this.txtNumeroEdicion.Name = "txtNumeroEdicion";
            this.txtNumeroEdicion.Size = new System.Drawing.Size(121, 20);
            this.txtNumeroEdicion.TabIndex = 8;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(18, 284);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(96, 13);
            this.label3.TabIndex = 7;
            this.label3.Text = "Número de edición";
            // 
            // txtDescripcion
            // 
            this.txtDescripcion.Location = new System.Drawing.Point(21, 172);
            this.txtDescripcion.Multiline = true;
            this.txtDescripcion.Name = "txtDescripcion";
            this.txtDescripcion.Size = new System.Drawing.Size(365, 92);
            this.txtDescripcion.TabIndex = 6;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(18, 156);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(63, 13);
            this.label2.TabIndex = 5;
            this.label2.Text = "Descripción";
            // 
            // txtTitulo
            // 
            this.txtTitulo.Location = new System.Drawing.Point(161, 115);
            this.txtTitulo.Name = "txtTitulo";
            this.txtTitulo.Size = new System.Drawing.Size(225, 20);
            this.txtTitulo.TabIndex = 4;
            // 
            // lblTitulo
            // 
            this.lblTitulo.AutoSize = true;
            this.lblTitulo.Location = new System.Drawing.Point(165, 99);
            this.lblTitulo.Name = "lblTitulo";
            this.lblTitulo.Size = new System.Drawing.Size(35, 13);
            this.lblTitulo.TabIndex = 3;
            this.lblTitulo.Text = "Título";
            // 
            // txtISBN
            // 
            this.txtISBN.Location = new System.Drawing.Point(21, 115);
            this.txtISBN.Name = "txtISBN";
            this.txtISBN.Size = new System.Drawing.Size(121, 20);
            this.txtISBN.TabIndex = 2;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(18, 99);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(32, 13);
            this.label1.TabIndex = 1;
            this.label1.Text = "ISBN";
            // 
            // frmAgregarLibro
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(425, 448);
            this.Controls.Add(this.grbCrearLibro);
            this.Name = "frmAgregarLibro";
            this.Text = "Agregar libro";
            this.grbCrearLibro.ResumeLayout(false);
            this.grbCrearLibro.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.GroupBox grbCrearLibro;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.ComboBox cmbAutores;
        private System.Windows.Forms.Button btnCrearLibro;
        private System.Windows.Forms.TextBox txtNumeroEdicion;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox txtDescripcion;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox txtTitulo;
        private System.Windows.Forms.Label lblTitulo;
        private System.Windows.Forms.TextBox txtISBN;
        private System.Windows.Forms.Label label1;
    }
}