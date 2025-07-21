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
using TiendaDeportivaServidor.Entidades; // Define la entidad Articulo

namespace TiendaDeportivaServidor.LogicaNegocio
{
    // Clase de lógica de negocio para manejar los artículos
    public class LN_Articulo
    {
        private readonly Database _database; // Instancia de la clase Database para interactuar con la base de datos

        // Constructor de la clase LN_Articulo
        public LN_Articulo()
        {
            // Inicializa la instancia de la base de datos
            _database = new Database();
        }

        // Método para registrar un artículo en la base de datos
        // Recibe un objeto Articulo y retorna un valor booleano indicando si la operación fue exitosa
        public bool RegistrarArticulo(Articulo articulo)
        {
            // Llama al método RegistrarArticulo de la capa de datos para guardar el artículo en la base de datos
            return _database.RegistrarArticulo(articulo);
        }

        // Método para obtener la lista de artículos desde la base de datos
        // Retorna una lista de objetos Articulo.
        public List<Articulo> ObtenerArticulos()
        {
            // Llama al método ConsultarArticulos de la capa de datos para obtener la lista de artículos
            return _database.ConsultarArticulos();
        }
    }
}

