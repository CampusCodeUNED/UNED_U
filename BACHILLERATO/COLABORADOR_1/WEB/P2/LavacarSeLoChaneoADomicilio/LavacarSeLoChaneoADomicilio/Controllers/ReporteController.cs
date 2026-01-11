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
using AppWeb.Services;
using Microsoft.AspNetCore.Mvc;

namespace AppWeb.Controllers
{// Controlador para manejar los reportes de clientes pendientes
    public class ReporteController : Controller
    {
        private readonly ReporteHttpService _service;

        public ReporteController(ReporteHttpService service)
        {
            _service = service;
        }
        // Acción para mostrar la lista de clientes pendientes de contacto
        public async Task<IActionResult> ClientesPendientes()
        {
            var lista = await _service.ObtenerPendientesAsync();
            return View(lista);
        }
    }
}
