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
    public class Articulo
    {//atributos de la clase Articulo
        public int Id { get; set; }
        public string Descripcion { get; set; }
        public string Marca { get; set; }
        public bool Activo { get; set; }
    }

}
