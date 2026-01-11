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
    public class ClienteService
    {
        private readonly IMemoryCache _cache;
        private const string CacheKey = "clientes";
        // llave para acceder a la lista de clientes en caché
        public ClienteService(IMemoryCache cache)
        {
            _cache = cache;
            if (!_cache.TryGetValue(CacheKey, out List<Cliente> _))
            {
                _cache.Set(CacheKey, new List<Cliente>());
            }
        }

        public List<Cliente> GetAll() => _cache.Get<List<Cliente>>(CacheKey)!;
        // Obtiene todos los clientes de la caché
        public void Add(Cliente c)
        {
            var lista = GetAll();
            lista.Add(c);
            _cache.Set(CacheKey, lista);
        }
        // Agrega un nuevo cliente a la lista en caché
        public Cliente? GetById(string identificacion) =>
            GetAll().FirstOrDefault(c => c.Identificacion == identificacion);
        // Obtiene un cliente por su identificación desde la caché
        public void Update(string idOriginal, Cliente actualizado)
        {
            var lista = GetAll();
            var index = lista.FindIndex(c => c.Identificacion == idOriginal);
            if (index >= 0)
            {
                lista[index] = actualizado;
                _cache.Set(CacheKey, lista);
            }
        }
        // Actualiza un cliente existente en la lista de caché
        public void Delete(string identificacion)
        {
            var lista = GetAll();
            var cliente = lista.FirstOrDefault(c => c.Identificacion == identificacion);
            if (cliente != null)
            {
                lista.Remove(cliente);
                _cache.Set(CacheKey, lista);
            }
        }
    }
}
