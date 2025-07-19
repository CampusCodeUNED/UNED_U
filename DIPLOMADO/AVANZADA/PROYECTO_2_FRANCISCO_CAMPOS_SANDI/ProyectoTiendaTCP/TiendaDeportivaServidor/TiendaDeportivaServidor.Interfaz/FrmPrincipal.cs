/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso: Programación avanzada
Código: 00830
Proyecto #2: Tienda deportiva
Tutor: Juan Ramírez Valladares
Grupo: 09
Estudiante: Francisco Campos Sandi
Cédula: 114750560
III Cuatrimestre 2024
*/
using System.Collections.Generic;
using System.Net.Sockets;
using System.Net;
using System.Text;
using System.Threading;
using System;
using System.Windows.Forms;
using TiendaDeportivaServidor.Datos;
using TiendaDeportivaServidor.Entidades;
using System.Globalization;

namespace TiendaDeportivaServidor.Interfaz
{
    public partial class FrmPrincipal : Form
    {

       // Declara el servidor y la lista de clientes conectados
        private TcpListener _server;
        private List<TcpClient> _connectedClients = new List<TcpClient>();
        private bool _isRunning = false;  // Indica si el servidor está corriendo
        private Database _database;  // Instancia para acceder a la base de datos
        private readonly SemaphoreSlim _semaphore = new SemaphoreSlim(1, 1); // Semáforo para controlar el acceso a recursos críticos

        public FrmPrincipal()
        {
            InitializeComponent();
            this.StartPosition = FormStartPosition.CenterScreen;//Centrar el formulario en la pantalla
            this.MaximizeBox = false;//Deshabilitar el botón de maximizar
            this.FormBorderStyle = FormBorderStyle.FixedSingle;//Establecer un borde fijo
            btnApagarServer.Enabled = false;//Deshabilitar el botón de apagar servidor

            _database = new Database();//Inicializar la instancia de la base de datos

        }
        //Método para iniciar el servidor 
        private void StartServer()
        {
            if (_isRunning) return; 
            try
            {
                _server = new TcpListener(IPAddress.Parse("127.0.0.1"), 14100);//Crear el servidor en la dirección IP y puerto especificados
                _server.Start();
                _isRunning = true;
                LogToBitacora("Servidor iniciado en 127.0.0.1:14100");//Registrar en la bitácora que el servidor se ha iniciado

                Thread clientAcceptThread = new Thread(AcceptClients);//Crear un hilo para aceptar clientes
                clientAcceptThread.IsBackground = true;//Establecer el hilo como secundario
                clientAcceptThread.Start();//Iniciar el hilo
            }
            catch (Exception ex)
            {
                LogToBitacora($"Error al iniciar el servidor: {ex.Message}");//Registrar en la bitácora el error al iniciar el servidor
            }
        }

        // Método para aceptar clientes de manera concurrente
        private void AcceptClients()
        {
            // Mientras el servidor esté en ejecución, sigue aceptando clientes
            while (_isRunning)
            {
                try
                {
                    // Espera y acepta una nueva conexión de cliente
                    TcpClient client = _server.AcceptTcpClient();

                    // Agregar el cliente a la lista de clientes conectados
                    _connectedClients.Add(client);

                    // Actualiza la lista de clientes en la interfaz de usuario (o donde sea necesario)
                    UpdateClientList();

                    // Registrar en la bitácora que un cliente se ha conectado
                    LogToBitacora("Cliente conectado");

                    // Crear un nuevo hilo para manejar la comunicación con este cliente
                    Thread clientThread = new Thread(() => ManejarComunicaciónCliente(client));
                    clientThread.IsBackground = true;  // Establecer el hilo como fondo
                    clientThread.Start();  // Iniciar el hilo para manejar la comunicación
                }
                catch (Exception ex)
                {
                    // Si ocurre un error al aceptar un cliente, registrar el error en la bitácora
                    LogToBitacora($"Error al aceptar cliente: {ex.Message}");
                }
            }
        }
        //Método para manejar la comunicación con un cliente
        private async void ManejarComunicaciónCliente(TcpClient client)
        {
            NetworkStream stream = client.GetStream();//Obtener el flujo de red del cliente
            byte[] buffer = new byte[1024];//Crear un búfer para almacenar los datos recibidos
            int bytesRead;//Variable para almacenar la cantidad de bytes leídos

            try
            {     // Lee los datos enviados por el cliente de forma continua
                while ((bytesRead = await stream.ReadAsync(buffer, 0, buffer.Length)) > 0)
                {
                    string message = Encoding.UTF8.GetString(buffer, 0, bytesRead);//Convertir los bytes recibidos a una cadena
                    LogToBitacora($"Mensaje recibido de cliente: {message}");//Registrar en la bitácora el mensaje recibido

                    string respuesta;

                    // Espera hasta que el recurso esté disponible (sección crítica)
                    await _semaphore.WaitAsync();
                    try
                    {
                        if (message.StartsWith("VALIDAR_CLIENTE:"))
                        {   // Extrae la identificación del cliente y la valida en la base de datos
                            string identificacion = message.Substring("VALIDAR_CLIENTE:".Length);
                            string nombreCliente = _database.ValidarCliente(identificacion);

                            respuesta = nombreCliente != null ? $"OK:{nombreCliente}" : "ERROR:Cliente no encontrado o inactivo";//Enviar respuesta al cliente
                        }
                        else if (message == "CONSULTAR_SUCURSALES_ACTIVAS")
                        {   // Obtiene y responde con la lista de sucursales activas
                            respuesta = ConsultarSucursalesActivas();
                        }
                        else if (message.StartsWith("CONSULTAR_ARTICULOS_SUCURSAL:"))
                        {   // Verifica el ID de sucursal y botiene los artículos disponibles
                            if (int.TryParse(message.Substring("CONSULTAR_ARTICULOS_SUCURSAL:".Length), out int idSucursal))
                            {
                                respuesta = ConsultarArticulosDisponibles(idSucursal);
                            }
                            else
                            {
                                respuesta = "ERROR:ID de sucursal no válido";
                            }
                        }
                        else if (message.StartsWith("REALIZAR_RESERVA:"))
                        {   // Realiza una reserva utilizando los datos recibidos
                            respuesta = RealizarReserva(message.Substring("REALIZAR_RESERVA:".Length));
                        }
                        else if (message.StartsWith("CONSULTAR_RESERVAS:"))
                        {       // Realiza una reserva utilizando los datos recibidos
                            if (int.TryParse(message.Substring("CONSULTAR_RESERVAS:".Length), out int identificacionCliente))
                            {    
                                respuesta = ConsultarReservasPorCliente(identificacionCliente);//Obtener las reservas del cliente
                            }
                            else
                            {
                                respuesta = "ERROR:Formato de identificación del cliente no válido";//Enviar respuesta al cliente
                            }
                        }
                        else if (message.StartsWith("CONSULTAR_RESERVA:"))
                        {   // Consulta una reserva específica con el ID del cliente y la reserva
                            string[] datos = message.Substring("CONSULTAR_RESERVA:".Length).Split(',');
                            if (datos.Length == 2 && int.TryParse(datos[0], out int identificacionCliente) && int.TryParse(datos[1], out int idReserva))//Verificar el formato de los datos
                            {
                                respuesta = ConsultarReservaPorId(identificacionCliente, idReserva);//Obtener la reserva específica
                            }
                            else
                            {
                                respuesta = "ERROR:Formato de datos inválido";
                            }
                        }
                        else
                        {
                            respuesta = "ERROR:Comando no reconocido";
                        }
                    }
                    finally
                    {
                        // Liberar el semáforo después de que el recurso ya no sea necesario
                        _semaphore.Release();
                    }

                    // Enviar respuesta al cliente a través del flujo de datos
                    byte[] responseBuffer = Encoding.UTF8.GetBytes(respuesta);
                    await stream.WriteAsync(responseBuffer, 0, responseBuffer.Length);
                }
            }
            catch (Exception ex)
            {    // Registra un error si ocurre alguna excepción en la comunicación con el cliente
                LogToBitacora($"Error en comunicación con cliente: {ex.Message}");
            }
            finally
            {   // Finaliza la conexión del cliente, liberando los recursos asociados
                DisconnectClient(client);
            }
        }


        //Método para consultar las sucursales activas
        private string ConsultarSucursalesActivas()
        {   // Obtiene la lista de sucursales activas desde la base de datos
            List<Sucursal> sucursales = _database.ObtenerSucursalesActivas();
            // Verifica si hay sucursales activas disponibles
            if (sucursales.Count == 0)
            {
                return "ERROR:No hay sucursales activas disponibles";
            }
            // Construye la respuesta concatenando los datos de cada sucursal
            StringBuilder response = new StringBuilder("OK:");
            foreach (var sucursal in sucursales)
            {
                response.Append($"{sucursal.Id},{sucursal.Nombre},{sucursal.Direccion},{sucursal.Telefono},{sucursal.Activo}|");
            }
            response.Length--; // Elimina el último carácter '|' de la respuesta
            return response.ToString();// Retorna la respuesta como una cadena
        }


        //Método para consultar los artículos disponibles en una sucursal
        private string ConsultarArticulosDisponibles(int idSucursal)
        {
            List<ArticuloSucursal> articulos = _database.ObtenerArticulosDisponibles(idSucursal);//Obtener los artículos disponibles en la sucursal
            // Verifica si hay artículos disponibles en la sucursal
            if (articulos.Count == 0)
            {
                return "ERROR:No hay artículos disponibles en esta sucursal";
            }
            // Construye la respuesta concatenando los datos de cada artículo
            StringBuilder response = new StringBuilder("OK:");
            foreach (var articulo in articulos)
            {
                response.Append($"{articulo.Articulo.Id},{articulo.Articulo.Descripcion},{articulo.Articulo.Marca},{articulo.Articulo.Activo},{articulo.Cantidad}|");
            }
            response.Length--; // Elimina el último carácter '|' de la respuesta
            return response.ToString();
        }
        //Método para realizar una reserva
        private string RealizarReserva(string reservaData)
        {
            try
            {//Registrar en la bitácora los datos recibidos para la reserva
                LogToBitacora($"Datos recibidos para reserva: {reservaData}");

                string[] datos = reservaData.Split(',');//Dividir los datos de la reserva en un arreglo
                if (datos.Length != 4)
                {
                    LogToBitacora("Error: Formato de datos de reserva inválido.");
                    return "ERROR:Formato de datos de reserva inválido.";
                }
                // Convierte y valida cada componente de los datos recibidos para la reserva
                int idSucursal = Convert.ToInt32(datos[0]);
                int idArticulo = Convert.ToInt32(datos[1]);
                int identificacionCliente = Convert.ToInt32(datos[2]);
                DateTime fechaReserva = DateTime.ParseExact(datos[3], "yyyy-MM-dd HH:mm:ss", CultureInfo.InvariantCulture);
                // Llama al método para registrar la reserva en la base de datos y evalúa el resultado
                bool reservaExitosa = _database.RegistrarReserva(idSucursal, idArticulo, identificacionCliente, fechaReserva);
                // Retorna una respuesta en funcion del éxito de la reserva
                return reservaExitosa ? "OK:Reserva exitosa" : "ERROR:No se pudo realizar la reserva.";
            }
            catch (Exception ex)
            {
                LogToBitacora($"Error en RealizarReserva: {ex.Message}");//Registrar en la bitácora el error al procesar la reserva
                return "ERROR:Ocurrió un error al procesar la reserva.";
            }
        }


        //Método para consultar las reservas de un cliente
        private string ConsultarReservasPorCliente(int identificacionCliente)
        { // Consulta en la base de datos las reservas asociadas al cliente especifico
            List<Reserva> reservas = _database.ConsultarReservasPorCliente(identificacionCliente);

            if (reservas.Count == 0)//Verificar si hay reservas para el cliente
            {
                return "ERROR:No hay reservas para este cliente";
            }

            StringBuilder response = new StringBuilder("OK:");//Construir la respuesta concatenando los datos de cada reserva
            foreach (var reserva in reservas)
            {  //Agregar los datos de la reserva a la respuesta
                response.Append($"{reserva.Id},{reserva.Sucursal},{reserva.Articulo},{reserva.FechaReserva:yyyy-MM-dd HH:mm:ss}|");
            }
            response.Length--;//Eliminar el último carácter '|' de la respuesta
            return response.ToString();//Retornar la respuesta como una cadena
        }
        //Método para consultar una reserva específica por ID
        private string ConsultarReservaPorId(int identificacionCliente, int idReserva)
        {      //Consultar la reserva en la base de datos
            Reserva reserva = _database.ConsultarReservaPorId(identificacionCliente, idReserva);//Consultar la reserva en la base de datos

            if (reserva == null)// Retorna error si no se encuentra la reserva
            {
                return "ERROR:No se encontró la reserva con el ID proporcionado para este cliente";
            }
              // Formatea la respuesta con la información de la reserva
            return $"OK:{reserva.Id},{reserva.Sucursal},{reserva.Articulo},{reserva.FechaReserva:yyyy-MM-dd HH:mm:ss}";
        }

        //Método para desconectar un cliente
        private void DisconnectClient(TcpClient client)
        {   // Verifica si el cliente está en la lista de clientes conectado
            if (_connectedClients.Contains(client))
            {
                _connectedClients.Remove(client);//Elimina el cliente de la lista de clientes conectados
                UpdateClientList(); // Actualiza la lista de clientes en el Formulario
                LogToBitacora("Cliente desconectado");//Registrar en la bitácora que el cliente se ha desconectado
                client.Close();//Cerrar la conexión con el cliente
            }
        }
        //Método para actualizar la lista de clientes en el formulario
        private void UpdateClientList()
        {
            if (InvokeRequired)// Verifica si se necesita invocar en el hilo principal de la interfaz
            {
                Invoke(new Action(UpdateClientList));
                return;
            }

            listBox2.Items.Clear(); 
            foreach (var client in _connectedClients)// Agrega cada cliente a la lista de clientes en el formulario
            {
                listBox2.Items.Add(client.Client.RemoteEndPoint.ToString());
            }
        }
        //Método para registrar mensajes en la bitácora
        private void LogToBitacora(string message)
        {
            if (InvokeRequired)// Verifica si se necesita invocar en el hilo principal de la interfaz
            {
                Invoke(new Action<string>(LogToBitacora), message);//Invoca el método en el hilo principal
                return;
            }

            listBox1.Items.Add($"{DateTime.Now}: {message}");//Agrega el mensaje a la lista de mensajes en el formulario
            listBox1.TopIndex = listBox1.Items.Count - 1; // Mantiene el último mensaje visible
        }
        //Método para cerrar el formulario
        protected override void OnFormClosing(FormClosingEventArgs e)
        {
            base.OnFormClosing(e);//Llama al método de la clase base
            StopServer();//Detiene el servidor
        }
        //Método para detener el servidor
        private void StopServer()
        {
            if (!_isRunning) return; // Verifica si el servidor ya está detenido
            _isRunning = false;//Establece que el servidor ya no está en ejecución

            foreach (var client in _connectedClients)//Desconectar a todos los clientes conectados
            {
                if (client.Connected)
                {
                    client.Close();
                }
            }
            _connectedClients.Clear();//Limpiar la lista de clientes conectados
            _server?.Stop();//Detener el servidor
            LogToBitacora("Servidor detenido");//Registrar en la bitácora que el servidor se ha detenido
        }
        //Método para manejar el evento de clic en el botón de apagar servidor
        private void btnApagarServer_Click(object sender, EventArgs e)
        {
            StopServer();
            btnApagarServer.Enabled = false;//Deshabilitar el botón de apagar servidor
            btnEncenderServer.Enabled = true;  // Habilitar el botón de encender servidor
        }
        //Método para manejar el evento de clic en el botón de encender servidor
        private void btnEncenderServer_Click(object sender, EventArgs e)
        {
            StartServer();
            btnEncenderServer.Enabled = false;//Deshabilitar el botón de encender servidor
            btnApagarServer.Enabled = true;//Habilitar el botón de apagar servidor
        }
        //Método para manejar el evento del botón de categorías
        private void btnCategorias_Click(object sender, EventArgs e)
        {
            FrmCategoria frmCategoria = new FrmCategoria();//Crear una instancia del formulario de categorías
            frmCategoria.ShowDialog();//Mostrar el formulario de categorías
        }
        //Método para manejar el evento del botón de artículos
        private void btnArticulos_Click(object sender, EventArgs e)
        {
            FrmArticulo frmArticulo = new FrmArticulo();//Crear una instancia del formulario de artículos
            frmArticulo.ShowDialog();//Mostrar el formulario de artículos
        }
        //Método para manejar el evento del botón de administradores 
        private void btnAdministradores_Click(object sender, EventArgs e)
        {
            FrmAdministrador frmAdministrador = new FrmAdministrador();//Crear una instancia del formulario de administradores
            frmAdministrador.ShowDialog();//Mostrar el formulario de administradores
        }
        //Método para manejar el evento del botón de sucurales
        private void BtnSucursales_Click(object sender, EventArgs e)
        {
            FrmSucursal frmSucursal = new FrmSucursal();//Crear una instancia del formulario de sucursales
            frmSucursal.ShowDialog();//Mostrar el formulario de sucursales
        }
        //Método para manejar el evento del botón de clientes
        private void btnClientes_Click(object sender, EventArgs e)
        {
            FrmCliente frmCliente = new FrmCliente();//Crear una instancia del formulario de clientes
            frmCliente.ShowDialog();//Mostrar el formulario de clientes
        }
        //Método para manejar el evento del botón de artículoXsucursal
        private void btnArticulosSucursal_Click(object sender, EventArgs e)
        {
            FrmArticuloSucursal frmArticuloSucursal = new FrmArticuloSucursal();//Crear una instancia del formulario de artículoXsucursal
            frmArticuloSucursal.ShowDialog();//Mostrar el formulario de artículoXsucursal
        }
    }
}
