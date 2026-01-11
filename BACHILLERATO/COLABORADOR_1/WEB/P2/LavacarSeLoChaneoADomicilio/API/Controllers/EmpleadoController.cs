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
using API.Models;
using API.Services;
using Microsoft.AspNetCore.Mvc;

namespace API.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class EmpleadoController : ControllerBase
    {// controlador para manejar las operaciones CRUD de Empleado
        private readonly EmpleadoService _service;

        public EmpleadoController(EmpleadoService service)
        {
            _service = service;
        }

        [HttpGet]
        // Obtiene todos los empleados
        public ActionResult<IEnumerable<Empleado>> GetAll() => Ok(_service.GetAll());// Obtiene todos los empleados
        
        [HttpGet("{cedula}")]
        // Obtiene un empleado por su cédula
        public ActionResult<Empleado> GetByCedula(string cedula)// Obtiene un empleado por su cédula
        {
            var emp = _service.GetByCedula(cedula);
            return emp is null ? NotFound() : Ok(emp);
        }

        [HttpPost]
        // Crea un nuevo empleado
        public IActionResult Create([FromBody] Empleado emp)
        {
            _service.Add(emp);
            return CreatedAtAction(nameof(GetByCedula), new { cedula = emp.Cedula }, emp);
        }

        [HttpPut("{cedula}")]
        // Actualiza un empleado existente por su cédula
        public IActionResult Update(string cedula, [FromBody] Empleado emp)
        {
            _service.Update(cedula, emp);
            return NoContent();
        }

        [HttpDelete("{cedula}")]
        // Elimina un empleado por su cédula
        public IActionResult Delete(string cedula)
        {
            _service.Delete(cedula);
            return NoContent();
        }
    }
}
