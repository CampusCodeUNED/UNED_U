/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso: Programación avanzada
Código: 00830
Proyecto #1: Tienda deportiva
Tutor: Juan Ramírez Valladares
Grupo: 09
Estudiante: Francisco Campos Sandi
Cédula: 114750560
III Cuatrimestre 2024
*/
using System;
using TiendaDeportiva.CapaAccesoDatos;
using TiendaDeportiva.CapaEntidades;

namespace TiendaDeportiva.CapaLogicaNegocio
{
    // Clase que maneja la lógica de negocio para los clientes
    public class CLN_Cliente
    {
        
        private static CLN_Cliente instancia = null;

        // Acceso a la capa de datos para clientes
        private CAD_Cliente clienteData = new CAD_Cliente();

        // Constructor privado para evitar la instanciación externa
        private CLN_Cliente() { }

        // Método para obtener la instancia única de la clase
        public static CLN_Cliente Instancia()
        {
            if (instancia == null)
            {
                instancia = new CLN_Cliente();
            }
            return instancia;
        }

        // Método para agregar un nuevo cliente
        public void AgregarCliente(Cliente cliente)
        {
            // Crea un nuevo cliente y lo agrega a la capa de datos
            Cliente nuevoCliente = new Cliente(cliente.Identificacion, cliente.Nombre, cliente.PrimerApellido, cliente.SegundoApellido, cliente.FechaNacimiento, cliente.Activo);
            clienteData.AgregarCliente(nuevoCliente);
        }

        // Método para obtener todos los clientes
        public Cliente[] ObtenerClientes()
        {
            return clienteData.ObtenerClientes();
        }

        // Método para verificar si existe un cliente con un ID específico
        public bool ExisteCliente(int id)
        {
            return clienteData.ExisteCliente(id);
        }
    }
}

