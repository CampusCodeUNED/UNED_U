/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso: Programación avanzada
Código: 00830
Proyecto #1: Tienda deportiva
Tutor: Juan Ramírez Valladares
Grupo: 09
Estudiante: Francisco Campos Sandi
Cédula: 114750560
III Cuatrimestre 2024
*/
using System;
using TiendaDeportiva.CapaAccesoDatos;
using TiendaDeportiva.CapaEntidades;

namespace TiendaDeportiva.CapaLogicaNegocio
{
    // Clase que maneja la lógica de negocio para los artículos
    public class CLN_Articulo
    {
        
        private static CLN_Articulo instancia = null;

        // Acceso a la capa de datos para artículos
        private CAD_Articulo articuloData = new CAD_Articulo();

        // Constructor privado para evitar la instanciación externa
        private CLN_Articulo() { }

        // Método para obtener la instancia única de la clase
        public static CLN_Articulo Instancia()
        {
            if (instancia == null)
            {
                instancia = new CLN_Articulo();
            }
            return instancia;
        }

        // Método para agregar un nuevo artículo
        public void AgregarArticulo(int id, string descripcion, Categoria categoria, string marca, bool activo)
        {
            Articulo nuevoArticulo = new Articulo(id, descripcion, categoria, marca, activo);
            articuloData.AgregarArticulo(nuevoArticulo);
        }

        // Método para obtener todos los artículos
        public Articulo[] ObtenerArticulos()
        {
            return articuloData.ObtenerArticulos();
        }

        // Método para verificar si existe un artículo por su ID
        public bool ExisteIdArticulo(int id)
        {
            return articuloData.ExisteIdArticulo(id);
        }

        // Método para verificar si existe un artículo por su descripción
        public bool ExisteDescripcionArticulo(string nombre)
        {
            return articuloData.ExisteDescripcionArticulo(nombre);
        }

        // Método para obtener todos los artículos activos
        public Articulo[] ObtenerArticulosActivos()
        {
            return articuloData.ObtenerArticulosActivos();
        }

        // Método para obtener un artículo por su ID
        public Articulo ObtenerArticuloPorId(int id)
        {
            return articuloData.ObtenerArticuloPorId(id);
        }
    }
}

