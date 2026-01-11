/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso: FUNDAMENTOS DE PROGRAMACION WEB
Código: 03075 
Proyecto #1: Lavacar se lo chaneo  a domicilio
Tutor: Sigifredo Leitón Luna
Grupo: 04
Estudiante: Francisco Campos Sandi
Cédula: 114750560
II Cuatrimestre 2025
*/
using API.Models;

namespace API.Services
{
    public class EmpleadoService
    {
        private readonly List<Empleado> _empleados = new()
    {// Datos de ejemplo para simular una base de datos
        new Empleado
        {
            Cedula = "123456789",
            FechaNacimiento = new DateTime(1991, 5, 12),
            FechaIngreso = DateTime.Now.AddYears(-2),
            SalarioPorDia = 35000,
            DiasVacacionesAcumulados = 12,
            FechaRetiro = null,
            MontoLiquidacion = 0
        }
    };
        // Constructor que inicializa la lista de empleados
        public List<Empleado> GetAll() => _empleados;
        //Obtiene todos los empleados
        public Empleado? GetByCedula(string cedula) =>
            _empleados.FirstOrDefault(e => e.Cedula == cedula);
        // Obtiene un empleado por su cédula
        public void Add(Empleado empleado) => _empleados.Add(empleado);
        // Agrega un nuevo empleado a la lista
        public void Update(string cedula, Empleado updated)
        {
            var index = _empleados.FindIndex(e => e.Cedula == cedula);
            if (index >= 0) _empleados[index] = updated;
        }
        // Actualiza un empleado existente por su cédula
        public void Delete(string cedula)
        {
            var emp = _empleados.FirstOrDefault(e => e.Cedula == cedula);
            if (emp != null) _empleados.Remove(emp);
        }
    }
}
