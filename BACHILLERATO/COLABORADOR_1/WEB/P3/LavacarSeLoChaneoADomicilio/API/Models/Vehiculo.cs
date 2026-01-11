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
using System.Text.Json.Serialization;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace API.Models
{
    public class Vehiculo
    {
        [Key]
        public string Placa { get; set; } = string.Empty;

        public string Marca { get; set; } = string.Empty;
        public string Modelo { get; set; } = string.Empty;
        public string Traccion { get; set; } = string.Empty;
        public string Color { get; set; } = string.Empty;
        public DateTime? UltimaFechaAtencion { get; set; }
        public bool TratamientoNanoCeramico { get; set; }

        [Required]
        public string ClienteIdentificacion { get; set; } = string.Empty;
        // Relación con Cliente
        [ForeignKey("ClienteIdentificacion")]
        [JsonIgnore(Condition = JsonIgnoreCondition.WhenWritingNull)]
        public Cliente? Cliente { get; set; }


    }
}
