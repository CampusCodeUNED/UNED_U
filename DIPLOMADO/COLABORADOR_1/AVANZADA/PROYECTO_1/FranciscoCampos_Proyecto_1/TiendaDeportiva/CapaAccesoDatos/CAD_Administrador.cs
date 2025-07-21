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
    public class CAD_Administrador
    {
        // Arreglo para adminisrtadores
        private Administrador[] administradores = new Administrador[20];
        // Contador administradores 
        private int contador = 0;

        // Método para agregar un administrador al arreglo
        public void AgregarAdministrador(Administrador administrador)
        {
            // Verifica si se ha alcanzado la capacidad máxima del arreglo
            if (contador >= administradores.Length)
            {
                throw new InvalidOperationException("No se pueden agregar más administradores. Capacidad máxima alcanzada.");
            }

            // Verifica si el número de identificación del administrador ya existe
            for (int i = 0; i < contador; i++)
            {
                if (administradores[i] != null && administradores[i].Identificacion == administrador.Identificacion)
                {
                    throw new ArgumentException("El número de identificación ya existe.");
                }
            }

            // Agrega el nuevo administrador al arreglo y aumenta el contador
            administradores[contador] = administrador;
            contador++;
        }

        // Método para obtener el arreglo de administradores
        public Administrador[] ObtenerAdministradores()
        {
            return administradores;
        }

        // Método para verificar si un administrador con una identificación específica existe
        public bool ExisteAdministrador(int identificacion)
        {
            for (int i = 0; i < contador; i++)
            {
                if (administradores[i] != null && administradores[i].Identificacion == identificacion)
                {
                    return true; 
                }
            }
            return false; 
        }
         
    }

}

