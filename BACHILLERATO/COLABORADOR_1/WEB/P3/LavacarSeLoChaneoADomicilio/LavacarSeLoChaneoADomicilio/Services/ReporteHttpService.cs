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
using AppWeb.Models;

namespace AppWeb.Services
{
    public class ReporteHttpService
    {// Servicio para manejar las operaciones de reporte
        private readonly HttpClient _http;
        private readonly string _url = "https://localhost:7171/api/reporte/pendientes-contacto";

        public ReporteHttpService(HttpClient http)
        {
            _http = http;
        }
        // Método para obtener la lista de clientes pendientes de contacto
        public async Task<List<ClientePendienteContacto>> ObtenerPendientesAsync()
        {
            return await _http.GetFromJsonAsync<List<ClientePendienteContacto>>(_url)
                   ?? new();
        }
    }
}
