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
    public class ClienteController : Controller
    {
        private readonly ClienteHttpService _service;

        public ClienteController(ClienteHttpService service)
        {
            _service = service;
        }
        // Ruta principal para listar clientes
        public async Task<IActionResult> Index(string? busqueda)
        {
            var clientes = await _service.GetAllAsync();

            if (!string.IsNullOrEmpty(busqueda))
            {
                busqueda = busqueda.ToLower();
                clientes = clientes
                    .Where(c => c.Identificacion.ToLower().Contains(busqueda) ||
                                c.NombreCompleto.ToLower().Contains(busqueda) ||
                                c.Provincia.ToLower().Contains(busqueda) ||
                                c.Telefono.Contains(busqueda))
                    .ToList();
            }
            // Asignar la lista filtrada a la vista
            ViewBag.Busqueda = busqueda;
            return View(clientes);
        }
        // Ruta para crear un nuevo cliente
        public IActionResult Create() => View();
        // Maneja la creación de un nuevo cliente
        [HttpPost]
        public async Task<IActionResult> Create(Cliente c)
        {
            if (!ModelState.IsValid) return View(c);
            await _service.CreateAsync(c);
            return RedirectToAction("Index");
        }
        // Ruta para editar un cliente existente
        public async Task<IActionResult> Edit(string identificacion)
        {
            var cliente = await _service.GetByIdAsync(identificacion);
            return cliente == null ? NotFound() : View(cliente);
        }
        // Maneja la actualización de un cliente existente
        [HttpPost]
        public async Task<IActionResult> Edit(string idOriginal, Cliente c)
        {
            if (!ModelState.IsValid) return View(c);
            await _service.UpdateAsync(idOriginal, c);
            return RedirectToAction("Index");
        }
        // Ruta para ver los detalles de un cliente
        public async Task<IActionResult> Details(string identificacion)
        {
            var cliente = await _service.GetByIdAsync(identificacion);
            if (cliente == null) return NotFound();

            var vehiculos = await _service.GetVehiculosDeClienteAsync(identificacion);
            ViewBag.Vehiculos = vehiculos;

            return View(cliente);
        }

        // Maneja la eliminación de un cliente
        public async Task<IActionResult> Delete(string identificacion)
        {
            await _service.DeleteAsync(identificacion);
            return RedirectToAction("Index");
        }


    }

}
