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
    public class VehiculoController : Controller
    {// Controlador para manejar las operaciones de Vehículo
        private readonly VehiculoHttpService _service;
        private readonly ClienteHttpService _clienteService;

        public VehiculoController(VehiculoHttpService vehiculoService, ClienteHttpService clienteService)
        {
            _service = vehiculoService;
            _clienteService = clienteService;
        }
        // Ruta principal para listar vehículos
        public async Task<IActionResult> Index(string? busqueda)
        {
            var vehiculos = await _service.GetAllAsync();

            if (!string.IsNullOrEmpty(busqueda))
            {
                busqueda = busqueda.ToLower();
                vehiculos = vehiculos
                    .Where(v => v.Placa.ToLower().Contains(busqueda) ||
                                v.Marca.ToLower().Contains(busqueda) ||
                                v.Color.ToLower().Contains(busqueda))
                    .ToList();
            }

            ViewBag.Busqueda = busqueda;
            return View(vehiculos);
        }
        // Ruta para crear un nuevo vehículo
        public async Task<IActionResult> Create()
        {
            var clientes = await _clienteService.GetAllAsync();
            ViewBag.Clientes = clientes;
            return View();
        }
        // Maneja la creación de un nuevo vehículo
        [HttpPost]
        public async Task<IActionResult> Create(Vehiculo v)
        {
            if (!ModelState.IsValid)
            {
                ViewBag.Clientes = await _clienteService.GetAllAsync();
                return View(v);
            }

            await _service.CreateAsync(v);
            return RedirectToAction("Index");
        }
        // Ruta para editar un vehículo existente

        public async Task<IActionResult> Edit(string placa)
        {
            var v = await _service.GetByPlacaAsync(placa);
            if (v == null) return NotFound();

            ViewBag.Clientes = await _clienteService.GetAllAsync(); 
            return View(v);
        }

        // Maneja la actualización de un vehículo existente
        [HttpPost]
        public async Task<IActionResult> Edit(string placaOriginal, Vehiculo v)
        {
            if (!ModelState.IsValid)
            {
                ViewBag.Clientes = await _clienteService.GetAllAsync();
                return View(v);
            }

            v.Cliente = null;

            await _service.UpdateAsync(placaOriginal, v);
            return RedirectToAction("Index");
        }

        // Ruta para ver los detalles de un vehículo
        public async Task<IActionResult> Details(string placa)
        {
            var v = await _service.GetByPlacaAsync(placa);
            return v == null ? NotFound() : View(v);
        }
        // Maneja la eliminación de un vehículo
        public async Task<IActionResult> Delete(string placa)
        {
            await _service.DeleteAsync(placa);
            return RedirectToAction("Index");
        }
    }

}
