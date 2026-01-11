/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso: FUNDAMENTOS DE PROGRAMACION WEB
Código: 03075 
Proyecto #1: Lavacar se lo chaneo  a domicilio
Tutor: Sigifredo Leitón Luna
Grupo: 04
Estudiante: Francisco Campos Sandi
Cédula: 114750560
II Cuatrimestre 2025
*/
using API.Models;

namespace API.Services
{// Servicio para generar reportes de clientes pendientes de contacto
    public class ReporteService
    {// Dependencias de servicios para acceder a clientes y vehículos
        private readonly ClienteService _clienteService;
        private readonly VehiculoService _vehiculoService;

        public ReporteService(ClienteService clienteService, VehiculoService vehiculoService)
        {
            _clienteService = clienteService;
            _vehiculoService = vehiculoService;
        }
        // Método para obtener los clientes con vehículos pendientes de contacto
        public List<ClientePendienteContacto> ObtenerClientesPendientes()
        {
            var haceUnMes = DateTime.Today.AddMonths(-1);
            var clientes = _clienteService.GetAll();
            var vehiculos = _vehiculoService.GetAll();

            var resultado = new List<ClientePendienteContacto>();
            // Filtrar clientes que tienen vehículos pendientes de contacto
            foreach (var cliente in clientes)
            {
                var pendientes = vehiculos
                    .Where(v => v.IdentificacionCliente == cliente.Identificacion)
                    .Where(v => v.UltimaFechaAtencion == null || v.UltimaFechaAtencion <= haceUnMes)
                    .Select(v => v.Placa)
                    .ToList();

                if (pendientes.Any())
                {
                    resultado.Add(new ClientePendienteContacto
                    {
                        Identificacion = cliente.Identificacion,
                        NombreCompleto = cliente.NombreCompleto,
                        Telefono = cliente.Telefono,
                        PlacasVehiculosPendientes = pendientes
                    });
                }
            }

            return resultado;
        }
    }
}
