namespace TiendaDeportivaCliente.Interfaz
{
    partial class FrmCliente
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FrmCliente));
            this.backgroundWorker1 = new System.ComponentModel.BackgroundWorker();
            this.panel1 = new System.Windows.Forms.Panel();
            this.btnConsultarReserva = new System.Windows.Forms.Button();
            this.btnReservaArticulo = new System.Windows.Forms.Button();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.btnDesconectar = new System.Windows.Forms.Button();
            this.btnConectarse = new System.Windows.Forms.Button();
            this.txtIdentificacion = new System.Windows.Forms.TextBox();
            this.lblNombreCliente = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.SuspendLayout();
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.Color.LightSkyBlue;
            this.panel1.Controls.Add(this.btnConsultarReserva);
            this.panel1.Controls.Add(this.btnReservaArticulo);
            this.panel1.Controls.Add(this.pictureBox1);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Left;
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(233, 258);
            this.panel1.TabIndex = 4;
            // 
            // btnConsultarReserva
            // 
            this.btnConsultarReserva.FlatAppearance.MouseOverBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(128)))), ((int)(((byte)(255)))), ((int)(((byte)(128)))));
            this.btnConsultarReserva.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnConsultarReserva.Font = new System.Drawing.Font("Century Gothic", 11.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnConsultarReserva.Image = ((System.Drawing.Image)(resources.GetObject("btnConsultarReserva.Image")));
            this.btnConsultarReserva.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnConsultarReserva.Location = new System.Drawing.Point(0, 178);
            this.btnConsultarReserva.Name = "btnConsultarReserva";
            this.btnConsultarReserva.Size = new System.Drawing.Size(200, 38);
            this.btnConsultarReserva.TabIndex = 6;
            this.btnConsultarReserva.Text = "Consultar Reserva";
            this.btnConsultarReserva.UseVisualStyleBackColor = true;
            this.btnConsultarReserva.Click += new System.EventHandler(this.btnConsultarReserva_Click);
            // 
            // btnReservaArticulo
            // 
            this.btnReservaArticulo.FlatAppearance.MouseOverBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(128)))), ((int)(((byte)(255)))), ((int)(((byte)(128)))));
            this.btnReservaArticulo.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnReservaArticulo.Font = new System.Drawing.Font("Century Gothic", 11.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnReservaArticulo.Image = ((System.Drawing.Image)(resources.GetObject("btnReservaArticulo.Image")));
            this.btnReservaArticulo.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnReservaArticulo.Location = new System.Drawing.Point(0, 112);
            this.btnReservaArticulo.Name = "btnReservaArticulo";
            this.btnReservaArticulo.Size = new System.Drawing.Size(200, 38);
            this.btnReservaArticulo.TabIndex = 5;
            this.btnReservaArticulo.Text = "Reservar Artículo";
            this.btnReservaArticulo.UseVisualStyleBackColor = true;
            this.btnReservaArticulo.Click += new System.EventHandler(this.btnReservaArticulo_Click);
            // 
            // pictureBox1
            // 
            this.pictureBox1.Image = ((System.Drawing.Image)(resources.GetObject("pictureBox1.Image")));
            this.pictureBox1.Location = new System.Drawing.Point(12, 12);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(172, 71);
            this.pictureBox1.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
            this.pictureBox1.TabIndex = 0;
            this.pictureBox1.TabStop = false;
            // 
            // btnDesconectar
            // 
            this.btnDesconectar.Location = new System.Drawing.Point(605, 49);
            this.btnDesconectar.Name = "btnDesconectar";
            this.btnDesconectar.Size = new System.Drawing.Size(93, 23);
            this.btnDesconectar.TabIndex = 5;
            this.btnDesconectar.Text = "Desconectarse";
            this.btnDesconectar.UseVisualStyleBackColor = true;
            this.btnDesconectar.Click += new System.EventHandler(this.btnDesconectar_Click);
            // 
            // btnConectarse
            // 
            this.btnConectarse.Location = new System.Drawing.Point(508, 49);
            this.btnConectarse.Name = "btnConectarse";
            this.btnConectarse.Size = new System.Drawing.Size(93, 23);
            this.btnConectarse.TabIndex = 6;
            this.btnConectarse.Text = "Conectarse";
            this.btnConectarse.UseVisualStyleBackColor = true;
            this.btnConectarse.Click += new System.EventHandler(this.btnConectarse_ClickAsync);
            // 
            // txtIdentificacion
            // 
            this.txtIdentificacion.Location = new System.Drawing.Point(402, 51);
            this.txtIdentificacion.Name = "txtIdentificacion";
            this.txtIdentificacion.Size = new System.Drawing.Size(100, 20);
            this.txtIdentificacion.TabIndex = 7;
            // 
            // lblNombreCliente
            // 
            this.lblNombreCliente.AutoSize = true;
            this.lblNombreCliente.Location = new System.Drawing.Point(261, 51);
            this.lblNombreCliente.Name = "lblNombreCliente";
            this.lblNombreCliente.Size = new System.Drawing.Size(108, 13);
            this.lblNombreCliente.TabIndex = 8;
            this.lblNombreCliente.Text = "Cliente no conectado";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(402, 32);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(70, 13);
            this.label1.TabIndex = 9;
            this.label1.Text = "Identificación";
            // 
            // FrmCliente
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(710, 258);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.lblNombreCliente);
            this.Controls.Add(this.txtIdentificacion);
            this.Controls.Add(this.btnConectarse);
            this.Controls.Add(this.btnDesconectar);
            this.Controls.Add(this.panel1);
            this.Name = "FrmCliente";
            this.Text = "FrmCliente";
            this.panel1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.ComponentModel.BackgroundWorker backgroundWorker1;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Button btnConsultarReserva;
        private System.Windows.Forms.Button btnReservaArticulo;
        private System.Windows.Forms.PictureBox pictureBox1;
        private System.Windows.Forms.Button btnDesconectar;
        private System.Windows.Forms.Button btnConectarse;
        private System.Windows.Forms.TextBox txtIdentificacion;
        private System.Windows.Forms.Label lblNombreCliente;
        private System.Windows.Forms.Label label1;
    }
}