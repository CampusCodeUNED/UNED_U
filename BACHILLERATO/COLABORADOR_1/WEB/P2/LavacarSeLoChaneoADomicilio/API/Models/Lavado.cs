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
    public class Lavado
    {// parametros de la clase Lavado
        public int Id { get; set; }
        public DateTime Fecha { get; set; } = DateTime.Now;

        public Cliente Cliente { get; set; } = new Cliente();       
        public Vehiculo Vehiculo { get; set; } = new Vehiculo();   
        public Empleado Empleado { get; set; } = new Empleado();   

        public TipoLavado Tipo { get; set; }
        public EstadoLavado Estado { get; set; }

        public decimal Precio { get; set; }

        public decimal IVA => Math.Round(Precio * 0.13M, 2);
        public decimal Total => Precio + IVA;
    }
}
