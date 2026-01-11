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
    // Controlador para manejar las operaciones CRUD de Vehículo
    public class VehiculoController : ControllerBase
    {
        private readonly VehiculoService _service;

        public VehiculoController(VehiculoService service)
        {
            _service = service;
        }

        [HttpGet]
        // Obtiene todos los vehículos
        public ActionResult<IEnumerable<Vehiculo>> GetAll() => Ok(_service.GetAll());

        [HttpGet("{placa}")]
        // Obtiene un vehículo por su placa
        public ActionResult<Vehiculo> GetByPlaca(string placa)
        {
            var v = _service.GetByPlaca(placa);
            return v is null ? NotFound() : Ok(v);
        }

        [HttpGet("por-cliente/{identificacion}")]
        // Obtiene los vehículos asociados a un cliente por su identificación
        public IActionResult GetPorCliente(string identificacion)
        {
            var vehiculos = _service.GetByCliente(identificacion);
            return Ok(vehiculos);
        }



        [HttpPost]
        // Crea un nuevo vehículo
        public IActionResult Create([FromBody] Vehiculo v)
        {
            _service.Add(v);
            return CreatedAtAction(nameof(GetByPlaca), new { placa = v.Placa }, v);
        }

        [HttpPut("{placa}")]
        // Actualiza un vehículo existente por su placa
        public IActionResult Update(string placa, [FromBody] Vehiculo v)
        {
            _service.Update(placa, v);
            return NoContent();
        }

        [HttpDelete("{placa}")]
        // Elimina un vehículo por su placa
        public IActionResult Delete(string placa)
        {
            _service.Delete(placa);
            return NoContent();
        }
    }
}
