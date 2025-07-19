CREATE DATABASE Evaluacion2FranciscoCampos
USE Evaluacion2FranciscoCampos


-- Creación de la tabla Empleados
CREATE TABLE Empleados (
    EmpleadoID INT PRIMARY KEY,
    NombreEmpleado NVARCHAR(100),
    ApellidoEmpleado NVARCHAR(100),
    Cargo NVARCHAR(100),
    FechaContratacion DATE
);

-- Creación de la tabla Producto
CREATE TABLE Producto (
    ProductoID INT PRIMARY KEY,
    Nombre NVARCHAR(100),
    Descripcion NVARCHAR(MAX),
    Precio DECIMAL(18, 2),
    CantDisponible INT
);

-- Creación de la tabla Cliente
CREATE TABLE Cliente (
    ClienteID INT PRIMARY KEY,
    NombreCliente NVARCHAR(100),
    ApellidoCliente NVARCHAR(100),
    Correo NVARCHAR(100),
    Direccion NVARCHAR(MAX),
    FechaNacimiento DATE
);

-- Creación de la tabla Pedidos
CREATE TABLE Pedidos (
    PedidoID INT PRIMARY KEY,
    ClientID INT,
    FechaPedido DATE,
    MontoTotal DECIMAL(18, 2),
    Empleado INT,
    FOREIGN KEY (ClientID) REFERENCES Cliente(ClienteID),
    FOREIGN KEY (Empleado) REFERENCES Empleados(EmpleadoID)
);

-- Creación de la tabla DetallePedido
CREATE TABLE DetallePedido (
    DetalleID INT PRIMARY KEY,
    PedidoID INT,
    ProductID INT,
    Cantidad INT,
    FOREIGN KEY (PedidoID) REFERENCES Pedidos(PedidoID),
    FOREIGN KEY (ProductID) REFERENCES Producto(ProductoID)
);


/*Ingreso informacion*/

INSERT INTO Cliente(ClienteID, NombreCliente, ApellidoCliente, Correo, Direccion, FechaNacimiento) VALUES
(0302530950,'Marcos Luis','Sanchez','marcos.sanchez@gmail.com','300m  sur  de  la  Catedral  de  Coronado, Urbanización Los Pinos, casa #10','10/12/1985'),
(0302530951,'Andres Jose','Fernandez Lopez','jose.fernandez@gmail.com','150m  sur  de  la  Catedral  de  Coronado, Urbanización Los Pinos, casa #5','03/05/1990'),
(0302530952,'Karen Adriana','Gomez Torres','karen.gomez@gmail.com','50m  sur  de  la  Catedral  de  Coronado, Urbanización Los Pinos, casa #1','02/01/1984');


INSERT INTO Empleados (EmpleadoID,NombreEmpleado,ApellidoEmpleado,Cargo,FechaContratacion) VALUES
(123456789,'Alice','Johnson White','Ventas','01/01/2023'),
(234567890,'Martha','Williams Brown','Ventas','01/02/2023'),
(456789012,'Daina','Jones Taylor','Administradora','01/05/2023');

INSERT INTO Producto (ProductoID, Nombre, Descripcion, Precio, CantDisponible) VALUES
(1,'Televisor marca Patitos','tv smart pantalla alta resolucion',120000,3),
(2,'Camara Digital','cámara nueva marca sony',280000,0),
(7,'Smartphone ABC','celular con 8gb ram, 256gb almacenamiento',450000,5);



INSERT INTO Pedidos(PedidoID,ClientID,FechaPedido,MontoTotal,Empleado) VALUES
(1,0302530950,'10/04/2024',360000,123456789),
(2,0302530951,'12/04/2024',450000,234567890),
(328,0302530952,'14/04/2024',900000,456789012);

INSERT INTO DetallePedido(DetalleID,PedidoID,ProductID,Cantidad) VALUES
(1,1,1,1),
(2,2,1,1),
(3,328,7,2);


/*
1.  Obtener el nombre y la cantidad de cada producto vendido en el PedidoID = 328, 
así como la fecha del pedido, ordenado por el nombre del producto. (10 puntos).
*/
SELECT Producto.Nombre, DetallePedido.Cantidad, Pedidos.FechaPedido
FROM DetallePedido
JOIN Producto ON DetallePedido.ProductID = Producto.ProductoID
JOIN Pedidos ON DetallePedido.PedidoID = Pedidos.PedidoID
WHERE DetallePedido.PedidoID = 328
ORDER BY Producto.Nombre;
/*
2. Inserte un nuevo cliente en la tabla Cliente con los siguientes datos: 
(ClienteID: 0112530667,  NombreCliente:  'Emanuel',  ApellidoCliente:  ‘López’,  Correo: ‘elopez@gmail.com’, 
Dirección:  ‘200m  sur  de  la  Catedral  de  Coronado, Urbanización Los Pinos, casa #7’, 
FechaNacimiento: 18/01/1985). Como parte de la inserción, debe revisar que no exista un cliente con el mismo ClienteID,
para evitar duplicarla (10 puntos).
*/
INSERT INTO Cliente (ClienteID, NombreCliente, ApellidoCliente, Correo, Direccion, FechaNacimiento)
VALUES ('0112530667', 'Emanuel', 'López', 'elopez@gmail.com', 
'200m sur de la Catedral de Coronado, Urbanización Los Pinos, casa #7', '1985-01-18');
SELECT * FROM Cliente
/*
3. Actualizar el precio de lista del producto con el ProductID = 7 a un 10% más alto que su precio actual (10 puntos).
*/
SELECT * FROM Producto WHERE ProductoID=7
UPDATE Producto
SET Precio = Precio * 1.1
WHERE ProductoID = 7;
SELECT * FROM Producto WHERE ProductoID=7



/*
4. Eliminar de la tabla de Producto, todos los productos cuyo precio sea superior a 3000, 
que su nombre contenga la palabra ‘digital’ en cualquier posición y que la cantidad disponible 
sea igual a cero (10 puntos).
*/
SELECT * FROM Producto
DELETE FROM Producto
WHERE Precio > 3000 AND Nombre LIKE '%digital%' AND CantDisponible = 0;
SELECT * FROM Producto
/*
5. Obtener la lista de pedidos (PedidoID), el monto total de cada pedido, la fecha de cada pedido,
el nombre del empleado y el apellido del empleado que generó cada pedido,  para  el  cliente  
con  el  ClienteID  =  0302530952,  ordenados  en  orden alfabética por el PedidoID (10 puntos).
*/
SELECT Pedidos.PedidoID, Pedidos.MontoTotal, Pedidos.FechaPedido, Empleados.NombreEmpleado, Empleados.ApellidoEmpleado
FROM Pedidos
JOIN Empleados ON Pedidos.Empleado = Empleados.EmpleadoID
WHERE Pedidos.ClientID = 0302530952
ORDER BY Pedidos.PedidoID;
/*
6. Seleccionar el EmpleadoID, nombre y apellido de cada empleado, así como el número de pedidos 
que ha generado y el monto total de las ventas para los pedidos que generó, 
ordenados por el monto de ventas de mayor a menor (10 puntos).
*/
SELECT Empleados.EmpleadoID, Empleados.NombreEmpleado, Empleados.ApellidoEmpleado, COUNT(Pedidos.PedidoID) AS NumPedidos
,SUM(Pedidos.MontoTotal) AS MontoTotalVentas
FROM Empleados JOIN Pedidos ON Empleados.EmpleadoID = Pedidos.Empleado
GROUP BY Empleados.EmpleadoID, Empleados.NombreEmpleado, Empleados.ApellidoEmpleado
ORDER BY MontoTotalVentas DESC;
