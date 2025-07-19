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
using System.Collections.Generic;// Importa la clase List.
using TiendaDeportivaServidor.Datos; // Acceso a la capa de datos (Database).
using TiendaDeportivaServidor.Entidades; // Define la entidad Administrador.

namespace TiendaDeportivaServidor.LogicaNegocio
{
    // Clase de lógica de negocio para el manejo de los administradores
    public class LN_Administrador
    {
        private readonly Database _database; // Instancia de la clase Database para interactuar con la base de datos

        // Constructor de la clase LN_Administrador
        public LN_Administrador()
        {
            // Inicializa la instancia de la base de datos
            _database = new Database();
        }

        // Método para registrar un administrador en la base de datos
        // Recibe un objeto Administrador y retorna un valor booleano indicando si la operación fue exitosa
        public bool RegistrarAdministrador(Administrador administrador)
        {
            // Llama al método RegistrarAdministrador de la capa de datos para guardar el administrador en la base de datos
            return _database.RegistrarAdministrador(administrador);
        }

        // Método para obtener la lista de administradores de la base de datos
        // Retorna una lista de objetos Administrador
        public List<Administrador> ObtenerAdministradores()
        {
            // Llama al método ConsultarAdministradores de la capa de datos para obtener la lista de administradores
            return _database.ConsultarAdministradores();//
        }
    }
}
