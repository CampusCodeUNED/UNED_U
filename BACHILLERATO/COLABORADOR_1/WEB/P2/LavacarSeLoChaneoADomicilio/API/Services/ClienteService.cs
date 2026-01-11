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
    public class ClienteService
    {
        private readonly List<Cliente> _clientes = new()
    {   // Datos de ejemplo para simular una base de datos
        new Cliente
        {
            Identificacion = "103689750",
            NombreCompleto = "Francisco Campos",
            Provincia = "Puntarenas",
            Canton = "Coto Brus",
            Distrito = "San Vito",
            DireccionExacta = "Del parque central 200m norte",
            Telefono = "85637589",
            PreferenciaLavado = "semanal"
        }
    };
        // Constructor que inicializa la lista de clientes
        public List<Cliente> GetAll() => _clientes;
        // Obtiene todos los clientes
        public Cliente? GetById(string id) => _clientes.FirstOrDefault(c => c.Identificacion == id);
        // Obtiene un cliente por su identificación
        public void Add(Cliente c) => _clientes.Add(c);
        // Agrega un nuevo cliente a la lista
        public void Update(string idOriginal, Cliente actualizado)
        {
            var index = _clientes.FindIndex(c => c.Identificacion == idOriginal);
            if (index >= 0) _clientes[index] = actualizado;
        }
        // Actualiza un cliente existente por su identificación
        public void Delete(string id)
        {
            var cliente = _clientes.FirstOrDefault(c => c.Identificacion == id);
            if (cliente != null) _clientes.Remove(cliente);
        }
    }
}
