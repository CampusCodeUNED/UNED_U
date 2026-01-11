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
    public class LavadoHttpService
    {
        private readonly HttpClient _http;
        private readonly string _urlBase = "https://localhost:7171/api/lavado";
        // URL base del servicio de Lavado
        public LavadoHttpService(HttpClient http)
        {
            _http = http;
        }
        // Constructor que recibe el HttpClient para realizar las peticiones HTTP
        public async Task<List<Lavado>> GetAllAsync()
        {
            return await _http.GetFromJsonAsync<List<Lavado>>(_urlBase) ?? new();
        }
        // Método para obtener todos los lavados
        public async Task<Lavado?> GetByIdAsync(int id)
        {
            return await _http.GetFromJsonAsync<Lavado>($"{_urlBase}/{id}");
        }
        // Método para obtener un lavado por su ID
        public async Task CreateAsync(Lavado l)
        {
            await _http.PostAsJsonAsync(_urlBase, l);
        }
        // Método para crear un nuevo lavado
        public async Task UpdateAsync(int id, Lavado l)
        {
            await _http.PutAsJsonAsync($"{_urlBase}/{id}", l);
        }
        // Método para actualizar un lavado existente
        public async Task DeleteAsync(int id)
        {
            await _http.DeleteAsync($"{_urlBase}/{id}");
        }
    }
}
