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
    // Clase que maneja la lógica de negocoi para las categorías
    public class CLN_Categoria
    {
        
        private static CLN_Categoria instancia = null;

        // Acceso a la capa de datos para categorías
        private CAD_Categoria categoriaData = new CAD_Categoria();

        // Constructor privado para evitar la instanciación externa
        private CLN_Categoria() { }

        // Método para obtener la instancia única de la clase
        public static CLN_Categoria Instancia()
        {
            if (instancia == null)
            {
                instancia = new CLN_Categoria();
            }
            return instancia;
        }

        // Método para agregar una nueva categoría
        public void AgregarCategoria(Categoria categoria)
        {
            // Crea una nueva categoría y la agrega a la capa de datos
            Categoria nuevoArticulo = new Categoria(categoria.IdCategoria, categoria.NombreCategoria, categoria.Descripcion);
            categoriaData.AgregarCategoria(nuevoArticulo);
        }

        // Método para obtener todas las categorías
        public Categoria[] ObtenerCategorias()
        {
            return categoriaData.ObtenerCategorias();
        }

        // Método para verificar si existe una categoría con un ID específico
        public bool ExisteIdCategoria(int id)
        {
            return categoriaData.ExisteIdCategoria(id);
        }

        // Método para verificar si existe una categoría con un nombre específico
        public bool ExisteNombreCategoria(string nombre)
        {
            return categoriaData.ExisteNombreCategoria(nombre);
        }
    }
}

