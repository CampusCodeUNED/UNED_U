/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso: FUNDAMENTOS DE PROGRAMACION WEB
Código: 03075 
Proyecto #3: Lavacar se lo chaneo  a domicilio
Tutor: Sigifredo Leitón Luna
Grupo: 04
Estudiante: Francisco Campos Sandi
Cédula: 114750560
II Cuatrimestre 2025
*/
using System.ComponentModel.DataAnnotations;

namespace API.Models
{
    public class Cliente
    {
        [Key]
        public string Identificacion { get; set; } = string.Empty;

        public string NombreCompleto { get; set; } = string.Empty;
        public string Provincia { get; set; } = string.Empty;
        public string Canton { get; set; } = string.Empty;
        public string Distrito { get; set; } = string.Empty;
        public string DireccionExacta { get; set; } = string.Empty;
        public string Telefono { get; set; } = string.Empty;
        public string PreferenciaLavado { get; set; } = string.Empty;

        public List<Vehiculo> Vehiculos { get; set; } = new();
    }
}
