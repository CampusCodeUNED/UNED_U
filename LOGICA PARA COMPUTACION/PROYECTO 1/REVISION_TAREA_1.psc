Algoritmo ExpedicionStickers
	Definir d�gitoPlaca,a�oFabricacion,a�oActual,mesRevision,a�oProximaRevision Como Entero
	Escribir 'Ingrese el �ltimo d�gito de la placa del veh�culo: '
	Leer d�gitoPlaca
	Escribir 'Ingrese el a�o de fabricaci�n del veh�culo: '
	Leer a�oFabricacion
	a�oActual <- 2023 // Suponiendo que el a�o actual es 2023
	Si d�gitoPlaca>=0 Y d�gitoPlaca<=9 Entonces
		Si a�oActual-a�oFabricacion<=5 Entonces
			a�oProximaRevision <- a�oActual+2
		SiNo
			a�oProximaRevision <- a�oActual+1
		FinSi
		Segun d�gitoPlaca  Hacer
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
		Escribir 'La pr�xima revisi�n ser� en el mes ',mesRevision,' del a�o ',a�oProximaRevision
	SiNo
		Escribir 'El �ltimo d�gito de la placa no es v�lido.'
	FinSi
FinAlgoritmo
