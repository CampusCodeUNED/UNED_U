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
    public class VehiculoService
    {
        private readonly IMemoryCache _cache;
        private const string CacheKey = "vehiculos";
        // Clave para almacenar la lista de vehículos en caché
        public VehiculoService(IMemoryCache cache)
        {
            _cache = cache;
            if (!_cache.TryGetValue(CacheKey, out List<Vehiculo> _))
            {
                _cache.Set(CacheKey, new List<Vehiculo>());
            }
        }
        // Constructor que inicializa el servicio y asegura que la lista de vehículos esté en caché
        public List<Vehiculo> GetAll() => _cache.Get<List<Vehiculo>>(CacheKey)!;

        public void Add(Vehiculo v)
        {
            var lista = GetAll();
            lista.Add(v);
            _cache.Set(CacheKey, lista);
        }
        // Agrega un nuevo vehículo a la lista en caché
        public Vehiculo? GetByPlaca(string placa) =>
            GetAll().FirstOrDefault(v => v.Placa.Equals(placa, StringComparison.OrdinalIgnoreCase));
        // Obtiene un vehículo por su placa desde la lista en caché
        public void Update(string placaOriginal, Vehiculo actualizado)
        {
            var lista = GetAll();
            var index = lista.FindIndex(v => v.Placa.Equals(placaOriginal, StringComparison.OrdinalIgnoreCase));
            if (index >= 0)
            {
                lista[index] = actualizado;
                _cache.Set(CacheKey, lista);
            }
        }
        // Actualiza un vehículo existente en la lista en caché
        public void Delete(string placa)
        {
            var lista = GetAll();
            var vehiculo = lista.FirstOrDefault(v => v.Placa.Equals(placa, StringComparison.OrdinalIgnoreCase));
            if (vehiculo != null)
            {
                lista.Remove(vehiculo);
                _cache.Set(CacheKey, lista);
            }
        }
    }
}
