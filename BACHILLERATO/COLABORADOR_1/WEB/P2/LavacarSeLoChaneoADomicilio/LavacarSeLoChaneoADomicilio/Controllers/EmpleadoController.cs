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
using LavacarSeLoChaneoADomicilio.Models;
using LavacarSeLoChaneoADomicilio.Services;
using Microsoft.AspNetCore.Mvc;

namespace LavacarSeLoChaneoADomicilio.Controllers
{// Controlador para manejar las operaciones CRUD de Empleado
    public class EmpleadoController : Controller
    {
        private readonly EmpleadoHttpService _service;

        public EmpleadoController(EmpleadoHttpService service)
        {
            _service = service;
        }
        // Acción para mostrar la lista de empleados con opción de búsqueda
        public async Task<IActionResult> Index(string? busqueda)
        {
            var empleados = await _service.GetAllAsync();

            if (!string.IsNullOrEmpty(busqueda))
            {
                busqueda = busqueda.ToLower();
                empleados = empleados
                    .Where(e => e.Cedula.ToLower().Contains(busqueda) ||
                                e.SalarioPorDia.ToString().Contains(busqueda) ||
                                e.DiasVacacionesAcumulados.ToString().Contains(busqueda))
                    .ToList();
            }

            ViewBag.Busqueda = busqueda;
            return View(empleados);
        }
        // Acción para mostrar el formulario de creación de un nuevo empleado
        public IActionResult Create() => View();

        [HttpPost]
        public async Task<IActionResult> Create(Empleado emp)
        {
            if (!ModelState.IsValid) return View(emp);
            await _service.CreateAsync(emp);
            return RedirectToAction("Index");
        }
        // Acción para mostrar el formulario de edición de un empleado existente
        public async Task<IActionResult> Edit(string cedula)
        {
            var emp = await _service.GetByCedulaAsync(cedula);
            if (emp == null) return NotFound();
            return View(emp);
        }
        // Acción para manejar la edición de un empleado existente
        [HttpPost]
        public async Task<IActionResult> Edit(string cedulaOriginal, Empleado emp)
        {
            if (!ModelState.IsValid) return View(emp);
            await _service.UpdateAsync(cedulaOriginal, emp);
            return RedirectToAction("Index");
        }
        // Acción para manejar la eliminación de un empleado
        public async Task<IActionResult> Delete(string cedula)
        {
            await _service.DeleteAsync(cedula);
            return RedirectToAction("Index");
        }
        // Acción para mostrar los detalles de un empleado específico
        public async Task<IActionResult> Details(string cedula)
        {
            var emp = await _service.GetByCedulaAsync(cedula);
            if (emp == null) return NotFound();
            return View(emp);
        }
    }

}
