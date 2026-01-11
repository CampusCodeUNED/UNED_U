/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso: FUNDAMENTOS DE PROGRAMACION WEB
Código: 03075 
Tarea #1: Computadoras API
Tutor: Sigifredo Leitón Luna
Grupo: 04
Estudiante: Francisco Campos Sandi
Cédula: 114750560
II Cuatrimestre 2025
*/
namespace ComputadorasAPI.Models
{
    public class Software
    {
        public int Id { get; set; }
        public string Descripcion { get; set; } = string.Empty;
        public string Version { get; set; } = string.Empty;
    }
}
