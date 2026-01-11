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
{
    public class VehiculoService
    {
        private readonly List<Vehiculo> _vehiculos = new()
{
    new Vehiculo
    {// Datos de ejemplo para simular una base de datos
        Placa = "ABC123",
        Marca = "Toyota",
        Modelo = "Corolla",
        Traccion = "4x2",
        Color = "Gris",
        UltimaFechaAtencion = DateTime.Now.AddDays(-10),
        TratamientoNanoCeramico = true,
        IdentificacionCliente = "001010101" 
    }
};
        // Constructor que inicializa la lista de vehículos

        public List<Vehiculo> GetAll() => _vehiculos;
        // Obtiene todos los vehículos
        public Vehiculo? GetByPlaca(string placa) =>
            _vehiculos.FirstOrDefault(v => v.Placa.Equals(placa, StringComparison.OrdinalIgnoreCase));
        // Obtiene un vehículo por su placa
        public List<Vehiculo> GetByCliente(string identificacionCliente)
        {
            return _vehiculos
                .Where(v => v.IdentificacionCliente.Equals(identificacionCliente, StringComparison.OrdinalIgnoreCase))
                .ToList();
        }
        // Obtiene los vehículos asociados a un cliente por su identificación
        public void Add(Vehiculo v) => _vehiculos.Add(v);
        // Agrega un nuevo vehículo a la lista
        public void Update(string placaOriginal, Vehiculo actualizado)
        {
            var index = _vehiculos.FindIndex(v => v.Placa.Equals(placaOriginal, StringComparison.OrdinalIgnoreCase));
            if (index >= 0) _vehiculos[index] = actualizado;
        }
        // Actualiza un vehículo existente por su placa
        public void Delete(string placa)
        {
            var v = _vehiculos.FirstOrDefault(v => v.Placa.Equals(placa, StringComparison.OrdinalIgnoreCase));
            if (v != null) _vehiculos.Remove(v);
        }
    }
}
