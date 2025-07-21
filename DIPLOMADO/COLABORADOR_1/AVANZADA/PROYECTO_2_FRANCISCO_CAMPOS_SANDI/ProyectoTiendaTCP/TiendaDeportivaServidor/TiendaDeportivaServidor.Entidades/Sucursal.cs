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
namespace TiendaDeportivaServidor.Entidades
{
    public class Sucursal
    {//Atributos de la clase Sucursal
        public int Id { get; set; }
        public string Nombre { get; set; }
        public string Direccion { get; set; }
        public string Telefono { get; set; }
        public bool Activo { get; set; }
        public Administrador Administrador { get; set; }

        public string ActivoTexto => Activo ? "Sí" : "No";//Propiedad ActivoTexto

        public string NombreAdministrador => $"{Administrador?.Nombre} {Administrador?.Apellido1} {Administrador?.Apellido2}";//
    }
}
