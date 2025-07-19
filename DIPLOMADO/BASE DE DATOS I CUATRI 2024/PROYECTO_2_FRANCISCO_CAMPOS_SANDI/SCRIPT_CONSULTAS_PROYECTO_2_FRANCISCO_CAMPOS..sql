use AUTOTECH
go

/*
2- Cree una consulta en SQL que presente la siguiente información de las órdenes de trabajo existentes en la base de datos: 
	número de orden, 
	descripción del trabajo, 
	fecha de ingreso, 
	fecha estimada de entrega, 
	estado actual de la orden, 
	número de cédula del cliente, 
	nombre completo del cliente, 
	teléfono del cliente, 
	placa del vehículo, 
	marca del vehículo y 
	modelo del vehículo.
*/
select o.NumeroOrden
	 , o.Descripcion
	 , o.FechaIngreso
	 , o.FechaEntrega
	 , o.EstadoOrden
	 , o.CedulaCliente
	 , c.Nombre + ' ' + c.Apellido1 + ' '+ c.Apellido2 as Nombre_Completo
	 , c.Telefono
	 , o.PlacaVehiculo
	 , v.Marca
	 , v.Modelo
	from dbo.ORDENES o 
		inner join dbo.CLIENTES c
			on o.CedulaCliente = c.Cedula
		inner join dbo.VEHICULO v
			on o.PlacaVehiculo = v.Placa

/*
3- Genere una consulta en SQL que indique la cantidad de órdenes de trabajo que están registradas en la base de datos, agrupados por el estado.
*/

select oe.Descripcion	as Tipo
	  ,count(1)			as Cantidad 
	from dbo.ORDENES o 
		inner join dbo.ORDENES_ESTADOS oe 
			on o.EstadoOrden = oe.IDEstado
group by oe.Descripcion

/*
4- Genere una consulta en SQL que permita calcular el costo que debe pagarse por cada orden de trabajo completada. Para lo anterior, deberá de sumar el costo de la
mano de obra al cálculo del costo de los repuestos utilizados. Al monto obtenido se le debe aplicar un 13% adicional de impuesto de ventas. 
*/

select *
	from dbo.ORDENES_ESTADOS oe 
/*
1	Sin Iniciar
2	En Proceso
3	Completada
4	Cancelada
*/

select o.CedulaCliente
	  ,o.NumeroOrden
	  ,o.Descripcion
	  ,FORMAT(sum(s.CostoManoObra),'C','Sp-Cr') as Costo_Mano_Obra 
	  --,r.NumeroParte
	  --,r.Nombre	  
	  ,FORMAT(sum(r.CostoRepuesto),'C','Sp-Cr') as Costo_Repuestos 
	  ,FORMAT( ( ( sum(s.CostoManoObra) + sum(r.CostoRepuesto) ) * 1.13),'C','Sp-Cr') as Total 
-- select *
	from dbo.ORDENES o
		inner join dbo.SERVICIOS_POR_ORDEN spo
			on o.NumeroOrden = spo.NumeroOrden
			and o.EstadoOrden = 3 
		inner join dbo.SERVICIOS s
			on spo.IdServicio = s.IDServicio
		inner join dbo.REPUESTOS_POR_SERVICIOS rps
			on s.IDServicio = rps.IdServicio
		inner join dbo.REPUESTOS r
			on rps.NumeroParte = r.NumeroParte
group by o.CedulaCliente
		,o.NumeroOrden
		,o.Descripcion
order by o.CedulaCliente
	    ,o.NumeroOrden

/*
5- Cree un comando en SQL que actualice la fecha estimada de entrega de las órdenes de trabajo a tres días adicionales a la fecha que tienen en la actualidad, 
para aquellas ordenes cuyo tipo de estado NO sea completada o cancelada. Por ejemplo, si en la actualidad tienen como fecha estimada de entrega el 15-09-2023, 
una vez actualizada debe quedar el 18-09-2023.
*/

select *
	from dbo.ORDENES o
where o.EstadoOrden in (1, 2)

update dbo.ORDENES 
	set FechaEntrega = DATEADD (day, 3, FechaEntrega)
where EstadoOrden in (1, 2)

