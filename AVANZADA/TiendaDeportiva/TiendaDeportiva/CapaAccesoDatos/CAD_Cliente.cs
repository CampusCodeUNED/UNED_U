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
using TiendaDeportiva.CapaEntidades;

namespace TiendaDeportiva.CapaAccesoDatos
{
    public class CAD_Cliente
    {
        // Arreglo clientes
        private Cliente[] clientes = new Cliente[20];
        private int contador = 0;

        // Método para agregar un nuevo cliente
        public void AgregarCliente(Cliente cliente)
        {
            // Verifica si se ha alcanzado la capacidad máxima del arreglo
            if (contador >= clientes.Length)
            {
                throw new InvalidOperationException("No se pueden agregar más clientes. Capacidad máxima alcanzada.");
            }

            // Verifica si la identificación del cliente ya existe
            for (int i = 0; i < contador; i++)
            {
                if (clientes[i] != null && clientes[i].Identificacion == cliente.Identificacion)
                {
                    throw new ArgumentException("La identificación del cliente ya existe.");
                }
            }

            // Agrega el nuevo cliente al arreglo y aumenta el contador
            clientes[contador] = cliente;
            contador++;
        }

        // Método para obtener el arreglo de clientes
        public Cliente[] ObtenerClientes()
        {
            return clientes;
        }

        // Método para verificar si ya existe un cliente con una identificación específica
        public bool ExisteCliente(int identificacion)
        {
            for (int i = 0; i < contador; i++)
            {
                if (clientes[i] != null && clientes[i].Identificacion == identificacion)
                {
                    return true;
                }
            }
            return false;
        }
    }
}

