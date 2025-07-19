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
using System;
using System.Net.Sockets;
using System.Text;

namespace TiendaDeportivaCliente.Interfaz
{
    public class ClienteTcp
    {
        private TcpClient _tcpClient;// Cliente TCP para conectar con el servidor
        private NetworkStream _stream;// Flujo de red para enviar y recibir datos
        // Método para conectar al servidor
        public bool Conectar(string servidor, int puerto)
        {
            try
            {// Intentar crear una conexión con el servidor y puerto dados
                _tcpClient = new TcpClient(servidor, puerto);// Obtenemos el flujo de la conexión
                _stream = _tcpClient.GetStream();// Retornar verdadero si la conexión fue exitosa
                return true;
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error al conectar al servidor: {ex.Message}");// Retornar falso si hubo un error
                return false;
            }
        }
        // Método para desconectar el cliente del servidor
        public void Desconectar()
        {
            _stream?.Close();// Cerrar el flujo de red
            _tcpClient?.Close();// Cerrar la conexión TCP
        }
        // Método para enviar un mensaje al servidor y recibir una respuesta
        public string EnviarMensaje(string mensaje)
        {// Si el cliente no está conectado, retornar un mensaje de error
            if (_tcpClient == null || !_tcpClient.Connected)
                return "Desconectado";
            // Convertimos el mensaje a bytes usando UTF8
            byte[] buffer = Encoding.UTF8.GetBytes(mensaje);
            _stream.Write(buffer, 0, buffer.Length);// Enviamos el mensaje al servidor
            // Creamos un buffer para recibir la respuesta
            byte[] respuestaBuffer = new byte[1024];
            int bytesLeidos = _stream.Read(respuestaBuffer, 0, respuestaBuffer.Length);// Convertimos la respuesta a texto
            return Encoding.UTF8.GetString(respuestaBuffer, 0, bytesLeidos);// Retornamos la respuesta
        }
    }
}
