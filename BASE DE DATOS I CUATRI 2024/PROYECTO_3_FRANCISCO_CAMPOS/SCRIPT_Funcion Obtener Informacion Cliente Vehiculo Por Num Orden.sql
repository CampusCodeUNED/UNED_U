/*
Crear una función que permita obtener la información de un cliente y su vehículo 
para una orden de trabajo específica. La lógica de la función es la siguiente: 
se recibe como parámetro el número de orden y se verifica si esa orden existe y 
no se encuentra en estado cancelada.
Si se cumple con las condiciones, se realiza una consulta en las tablas correspondientes
y se retorna el número de cédula, nombre completo y teléfono del cliente. 
También, el número de placa, marca y modelo del vehículo del cliente. 
dicionalmente, el número de orden de trabajo, la descripción y el estado de la orden.
*/

CREATE FUNCTION dbo.InformacionClienteVehiculoParaOrdenTrabajo
(
	--Variable que se recibirá como parámetro
    @NumeroOrden INT
)
RETURNS TABLE --Se indica que va a retornar una tabla 
AS
--Aqui se especifica que datos son los que va a retornar
RETURN
(
    SELECT 
		--Cedula cliente y se la renombre como CedulaCliente
        c.Cedula AS CedulaCliente,
		--Se concatena nombres y apellidos y se renombra como NombreCompletoCliente
        CONCAT(c.Nombre, ' ', c.Apellido1, ' ', c.Apellido2) AS NombreCompletoCliente,
		--el telefono se lo renombre como TelefonoCliente
        c.Telefono AS TelefonoCliente,
		--Placa, Marcar y Modelo del vehículo
		v.Placa, v.Marca, v.Modelo,
		--El numero de orden se lo renombra como NumeroOrdenTrabajo
        o.NumeroOrden AS NumeroOrdenTrabajo,
        --La descripcion de la orden se la renombre como DescripcionOrdenTrabajo
		o.Descripcion AS DescripcionOrdenTrabajo,
        --La descripcion de estado se la renombra como EstadoOrden
		e.Descripcion AS EstadoOrden
    FROM 
        dbo.ORDENES o --Ordenes es o
	--Inner Join con la tabla clientes donde clientes es c
    INNER JOIN dbo.CLIENTES c ON o.CedulaCliente = c.Cedula
    --Inner Join con la tabla vehiculo donde vehiculos es v
	INNER JOIN dbo.VEHICULO v ON o.PlacaVehiculo = v.Placa
	--Inner Join con la tabla clientes donde ordenes estados es e
    INNER JOIN dbo.ORDENES_ESTADOS e ON o.EstadoOrden = e.IDEstado
	--La condición es que el número de orden debe ser igual al nro orden ingresado
	--y el estado de la orden debe ser distinto a cancelada
    WHERE o.NumeroOrden = @NumeroOrden AND e.Descripcion <> 'Cancelada'
);

SELECT * FROM InformacionClienteVehiculoParaOrdenTrabajo(25)