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

    public class LavadoService
    {// Servicio para manejar las operaciones CRUD de Lavado
        private readonly List<Lavado> _lavados = new();
        private int _lastId = 1;
        // Constructor que inicializa algunos datos de ejemplo
        public LavadoService()
        {
            _lavados.Add(new Lavado
            {// Datos de ejemplo para simular una base de datos
                Id = _lastId++,
                Fecha = DateTime.Now.AddDays(-2),
                Cliente = new Cliente { Identificacion = "001010101", NombreCompleto = "Francisco Campos" },
                Vehiculo = new Vehiculo { Placa = "HTY123", Marca = "Toyota", Modelo = "Hilux", IdentificacionCliente = "001010101" },
                Empleado = new Empleado { Cedula = "145863987" },
                Tipo = TipoLavado.Basico,
                Estado = EstadoLavado.Facturado,
                Precio = 8000
            });
        }
        // Método para obtener todos los lavados
        public List<Lavado> GetAll() => _lavados;
        // Método para obtener un lavado por su ID
        public Lavado? GetById(int id) => _lavados.FirstOrDefault(x => x.Id == id);
        // Método para agregar un nuevo lavado
        public void Add(Lavado l)
        {
            l.Id = _lastId++;
            if (l.Tipo != TipoLavado.LaJoya)
                l.Precio = ObtenerPrecioPorTipo(l.Tipo);

            _lavados.Add(l);
        }
        // Método para actualizar un lavado existente
        public void Update(int id, Lavado l)
        {
            var index = _lavados.FindIndex(x => x.Id == id);
            if (index >= 0)
            {
                l.Id = id;
                if (l.Tipo != TipoLavado.LaJoya)
                    l.Precio = ObtenerPrecioPorTipo(l.Tipo);
                _lavados[index] = l;
            }
        }
        // Método para eliminar un lavado por su ID
        public void Delete(int id)
        {
            var l = _lavados.FirstOrDefault(x => x.Id == id);
            if (l != null) _lavados.Remove(l);
        }
        // Método privado para obtener el precio según el tipo de lavado
        private decimal ObtenerPrecioPorTipo(TipoLavado tipo)
        {
            return tipo switch
            {
                TipoLavado.Basico => 8000,
                TipoLavado.Premium => 12000,
                TipoLavado.Deluxe => 20000,
                TipoLavado.LaJoya => 0,
                _ => 0
            };
        }
    }
}
