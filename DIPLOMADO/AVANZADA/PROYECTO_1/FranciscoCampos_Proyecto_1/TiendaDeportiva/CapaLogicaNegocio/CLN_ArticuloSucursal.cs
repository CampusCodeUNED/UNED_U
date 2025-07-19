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
    // Clase que maneja la lógica de negocio para la relación entre artículos y sucursales
    public class CLN_ArticuloSucursal
    {
        
        private static CLN_ArticuloSucursal instancia = null;

        // Acceso a la capa de datos para artículos en sucursales
        private CAD_ArticuloxSucursal articuloxSucursalData = new CAD_ArticuloxSucursal();

        // Constructor privado para evitar la instanciación externa
        private CLN_ArticuloSucursal() { }

        // Método para obtener la instancia única de la clase
        public static CLN_ArticuloSucursal Instancia()
        {
            if (instancia == null)
            {
                instancia = new CLN_ArticuloSucursal();
            }
            return instancia;
        }

        // Método para agregar un nuevo artículo a una sucursal con una cantidad específica
        public void AgregarArticuloxSucursal(Sucursal sucursal, Articulo articulo, int cantidad)
        {
            ArticuloSucursal nuevoArticuloxSucursal = new ArticuloSucursal(sucursal, articulo, cantidad);
            articuloxSucursalData.AgregarArticuloxSucursal(nuevoArticuloxSucursal);
        }

        // Método para obtener todos los artículos por sucursal
        public ArticuloSucursal[] ObtenerArticulosXSucursal()
        {
            return articuloxSucursalData.ObtenerArticulosXSucursal();
        }

        // Método para verificar si existe una relación artículo-sucursal
        public bool ExisteArticuloxSucursal(int idSucursal, int idArticulo)
        {
            return articuloxSucursalData.ExisteArticuloxSucursal(idSucursal, idArticulo);
        }
    }
}

