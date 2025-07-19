using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BibiliotecaServidor.Entidades
{
    /// <summary>
    /// Clase Autor
    /// Representa un objeto de tipo Autor, la clase de un objeto debe ser lo más limpia posible
    /// y no implementar métodos del negocio, como por ejemplo Agregar o Crear Autores, eso se realiza en otras clases destinadas para dichos efectos
    /// </summary>
    [Serializable]
    public class Autor
    {
        public string IdAutor { get; set; }
        public string Nombre { get; set; }
        public string PrimerApellido { get; set; }
        public string SegundoApellido { get; set; }

    }
}
