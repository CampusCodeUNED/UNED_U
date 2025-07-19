/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso: Programación avanzada
Código: 00830
Proyecto #2: Tienda deportiva
Tutor: Juan Ramírez Valladares
Grupo: 09
Estudiante: Francisco Campos Sandi
Cédula: 114750560
III Cuatrimestre 2024
*/

namespace TiendaDeportivaServidor.Entidades
{
    public class ArticuloSucursal
    {//Atributos de la clase ArticuloSucursal
        public Sucursal Sucursal { get; set; }   
        public Articulo Articulo { get; set; }   
        public int Cantidad { get; set; }

        public string NombreSucursal => Sucursal?.Nombre ?? string.Empty;//Propiedad NombreSucursal

        public string DescripcionArticulo => Articulo?.Descripcion ?? string.Empty;//Propiedad DescripcionArticulo
    }
}
