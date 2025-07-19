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
    public class Categoria
    {// Atributos de la clase Categoria
        public int IdCategoria { get; set; }
        public string NombreCategoria { get; set; }
        public string Descripcion { get; set; }
        // Constructor de la clase Categoria para inicializar una nueva categoría con todos los datos
        public Categoria(int id, string nombre, string descripcion)
        {
            IdCategoria = id;
            NombreCategoria = nombre;
            Descripcion = descripcion;
        }
    }
}
