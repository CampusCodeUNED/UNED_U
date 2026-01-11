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
    public class ReporteController : ControllerBase
    {// Controlador para manejar reportes de clientes pendientes de contacto
        private readonly ReporteService _service;

        public ReporteController(ReporteService service)
        {
            _service = service;
        }
        // Ruta para obtener todos los clientes pendientes de contacto
        [HttpGet("pendientes-contacto")]
        public async Task<ActionResult<IEnumerable<ClientePendienteContacto>>> GetPendientes()
        {
            var datos = await _service.ObtenerClientesPendientesAsync();
            return Ok(datos);
        }
    }


}
