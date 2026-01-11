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

    public class VehiculoHttpService
    {
        private readonly HttpClient _http;
        private const string BaseUrl = "https://localhost:7171/api/vehiculo"; 

        public VehiculoHttpService(HttpClient http)
        {
            _http = http;
            _http.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
        }
        // Obtiene todos los vehículos
        public async Task<List<Vehiculo>> GetAllAsync()
        {
            var response = await _http.GetAsync(BaseUrl);
            response.EnsureSuccessStatusCode();
            var json = await response.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<List<Vehiculo>>(json)!;
        }

        // Obtiene vehículos por el ID del cliente
        public async Task<Vehiculo?> GetByPlacaAsync(string placa)
        {
            var response = await _http.GetAsync($"{BaseUrl}/{placa}");
            if (!response.IsSuccessStatusCode) return null;
            return JsonConvert.DeserializeObject<Vehiculo>(await response.Content.ReadAsStringAsync());
        }
        // Obtiene vehículos por el ID del cliente
        public async Task CreateAsync(Vehiculo v)
        {
            var content = new StringContent(JsonConvert.SerializeObject(v), System.Text.Encoding.UTF8, "application/json");
            var response = await _http.PostAsync(BaseUrl, content);

            if (!response.IsSuccessStatusCode)
            {
                var errorContent = await response.Content.ReadAsStringAsync();
                throw new HttpRequestException($"Error en POST: {response.StatusCode} - {errorContent}");
            }

            response.EnsureSuccessStatusCode();
        }

        // Obtiene vehículos por el ID del cliente
        public async Task UpdateAsync(string placa, Vehiculo v)
        {
            var content = new StringContent(JsonConvert.SerializeObject(v), System.Text.Encoding.UTF8, "application/json");
            var response = await _http.PutAsync($"{BaseUrl}/{placa}", content);
            response.EnsureSuccessStatusCode();
        }
        // Elimina un vehículo por su placa
        public async Task DeleteAsync(string placa)
        {
            var response = await _http.DeleteAsync($"{BaseUrl}/{placa}");
            response.EnsureSuccessStatusCode();
        }
    }
}
