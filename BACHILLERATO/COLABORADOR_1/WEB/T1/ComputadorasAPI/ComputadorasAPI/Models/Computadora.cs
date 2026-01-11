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
    public class Computadora
    {
        public int Id { get; set; }
        public string Marca { get; set; } = string.Empty;
        public string Tipo { get; set; } = string.Empty; 
        public int AnioFabricacion { get; set; }
        public List<Software> SoftwareInstalado { get; set; } = new();// lista de software instalado
    }
}
