using API.Data;
using API.Models;
using Microsoft.EntityFrameworkCore;
using System;

namespace API.Services
{
    public class ClienteService
    {
        private readonly LavadoDbContext _context;

        public ClienteService(LavadoDbContext context)
        {
            _context = context;
        }

        public async Task<List<Cliente>> GetAllAsync()
        {
            return await _context.Clientes.Include(c => c.Vehiculos).ToListAsync();
        }

        public async Task<Cliente?> GetByIdAsync(string id)
        {
            return await _context.Clientes
                .Include(c => c.Vehiculos)
                .FirstOrDefaultAsync(c => c.Identificacion == id);
        }

        public async Task AddAsync(Cliente c)
        {
            _context.Clientes.Add(c);
            await _context.SaveChangesAsync();
        }

        public async Task UpdateAsync(Cliente actualizado)
        {
            _context.Clientes.Update(actualizado);
            await _context.SaveChangesAsync();
        }

        public async Task DeleteAsync(string id)
        {
            var cliente = await _context.Clientes.FindAsync(id);
            if (cliente != null)
            {
                _context.Clientes.Remove(cliente);
                await _context.SaveChangesAsync();
            }
        }
    }
}
