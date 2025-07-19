namespace Principal
{
    partial class frmPrincipal
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
            this.btnIniciarCliete = new System.Windows.Forms.Button();
            this.btnIniciarServidor = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // btnIniciarCliete
            // 
            this.btnIniciarCliete.Location = new System.Drawing.Point(426, 214);
            this.btnIniciarCliete.Name = "btnIniciarCliete";
            this.btnIniciarCliete.Size = new System.Drawing.Size(139, 23);
            this.btnIniciarCliete.TabIndex = 3;
            this.btnIniciarCliete.Text = "Iniciar Cliente";
            this.btnIniciarCliete.UseVisualStyleBackColor = true;
            this.btnIniciarCliete.Click += new System.EventHandler(this.btnIniciarCliete_Click);
            // 
            // btnIniciarServidor
            // 
            this.btnIniciarServidor.Location = new System.Drawing.Point(236, 214);
            this.btnIniciarServidor.Name = "btnIniciarServidor";
            this.btnIniciarServidor.Size = new System.Drawing.Size(139, 23);
            this.btnIniciarServidor.TabIndex = 2;
            this.btnIniciarServidor.Text = "Iniciar Servirdor";
            this.btnIniciarServidor.UseVisualStyleBackColor = true;
            this.btnIniciarServidor.Click += new System.EventHandler(this.btnIniciarServidor_Click);
            // 
            // frmPrincipal
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.btnIniciarCliete);
            this.Controls.Add(this.btnIniciarServidor);
            this.Name = "frmPrincipal";
            this.Text = "Form1";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button btnIniciarCliete;
        private System.Windows.Forms.Button btnIniciarServidor;
    }
}

