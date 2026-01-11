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
using ComputadorasAPI.Models;

namespace ComputadorasAPI.Services
{
    public class ComputadoraService
    {
        private readonly List<Computadora> _computadoras = new();
        private int _nextComputadoraId = 1;
        private int _nextSoftwareId = 1;

        public List<Computadora> GetAll() => _computadoras;
        // Obtiene una computadora por su ID
        public Computadora? GetById(int id) =>
            _computadoras.FirstOrDefault(c => c.Id == id);
        // Crea una nueva computadora
        public Computadora Create(Computadora computadora)
        {
            computadora.Id = _nextComputadoraId++;

            foreach (var software in computadora.SoftwareInstalado)
            {
                software.Id = _nextSoftwareId++;
            }

            _computadoras.Add(computadora);
            return computadora;
        }

        // Actualiza una computadora existente
        public bool AddSoftware(int computadoraId, Software software)
        {
            var comp = GetById(computadoraId);
            if (comp == null) return false;
            software.Id = _nextSoftwareId++;
            comp.SoftwareInstalado.Add(software);
            return true;
        }
        // Elimina software de una computadora
        public bool DeleteSoftware(int computadoraId, int softwareId)
        {
            var comp = GetById(computadoraId);
            if (comp == null) return false;

            var software = comp.SoftwareInstalado.FirstOrDefault(s => s.Id == softwareId);
            if (software == null) return false;

            return comp.SoftwareInstalado.Remove(software);
        }
        // Actualiza un software instalado en una computadora
        public bool UpdateSoftware(int computadoraId, Software updated)
        {
            var comp = GetById(computadoraId);
            if (comp == null) return false;

            var software = comp.SoftwareInstalado.FirstOrDefault(s => s.Id == updated.Id);
            if (software == null) return false;

            software.Descripcion = updated.Descripcion;
            software.Version = updated.Version;
            return true;
        }
    }
}
