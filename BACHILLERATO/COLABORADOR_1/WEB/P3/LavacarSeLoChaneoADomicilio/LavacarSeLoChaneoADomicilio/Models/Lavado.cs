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
namespace LavacarSeLoChaneoADomicilio.Models
{
    public class Lavado
    {
        public int Id { get; set; }
        public DateTime Fecha { get; set; } = DateTime.Now;

        public string ClienteId { get; set; } = string.Empty;
        public string VehiculoPlaca { get; set; } = string.Empty;
        public string EmpleadoCedula { get; set; } = string.Empty;

        public Cliente? Cliente { get; set; }
        public Vehiculo? Vehiculo { get; set; }
        public Empleado? Empleado { get; set; }

        public TipoLavado Tipo { get; set; }
        public EstadoLavado Estado { get; set; }
        public decimal Precio { get; set; }
        public decimal IVA => Math.Round(Precio * 0.13M, 2);
        public decimal Total => Precio + IVA;
    }
}