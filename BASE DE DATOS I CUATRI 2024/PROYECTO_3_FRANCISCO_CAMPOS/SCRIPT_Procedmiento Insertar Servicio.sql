/*
1. Crear un procedimiento almacenado que incluya un nuevo servicio en la base de datos.
La lógica del procedimiento almacenado es la siguiente: 
se recibe como parámetros la descripción del servicio, el costo de la mano de obra,
el tiempo estimado del servicio y la cédula del empleado que presta el servicio. 
Antes de insertar  debe  validar  que  el  empleado  exista  en  la  tabla  Empleado.
Si  las validaciones son exitosas, inserta los valores en la tabla Servicios
*/

USE AUTOTECH
CREATE PROCEDURE dbo.SPInsertarServicio
	--Variables que se recibirán como parámetros
    @Descripcion NVARCHAR(200),
    @Costo REAL,
    @Tiempo INT,
    @Cedula NVARCHAR(30)
AS
BEGIN
    SET NOCOUNT ON;

    -- Declarar variable para almacenar el próximo ID de servicio, esta variable funciona internamente
    DECLARE @NuevoIDServicio INT;
	--Declarar variable para almacenar el IdEmpleado de la cedula ingresada, en caso de que exista
	DECLARE @EmpleadoID INT;

    -- Obtener el próximo ID de servicio, en caso de que sea null, se remplaza por 0 y se le aumenta 1
    SELECT @NuevoIDServicio = ISNULL(MAX(IDServicio), 0) + 1 FROM dbo.SERVICIOS;

    -- Verificar si el empleado existe
	--Si no existe 1 registro de la tabla empleados con la cedula que se está ingresando
    IF NOT EXISTS (SELECT 1 FROM dbo.EMPLEADOS WHERE Cedula = @Cedula)
    BEGIN
		--se muestra en pantalla que el empleado no existe
        PRINT 'El Empleado Con La Cédula '+@Cedula+' no existe';
        RETURN;
    END;

	SELECT @EmpleadoID=(SELECT IDEmpleado FROM dbo.EMPLEADOS WHERE Cedula = @Cedula);
    -- Insertar el nuevo servicio
	--Se indica los campos en el orden que se van a insertar
    INSERT INTO dbo.SERVICIOS (IDServicio, Descripcion, CostoManoObra, TiempoEjecucion, IDEmpleado)
    VALUES (@NuevoIDServicio, @Descripcion, @Costo, @Tiempo, @EmpleadoID);
    
	--Si los errores son 0
    IF @@ERROR = 0
		--Se imprime mensaje indicando que el servicio se ha insertado correctamente
        PRINT 'El Servicio Se Ha Insertado Correctamente.';
    ELSE--caso contrario
		--Se imprime mensaje que hubo un error al insertar el servicio
        PRINT 'Error Al Insertar Servicio.';
END;
GO

--Cedula empleado que no existe
EXEC SPInsertarServicio @Descripcion='Remodelacion total', @Costo=700000, @Tiempo=90,
@Cedula='6789012005'

--Cedula empleado que si existe
EXEC SPInsertarServicio @Descripcion='Cambio de pintura', @Costo=200000, @Tiempo=25,
@Cedula='6789012345'



--ver todos los servicios
SELECT * FROM SERVICIOS




