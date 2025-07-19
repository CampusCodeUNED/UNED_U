/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso: Programación avanzada
Código: 00830
Proyecto #2: Tienda deportiva
Tutor: Juan Ramírez Valladares
Grupo: 09
Estudiante: Francisco Campos Sandi
Cédula: 114750560
III Cuatrimestre 2024
*/
using System.Collections.Generic;//Librería para usar List<>
using TiendaDeportivaServidor.Datos; // Acceso a la capa de datos (Database)
using TiendaDeportivaServidor.Entidades; // Define las entidades Cliente

namespace TiendaDeportivaServidor.LogicaNegocio
{
    // Clase que gestiona la lógica de negocio relacionada con los clientes de la tienda
    public class LN_Cliente
    {
        private readonly Database _database; // Instancia de la clase Database para interactuar con la base de datos
        // Constructor de la clase LN_Cliente.
        public LN_Cliente()
        {
            // Inicializa la instancia de la base de datos para realizar operaciones
            _database = new Database();
        }

        // Método para registrar un nuevo cliente en la base de datos
        // Recibe un objeto Cliente y devuelve un valor booleano indicando el éxito de la operación
        public bool RegistrarCliente(Cliente cliente)
        {
            // Llama al método RegistrarCliente de la capa de datos para registrar al cliente en la base de datos
            return _database.RegistrarCliente(cliente);
        }

        // Método para obtener la lista de clientes desde la base de datos
        // Retorna una lista de objetos Cliente
        public List<Cliente> ObtenerClientes()
        {
            // Llama al método ConsultarClientes de la capa de datos para obtener todos los clientes registrados
            return _database.ConsultarClientes();
        }
    }
}
