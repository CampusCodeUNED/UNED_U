/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso: FUNDAMENTOS DE PROGRAMACION WEB
Código: 03075 
Proyecto #3: Lavacar se lo chaneo  a domicilio
Tutor: Sigifredo Leitón Luna
Grupo: 04
Estudiante: Francisco Campos Sandi
Cédula: 114750560
II Cuatrimestre 2025
*/
using API.Models;
using Microsoft.EntityFrameworkCore;

namespace API.Data
{
    public class LavadoDbContext : DbContext
    {
        public LavadoDbContext(DbContextOptions<LavadoDbContext> options)
            : base(options) { }
        // DbSets para las entidades del modelo
        public DbSet<Cliente> Clientes { get; set; }
        public DbSet<Vehiculo> Vehiculos { get; set; }
        public DbSet<Empleado> Empleados { get; set; }
        public DbSet<Lavado> Lavados { get; set; }
        // Configuración del modelo
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {// Configuración de las entidades
            modelBuilder.Entity<Cliente>().HasKey(c => c.Identificacion);
            modelBuilder.Entity<Vehiculo>().HasKey(v => v.Placa);
            modelBuilder.Entity<Empleado>().HasKey(e => e.Cedula);
            modelBuilder.Entity<Lavado>().HasKey(l => l.Id);
            // Configuración de las relaciones
            modelBuilder.Entity<Vehiculo>()
                .HasOne(v => v.Cliente)
                .WithMany(c => c.Vehiculos)
                .HasForeignKey(v => v.ClienteIdentificacion);
            // Configuración de las relaciones entre Lavado, Cliente, Vehiculo y Empleado
            modelBuilder.Entity<Lavado>()
                .HasOne(l => l.Cliente)
                .WithMany()
                .HasForeignKey("ClienteId")
                .OnDelete(DeleteBehavior.Restrict);
            // Relación entre Lavado y Vehiculo
            modelBuilder.Entity<Lavado>()
                .HasOne(l => l.Vehiculo)
                .WithMany()
                .HasForeignKey("VehiculoPlaca")
                .OnDelete(DeleteBehavior.Restrict);
            // Relación entre Lavado y Empleado
            modelBuilder.Entity<Lavado>()
                .HasOne(l => l.Empleado)
                .WithMany()
                .HasForeignKey("EmpleadoCedula")
                .OnDelete(DeleteBehavior.Restrict);


        }
    }
}
