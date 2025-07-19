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
using System.Collections.Generic;//Librería para usar List
using TiendaDeportivaServidor.Datos; // Acceso a la capa de datos (Database)
using TiendaDeportivaServidor.Entidades; // Define las entidades Categoria

namespace TiendaDeportivaServidor.LogicaNegocio
{
    // Clase que gestiona la lógica de negocio relacionada con las categorías de productos
    public class LN_Categoria
    {
        private readonly Database _database; // Instancia de la clase Database para realizar operaciones con la base de datos

        // Constructor de la clase LN_Categoria
        public LN_Categoria()
        {
            // Inicializa la instancia de la base de datos para interactuar con ella
            _database = new Database();
        }

        // Método para registrar una nueva categoría en la base de datos
        // Recibe un objeto Categoria y devuelve un valor booleano indicando el éxito de la operación
        public bool RegistrarCategoria(Categoria categoria)
        {
            // Llama al método RegistrarCategoria de la capa de datos para registrar la categoría en la base de datos
            return _database.RegistrarCategoria(categoria);
        }

        // Método para obtener la lista de categorías desde la base de datos
        // Retorna una lista de objetos Categoria
        public List<Categoria> ObtenerCategorias()
        {
            // Llama al método ConsultarCategorias de la capa de datos para obtener todas las categorías registradas
            return _database.ConsultarCategorias();
        }
    }
}

