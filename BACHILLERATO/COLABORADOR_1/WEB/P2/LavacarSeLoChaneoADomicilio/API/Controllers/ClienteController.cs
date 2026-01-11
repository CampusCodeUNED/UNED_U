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
    // Controlador para manejar las operaciones CRUD de Cliente
    [ApiController]
    [Route("api/[controller]")]
    public class ClienteController : ControllerBase
    { // Servicio para manejar la lógica de negocio de Cliente
        private readonly ClienteService _service;
        // Constructor que recibe el servicio de Cliente
        public ClienteController(ClienteService service)
        {
            _service = service;
        }
        // Métodos HTTP para manejar las operaciones CRUD de Cliente
        [HttpGet]
        // Obtiene todos los clientes
        public ActionResult<IEnumerable<Cliente>> GetAll() => Ok(_service.GetAll()); 

        [HttpGet("{id}")]
        public ActionResult<Cliente> GetById(string id)// Obtiene un cliente por su identificación
        {
            var cliente = _service.GetById(id);
            return cliente == null ? NotFound() : Ok(cliente);
        }

        [HttpPost]
        public IActionResult Create([FromBody] Cliente cliente)
        {// Crea un nuevo cliente
            _service.Add(cliente);
            return CreatedAtAction(nameof(GetById), new { id = cliente.Identificacion }, cliente);
        }

        [HttpPut("{id}")]
        public IActionResult Update(string id, [FromBody] Cliente cliente)// Actualiza un cliente existente
        {
            _service.Update(id, cliente);
            return NoContent();
        }

        [HttpDelete("{id}")]
        public IActionResult Delete(string id)// Elimina un cliente por su identificación
        {
            _service.Delete(id);
            return NoContent();
        }
    }
}
