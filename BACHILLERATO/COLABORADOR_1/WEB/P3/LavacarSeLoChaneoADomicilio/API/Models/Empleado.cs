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
using Microsoft.EntityFrameworkCore;
using System.ComponentModel.DataAnnotations;

namespace API.Models
{
    public class Empleado
    {
        [Key]
        public string Cedula { get; set; } = string.Empty;

        public DateTime FechaNacimiento { get; set; }
        public DateTime FechaIngreso { get; set; }

        [Precision(18, 2)] 
        public decimal SalarioPorDia { get; set; }

        public int DiasVacacionesAcumulados { get; set; }
        public DateTime? FechaRetiro { get; set; }

        [Precision(18, 2)]
        public decimal MontoLiquidacion { get; set; }
    }
}
