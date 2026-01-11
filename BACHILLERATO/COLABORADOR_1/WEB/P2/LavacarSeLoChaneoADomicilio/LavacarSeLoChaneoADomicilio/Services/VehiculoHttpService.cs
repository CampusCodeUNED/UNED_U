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

    public class VehiculoHttpService
    {// Servicio para manejar las operaciones CRUD de Vehículo
        private readonly HttpClient _http;
        private const string BaseUrl = "https://localhost:7171/api/vehiculo"; // URL base de la API de Vehículo
        // Constructor que recibe el HttpClient inyectado
        public VehiculoHttpService(HttpClient http)
        {
            _http = http;
            _http.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
        }
        // Método para obtener todos los vehículos
        public async Task<List<Vehiculo>> GetAllAsync()
        {
            var response = await _http.GetAsync(BaseUrl);
            response.EnsureSuccessStatusCode();
            var json = await response.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<List<Vehiculo>>(json)!;
        }
        // Método para obtener un vehículo por su placa
        public async Task<Vehiculo?> GetByPlacaAsync(string placa)
        {
            var response = await _http.GetAsync($"{BaseUrl}/{placa}");
            if (!response.IsSuccessStatusCode) return null;
            return JsonConvert.DeserializeObject<Vehiculo>(await response.Content.ReadAsStringAsync());
        }
        // Método para obtener los vehículos asociados a un cliente por su identificación
        public async Task CreateAsync(Vehiculo v)
        {
            var content = new StringContent(JsonConvert.SerializeObject(v), System.Text.Encoding.UTF8, "application/json");
            var response = await _http.PostAsync(BaseUrl, content);
            response.EnsureSuccessStatusCode();
        }
        // Método para crear un nuevo vehículo
        public async Task UpdateAsync(string placa, Vehiculo v)
        {
            var content = new StringContent(JsonConvert.SerializeObject(v), System.Text.Encoding.UTF8, "application/json");
            var response = await _http.PutAsync($"{BaseUrl}/{placa}", content);
            response.EnsureSuccessStatusCode();
        }
        // Método para actualizar un vehículo existente por su placa
        public async Task DeleteAsync(string placa)
        {
            var response = await _http.DeleteAsync($"{BaseUrl}/{placa}");
            response.EnsureSuccessStatusCode();
        }
    }
}
