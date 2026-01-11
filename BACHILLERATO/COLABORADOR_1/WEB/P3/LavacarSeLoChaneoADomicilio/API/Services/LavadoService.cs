using API.Data;
using API.Models;
using Microsoft.EntityFrameworkCore;

namespace API.Services
{
    public class LavadoService
    {
        private readonly LavadoDbContext _context;

        public LavadoService(LavadoDbContext context)
        {
            _context = context;
        }

        public async Task<List<Lavado>> GetAllAsync()
        {
            return await _context.Lavados
                .Include(l => l.Cliente)
                .Include(l => l.Vehiculo)
                .Include(l => l.Empleado)
                .ToListAsync();
        }

        public async Task<Lavado?> GetByIdAsync(int id)
        {
            return await _context.Lavados
                .Include(l => l.Cliente)
                .Include(l => l.Vehiculo)
                .Include(l => l.Empleado)
                .FirstOrDefaultAsync(l => l.Id == id);
        }

        public async Task AddAsync(Lavado l)
        {
            if (l.Tipo != TipoLavado.LaJoya)
                l.Precio = ObtenerPrecioPorTipo(l.Tipo);

            _context.Lavados.Add(l);
            await _context.SaveChangesAsync();
        }

        public async Task UpdateAsync(Lavado l)
        {
            if (l.Tipo != TipoLavado.LaJoya)
                l.Precio = ObtenerPrecioPorTipo(l.Tipo);

            _context.Lavados.Update(l);
            await _context.SaveChangesAsync();
        }

        public async Task DeleteAsync(int id)
        {
            var l = await _context.Lavados.FindAsync(id);
            if (l != null)
            {
                _context.Lavados.Remove(l);
                await _context.SaveChangesAsync();
            }
        }

        private decimal ObtenerPrecioPorTipo(TipoLavado tipo)
        {
            return tipo switch
            {
                TipoLavado.Basico => 8000,
                TipoLavado.Premium => 12000,
                TipoLavado.Deluxe => 20000,
                TipoLavado.LaJoya => 0,
                _ => 0
            };
        }
    }
}
