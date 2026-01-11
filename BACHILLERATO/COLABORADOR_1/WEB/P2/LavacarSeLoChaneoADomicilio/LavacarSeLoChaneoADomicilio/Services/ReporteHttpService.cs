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
using AppWeb.Models;

namespace AppWeb.Services
{
    public class ReporteHttpService
    {// Servicio para manejar las operaciones de Reporte
        private readonly HttpClient _http;
        private readonly string _url = "https://localhost:7171/api/reporte/pendientes-contacto";// URL del endpoint para obtener los clientes pendientes de contacto
        // Servicio para obtener los clientes pendientes de contacto a través de HTTP
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
