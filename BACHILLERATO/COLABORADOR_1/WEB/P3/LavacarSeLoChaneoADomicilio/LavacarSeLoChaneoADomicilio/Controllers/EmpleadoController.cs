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
using LavacarSeLoChaneoADomicilio.Models;
using LavacarSeLoChaneoADomicilio.Services;
using Microsoft.AspNetCore.Mvc;

namespace LavacarSeLoChaneoADomicilio.Controllers
{
    public class EmpleadoController : Controller
    {
        private readonly EmpleadoHttpService _service;

        public EmpleadoController(EmpleadoHttpService service)
        {
            _service = service;
        }
        // Ruta principal para listar empleados
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
            // Asignar la lista filtrada a la vista
            ViewBag.Busqueda = busqueda;
            return View(empleados);
        }
        // Ruta para crear un nuevo empleado
        public IActionResult Create() => View();
        // Maneja la creación de un nuevo empleado
        [HttpPost]
        public async Task<IActionResult> Create(Empleado emp)
        {
            if (!ModelState.IsValid) return View(emp);
            await _service.CreateAsync(emp);
            return RedirectToAction("Index");
        }
        // Ruta para editar un empleado existente
        public async Task<IActionResult> Edit(string cedula)
        {
            var emp = await _service.GetByCedulaAsync(cedula);
            if (emp == null) return NotFound();
            return View(emp);
        }
        // Maneja la edición de un empleado existente
        [HttpPost]
        public async Task<IActionResult> Edit(string cedulaOriginal, Empleado emp)
        {
            if (!ModelState.IsValid) return View(emp);
            await _service.UpdateAsync(cedulaOriginal, emp);
            return RedirectToAction("Index");
        }
        // Maneja la eliminación de un empleado
        public async Task<IActionResult> Delete(string cedula)
        {
            await _service.DeleteAsync(cedula);
            return RedirectToAction("Index");
        }
        // Ruta para ver los detalles de un empleado
        public async Task<IActionResult> Details(string cedula)
        {
            var emp = await _service.GetByCedulaAsync(cedula);
            if (emp == null) return NotFound();
            return View(emp);
        }
    }

}
