CREATE DATABASE LavadoDB;
GO

USE LavadoDB;
GO

-- Tabla: Clientes
CREATE TABLE Clientes (
    Identificacion NVARCHAR(450) NOT NULL PRIMARY KEY,
    NombreCompleto NVARCHAR(100) NOT NULL,
    Provincia NVARCHAR(50) NOT NULL,
    Canton NVARCHAR(50) NOT NULL,
    Distrito NVARCHAR(50) NOT NULL,
    DireccionExacta NVARCHAR(200) NOT NULL,
    Telefono NVARCHAR(15) NOT NULL,
    PreferenciaLavado NVARCHAR(100) NOT NULL
);
GO

-- Tabla: Empleados
CREATE TABLE Empleados (
    Cedula NVARCHAR(450) NOT NULL PRIMARY KEY,
    FechaNacimiento DATETIME2 NOT NULL,
    FechaIngreso DATETIME2 NOT NULL,
    SalarioPorDia DECIMAL(18,2) NOT NULL,
    DiasVacacionesAcumulados INT NOT NULL,
    FechaRetiro DATETIME2 NULL,
    MontoLiquidacion DECIMAL(18,2) NOT NULL
);
GO

-- Tabla: Vehiculos
CREATE TABLE Vehiculos (
    Placa NVARCHAR(450) NOT NULL PRIMARY KEY,
    Marca NVARCHAR(50) NOT NULL,
    Modelo NVARCHAR(50) NOT NULL,
    Traccion NVARCHAR(20) NOT NULL,
    Color NVARCHAR(30) NOT NULL,
    UltimaFechaAtencion DATETIME2 NULL,
    TratamientoNanoCeramico BIT NOT NULL,
    ClienteIdentificacion NVARCHAR(450) NOT NULL,
    FOREIGN KEY (ClienteIdentificacion) REFERENCES Clientes(Identificacion) ON DELETE CASCADE
);
GO

-- Tabla: Lavados
CREATE TABLE Lavados (
    Id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    Fecha DATETIME2 NOT NULL DEFAULT GETDATE(),

    ClienteId NVARCHAR(450) NOT NULL,
    VehiculoPlaca NVARCHAR(450) NOT NULL,
    EmpleadoCedula NVARCHAR(450) NOT NULL,

    Tipo INT NOT NULL,      
    Estado INT NOT NULL,    
    Precio DECIMAL(18,2) NOT NULL,

    FOREIGN KEY (ClienteId) REFERENCES Clientes(Identificacion),
    FOREIGN KEY (VehiculoPlaca) REFERENCES Vehiculos(Placa),
    FOREIGN KEY (EmpleadoCedula) REFERENCES Empleados(Cedula)
);
GO
