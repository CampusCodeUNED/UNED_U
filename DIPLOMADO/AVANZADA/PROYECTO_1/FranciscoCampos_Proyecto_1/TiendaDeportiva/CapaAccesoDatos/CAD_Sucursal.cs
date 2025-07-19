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
    public class CAD_Sucursal
    {
        // Arreglo sucursales
        private Sucursal[] sucursales = new Sucursal[5];
        private int contador = 0;

        // Método para agregar una nueva sucursal
        public void AgregarSucursal(Sucursal sucursal)
        {
            // Verifica si se ha alcanzado la capacidad máxima del arreglo
            if (contador >= sucursales.Length)
            {
                throw new InvalidOperationException("No se pueden agregar más sucursales. Capacidad máxima alcanzada.");
            }

            // Verifica si el Id de la sucursal ya existe
            for (int i = 0; i < contador; i++)
            {
                if (sucursales[i] != null && sucursales[i].Id == sucursal.Id)
                {
                    throw new ArgumentException("El Id de la sucursal ya existe.");
                }
            }

            // Agrega la nueva sucursal al arreglo y aumenta el contador
            sucursales[contador] = sucursal;
            contador++;
        }

        // Método para obtener el arreglo de sucursales
        public Sucursal[] ObtenerSucursales()
        {
            return sucursales;
        }

        // Método para verificar si ya existe una sucursal con un Id específico
        public bool ExisteIdSucursal(int id)
        {
            for (int i = 0; i < contador; i++)
            {
                if (sucursales[i] != null && sucursales[i].Id == id)
                {
                    return true;
                }
            }
            return false;
        }

        // Método para verificar si ya existe una sucursal con un nombre específico
        public bool ExisteNombreSucursal(string nombre)
        {
            // Compara nombres sin importar mayúsculas/minúsculas
            for (int i = 0; i < contador; i++)
            {
                if (sucursales[i] != null &&
                    string.Equals(sucursales[i].Nombre, nombre, StringComparison.OrdinalIgnoreCase))
                {
                    return true;
                }
            }
            return false;
        }

        // Método para obtener todas las sucursales activas
        public Sucursal[] ObtenerSucursalesActivas()
        {
            int count = 0;
            // Cuenta cuántas sucursales están activas
            for (int i = 0; i < contador; i++)
            {
                if (sucursales[i] != null && sucursales[i].Activo)
                {
                    count++;
                }
            }

            // Crea un nuevo arreglo para almacenar las sucursales activas
            Sucursal[] sucursalesActivas = new Sucursal[count];
            int index = 0;
            // Llena el nuevo arreglo con las sucursales activas
            for (int i = 0; i < contador; i++)
            {
                if (sucursales[i] != null && sucursales[i].Activo)
                {
                    sucursalesActivas[index] = sucursales[i];
                    index++;
                }
            }

            return sucursalesActivas;
        }
    }
}

