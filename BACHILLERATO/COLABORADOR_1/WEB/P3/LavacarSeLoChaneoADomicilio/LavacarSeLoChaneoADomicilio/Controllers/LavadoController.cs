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
    public class LavadoController : Controller
    {// Controlador para manejar las operaciones de Lavado
        private readonly LavadoHttpService _lavadoService;
        private readonly ClienteHttpService _clienteService;
        private readonly VehiculoHttpService _vehiculoService;
        private readonly EmpleadoHttpService _empleadoService;
        // Constructor que recibe los servicios necesarios
        public LavadoController(
            LavadoHttpService lavadoService,
            ClienteHttpService clienteService,
            VehiculoHttpService vehiculoService,
            EmpleadoHttpService empleadoService)
        {// Inicializa los servicios inyectados
            _lavadoService = lavadoService;
            _clienteService = clienteService;
            _vehiculoService = vehiculoService;
            _empleadoService = empleadoService;
        }
        // Ruta principal para listar lavados
        public async Task<IActionResult> Index(string? busqueda)
        {
            var lavados = await _lavadoService.GetAllAsync();
            if (!string.IsNullOrEmpty(busqueda))
            {
                busqueda = busqueda.ToLower();
                lavados = lavados.Where(l =>
                    l.VehiculoPlaca.ToLower().Contains(busqueda) ||
                    l.ClienteId.ToLower().Contains(busqueda) ||
                    l.EmpleadoCedula.ToLower().Contains(busqueda) ||
                    l.Tipo.ToString().ToLower().Contains(busqueda) ||
                    l.Estado.ToString().ToLower().Contains(busqueda)
                ).ToList();
            }
            ViewBag.Busqueda = busqueda;
            return View(lavados);
        }
        // Ruta para crear un nuevo lavado
        public async Task<IActionResult> Create()
        {
            await CargarListasAsync();
            return View();
        }
        // Maneja la creación de un nuevo lavado
        [HttpPost]
        public async Task<IActionResult> Create(Lavado l)
        {
            if (!ModelState.IsValid)
            {
                await CargarListasAsync();
                return View(l);
            }

            l.Cliente = null;
            l.Vehiculo = null;
            l.Empleado = null;

            await _lavadoService.CreateAsync(l);
            return RedirectToAction("Index");
        }
        // Ruta para editar un lavado existente
        public async Task<IActionResult> Edit(int id)
        {
            var l = await _lavadoService.GetByIdAsync(id);
            if (l == null) return NotFound();

            await CargarListasAsync(); 
            return View(l);
        }
        // Maneja la edición de un lavado existente
        [HttpPost]
        public async Task<IActionResult> Edit(int id, Lavado l)
        {
            if (!ModelState.IsValid)
            {
                await CargarListasAsync();
                return View(l);
            }

            l.Cliente = null;
            l.Vehiculo = null;
            l.Empleado = null;

            await _lavadoService.UpdateAsync(id, l);
            return RedirectToAction("Index");
        }
        // Ruta para ver los detalles de un lavado
        public async Task<IActionResult> Details(int id)
        {
            var l = await _lavadoService.GetByIdAsync(id);
            return l == null ? NotFound() : View(l);
        }
        // Maneja la eliminación de un lavado
        public async Task<IActionResult> Delete(int id)
        {
            await _lavadoService.DeleteAsync(id);
            return RedirectToAction("Index");
        }
        // Carga las listas de clientes, empleados y vehículos para los formularios
        private async Task CargarListasAsync()
        {
            ViewBag.Clientes = await _clienteService.GetAllAsync();
            ViewBag.Empleados = await _empleadoService.GetAllAsync();
            ViewBag.Vehiculos = await _vehiculoService.GetAllAsync();
        }
    }
}
