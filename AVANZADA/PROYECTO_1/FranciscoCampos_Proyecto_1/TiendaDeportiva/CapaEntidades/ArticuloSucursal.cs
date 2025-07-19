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
    public class ArticuloSucursal
    {// Atributos de la clase ArticuloSucursal
        public Sucursal Sucursal { get; set; }
        public Articulo Articulo { get; set; }
        public int Cantidad { get; set; }
        // Constructor de la clase ArticuloSucursal para inicializar un nuevo artículo en una sucursal con la cantidad
        public ArticuloSucursal(Sucursal sucursal, Articulo articulo, int cantidad)
        {
            Sucursal = sucursal;
            Articulo = articulo;
            Cantidad = cantidad;
        }
    }

}
