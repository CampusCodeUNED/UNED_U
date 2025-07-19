/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso: Programación avanzada
Código: 00830
Proyecto #1: Tienda deportiva
Tutor: Juan Ramírez Valladares
Grupo: 09
Estudiante: Francisco Antonio Campos Sandi
Cédula: 114750560
III Cuatrimestre 2024
*/
using System;
using TiendaDeportiva.CapaAccesoDatos;
using TiendaDeportiva.CapaEntidades;

namespace TiendaDeportiva.CapaLogicaNegocio
{
    // Clase que maneja la lógica de negocio para las sucursales
    public class CLN_Sucursal
    {
        
        private static CLN_Sucursal instancia = null;

        // Acceso a la capa de datos para sucursales
        private CAD_Sucursal sucursalData = new CAD_Sucursal();

        // Constructor privado para evitar la instanciación externa
        private CLN_Sucursal() { }

        // Método para obtener la instancia única de la clase
        public static CLN_Sucursal Instancia()
        {
            if (instancia == null)
            {
                instancia = new CLN_Sucursal();
            }
            return instancia;
        }

        // Método para agregar una nueva sucursal
        public void AgregarSucursal(Sucursal sucursal)
        {
            // Crea una nueva sucursal y la agrega a la capa de datos
            Sucursal nuevaSucursal = new Sucursal(sucursal.Id, sucursal.Nombre, sucursal.Administrador, sucursal.Direccion, sucursal.Telefono, sucursal.Activo);
            sucursalData.AgregarSucursal(nuevaSucursal);
        }

        // Método para obtener todas las sucursales
        public Sucursal[] ObtenerSucursales()
        {
            return sucursalData.ObtenerSucursales();
        }

        // Método para verificar si existe una sucursal con un ID específico
        public bool ExisteIdSucursal(int id)
        {
            return sucursalData.ExisteIdSucursal(id);
        }

        // Método para verificar si existe una sucursal con un nombre específico
        public bool ExisteNombreSucursal(string nombre)
        {
            return sucursalData.ExisteNombreSucursal(nombre);
        }

        // Método para obtener todas las sucursales activas
        public Sucursal[] ObtenerSucursalesActivas()
        {
            return sucursalData.ObtenerSucursalesActivas();
        }
    }
}

