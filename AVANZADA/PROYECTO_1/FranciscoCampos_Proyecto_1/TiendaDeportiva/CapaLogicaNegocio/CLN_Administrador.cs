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
    public class CLN_Administrador
    {
      
        private static CLN_Administrador instancia = null;

        // Acceso a la capa de datos para administradores
        private CAD_Administrador administradorData = new CAD_Administrador();

        // Método para obtener la instancia única de CLN_Administrador
        public static CLN_Administrador Instancia()
        {
            if (instancia == null)
            {
                instancia = new CLN_Administrador();
            }
            return instancia; // Retorna la instancia
        }

        // Método para agregar un nuevo administrador
        public void AgregarAdministrador(Administrador administrador)
        {
            // Crea un nuevo objeto Administrador y lo agrega a la capa de datos
            Administrador nuevoAdministrador = new Administrador(administrador.Identificacion, administrador.Nombre, administrador.PrimerApellido, administrador.SegundoApellido, administrador.FechaNacimiento, administrador.FechaIngreso);
            administradorData.AgregarAdministrador(nuevoAdministrador);
        }

        // Método para obtener todos los administradores
        public Administrador[] ObtenerAdministradores()
        {
            return administradorData.ObtenerAdministradores(); // Retorna la lista de administradores
        }

        // Método para verificar si un administrador existe por su identificación
        public bool ExisteAdministrador(int identificacion)
        {
            return administradorData.ExisteAdministrador(identificacion); // Retorna true si existe
        }
    }
}

