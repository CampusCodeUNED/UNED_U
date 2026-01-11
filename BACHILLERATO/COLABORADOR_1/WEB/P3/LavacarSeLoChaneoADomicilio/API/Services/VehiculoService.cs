using API.Data;
using API.Models;
using Microsoft.EntityFrameworkCore;
using System;

namespace API.Services
{
    public class VehiculoService
    {
        private readonly LavadoDbContext _context;

        public VehiculoService(LavadoDbContext context)
        {
            _context = context;
        }

        public async Task<List<Vehiculo>> GetAllAsync()
        {
            return await _context.Vehiculos.Include(v => v.Cliente).ToListAsync();
        }

        public async Task<Vehiculo?> GetByPlacaAsync(string placa)
        {
            return await _context.Vehiculos
                .Include(v => v.Cliente)
                .FirstOrDefaultAsync(v => v.Placa == placa);
        }

        public async Task<List<Vehiculo>> GetByClienteAsync(string identificacionCliente)
        {
            return await _context.Vehiculos
                .Where(v => v.ClienteIdentificacion == identificacionCliente)
                .Include(v => v.Cliente)
                .ToListAsync();
        }

        public async Task AddAsync(Vehiculo v)
        {
            _context.Vehiculos.Add(v);
            await _context.SaveChangesAsync();
        }

        public async Task<bool> UpdateAsync(string placaOriginal, Vehiculo actualizado)
        {
            var existing = await _context.Vehiculos.FindAsync(placaOriginal);
            if (existing == null) return false;

            existing.Marca = actualizado.Marca;
            existing.Modelo = actualizado.Modelo;
            existing.Traccion = actualizado.Traccion;
            existing.Color = actualizado.Color;
            existing.UltimaFechaAtencion = actualizado.UltimaFechaAtencion;
            existing.TratamientoNanoCeramico = actualizado.TratamientoNanoCeramico;
            existing.ClienteIdentificacion = actualizado.ClienteIdentificacion; 

            await _context.SaveChangesAsync();
            return true;
        }

        public async Task DeleteAsync(string placa)
        {
            var v = await _context.Vehiculos.FindAsync(placa);
            if (v != null)
            {
                _context.Vehiculos.Remove(v);
                await _context.SaveChangesAsync();
            }
        }
    }
}
