/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso: FUNDAMENTOS DE PROGRAMACION WEB
Código: 03075 
Tarea #1: Computadoras API
Tutor: Sigifredo Leitón Luna
Grupo: 04
Estudiante: Francisco Campos Sandi
Cédula: 114750560
II Cuatrimestre 2025
*/
using ComputadorasAPI.Models;
using ComputadorasAPI.Services;
using Microsoft.AspNetCore.Mvc;

namespace ComputadorasAPI.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class ComputadorasController : ControllerBase
    {
        private readonly ComputadoraService _service;
        // Constructor que recibe el servicio de computadoras
        public ComputadorasController(ComputadoraService service)
        {
            _service = service;
        }
        // Métodos de la API para manejar computadoras
        [HttpGet]
        public ActionResult<List<Computadora>> GetAll()
        {
            return _service.GetAll();
        }
        // Obtiene una computadora por su ID
        [HttpGet("{id}")]
        public ActionResult<Computadora> GetById(int id)
        {
            var comp = _service.GetById(id);
            if (comp == null) return NotFound();
            return comp;
        }
        // Crea una nueva computadora
        [HttpPost]
        public ActionResult<Computadora> Create(Computadora computadora)
        {
            var comp = _service.Create(computadora);
            return CreatedAtAction(nameof(GetById), new { id = comp.Id }, comp);
        }
        // Agrega software a una computadora
        [HttpPost("{id}/software")]
        public IActionResult AddSoftware(int id, Software software)
        {
            if (_service.AddSoftware(id, software))
                return Ok();
            return NotFound();
        }
        // Elimina software de una computadora
        [HttpDelete("{id}/software/{softwareId}")]
        public IActionResult DeleteSoftware(int id, int softwareId)
        {
            if (_service.DeleteSoftware(id, softwareId))
                return Ok();
            return NotFound();
        }
        // Actualiza el software de una computadora
        [HttpPut("{id}/software")]
        public IActionResult UpdateSoftware(int id, Software software)
        {
            if (_service.UpdateSoftware(id, software))
                return Ok();
            return NotFound();
        }
    }
}
