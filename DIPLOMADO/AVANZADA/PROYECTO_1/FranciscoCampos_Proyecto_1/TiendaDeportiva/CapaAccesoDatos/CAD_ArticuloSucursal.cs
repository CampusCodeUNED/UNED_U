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
    public class CAD_ArticuloxSucursal
    {
        // Arreglo  Artículo con Sucursal
        private ArticuloSucursal[] articulosXSucursal = new ArticuloSucursal[100];
        private int contador = 0;// Contador de asociaciones

        // Método para agregar una asociación entre Artículo y Sucursal
        public void AgregarArticuloxSucursal(ArticuloSucursal articuloxSucursal)
        {
            // Verifica si se ha alcanzado la capacidad máxima del arreglo
            if (contador >= articulosXSucursal.Length)
            {
                throw new InvalidOperationException("No se pueden agregar más asociaciones. Capacidad máxima alcanzada.");
            }

            // Verifica si la combinación de Sucursal y Artículo ya existe
            for (int i = 0; i < contador; i++)
            {
                if (articulosXSucursal[i] != null &&
                    articulosXSucursal[i].Sucursal.Id == articuloxSucursal.Sucursal.Id &&
                    articulosXSucursal[i].Articulo.Id == articuloxSucursal.Articulo.Id)
                {
                    throw new ArgumentException("La combinación de Sucursal y Artículo ya existe.");
                }
            }

            // Agrega la nueva asociación y aumenta el contador
            articulosXSucursal[contador] = articuloxSucursal;
            contador++;
        }

        // Método para obtener el arreglo de asociaciones de Artículo con Sucursal
        public ArticuloSucursal[] ObtenerArticulosXSucursal()
        {
            return articulosXSucursal;
        }

        // Método para verificar si existe una asociación específica de Artículo con Sucursal
        public bool ExisteArticuloxSucursal(int idSucursal, int idArticulo)
        {
            for (int i = 0; i < contador; i++)// Recorre el arreglo de asociaciones
            {
                if (articulosXSucursal[i] != null &&
                    articulosXSucursal[i].Sucursal.Id == idSucursal &&
                    articulosXSucursal[i].Articulo.Id == idArticulo)
                {
                    return true;
                }
            }
            return false;
        }
    }
}
