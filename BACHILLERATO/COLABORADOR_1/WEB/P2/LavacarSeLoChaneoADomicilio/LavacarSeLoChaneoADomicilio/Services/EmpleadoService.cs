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
using Microsoft.Extensions.Caching.Memory;

namespace LavacarSeLoChaneoADomicilio.Services
{
    public class EmpleadoService
    {
        private readonly IMemoryCache _cache;
        private const string CacheKey = "empleados";
        // Clave para almacenar la lista de empleados en caché
        public EmpleadoService(IMemoryCache cache)
        {
            _cache = cache;
            if (!_cache.TryGetValue(CacheKey, out List<Empleado> _))
            {
                _cache.Set(CacheKey, new List<Empleado>());
            }
        }
        // Constructor que inicializa el servicio y la caché de empleados
        public List<Empleado> GetAll() => _cache.Get<List<Empleado>>(CacheKey)!;

        public void Add(Empleado empleado)
        {
            var empleados = GetAll();
            empleados.Add(empleado);
            _cache.Set(CacheKey, empleados);
        }
        // Agrega un nuevo empleado a la lista y actualiza la caché
        public Empleado? GetByCedula(string cedula) =>
            GetAll().FirstOrDefault(e => e.Cedula == cedula);
        // Obtiene un empleado por su cédula
        public void Update(string cedulaOriginal, Empleado empleadoActualizado)
        {
            var empleados = GetAll();
            var index = empleados.FindIndex(e => e.Cedula == cedulaOriginal);
            if (index >= 0)
            {
                empleados[index] = empleadoActualizado;
                _cache.Set(CacheKey, empleados);
            }
        }

        // Actualiza un empleado existente por su cédula y actualiza la caché
        public void Delete(string cedula)
        {
            var empleados = GetAll();
            var empleado = empleados.FirstOrDefault(e => e.Cedula == cedula);
            if (empleado != null)
            {
                empleados.Remove(empleado);
                _cache.Set(CacheKey, empleados);
            }
        }
    }
}
