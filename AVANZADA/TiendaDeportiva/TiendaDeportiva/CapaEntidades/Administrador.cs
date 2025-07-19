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
namespace TiendaDeportiva.CapaEntidades
{
    public class Administrador
    {// Atributos de la clase Administrador
        public int Identificacion { get; set; }
        public string Nombre { get; set; }
        public string PrimerApellido { get; set; }
        public string SegundoApellido { get; set; }
        public DateTime FechaNacimiento { get; set; }
        public DateTime FechaIngreso { get; set; }
        // Constructor para inicializar un nuevo administrador con todos los datos
        public Administrador(int identificacion, string nombre, string primerApellido, string segundoApellido, DateTime fechaNacimiento, DateTime fechaIngreso)
        {
            Identificacion = identificacion;
            Nombre = nombre;
            PrimerApellido = primerApellido;
            SegundoApellido = segundoApellido;
            FechaNacimiento = fechaNacimiento;
            FechaIngreso = fechaIngreso;
        }
    }

}
