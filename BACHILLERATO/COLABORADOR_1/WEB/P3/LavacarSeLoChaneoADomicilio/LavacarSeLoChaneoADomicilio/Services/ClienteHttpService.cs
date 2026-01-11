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
using Newtonsoft.Json;
using System.Net.Http.Headers;

namespace LavacarSeLoChaneoADomicilio.Services
{
    public class ClienteHttpService
    {// Controlador para manejar las operaciones de Cliente
        private readonly HttpClient _http;
        private const string BaseUrl = "https://localhost:7171/api/cliente"; 

        public ClienteHttpService(HttpClient http)
        {
            _http = http;
            _http.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
        }
        // Obtiene todos los clientes
        public async Task<List<Cliente>> GetAllAsync()
        {
            var response = await _http.GetAsync(BaseUrl);
            response.EnsureSuccessStatusCode();
            var json = await response.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<List<Cliente>>(json)!;
        }
        // Obtiene un cliente por su ID
        public async Task<Cliente?> GetByIdAsync(string id)
        {
            var response = await _http.GetAsync($"{BaseUrl}/{id}");
            return response.IsSuccessStatusCode
                ? JsonConvert.DeserializeObject<Cliente>(await response.Content.ReadAsStringAsync())
                : null;
        }
        // Crea un nuevo cliente
        public async Task CreateAsync(Cliente c)
        {
            var content = new StringContent(JsonConvert.SerializeObject(c), System.Text.Encoding.UTF8, "application/json");
            var response = await _http.PostAsync(BaseUrl, content);
            response.EnsureSuccessStatusCode();
        }
        // Actualiza un cliente existente
        public async Task UpdateAsync(string id, Cliente c)
        {
            var content = new StringContent(JsonConvert.SerializeObject(c), System.Text.Encoding.UTF8, "application/json");
            var response = await _http.PutAsync($"{BaseUrl}/{id}", content);
            response.EnsureSuccessStatusCode();
        }
        // Elimina un cliente por su ID
        public async Task DeleteAsync(string id)
        {
            var response = await _http.DeleteAsync($"{BaseUrl}/{id}");
            response.EnsureSuccessStatusCode();
        }
        // Obtiene los vehículos asociados a un cliente por su ID
        public async Task<List<Vehiculo>> GetVehiculosDeClienteAsync(string id)
        {
            var response = await _http.GetAsync($"{BaseUrl}/{id}/vehiculos");
            response.EnsureSuccessStatusCode();
            var json = await response.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<List<Vehiculo>>(json)!;
        }

    }
}
