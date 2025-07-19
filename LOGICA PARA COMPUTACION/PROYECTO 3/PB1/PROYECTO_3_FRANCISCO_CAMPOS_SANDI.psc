// *******************************************************************************************************************************************************************
// Universidad Estatal a Distancia - Costa Rica
// Catedra de Desarrollo de Sistemas
// Grado Academico: Diplomado
// III Cuatrimestre, 2023
// Codigo: 03071 - Logica para Computaci�n
// Proyecto No.3
// Temas: 1, 2, 3,4
// Grupo: 08, Profesor: Mauricio Ru�z P�rez
// Estudiante: Francisco Campos Sandi
// Pseudocodigo realizado en PSeint Versi�n pseint-w32-20210609
// *******************************************************************************************************************************************************************
// Se inicia el algoritmo y se declaran las variables
Algoritmo ReporteTardias
	Definir HorasEntrada,horaGenerada,cantTardiasMes,opcion,validacion,sumaDia,mayorDia,sumaSemana,mayorSemana,ubicacionX,ubicacionY,tamannoX,tamannoY Como Entero;
	Definir semanaConMasTardias,mayorSuma  Como Entero;
	Definir MatrizTardias,diasSemana,diaConMasTardias Como Caracter;
	// *****************************************************************************************************************************************************************	
	// Se inicializan las variables en 0
	horaGenerada <- 0;
	cantTardiasMes <- 0;
	opcion <- 0;
	validacion <- 0;
	sumaDia <- 0;
	mayorDia <- 0;
	sumaSemana <- 0;
	mayorSemana <- 0;
	cantTardiasMes <- 0;
	ubicacionY <- 0;
	tamannoX <- 4;//cant semanas
	tamannoY <- 5;//cant d�as
	semanaConMasTardias <- 0;
	mayorSuma <- 0;
	diaConMasTardias <- '';
	Dimension HorasEntrada[tamannoX,tamannoY];
	Dimension MatrizTardias[tamannoX,tamannoY];
	Dimension diasSemana[5];
	diasSemana[0] <- 'Lunes';
	diasSemana[1] <- 'Martes';
	diasSemana[2] <- 'Mi�rcoles';
	diasSemana[3] <- 'Jueves';
	diasSemana[4] <- 'Viernes';
	// *******************************************************************************************************************************************************************
	// Se inicializan las matrices
	Para ubicacionX <- 0 Hasta tamannoX-1 Hacer
		Para ubicacionY <- 0 Hasta tamannoY-1 Hacer
			HorasEntrada[ubicacionX,ubicacionY] <- 0;
			MatrizTardias[ubicacionX,ubicacionY]<-' ';
		FinPara
	FinPara
	// *****************************************************************************************************************************************************************	
	Repetir                                        // se muestra el men� principal 
		Escribir '|==========================|';
		Escribir '|     Men� Principal       |';
		Escribir '|==========================|';
		Escribir '| 1. Generar entradas      |';
		Escribir '| 2. Generar reporte       |';
		Escribir '| 3. Salir                 |';
		Escribir '|__________________________|';
		Escribir ' Ingrese la opci�n deseada: ';
		Leer opcion;
		Repetir
			Si opcion < 0 O opcion > 3 Entonces               // se valida que solo se ingrese valores de 1/2/3                       
				Escribir 'opci�n no disponible. Ingrese una opci�n v�lida (1/2/3).';
				Leer opcion;
			FinSi
		Hasta Que opcion > 0 Y opcion < 4
		// *******************************************************************************************************************************************************************
		Segun opcion  Hacer       //Generamos la matriz con las entradas del colobarador, de manera aleatoria para luego mostrar las entradas por semana y d�a
			1:
				validacion <- 1;                                  //se valida que primero se genere las entradas
				Para ubicacionX <- 0 Hasta tamannoX-1 Hacer
					Para ubicacionY <- 0 Hasta tamannoY-1 Hacer
						Repetir
							horaGenerada <- ALEATORIO(730,830);                                                     //se generan las entradas de manera aleatoria
						Hasta Que (horaGenerada >= 730 Y horaGenerada <= 759) O (horaGenerada >= 800 Y horaGenerada <= 830)   //Solo horas de entradas v�lidas de acurdo al enunciado
						HorasEntrada[ubicacionX,ubicacionY] <- horaGenerada;
					FinPara
				FinPara
				Escribir '===========================';
				Escribir 'Semana  L   K    M   J   V';
				Escribir '===========================';
				Para ubicacionX <- 0 Hasta tamannoX-1 Hacer
					Escribir '   ',ubicacionX+1,'   ' Sin Saltar;
					Para ubicacionY<-0 Hasta tamannoY-1 Hacer
						Escribir HorasEntrada[ubicacionX,ubicacionY],' ' Sin Saltar;             // se muestran las entradas generadas en el paso anterior
					FinPara
					Escribir (' ');
				FinPara
				// *******************************************************************************************************************************************************************
			2:
				Borrar Pantalla;       // Si el usario ya gener� la entradas en el paso 1 se procede a mostrar la matrz del paso anterior de acuerdo a las indicaciones del proyecto
				Si validacion = 1 Entonces
					Escribir '===========================';                            // se vuelve a mostrar las entradas de acuerdo a las indicaciones
					Escribir 'Semana  L   K    M   J   V';
					Escribir '===========================';
					Para ubicacionX <- 0 Hasta tamannoX-1 Hacer
						Escribir '   ',ubicacionX + 1,'   ' Sin Saltar;
						Para ubicacionY<-0 Hasta tamannoY-1 Hacer
							Escribir HorasEntrada[ubicacionX,ubicacionY],' ' Sin Saltar;
						FinPara
						Escribir (' ');
					FinPara
					Para ubicacionX <- 0 Hasta tamannoX-1 Hacer      // Se convierten las entradas en P o T, de acuerdo a la hora de cada entrada y se muestra las entradas de las 4 semanas
						Para ubicacionY <- 0 Hasta tamannoY-1 Hacer
							Si HorasEntrada[ubicacionX,ubicacionY] > 800 Entonces
								cantTardiasMes <- cantTardiasMes + 1;      //	se suman las tard�as del mes
								MatrizTardias[ubicacionX,ubicacionY] <- 'T';
							SiNo
								MatrizTardias[ubicacionX,ubicacionY] <- 'P';
							FinSi
						FinPara
					FinPara                                       
					Escribir '===========================';    //se muestra la matriz con las entradas convertidas en P o T
					Escribir 'Semana  L   K    M   J   V ';
					Escribir '===========================';
					Para ubicacionX <- 0 Hasta tamannoX-1 Hacer
						Escribir '  ',ubicacionX+1,'   ' Sin Saltar;
						Para ubicacionY <- 0 Hasta tamannoY-1 Hacer
							Escribir '  ',MatrizTardias[ubicacionX,ubicacionY],' ' Sin Saltar;
						FinPara
						Escribir ' ';
					FinPara
					// *******************************************************************************************************************************************************************
                        // Ahora se itera con los d�as de la semana para calcular el d�a con m�s tard�as de acuerdo a la matriz anterior con P o T
					Para ubicacionY <- 0 Hasta tamannoY - 1 Hacer
						sumaDia <- 0;
						Para ubicacionX <- 0 Hasta tamannoX - 1 Hacer
							Si MatrizTardias[ubicacionX, ubicacionY] = "T" Entonces  //se suman la cantidad de T por d�a
								sumaDia <- sumaDia + 1;
							FinSi
						FinPara
						Si sumaDia > mayorDia Entonces          //si la sumaDia es mayor que la suma del d�a anterior se almacena la ubicaci�n de ese d�a, recorriendo todos los d�as
							mayorDia <- sumaDia;
							diaConMasTardias <- diasSemana[ubicacionY];
						FinSi
					FinPara
					// *******************************************************************************************************************************************************************
					Para ubicacionX <- 0 Hasta tamannoX-1 Hacer // ahora se realiza la iteraci�n por semana sumando las T por cada semana 
						sumaSemana <- 0;
						Para ubicacionY <- 0 Hasta tamannoY-1 Hacer
							Si MatrizTardias[ubicacionX,ubicacionY]='T' Entonces //se suman la cantidad de T por semana
								sumaSemana <- sumaSemana + 1;
							FinSi
						FinPara
						Si sumaSemana > mayorSemana Entonces          //si la sumaSeman es mayor que la suma de la semana anterior se almacena la ubicaci�n de la misma, recorriendo las 4 semanas
							mayorSemana <- sumaSemana;     
							semanaConMasTardias <- ubicacionX+1;
						FinSi
					FinPara
					Escribir 'La cantidad de tard�as del mes es: ',cantTardiasMes;                    // se muestran los resultados almacenados de acuerdo al enunciado
					Escribir 'El d�a con m�s tard�as en el mes es: ',diaConMasTardias;
					Si semanaConMasTardias > 0 Entonces
						Escribir 'La semana con m�s tard�as en el mes es la semana: ',semanaConMasTardias;   
					SiNo
						Escribir 'No se encontraron tard�as en ninguna semana.';                  // si en dado caso no hay tard�as se muestra ese mensaje
					FinSi
				SiNo
					Escribir 'No se han generado el registro de entradas.';                  // se deben de generar las entradas de los contrario se muestra dicho mensaje
				FinSi
				// *******************************************************************************************************************************************************************
			3:
				Escribir 'Saliendo del registro';                                 // mensaje de salida del Algoritmo 
		FinSegun
	Hasta Que opcion=3
FinAlgoritmo
