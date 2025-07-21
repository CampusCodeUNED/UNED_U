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
    public class Sucursal
    {// Atributos de la clase Sucursal
        public int Id { get; set; }
        public string Nombre { get; set; }
        public Administrador Administrador { get; set; }
        public string Direccion { get; set; }
        public string Telefono { get; set; }
        public bool Activo { get; set; }
        // Constructor de la clase Sucursal para inicializar una nueva sucursal con todos los datos
        public Sucursal(int id, string nombre, Administrador administrador, string direccion, string telefono, bool activo)
        {
            Id = id;
            Nombre = nombre;
            Administrador = administrador;
            Direccion = direccion;
            Telefono = telefono;
            Activo = activo;
        }
        // Método ToString para mostrar el nombre de la sucursal
        public override string ToString()
        {
            return Nombre; 
        }

    }

}
