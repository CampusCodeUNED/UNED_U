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
{// Controlador para manejar las operaciones de reporte
    [ApiController]
    [Route("api/[controller]")]
    public class ReporteController : ControllerBase
    {
        private readonly ReporteService _service;
        // Constructor que recibe los servicios de Cliente y Vehículo
        public ReporteController(ClienteService clienteService, VehiculoService vehiculoService)
        {
            _service = new ReporteService(clienteService, vehiculoService);
        }

        [HttpGet("pendientes-contacto")]
        // Obtiene los clientes con vehículos pendientes de contacto
        public ActionResult<IEnumerable<ClientePendienteContacto>> GetPendientes()
        {
            var datos = _service.ObtenerClientesPendientes();
            return Ok(datos);
        }
    }

}
