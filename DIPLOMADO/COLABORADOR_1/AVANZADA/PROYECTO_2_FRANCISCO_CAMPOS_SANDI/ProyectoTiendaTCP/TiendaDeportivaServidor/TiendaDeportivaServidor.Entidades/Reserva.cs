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
    public class Reserva
    {//Atributos de la clase Reserva
        public int Id { get; set; }
        public string Sucursal { get; set; }
        public string Articulo { get; set; }
        public DateTime FechaReserva { get; set; }
    }
}
