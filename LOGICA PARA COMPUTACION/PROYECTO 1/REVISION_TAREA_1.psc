Algoritmo ExpedicionStickers
	Definir dígitoPlaca,añoFabricacion,añoActual,mesRevision,añoProximaRevision Como Entero
	Escribir 'Ingrese el último dígito de la placa del vehículo: '
	Leer dígitoPlaca
	Escribir 'Ingrese el año de fabricación del vehículo: '
	Leer añoFabricacion
	añoActual <- 2023 // Suponiendo que el año actual es 2023
	Si dígitoPlaca>=0 Y dígitoPlaca<=9 Entonces
		Si añoActual-añoFabricacion<=5 Entonces
			añoProximaRevision <- añoActual+2
		SiNo
			añoProximaRevision <- añoActual+1
		FinSi
		Segun dígitoPlaca  Hacer
			1:
				// Enero
				mesRevision <- 1
			2: // Febrero
				mesRevision <- 2
			3: // Marzo
				mesRevision <- 3
			4: // Abril
				mesRevision <- 4
			5: // Mayo
				mesRevision <- 5
			6: // Junio
				mesRevision <- 6
			7: // Julio
				mesRevision <- 7
			8: // Agosto
				mesRevision <- 8
			9: // Setiembre
				mesRevision <- 9
			0: // Octubre
				mesRevision <- 10
		FinSegun
		Escribir 'La próxima revisión será en el mes ',mesRevision,' del año ',añoProximaRevision
	SiNo
		Escribir 'El último dígito de la placa no es válido.'
	FinSi
FinAlgoritmo
