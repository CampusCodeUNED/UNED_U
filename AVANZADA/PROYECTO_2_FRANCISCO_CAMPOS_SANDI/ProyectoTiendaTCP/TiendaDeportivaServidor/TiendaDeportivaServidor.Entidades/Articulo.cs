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
    public class Articulo
    {//Atributos de la clase Articulo
        public int Id { get; set; }
        public string Descripcion { get; set; }
        public string Marca { get; set; }
        public bool Activo { get; set; }
        public Categoria Categoria { get; set; }

        public string ActivoTexto => Activo ? "Sí" : "No";//Propiedad ActivoTexto
        public string CategoriaNombre => Categoria?.Nombre ?? string.Empty;//Propiedad CategoriaNombre
    }
}
