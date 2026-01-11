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
    public class EmpleadoHttpService
    {// Servicio HTTP para manejar las operaciones de Empleado
        private readonly HttpClient _http;
        private const string BaseUrl = "https://localhost:7171/api/empleado"; 

        public EmpleadoHttpService(HttpClient http)
        {
            _http = http;
            _http.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
        }
        // Obtiene todos los empleados
        public async Task<List<Empleado>> GetAllAsync()
        {
            var response = await _http.GetAsync(BaseUrl);
            response.EnsureSuccessStatusCode();
            var json = await response.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<List<Empleado>>(json)!;
        }
        // Obtiene un empleado por su cédula
        public async Task<Empleado?> GetByCedulaAsync(string cedula)
        {
            var response = await _http.GetAsync($"{BaseUrl}/{cedula}");
            if (!response.IsSuccessStatusCode) return null;
            var json = await response.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<Empleado>(json);
        }
        // Crea un nuevo empleado
        public async Task CreateAsync(Empleado emp)
        {
            var content = new StringContent(JsonConvert.SerializeObject(emp), System.Text.Encoding.UTF8, "application/json");
            var response = await _http.PostAsync(BaseUrl, content);
            response.EnsureSuccessStatusCode();
        }
        // Actualiza un empleado existente
        public async Task UpdateAsync(string cedula, Empleado emp)
        {
            var content = new StringContent(JsonConvert.SerializeObject(emp), System.Text.Encoding.UTF8, "application/json");
            var response = await _http.PutAsync($"{BaseUrl}/{cedula}", content);
            response.EnsureSuccessStatusCode();
        }
        // Elimina un empleado por su cédula
        public async Task DeleteAsync(string cedula)
        {
            var response = await _http.DeleteAsync($"{BaseUrl}/{cedula}");
            response.EnsureSuccessStatusCode();
        }
    }
}
