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
    public class LavadoController : ControllerBase
    {
        private readonly LavadoService _service;
        // Constructor que recibe el servicio de Lavado
        public LavadoController(LavadoService service)
        {
            _service = service;
        }
        // Rutas de la API para manejar lavados
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Lavado>>> GetAll()
            => Ok(await _service.GetAllAsync());
        // Obtiene un lavado por su ID
        [HttpGet("{id}")]
        public async Task<ActionResult<Lavado>> GetById(int id)
        {
            var l = await _service.GetByIdAsync(id);
            return l == null ? NotFound() : Ok(l);
        }
        // Obtiene lavados por el ID del vehículo
        [HttpPost]
        public async Task<IActionResult> Create([FromBody] Lavado l)
        {
            await _service.AddAsync(l);
            return CreatedAtAction(nameof(GetById), new { id = l.Id }, l);
        }
        // Actualiza un lavado existente
        [HttpPut("{id}")]
        public async Task<IActionResult> Update(int id, [FromBody] Lavado l)
        {
            if (id != l.Id) return BadRequest("El ID no coincide.");
            await _service.UpdateAsync(l);
            return NoContent();
        }
        // Elimina un lavado por su ID
        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(int id)
        {
            await _service.DeleteAsync(id);
            return NoContent();
        }
    }

}
