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
    public class CAD_Articulo
    {
        // Arreglo artículos
        private Articulo[] articulos = new Articulo[20];
        // Contador para saber cuátos artículos se han agregado
        private int contador = 0;

        // Método para agregar un artículo al arreglo
        public void AgregarArticulo(Articulo articulo)
        {
            // Verifica si se ha alcanzado la capacidad máxima del arreglo
            if (contador >= articulos.Length)
            {
                throw new InvalidOperationException("No se pueden agregar más artículos. Capacidad máxima alcanzada.");
            }

            // Verifica si el Id del artículo ya existe
            foreach (var art in articulos)
            {
                if (art != null && art.Id == articulo.Id)
                {
                    throw new ArgumentException("El Id del artículo ya existe.");
                }
            }

            // Agrega el nuevo artículo al arreglo y aumenta el contador
            articulos[contador] = articulo;
            contador++;
        }

        // Método para obtener el arreglo de artículos
        public Articulo[] ObtenerArticulos()
        {
            return articulos;
        }

        // Método para verificar si un artículo con un Id específico existe
        public bool ExisteIdArticulo(int id)
        {
            for (int i = 0; i < contador; i++)
            {
                if (articulos[i] != null && articulos[i].Id == id)
                {
                    return true;
                }
            }
            return false;
        }

        // Método para verificar si ya existe un artículo con una descripción específica
        public bool ExisteDescripcionArticulo(string descripcion)
        {
            // Compara descripciones sin importar mayúsculas/minúsculas
            for (int i = 0; i < contador; i++)
            {
                if (articulos[i] != null && string.Equals(articulos[i].Descripcion, descripcion, StringComparison.OrdinalIgnoreCase))
                {
                    return true;
                }
            }
            return false;
        }

        // Método para obtener un arreglo de artículos activos
        public Articulo[] ObtenerArticulosActivos()
        {
            // Cuenta cuántos artículos activos hay
            int count = 0;
            for (int i = 0; i < contador; i++)
            {
                if (articulos[i] != null && articulos[i].Activo)
                {
                    count++;
                }
            }

            // Crea un nuevo arreglo con solo los artículos activos
            Articulo[] articulosActivos = new Articulo[count];
            int index = 0;
            for (int i = 0; i < contador; i++)
            {
                if (articulos[i] != null && articulos[i].Activo)
                {
                    articulosActivos[index] = articulos[i];//agrega el articulo al arreglo
                    index++;
                }
            }

            return articulosActivos;
        }

        // Método para obtener un artículo por su Id
        public Articulo ObtenerArticuloPorId(int id)
        {
            // Recorre el arreglo buscando el Id
            foreach (var articulo in articulos)
            {
                if (articulo != null && articulo.Id == id)
                {
                    return articulo;
                }
            }

            // Lanza una excepción si no se encuentra el artículo
            throw new ArgumentException("No se encontró un artículo con el Id especificado.");
        }
    }
}

