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
    public class ClienteController : ControllerBase
    {
        private readonly ClienteService _clienteService;
        private readonly VehiculoService _vehiculoService;
        // Constructor que recibe los servicios necesarios
        public ClienteController(ClienteService clienteService, VehiculoService vehiculoService)
        {
            _clienteService = clienteService;
            _vehiculoService = vehiculoService;
        }
        // Rutas de la API para manejar clientes
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Cliente>>> GetAll()
            => Ok(await _clienteService.GetAllAsync());
        // Obtiene un cliente por su ID
        [HttpGet("{id}")]
        public async Task<ActionResult<Cliente>> GetById(string id)
        {
            var cliente = await _clienteService.GetByIdAsync(id);
            return cliente == null ? NotFound() : Ok(cliente);
        }
        // Crea un nuevo cliente
        [HttpPost]
        public async Task<IActionResult> Create([FromBody] Cliente cliente)
        {
            await _clienteService.AddAsync(cliente);
            return CreatedAtAction(nameof(GetById), new { id = cliente.Identificacion }, cliente);
        }
        // Actualiza un cliente existente
        [HttpPut("{id}")]
        public async Task<IActionResult> Update(string id, [FromBody] Cliente cliente)
        {
            if (id != cliente.Identificacion) return BadRequest("ID no coincide.");
            await _clienteService.UpdateAsync(cliente);
            return NoContent();
        }
        // Elimina un cliente por su ID
        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(string id)
        {
            await _clienteService.DeleteAsync(id);
            return NoContent();
        }
        // Obtiene los vehículos asociados a un cliente por su ID
        [HttpGet("{id}/vehiculos")]
        public async Task<IActionResult> GetVehiculosDeCliente(string id)
        {
            var cliente = await _clienteService.GetByIdAsync(id);
            if (cliente == null)
                return NotFound(new { mensaje = "Cliente no encontrado" });

            return Ok(cliente.Vehiculos);
        }
    }

}
