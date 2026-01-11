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
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;

namespace API.Models
{
    public class Lavado
    {
        public int Id { get; set; }
        public DateTime Fecha { get; set; } = DateTime.Now;

        public string ClienteId { get; set; } = string.Empty;
        public string VehiculoPlaca { get; set; } = string.Empty;
        public string EmpleadoCedula { get; set; } = string.Empty;
        // Relación con Cliente, Vehiculo y Empleado
        [JsonIgnore(Condition = JsonIgnoreCondition.WhenWritingNull)]
        public Cliente? Cliente { get; set; }
        // Relación con Vehiculo
        [JsonIgnore(Condition = JsonIgnoreCondition.WhenWritingNull)]
        public Vehiculo? Vehiculo { get; set; }
        // Relación con Empleado
        [JsonIgnore(Condition = JsonIgnoreCondition.WhenWritingNull)]
        public Empleado? Empleado { get; set; }

        public TipoLavado Tipo { get; set; }
        public EstadoLavado Estado { get; set; }
        public decimal Precio { get; set; }
        // Propiedades calculadas para IVA y Total
        [NotMapped]
        public decimal IVA => Math.Round(Precio * 0.13M, 2);

        [NotMapped]
        public decimal Total => Precio + IVA;
    }
}