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
{
    public class ClienteController : Controller
    {
        private readonly ClienteHttpService _service;
        // Constructor que recibe el servicio de Cliente
        public ClienteController(ClienteHttpService service)
        {
            _service = service;
        }
        // Acción para mostrar la lista de clientes con opción de búsqueda
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

            ViewBag.Busqueda = busqueda;
            return View(clientes);
        }
        // Acción para mostrar el formulario de creación de un nuevo cliente
        public IActionResult Create() => View();

        [HttpPost]
        // Acción para manejar la creación de un nuevo cliente
        public async Task<IActionResult> Create(Cliente c)
        {
            if (!ModelState.IsValid) return View(c);
            await _service.CreateAsync(c);
            return RedirectToAction("Index");
        }
        // Acción para mostrar el formulario de edición de un cliente existente
        public async Task<IActionResult> Edit(string identificacion)
        {
            var cliente = await _service.GetByIdAsync(identificacion);
            return cliente == null ? NotFound() : View(cliente);
        }
        // Acción para manejar la edición de un cliente existente
        [HttpPost]
        public async Task<IActionResult> Edit(string idOriginal, Cliente c)
        {
            if (!ModelState.IsValid) return View(c);
            await _service.UpdateAsync(idOriginal, c);
            return RedirectToAction("Index");
        }
        // Acción para mostrar los detalles de un cliente específico
        public async Task<IActionResult> Details(string identificacion)
        {
            var cliente = await _service.GetByIdAsync(identificacion);
            return cliente == null ? NotFound() : View(cliente);
        }
        // Acción para manejar la eliminación de un cliente
        public async Task<IActionResult> Delete(string identificacion)
        {
            await _service.DeleteAsync(identificacion);
            return RedirectToAction("Index");
        }
    }

}
