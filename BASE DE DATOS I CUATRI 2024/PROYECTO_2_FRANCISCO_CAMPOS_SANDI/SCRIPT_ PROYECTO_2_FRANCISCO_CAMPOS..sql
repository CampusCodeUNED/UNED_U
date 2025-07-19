use AUTOTECH
go


--
---------------------------------------------------------
--
--/* Eliminar todos los datos de las tablas.*/

select 'DELETE'

DELETE FROM SERVICIOS_POR_ORDEN;
DELETE FROM REPUESTOS_POR_SERVICIOS;
DELETE FROM SERVICIOS;
DELETE FROM EMPLEADOS;
DELETE FROM REPUESTOS;
DELETE FROM TIPO_REPUESTO;
DELETE FROM ORDENES;
DELETE FROM CLIENTES;
DELETE FROM ORDENES_ESTADOS;
DELETE FROM VEHICULO;  

--
---------------------------------------------------------
--
/*volver a cargar los datos.*/


select '1 - CLIENTES' as tabla, *
	from dbo.CLIENTES

select '2 - EMPLEADOS' as tabla, *
	from dbo.EMPLEADOS

select '3 - ORDENES' as tabla, *
	from dbo.ORDENES

select '4 - ORDENES_ESTADOS' as tabla, *
	from dbo.ORDENES_ESTADOS

select '5 - REPUESTOS' as tabla, *
	from dbo.REPUESTOS

select '6 - REPUESTOS_POR_SERVICIOS' as tabla, *
	from dbo.REPUESTOS_POR_SERVICIOS

select '7 - SERVICIOS' as tabla, *
	from dbo.SERVICIOS

select '8 - SERVICIOS_POR_ORDEN' as tabla, *
	from dbo.SERVICIOS_POR_ORDEN

select '9 - TIPO_REPUESTO' as tabla, *
	from dbo.TIPO_REPUESTO

select '10 - VEHICULO'as tabla, *
	from dbo.VEHICULO

--
---------------------------------------------------------
--/*insertamos las tablas*/

select 'INSERT'

--
---------------------------------------------------------
--


-- CLIENTES
-- 11 ROWS
insert into dbo.CLIENTES VALUES (1010101010, 'Amanda', 'Gonzalez', 'Perez', 9876543210, '707 Pineapple St', 'amanda@example.com')
insert into dbo.CLIENTES VALUES (1111111111, 'John', 'Doe', 'Smith', 1234567890, '123 Main St', 'john@example.com')
insert into dbo.CLIENTES VALUES (1212121212, 'David', 'Smith', 'Taylor', 9878456210, '907 Pineapple St', 'David@example.com')
insert into dbo.CLIENTES VALUES (2222222222, 'Jane', 'Smith', 'Doe', 987654321, '456 Elm St', 'jane@example.com')
insert into dbo.CLIENTES VALUES (3333333333, 'Michael', 'Johnson', 'Williams', 5678901234, '789 Oak St', 'michael@example.com')
insert into dbo.CLIENTES VALUES (4444444444, 'Emily', 'Brown', 'Jones', 2345678901, '101 Pine St', 'emily@example.com')
insert into dbo.CLIENTES VALUES (5555555555, 'David', 'Wilson', 'Taylor', 8901234567, '202 Cedar St', 'david@example.com')
insert into dbo.CLIENTES VALUES (6666666666, 'Sarah', 'Martinez', 'Garcia', 3456789012, '303 Maple St', 'sarah@example.com')
insert into dbo.CLIENTES VALUES (7777777777, 'Daniel', 'Anderson', 'Davis', 123456789, '404 Birch St', 'daniel@example.com')
insert into dbo.CLIENTES VALUES (8888888888, 'Jessica', 'Rodriguez', 'Lopez', 4567890123, '505 Walnut St', 'jessica@example.com')
insert into dbo.CLIENTES VALUES (9999999999, 'Christopher', 'Hernandez', 'Brown', 6789012345, '606 Willow St', 'christopher@example.com')

-- EMPLEADOS
-- 10 ROWS
insert into dbo.EMPLEADOS VALUES(1, 1234567890, 'Alice', 'Johnson', 'White', 'Mecanico', 'Brakes', '2023-01-01')
insert into dbo.EMPLEADOS VALUES(2, 2345678901, 'Bob', 'Williams', 'Brown', 'Mecanico', 'Brakes', '2023-01-01')
insert into dbo.EMPLEADOS VALUES(3, 3456789012, 'Charlie', 'Garcia', 'Martinez', 'Mecanico', 'TuneUp', '2023-01-01')
insert into dbo.EMPLEADOS VALUES(4, 4567890123, 'Diana', 'Jones', 'Taylor', 'Mecanico', 'TuneUp', '2023-01-01')
insert into dbo.EMPLEADOS VALUES(5, 5678901234, 'Emma', 'Smith', 'Davis', 'Mecanico', 'Tires', '2023-01-01')
insert into dbo.EMPLEADOS VALUES(6, 6789012345, 'Frank', 'Lopez', 'Rodriguez', 'Mecanico', 'Tires', '2023-01-01')
insert into dbo.EMPLEADOS VALUES(7, 7890123456, 'Grace', 'Anderson', 'Hernandez', 'Administrador', 'Sales', '2023-01-01')
insert into dbo.EMPLEADOS VALUES(8, 8901234567, 'Henry', 'Brown', 'Gonzalez', 'Ventas', 'Sales', '2023-01-01')
insert into dbo.EMPLEADOS VALUES(9, 9012345678, 'Isabel', 'Perez', 'Wilson', 'Mecanico', 'CARWASH', '2023-01-01')
insert into dbo.EMPLEADOS VALUES(10, 123456789, 'Jack', 'Gonzalez', 'Hernandez', 'Mecanico', 'CARWASH', '2023-01-01')

-- ORDENES_ESTADOS
-- 4 ROWS
insert into dbo.ORDENES_ESTADOS VALUES (1, 'Sin Iniciar')
insert into dbo.ORDENES_ESTADOS VALUES (2, 'En Proceso')
insert into dbo.ORDENES_ESTADOS VALUES (3, 'Completada')
insert into dbo.ORDENES_ESTADOS VALUES (4, 'Cancelada')


-- VEHICULO
-- 10 ROWS
insert into dbo.VEHICULO VALUES ('ABC123','Toyota', 'Corolla', 2022, 'Red')
insert into dbo.VEHICULO VALUES ('BCD890','Kia', 'Optima', 2023, 'Purple')
insert into dbo.VEHICULO VALUES ('DEF456','Honda', 'Civic', 2021, 'Blue')
insert into dbo.VEHICULO VALUES ('GHI789','Ford', 'Focus', 2023, 'Black')
insert into dbo.VEHICULO VALUES ('JKL012','Chevrolet', 'Malibu', 2020, 'Silver')
insert into dbo.VEHICULO VALUES ('MNO345','BMW', '3 Series', 2019, 'White')
insert into dbo.VEHICULO VALUES ('PQR678','Audi', 'A4', 2022, 'Gray')
insert into dbo.VEHICULO VALUES ('STU901','Mercedes-Benz', 'C-Class', 2023, 'Green')
insert into dbo.VEHICULO VALUES ('VWX234','Nissan', 'Altima', 2021, 'Yellow')
insert into dbo.VEHICULO VALUES ('YZA567','Volkswagen', 'Jetta', 2020, 'Orange')

-- ORDENES
-- 48 ROWS
insert into dbo.ORDENES VALUES (1, 'LAVADO FULL', '2024-07-03', '2024-07-03',1111111111, 'ABC123', 2)
insert into dbo.ORDENES VALUES (2, 'LAVADO VEHICULO', '2024-07-03', '2024-07-03',1111111111, 'DEF456', 2)
insert into dbo.ORDENES VALUES (3, 'LAVADO FULL', '2024-07-03', '2024-07-03',2222222222, 'GHI789', 2)
insert into dbo.ORDENES VALUES (4, 'LAVADO FULL', '2024-07-03', '2024-07-03',2222222222, 'JKL012', 2)
insert into dbo.ORDENES VALUES (5, 'LAVADO FULL', '2024-07-03', '2024-07-03',3333333333, 'MNO345', 2)
insert into dbo.ORDENES VALUES (6, 'LAVADO FULL', '2024-07-03', '2024-07-03',3333333333, 'PQR678', 2)
insert into dbo.ORDENES VALUES (7, 'LAVADO FULL', '2024-07-03', '2024-07-03',4444444444, 'STU901', 2)
insert into dbo.ORDENES VALUES (8, 'LAVADO FULL', '2024-07-03', '2024-07-03',4444444444, 'VWX234', 2)
insert into dbo.ORDENES VALUES (9, 'LAVADO FULL', '2024-07-03', '2024-07-03',5555555555, 'YZA567', 2)
insert into dbo.ORDENES VALUES (10, 'LAVADO FULL', '2024-07-03', '2024-07-03',5555555555, 'BCD890', 2)
insert into dbo.ORDENES VALUES (11, 'LAVADO FULL', '2024-07-03', '2024-07-03',6666666666, 'ABC123', 2)
insert into dbo.ORDENES VALUES (12, 'LAVADO FULL', '2024-07-03', '2024-07-03',6666666666, 'DEF456', 2)
insert into dbo.ORDENES VALUES (13, 'LAVADO FULL', '2024-07-03', '2024-07-03',7777777777, 'GHI789', 2)
insert into dbo.ORDENES VALUES (14, 'LAVADO FULL', '2024-07-03', '2024-07-03',7777777777, 'VWX234', 2)
insert into dbo.ORDENES VALUES (15, 'LAVADO FULL', '2024-07-03', '2024-07-03',8888888888, 'BCD890', 2)
insert into dbo.ORDENES VALUES (16, 'LAVADO FULL', '2024-07-03', '2024-07-03',8888888888, 'YZA567', 2)
insert into dbo.ORDENES VALUES (17, 'LAVADO FULL', '2024-07-03', '2024-07-03',9999999999, 'MNO345', 2)
insert into dbo.ORDENES VALUES (18, 'LAVADO FULL', '2024-07-03', '2024-07-03',9999999999, 'VWX234', 2)
insert into dbo.ORDENES VALUES (19, 'LAVADO FULL', '2024-07-03', '2024-07-03',1010101010, 'GHI789', 2)
insert into dbo.ORDENES VALUES (20, 'LAVADO FULL', '2024-07-03', '2024-07-03',1010101010, 'STU901', 2)
insert into dbo.ORDENES VALUES (21, 'LAVADO FULL', '2024-07-03', '2024-07-03',1212121212, 'PQR678', 2)
insert into dbo.ORDENES VALUES (22, 'LAVADO FULL', '2024-07-03', '2024-07-03',9999999999, 'MNO345', 3)
insert into dbo.ORDENES VALUES (23, 'LAVADO FULL', '2024-07-03', '2024-07-03',1111111111, 'ABC123', 3)
insert into dbo.ORDENES VALUES (24, 'LAVADO SENCILLO', '2024-07-03', '2024-07-03',1111111111, 'DEF456', 3)
insert into dbo.ORDENES VALUES (25, 'CAMBIO LLANTAS', '2024-07-03', '2024-07-03',2222222222, 'GHI789', 3)
insert into dbo.ORDENES VALUES (26, 'BALANCEO Y ALINEAMIENTO', '2024-07-03', '2024-07-03',2222222222, 'JKL012', 3)
insert into dbo.ORDENES VALUES (27, 'CAMBIO ACEITE', '2024-07-03', '2024-07-03',3333333333, 'MNO345', 3)
insert into dbo.ORDENES VALUES (28, 'AFINAMIENTO MOTOR', '2024-07-03', '2024-07-03',3333333333, 'PQR678', 3)
insert into dbo.ORDENES VALUES (29, 'AJUSTE DE FRENOS', '2024-07-03', '2024-07-03',4444444444, 'STU901', 3)
insert into dbo.ORDENES VALUES (30, 'CAMBIO DE FRENOS', '2024-07-03', '2024-07-03',4444444444, 'VWX234', 3)
insert into dbo.ORDENES VALUES (31, 'LAVADO FULL', '2024-07-03', '2024-07-03',5555555555, 'YZA567', 3)
insert into dbo.ORDENES VALUES (32, 'LAVADO SENCILLO', '2024-07-03', '2024-07-03',5555555555, 'BCD890', 3)
insert into dbo.ORDENES VALUES (33, 'CAMBIO LLANTAS', '2024-07-03', '2024-07-03',6666666666, 'ABC123', 3)
insert into dbo.ORDENES VALUES (34, 'BALANCEO Y ALINEAMIENTO', '2024-07-03', '2024-07-03',6666666666, 'DEF456', 3)
insert into dbo.ORDENES VALUES (35, 'CAMBIO ACEITE', '2024-07-03', '2024-07-03',7777777777, 'GHI789', 3)
insert into dbo.ORDENES VALUES (36, 'AFINAMIENTO MOTOR', '2024-07-03', '2024-07-03',7777777777, 'VWX234', 3)
insert into dbo.ORDENES VALUES (37, 'AJUSTE DE FRENOS', '2024-07-03', '2024-07-03',8888888888, 'BCD890', 3)
insert into dbo.ORDENES VALUES (38, 'CAMBIO DE FRENOS', '2024-07-03', '2024-07-03',8888888888, 'YZA567', 3)
insert into dbo.ORDENES VALUES (39, 'LAVADO FULL', '2024-07-03', '2024-07-03',4444444444, 'STU901', 1)
insert into dbo.ORDENES VALUES (40, 'LAVADO FULL', '2024-07-03', '2024-07-03',4444444444, 'VWX234', 1)
insert into dbo.ORDENES VALUES (41, 'LAVADO FULL', '2024-07-03', '2024-07-03',5555555555, 'YZA567', 1)
insert into dbo.ORDENES VALUES (42, 'LAVADO FULL', '2024-07-03', '2024-07-03',5555555555, 'BCD890', 1)
insert into dbo.ORDENES VALUES (43, 'LAVADO FULL', '2024-07-03', '2024-07-03',6666666666, 'ABC123', 1)
insert into dbo.ORDENES VALUES (44, 'LAVADO FULL', '2024-07-03', '2024-07-03',6666666666, 'DEF456', 1)
insert into dbo.ORDENES VALUES (45, 'LAVADO FULL', '2024-07-03', '2024-07-03',7777777777, 'GHI789', 1)
insert into dbo.ORDENES VALUES (46, 'LAVADO FULL', '2024-07-03', '2024-07-03',7777777777, 'VWX234', 1)
insert into dbo.ORDENES VALUES (47, 'LAVADO FULL', '2024-07-03', '2024-07-03',4444444444, 'STU901', 4)
insert into dbo.ORDENES VALUES (48, 'LAVADO FULL', '2024-07-03', '2024-07-03',4444444444, 'VWX234', 4)

-- TIPO_REPUESTO
-- 4 ROWS
insert into dbo.TIPO_REPUESTO VALUES (1, 'LIMIEZA')
insert into dbo.TIPO_REPUESTO VALUES (2, 'CARROCERIA')
insert into dbo.TIPO_REPUESTO VALUES (3, 'MOTOR')
insert into dbo.TIPO_REPUESTO VALUES (4, 'SEGURIDAD')


-- REPUESTOS
-- 12  ROWS
insert into dbo.REPUESTOS VALUES('1AS2D3', 'VALVULA', 'VALVULA LLANTA', 1000, 7, 4)
insert into dbo.REPUESTOS VALUES('1QW2E3', 'CERA MEGUIARS', 'CERA PARA CARROCERIA', 10000, 10, 2)
insert into dbo.REPUESTOS VALUES('1UI2P3', 'ZAPATAS', 'ZAPATAS PARA FRENO', 108500, 40, 3)
insert into dbo.REPUESTOS VALUES('1ZX2C3', 'ACEITE CASTROL', 'ACEITE 20W50', 18500, 20, 3)
insert into dbo.REPUESTOS VALUES('2AS3D4', 'DUNLOP', 'LLANTA ARO 13', 20000, 4, 4)
insert into dbo.REPUESTOS VALUES('2QW3E4', 'SHAMPOO MEGUIARS', 'SHAMPOO PARA CARROCERIA', 3000, 10, 1)
insert into dbo.REPUESTOS VALUES('2UI3P4', 'LIMPIADOR DE FRENES', 'LIMPIEZA DE FRENOS', 8500, 15, 3)
insert into dbo.REPUESTOS VALUES('2ZX3C4', 'FILTRO ACEITE', 'FILTRO MOTOR DE ACEITE', 58500, 16, 3)
insert into dbo.REPUESTOS VALUES('3AS4D5', 'DUNLOP', 'LLANTA ARO 15', 20000, 7, 4)
insert into dbo.REPUESTOS VALUES('3QW4E5', 'ABRILLANTADOR MEGUIARS', 'ABRILLANTADOR PARA LLANTAS', 3000, 10, 1)
insert into dbo.REPUESTOS VALUES('4AS5D6', 'DUNLOP', 'LLANTA ARO 17', 20000, 15, 4)
insert into dbo.REPUESTOS VALUES('5AS6D7', 'PESAS', 'PESAS PARA ALINEADO', 20000, 12, 4)

-- SERVICIOS
-- 8 ROWS
insert into dbo.SERVICIOS values (1, 'Lavado, Encerado y Aspirado', 10000, 60, 9)
insert into dbo.SERVICIOS values (2, 'Lavado y Aspirado', 8000, 60, 10)
insert into dbo.SERVICIOS values (3, 'Cambio llantas', 15000, 20, 5)
insert into dbo.SERVICIOS values (4, 'Balanceo y alineamiento de llantas', 20000, 10, 6)
insert into dbo.SERVICIOS values (5, 'Cambio aceite', 30000, 60, 4)
insert into dbo.SERVICIOS values (6, 'Afinamiento Motor', 30000, 60, 3)
insert into dbo.SERVICIOS values (7, 'Ajuste de frenos', 50000, 60, 2)
insert into dbo.SERVICIOS values (8, 'Cambio de frenos', 100000, 60, 1)

-- REPUESTOS_POR_SERVICIOS
-- 10 ROWS
insert into dbo.REPUESTOS_POR_SERVICIOS VALUES ('1AS2D3', 3)
insert into dbo.REPUESTOS_POR_SERVICIOS VALUES ('1QW2E3', 1)
insert into dbo.REPUESTOS_POR_SERVICIOS VALUES ('1UI2P3', 8)
insert into dbo.REPUESTOS_POR_SERVICIOS VALUES ('1ZX2C3', 5)
insert into dbo.REPUESTOS_POR_SERVICIOS VALUES ('2QW3E4', 1)
insert into dbo.REPUESTOS_POR_SERVICIOS VALUES ('2QW3E4', 2)
insert into dbo.REPUESTOS_POR_SERVICIOS VALUES ('2UI3P4', 8)
insert into dbo.REPUESTOS_POR_SERVICIOS VALUES ('2ZX3C4', 5)
insert into dbo.REPUESTOS_POR_SERVICIOS VALUES ('3QW4E5', 1)
insert into dbo.REPUESTOS_POR_SERVICIOS VALUES ('5AS6D7', 4)


-- SERVICIOS_POR_ORDEN
-- 48 ROWS
insert into dbo.SERVICIOS_POR_ORDEN VALUES (1, 1)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (1, 3)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (1, 5)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (1, 7)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (1, 9)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (1, 11)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (1, 13)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (1, 15)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (1, 17)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (1, 19)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (1, 21)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (1, 22)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (1, 23)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (1, 31)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (1, 40)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (1, 42)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (1, 44)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (1, 46)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (1, 48)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (2, 2)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (2, 4)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (2, 6)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (2, 8)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (2, 10)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (2, 12)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (2, 14)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (2, 16)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (2, 18)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (2, 20)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (2, 24)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (2, 32)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (2, 39)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (2, 41)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (2, 43)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (2, 45)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (2, 47)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (3, 25)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (3, 33)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (4, 26)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (4, 34)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (5, 27)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (5, 35)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (6, 28)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (6, 36)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (7, 29)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (7, 37)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (8, 30)
insert into dbo.SERVICIOS_POR_ORDEN VALUES (8, 38)


--
---------------------------------------------------------
--
/*seleccionamos las tablas*/
select '1 - CLIENTES' as tabla, *
	from dbo.CLIENTES

select '2 - EMPLEADOS' as tabla, *
	from dbo.EMPLEADOS

select '3 - ORDENES' as tabla, *
	from dbo.ORDENES

select '4 - ORDENES_ESTADOS' as tabla, *
	from dbo.ORDENES_ESTADOS

select '5 - REPUESTOS' as tabla, *
	from dbo.REPUESTOS

select '6 - REPUESTOS_POR_SERVICIOS' as tabla, *
	from dbo.REPUESTOS_POR_SERVICIOS

select '7 - SERVICIOS' as tabla, *
	from dbo.SERVICIOS

select '8 - SERVICIOS_POR_ORDEN' as tabla, *
	from dbo.SERVICIOS_POR_ORDEN

select '9 - TIPO_REPUESTO' as tabla, *
	from dbo.TIPO_REPUESTO

select '10 - VEHICULO'as tabla, *
	from dbo.VEHICULO

--
---------------------------------------------------------
--
