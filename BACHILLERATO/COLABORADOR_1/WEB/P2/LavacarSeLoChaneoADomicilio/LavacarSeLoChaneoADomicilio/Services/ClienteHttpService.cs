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
using Newtonsoft.Json;
using System.Net.Http.Headers;

namespace LavacarSeLoChaneoADomicilio.Services
{
    public class ClienteHttpService
    {// Servicio para manejar las operaciones HTTP relacionadas con Cliente
        private readonly HttpClient _http;
        private const string BaseUrl = "https://localhost:7171/api/cliente"; // URL base de la API de Cliente

        public ClienteHttpService(HttpClient http)
        {
            _http = http;
            _http.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
        }
        // Método para obtener todos los clientes de la API
        public async Task<List<Cliente>> GetAllAsync()
        {
            var response = await _http.GetAsync(BaseUrl);
            response.EnsureSuccessStatusCode();
            var json = await response.Content.ReadAsStringAsync();// Leer la respuesta JSON
            return JsonConvert.DeserializeObject<List<Cliente>>(json)!;
        }
        // Método para buscar un cliente por su identificación
        public async Task<Cliente?> GetByIdAsync(string id)
        {
            var response = await _http.GetAsync($"{BaseUrl}/{id}");
            return response.IsSuccessStatusCode
                ? JsonConvert.DeserializeObject<Cliente>(await response.Content.ReadAsStringAsync())
                : null;
        }
        // Método para crear un nuevo cliente en la API
        public async Task CreateAsync(Cliente c)
        {
            var content = new StringContent(JsonConvert.SerializeObject(c), System.Text.Encoding.UTF8, "application/json");
            var response = await _http.PostAsync(BaseUrl, content);
            response.EnsureSuccessStatusCode();
        }
        // Método para actualizar un cliente existente en la API
        public async Task UpdateAsync(string id, Cliente c)
        {
            var content = new StringContent(JsonConvert.SerializeObject(c), System.Text.Encoding.UTF8, "application/json");
            var response = await _http.PutAsync($"{BaseUrl}/{id}", content);
            response.EnsureSuccessStatusCode();
        }
        // Método para eliminar un cliente por su identificación
        public async Task DeleteAsync(string id)
        {
            var response = await _http.DeleteAsync($"{BaseUrl}/{id}");
            response.EnsureSuccessStatusCode();
        }
    }
}
