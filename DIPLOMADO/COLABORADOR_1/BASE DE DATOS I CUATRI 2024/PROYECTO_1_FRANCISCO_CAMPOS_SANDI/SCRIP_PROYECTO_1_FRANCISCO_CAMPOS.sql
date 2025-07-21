-- Creación de la base de datos
CREATE DATABASE  AutoTech;

USE AutoTech;

-- Tabla para registrar vehículos
CREATE TABLE  Vehiculos (
    Placa VARCHAR(10) PRIMARY KEY,
    Marca VARCHAR(50),
    Modelo VARCHAR(50),
    Anio INT,
    Color VARCHAR(20)
) ENGINE=InnoDB; -- Agregamos el motor de almacenamiento para evitar advertencias relacionadas con el motor predeterminado;

-- Tabla para registrar clientes
CREATE TABLE  Clientes (
    Cedula VARCHAR(15) PRIMARY KEY,
    Nombre VARCHAR(100),
    Apellidos VARCHAR(100),
    Telefono VARCHAR(20),
    Direccion VARCHAR(200),
    CorreoElectronico VARCHAR(100)
) ENGINE=InnoDB;

-- Tabla para registrar empleados
CREATE TABLE  Empleados (
    Cedula VARCHAR(15) PRIMARY KEY,
    CodigoEmpleado VARCHAR(20),
    Nombre VARCHAR(100),
    Apellidos VARCHAR(100),
    Cargo VARCHAR(100),
    EspecialidadMecanica VARCHAR(100),
    FechaContratacion DATE
) ENGINE=InnoDB;

-- Tabla para registrar órdenes de trabajo
CREATE TABLE  OrdenesTrabajo (
    NumeroOrden INT AUTO_INCREMENT PRIMARY KEY,
    DescripcionTrabajo TEXT,
    FechaIngreso DATE,
    FechaEstimadaEntrega DATE,
    Estado ENUM('Sin iniciar', 'En proceso', 'Completada', 'Cancelada'),
    ClienteCedula VARCHAR(15),
    VehiculoPlaca VARCHAR(10),
    FOREIGN KEY (ClienteCedula) REFERENCES Clientes(Cedula),
    FOREIGN KEY (VehiculoPlaca) REFERENCES Vehiculos(Placa)
) ENGINE=InnoDB;

-- Tabla para registrar servicios realizados en órdenes de trabajo
CREATE TABLE  Servicios (
    CodigoServicio INT AUTO_INCREMENT PRIMARY KEY,
    Descripcion VARCHAR(200),
    CostoManoObra DECIMAL(10, 2),
    TiempoEstimadoEjecucion INT,
    RepuestosUtilizados TEXT,
    EmpleadoCedula VARCHAR(15),
    OrdenTrabajoNumero INT,
    FOREIGN KEY (EmpleadoCedula) REFERENCES Empleados(Cedula),
    FOREIGN KEY (OrdenTrabajoNumero) REFERENCES OrdenesTrabajo(NumeroOrden)
) ENGINE=InnoDB;

-- Tabla para registrar repuestos
CREATE TABLE Repuestos (
    NumeroParte INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(100),
    Descripcion VARCHAR(200),
    TipoRepuesto VARCHAR(100),
    Costo DECIMAL(10, 2),
    CantidadDisponible INT,
    ServicioRelacionado INT,  -- Llave foránea para hacer referencia a la tabla de Servicios
    FOREIGN KEY (ServicioRelacionado) REFERENCES Servicios(CodigoServicio)
) ENGINE=InnoDB;