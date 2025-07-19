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
    public class Articulo
    {// atributos de la clase Articulo
        public int Id { get; set; }
        public string Descripcion { get; set; }
        public Categoria CategoriaArticulo { get; set; }
        public string Marca { get; set; }
        public bool Activo { get; set; }
        // Constructor de la clase Articulo para inicializar un nuevo artículo con todos los datos
        public Articulo(int id, string descripcion, Categoria categoria, string marca, bool activo)
        {
            this.Id = id;
            this.Descripcion = descripcion;
            this.CategoriaArticulo = categoria;
            this.Marca = marca;
            this.Activo = activo;
        }
    }

}
