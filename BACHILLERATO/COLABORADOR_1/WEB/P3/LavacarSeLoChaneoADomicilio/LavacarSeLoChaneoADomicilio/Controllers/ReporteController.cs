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
using AppWeb.Services;
using Microsoft.AspNetCore.Mvc;

namespace AppWeb.Controllers
{// Controlador para manejar los reportes
    public class ReporteController : Controller
    {
        private readonly ReporteHttpService _service;
        // Constructor que recibe el servicio de reporte
        public ReporteController(ReporteHttpService service)
        {
            _service = service;
        }
        // Ruta para la página de clientes pendientes
        public async Task<IActionResult> ClientesPendientes()
        {
            var lista = await _service.ObtenerPendientesAsync();
            return View(lista);
        }
    }
}
