/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso: FUNDAMENTOS DE PROGRAMACION WEB
Código: 03075 
Proyecto #2: Lavacar se lo chaneo  a domicilio
Tutor: Sigifredo Leitón Luna
Grupo: 04
Estudiante: Francisco Campos Sandi
Cédula: 114750560
II Cuatrimestre 2025
*/
namespace API.Models
{
    public class Empleado
    {// parametros de la clase Empleado
        public string Cedula { get; set; }
        public DateTime FechaNacimiento { get; set; }
        public DateTime FechaIngreso { get; set; }
        public decimal SalarioPorDia { get; set; }
        public int DiasVacacionesAcumulados { get; set; }
        public DateTime? FechaRetiro { get; set; }
        public decimal MontoLiquidacion { get; set; }
    }
}
