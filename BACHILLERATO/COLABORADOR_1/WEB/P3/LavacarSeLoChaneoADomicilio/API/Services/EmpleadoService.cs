using API.Data;
using API.Models;
using Microsoft.EntityFrameworkCore;
using System;

namespace API.Services
{
    public class EmpleadoService
    {
        private readonly LavadoDbContext _context;

        public EmpleadoService(LavadoDbContext context)
        {
            _context = context;
        }

        public async Task<List<Empleado>> GetAllAsync() =>
            await _context.Empleados.ToListAsync();

        public async Task<Empleado?> GetByCedulaAsync(string cedula) =>
            await _context.Empleados.FindAsync(cedula);

        public async Task AddAsync(Empleado emp)
        {
            _context.Empleados.Add(emp);
            await _context.SaveChangesAsync();
        }

        public async Task<bool> UpdateAsync(string cedula, Empleado updated)
        {
            var existing = await _context.Empleados.FindAsync(cedula);
            if (existing == null) return false;

            _context.Entry(existing).CurrentValues.SetValues(updated);
            await _context.SaveChangesAsync();
            return true;
        }

        public async Task<bool> DeleteAsync(string cedula)
        {
            var existing = await _context.Empleados.FindAsync(cedula);
            if (existing == null) return false;

            _context.Empleados.Remove(existing);
            await _context.SaveChangesAsync();
            return true;
        }
    }
}
