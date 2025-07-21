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
namespace TiendaDeportivaCliente.Entidades
{
    public class ArticuloSucursal
    {//atributos de la clase ArticuloSucursal
        public Articulo Articulo { get; set; }
        public int Cantidad { get; set; }

        public int IdArticulo => Articulo?.Id ?? 0;
        public string DescripcionArticulo => Articulo?.Descripcion ?? string.Empty;
        public string MarcaArticulo => Articulo?.Marca ?? string.Empty;
    }

}
