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
using TiendaDeportiva.CapaEntidades;

namespace TiendaDeportiva.CapaAccesoDatos
{
    public class CAD_Categoria
    {
        // Arreglo  categorías
        private Categoria[] categorias = new Categoria[10];
        private int contador = 0;

        // Método para agregar una nueva categoría
        public void AgregarCategoria(Categoria categoria)
        {
            // Verifica si se ha alcanzado la capacidad máxima del arreglo
            if (contador >= categorias.Length)
            {
                throw new InvalidOperationException("No se pueden agregar más categorías. Capacidad máxima alcanzada.");
            }

            // Verifica si el Id de la categoría ya existe
            for (int i = 0; i < contador; i++)
            {
                if (categorias[i] != null && categorias[i].IdCategoria == categoria.IdCategoria)
                {
                    throw new ArgumentException("El Id de la categoría ya existe.");
                }
            }

            // Agrega la nueva categoría al arreglo y aumenta el contador
            categorias[contador] = categoria;
            contador++;
        }

        // Método para obtener el arreglo de categorías
        public Categoria[] ObtenerCategorias()
        {
            return categorias;
        }

        // Método para verificar si ya existe una categoría con un Id específico
        public bool ExisteIdCategoria(int id)
        {
            for (int i = 0; i < contador; i++)
            {
                if (categorias[i] != null && categorias[i].IdCategoria == id)
                {
                    return true;
                }
            }
            return false;
        }

        // Método para verificar si ya existe una categoría con un nombre específico
        public bool ExisteNombreCategoria(string nombre)
        {
            // Compara nombres sin importar mayúsculas/minúsculas
            for (int i = 0; i < contador; i++)
            {
                if (categorias[i] != null && string.Equals(categorias[i].NombreCategoria, nombre, StringComparison.OrdinalIgnoreCase))
                {
                    return true;
                }
            }
            return false;
        }
    }
}
