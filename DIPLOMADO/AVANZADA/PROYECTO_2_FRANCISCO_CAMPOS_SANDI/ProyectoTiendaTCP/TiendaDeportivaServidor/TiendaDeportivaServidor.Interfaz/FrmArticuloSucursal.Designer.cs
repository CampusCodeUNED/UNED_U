namespace TiendaDeportivaServidor.Interfaz
{
    partial class FrmArticuloSucursal
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
            this.label1 = new System.Windows.Forms.Label();
            this.dataGridArticulosSucursal = new System.Windows.Forms.DataGridView();
            this.cmbSucursal = new System.Windows.Forms.ComboBox();
            this.label4 = new System.Windows.Forms.Label();
            this.btnAgregarArticuloSucursal = new System.Windows.Forms.Button();
            this.txtCantidad = new System.Windows.Forms.TextBox();
            this.dataGridArticulos = new System.Windows.Forms.DataGridView();
            this.label3 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridArticulosSucursal)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridArticulos)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Century Gothic", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(26, 279);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(167, 17);
            this.label1.TabIndex = 65;
            this.label1.Text = "Listado Artículos Sucursal";
            // 
            // dataGridArticulosSucursal
            // 
            this.dataGridArticulosSucursal.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridArticulosSucursal.Location = new System.Drawing.Point(29, 299);
            this.dataGridArticulosSucursal.Name = "dataGridArticulosSucursal";
            this.dataGridArticulosSucursal.Size = new System.Drawing.Size(560, 140);
            this.dataGridArticulosSucursal.TabIndex = 64;
            // 
            // cmbSucursal
            // 
            this.cmbSucursal.FormattingEnabled = true;
            this.cmbSucursal.Location = new System.Drawing.Point(29, 89);
            this.cmbSucursal.Name = "cmbSucursal";
            this.cmbSucursal.Size = new System.Drawing.Size(140, 21);
            this.cmbSucursal.TabIndex = 63;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Century Gothic", 20.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(203, 21);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(236, 33);
            this.label4.TabIndex = 62;
            this.label4.Text = "Artículos Sucursal";
            // 
            // btnAgregarArticuloSucursal
            // 
            this.btnAgregarArticuloSucursal.FlatAppearance.MouseOverBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(45)))), ((int)(((byte)(45)))), ((int)(((byte)(48)))));
            this.btnAgregarArticuloSucursal.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnAgregarArticuloSucursal.Font = new System.Drawing.Font("Century Gothic", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnAgregarArticuloSucursal.Location = new System.Drawing.Point(467, 83);
            this.btnAgregarArticuloSucursal.Name = "btnAgregarArticuloSucursal";
            this.btnAgregarArticuloSucursal.Size = new System.Drawing.Size(122, 27);
            this.btnAgregarArticuloSucursal.TabIndex = 61;
            this.btnAgregarArticuloSucursal.Text = "Agregar";
            this.btnAgregarArticuloSucursal.UseVisualStyleBackColor = true;
            this.btnAgregarArticuloSucursal.Click += new System.EventHandler(this.btnAgregarArticuloSucursal_Click);
            // 
            // txtCantidad
            // 
            this.txtCantidad.Font = new System.Drawing.Font("Century Gothic", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtCantidad.Location = new System.Drawing.Point(184, 87);
            this.txtCantidad.Name = "txtCantidad";
            this.txtCantidad.Size = new System.Drawing.Size(140, 23);
            this.txtCantidad.TabIndex = 60;
            // 
            // dataGridArticulos
            // 
            this.dataGridArticulos.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridArticulos.Location = new System.Drawing.Point(29, 123);
            this.dataGridArticulos.Name = "dataGridArticulos";
            this.dataGridArticulos.Size = new System.Drawing.Size(560, 140);
            this.dataGridArticulos.TabIndex = 59;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Century Gothic", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(26, 69);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(59, 17);
            this.label3.TabIndex = 58;
            this.label3.Text = "Sucursal";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Century Gothic", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(181, 69);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(71, 17);
            this.label2.TabIndex = 57;
            this.label2.Text = "Cantidad";
            // 
            // FrmArticuloSucursal
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(614, 461);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.dataGridArticulosSucursal);
            this.Controls.Add(this.cmbSucursal);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.btnAgregarArticuloSucursal);
            this.Controls.Add(this.txtCantidad);
            this.Controls.Add(this.dataGridArticulos);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Name = "FrmArticuloSucursal";
            this.Text = "FrmArticuloSucursal";
            this.Load += new System.EventHandler(this.FrmArticuloSucursal_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridArticulosSucursal)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridArticulos)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.DataGridView dataGridArticulosSucursal;
        private System.Windows.Forms.ComboBox cmbSucursal;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Button btnAgregarArticuloSucursal;
        private System.Windows.Forms.TextBox txtCantidad;
        private System.Windows.Forms.DataGridView dataGridArticulos;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label2;
    }
}