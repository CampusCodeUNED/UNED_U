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
using System.Collections.Generic;// Importa la clase List
using TiendaDeportivaServidor.Datos; // Acceso a la capa de datos (Database)
using TiendaDeportivaServidor.Entidades; // Define las entidades Articulo y ArticuloSucursal
namespace TiendaDeportivaServidor.LogicaNegocio
{
    // Clase que maneja la lógica de negocio relacionada con los artículos por sucursal
    public class LN_ArticuloSucursal
    {
        private readonly Database _database; // Instancia de la clase Database para interactuar con la base de datos

        // Constructor de la clase LN_ArticuloSucursal
        public LN_ArticuloSucursal()
        {
            // Inicializa la instancia de la base de datos para realizar operaciones
            _database = new Database();
        }

        // Método para registrar los artículos por sucursal en la base de datos
        // Recibe un objeto ArticuloSucursal y retorna un valor booleano que indica el éxito de la operación
        public bool RegistrarArticulosPorSucursal(ArticuloSucursal articuloSucursal)
        {
            // Llama al método RegistrarArticulosPorSucursal de la capa de datos para registrar la relación en la base de datos
            return _database.RegistrarArticulosPorSucursal(articuloSucursal);
        }

        // Método para obtener la lista de artículos por sucursal desde la base de datos
        // Retorna una lista de objetos ArticuloSucursal
        public List<ArticuloSucursal> ObtenerArticulosPorSucursal()
        {
            // Llama al método ConsultarArticulosPorSucursal de la capa de datos para obtener los datos de artículos por sucursal
            return _database.ConsultarArticulosPorSucursal();
        }
    }
}
