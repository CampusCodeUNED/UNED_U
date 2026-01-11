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
using API.Models;
using API.Services;
using Microsoft.AspNetCore.Mvc;

namespace API.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class EmpleadoController : ControllerBase
    {
        private readonly EmpleadoService _service;

        public EmpleadoController(EmpleadoService service)
        {
            _service = service;
        }
        // Rutas de la API para manejar empleados
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Empleado>>> GetAll() =>
            Ok(await _service.GetAllAsync());
        // Obtiene un empleado por su cédula
        [HttpGet("{cedula}")]
        public async Task<ActionResult<Empleado>> GetByCedula(string cedula)
        {
            var emp = await _service.GetByCedulaAsync(cedula);
            return emp is null ? NotFound() : Ok(emp);
        }
        // Obtiene empleados por su rol
        [HttpPost]
        public async Task<IActionResult> Create([FromBody] Empleado emp)
        {
            await _service.AddAsync(emp);
            return CreatedAtAction(nameof(GetByCedula), new { cedula = emp.Cedula }, emp);
        }
        // Actualiza un empleado existente
        [HttpPut("{cedula}")]
        public async Task<IActionResult> Update(string cedula, [FromBody] Empleado emp)
        {
            var ok = await _service.UpdateAsync(cedula, emp);
            return ok ? NoContent() : NotFound();
        }
        // Elimina un empleado por su cédula
        [HttpDelete("{cedula}")]
        public async Task<IActionResult> Delete(string cedula)
        {
            var ok = await _service.DeleteAsync(cedula);
            return ok ? NoContent() : NotFound();
        }
    }

}
