namespace TiendaDeportivaCliente.Interfaz
{
    partial class FrmReservaArticulo
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
            this.menuStrip2 = new System.Windows.Forms.MenuStrip();
            this.label2 = new System.Windows.Forms.Label();
            this.cmbSucursales = new System.Windows.Forms.ComboBox();
            this.dgvArticulos = new System.Windows.Forms.DataGridView();
            this.btnReservarArticulo = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.dtpFechaReserva = new System.Windows.Forms.DateTimePicker();
            ((System.ComponentModel.ISupportInitialize)(this.dgvArticulos)).BeginInit();
            this.SuspendLayout();
            // 
            // menuStrip2
            // 
            this.menuStrip2.AutoSize = false;
            this.menuStrip2.BackColor = System.Drawing.Color.LightSkyBlue;
            this.menuStrip2.Location = new System.Drawing.Point(0, 0);
            this.menuStrip2.Name = "menuStrip2";
            this.menuStrip2.Size = new System.Drawing.Size(599, 61);
            this.menuStrip2.TabIndex = 5;
            this.menuStrip2.Text = "menuStrip2";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.BackColor = System.Drawing.Color.LightSkyBlue;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 20.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.ForeColor = System.Drawing.Color.White;
            this.label2.Location = new System.Drawing.Point(154, 18);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(265, 31);
            this.label2.TabIndex = 26;
            this.label2.Text = "Reserva de Artículos";
            // 
            // cmbSucursales
            // 
            this.cmbSucursales.FormattingEnabled = true;
            this.cmbSucursales.Location = new System.Drawing.Point(29, 108);
            this.cmbSucursales.Name = "cmbSucursales";
            this.cmbSucursales.Size = new System.Drawing.Size(121, 21);
            this.cmbSucursales.TabIndex = 27;
            this.cmbSucursales.SelectedIndexChanged += new System.EventHandler(this.cboSucursales_SelectedIndexChanged);
            // 
            // dgvArticulos
            // 
            this.dgvArticulos.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvArticulos.Location = new System.Drawing.Point(29, 149);
            this.dgvArticulos.Name = "dgvArticulos";
            this.dgvArticulos.Size = new System.Drawing.Size(543, 150);
            this.dgvArticulos.TabIndex = 28;
            // 
            // btnReservarArticulo
            // 
            this.btnReservarArticulo.Location = new System.Drawing.Point(466, 101);
            this.btnReservarArticulo.Name = "btnReservarArticulo";
            this.btnReservarArticulo.Size = new System.Drawing.Size(106, 33);
            this.btnReservarArticulo.TabIndex = 29;
            this.btnReservarArticulo.Text = "Realizar Reserva";
            this.btnReservarArticulo.UseVisualStyleBackColor = true;
            this.btnReservarArticulo.Click += new System.EventHandler(this.btnReservarArticulo_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(26, 92);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(59, 13);
            this.label1.TabIndex = 30;
            this.label1.Text = "Sucursales";
            // 
            // dtpFechaReserva
            // 
            this.dtpFechaReserva.Location = new System.Drawing.Point(260, 109);
            this.dtpFechaReserva.Name = "dtpFechaReserva";
            this.dtpFechaReserva.Size = new System.Drawing.Size(200, 20);
            this.dtpFechaReserva.TabIndex = 31;
            // 
            // FrmReservaArticulo
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(599, 311);
            this.Controls.Add(this.dtpFechaReserva);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.btnReservarArticulo);
            this.Controls.Add(this.dgvArticulos);
            this.Controls.Add(this.cmbSucursales);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.menuStrip2);
            this.Name = "FrmReservaArticulo";
            this.Text = "FrmReservaArticulo";
            ((System.ComponentModel.ISupportInitialize)(this.dgvArticulos)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.MenuStrip menuStrip2;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.ComboBox cmbSucursales;
        private System.Windows.Forms.DataGridView dgvArticulos;
        private System.Windows.Forms.Button btnReservarArticulo;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.DateTimePicker dtpFechaReserva;
    }
}