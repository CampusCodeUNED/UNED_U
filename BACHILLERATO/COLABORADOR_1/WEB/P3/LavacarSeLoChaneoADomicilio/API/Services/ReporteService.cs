using API.Models;

namespace API.Services
{
    public class ReporteService
    {
        private readonly ClienteService _clienteService;
        private readonly VehiculoService _vehiculoService;

        public ReporteService(ClienteService clienteService, VehiculoService vehiculoService)
        {
            _clienteService = clienteService;
            _vehiculoService = vehiculoService;
        }

        public async Task<List<ClientePendienteContacto>> ObtenerClientesPendientesAsync()
        {
            var haceUnMes = DateTime.Today.AddMonths(-1);
            var clientes = await _clienteService.GetAllAsync();
            var vehiculos = await _vehiculoService.GetAllAsync();

            var resultado = new List<ClientePendienteContacto>();

            foreach (var cliente in clientes)
            {
                var pendientes = vehiculos
                    .Where(v => v.Cliente.Identificacion == cliente.Identificacion)
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
