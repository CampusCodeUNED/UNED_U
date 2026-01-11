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
    // Controlador para manejar las operaciones CRUD de Lavado
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

        [HttpGet]
        // Obtiene todos los lavados
        public ActionResult<IEnumerable<Lavado>> GetAll() => Ok(_service.GetAll());

        [HttpGet("{id}")]
        // Obtiene un lavado por su ID
        public ActionResult<Lavado> GetById(int id)
        {
            var l = _service.GetById(id);
            return l is null ? NotFound() : Ok(l);
        }

        [HttpPost]
        // Crea un nuevo lavado
        public IActionResult Create([FromBody] Lavado l)
        {
            _service.Add(l);
            return CreatedAtAction(nameof(GetById), new { id = l.Id }, l);
        }

        [HttpPut("{id}")]
        // Actualiza un lavado existente por su ID
        public IActionResult Update(int id, [FromBody] Lavado l)
        {
            _service.Update(id, l);
            return NoContent();
        }

        [HttpDelete("{id}")]
        // Elimina un lavado por su ID
        public IActionResult Delete(int id)
        {
            _service.Delete(id);
            return NoContent();
        }
    }
}
