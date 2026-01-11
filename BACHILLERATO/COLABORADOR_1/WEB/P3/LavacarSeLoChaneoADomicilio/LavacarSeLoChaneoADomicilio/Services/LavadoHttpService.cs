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
using Microsoft.Extensions.Caching.Memory;

namespace LavacarSeLoChaneoADomicilio.Services
{
    public class LavadoHttpService
    {
        private readonly HttpClient _http;
        private readonly string _urlBase = "https://localhost:7171/api/lavado";
        // URL base de la API para los lavados
        public LavadoHttpService(HttpClient http)
        {
            _http = http;
        }

        public async Task<List<Lavado>> GetAllAsync()
        {
            return await _http.GetFromJsonAsync<List<Lavado>>(_urlBase) ?? new();
        }
        // Obtiene todos los lavados de la API
        public async Task<Lavado?> GetByIdAsync(int id)
        {
            return await _http.GetFromJsonAsync<Lavado>($"{_urlBase}/{id}");
        }
        // Obtiene un lavado por su ID desde la API
        public async Task CreateAsync(Lavado l)
        {
            try
            {
                var response = await _http.PostAsJsonAsync(_urlBase, l);

                if (!response.IsSuccessStatusCode)
                {
                    var errorContent = await response.Content.ReadAsStringAsync();
                    Console.WriteLine($"Error HTTP: {response.StatusCode}");
                    Console.WriteLine($"Contenido del error: {errorContent}");
                    throw new HttpRequestException($"Error en POST: {response.StatusCode} - {errorContent}");
                }
                // Verifica si la respuesta fue exitosa
                Console.WriteLine("HTTP POST exitoso");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Excepción en CreateAsync: {ex.Message}");
                throw;
            }
        }
        // Crea un nuevo lavado en la API
        public async Task UpdateAsync(int id, Lavado l)
        {
            await _http.PutAsJsonAsync($"{_urlBase}/{id}", l);
        }
        // Actualiza un lavado existente en la API
        public async Task DeleteAsync(int id)
        {
            await _http.DeleteAsync($"{_urlBase}/{id}");
        }
    }
}
