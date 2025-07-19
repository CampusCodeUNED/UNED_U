using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BibiliotecaServidor.Entidades
{
    /// <summary>
    /// Clase Libro
    /// Representa un objeto de tipo Libro, la clase de un objeto debe ser lo más limpia posible
    /// y no implementar métodos del negocio, como por ejemplo Agregar o Crear libros, eso se realiza en otras clases destinadas para dichos efectos
    /// </summary>
    [Serializable]
    public class Libro
    {
        public string ISBN { get; set; }
        public string IdAutor { get; set; }
        public string Titulo { get; set; }
        public string Descripcion { get; set; }
        public int NumeroEdicion { get; set; }
    }
}
