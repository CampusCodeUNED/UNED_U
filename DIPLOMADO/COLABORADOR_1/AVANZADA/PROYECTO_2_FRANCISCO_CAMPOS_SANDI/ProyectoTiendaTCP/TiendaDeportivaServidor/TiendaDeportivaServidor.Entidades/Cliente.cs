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
using System;

namespace TiendaDeportivaServidor.Entidades
{
    public class Cliente
    {//Atributos de la clase Cliente
        public int Identificacion { get; set; }
        public string Nombre { get; set; }
        public string Apellido1 { get; set; }
        public string Apellido2 { get; set; }
        public DateTime FechaNacimiento { get; set; }
        public bool Activo { get; set; }

        public string ActivoTexto => Activo ? "Sí" : "No";//Propiedad ActivoTexto
    }
}
