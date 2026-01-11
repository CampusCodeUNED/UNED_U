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
    public class LavadoService
    {
        private readonly IMemoryCache _cache;
        private const string CacheKey = "lavados";
        private int _lastId = 1;
        // Constructor que inicializa el servicio de Lavado
        public LavadoService(IMemoryCache cache)
        {
            _cache = cache;
            if (!_cache.TryGetValue(CacheKey, out List<Lavado> _))
            {
                _cache.Set(CacheKey, new List<Lavado>());
            }
        }
        // Método para inicializar algunos datos de ejemplo
        public List<Lavado> GetAll() => _cache.Get<List<Lavado>>(CacheKey)!;

        public void Add(Lavado lavado)
        {
            lavado.Id = _lastId++;
            if (lavado.Tipo != TipoLavado.LaJoya)
                lavado.Precio = ObtenerPrecioPorTipo(lavado.Tipo);
            var lista = GetAll();
            lista.Add(lavado);
            _cache.Set(CacheKey, lista);
        }
        public Lavado? GetById(int id) =>
            GetAll().FirstOrDefault(l => l.Id == id);
        // Obtiene un lavado por su ID
        public void Update(int id, Lavado actualizado)
        {
            var lista = GetAll();
            var index = lista.FindIndex(l => l.Id == id);
            if (index >= 0)
            {
                actualizado.Id = id;
                if (actualizado.Tipo != TipoLavado.LaJoya)
                    actualizado.Precio = ObtenerPrecioPorTipo(actualizado.Tipo);
                lista[index] = actualizado;
                _cache.Set(CacheKey, lista);
            }
        }
        // Actualiza un lavado existente por su ID
        public void Delete(int id)
        {
            var lista = GetAll();
            var l = lista.FirstOrDefault(x => x.Id == id);
            if (l != null)
            {
                lista.Remove(l);
                _cache.Set(CacheKey, lista);
            }
        }
        // Elimina un lavado por su ID
        private decimal ObtenerPrecioPorTipo(TipoLavado tipo)
        {
            return tipo switch
            {
                TipoLavado.Basico => 8000,
                TipoLavado.Premium => 12000,
                TipoLavado.Deluxe => 20000,
                TipoLavado.LaJoya => 0, 
                _ => 0
            };
        }
    }
}
