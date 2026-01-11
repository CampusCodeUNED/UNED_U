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
    public class VehiculoController : ControllerBase
    {
        private readonly VehiculoService _service;
        // Controlador para manejar vehículos
        public VehiculoController(VehiculoService service)
        {
            _service = service;
        }
        // Rutas de la API para manejar vehículos
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Vehiculo>>> GetAll()
            => Ok(await _service.GetAllAsync());
        // Obtiene un vehículo por su placa
        [HttpGet("{placa}")]
        public async Task<ActionResult<Vehiculo>> GetByPlaca(string placa)
        {
            var v = await _service.GetByPlacaAsync(placa);
            return v is null ? NotFound() : Ok(v);
        }
        // Obtiene vehículos por el ID del cliente
        [HttpGet("por-cliente/{identificacion}")]
        public async Task<IActionResult> GetPorCliente(string identificacion)
            => Ok(await _service.GetByClienteAsync(identificacion));
        // Crea un nuevo vehículo
        [HttpPost]
        public async Task<IActionResult> Create([FromBody] Vehiculo v)
        {
            Console.WriteLine(System.Text.Json.JsonSerializer.Serialize(v));

            await _service.AddAsync(v);
            return CreatedAtAction(nameof(GetByPlaca), new { placa = v.Placa }, v);
        }
        // Actualiza un vehículo existente

        [HttpPut("{placa}")]
        public async Task<IActionResult> Update(string placa, [FromBody] Vehiculo v)
        {
            var updated = await _service.UpdateAsync(placa, v);
            return updated ? NoContent() : NotFound();
        }
        // Elimina un vehículo por su placa
        [HttpDelete("{placa}")]
        public async Task<IActionResult> Delete(string placa)
        {
            await _service.DeleteAsync(placa);
            return NoContent();
        }
    }

}
