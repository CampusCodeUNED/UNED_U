//Universidad Estatal a Distancia - Costa Rica
//Catedra de Desarrollo de Sistemas
//Grado Academico: Diplomado
//lll Cuatrimestre, 2021
//Codigo: 03071 - Logica para Computacion
//Proyecto No.1 - Tipo Individual
//Temas: 1, 2, 3 (Algoritmos Programacion, Estructuras Decision y Repeticion)
//Grupo: 08, Profesor: Mauricio Ruiz Perez
//Estudiante: Leon Alaric Fernandez Henley
//Pseudocodigo realizado en PSeint Version pseint-w32-20200501
//Perfil Personalizado con requerimientos del Curso (imagen adjunta)
//Fecha: 12/10/2021
// 
//Segun Tutoria: Si condiciones de edad, ingresos o hijos no aplican terminar programa con mensaje y no seguir, ademas no se evalua validacion de enteros


Algoritmo Casa_Para_Costa_Rica
	//Definicion de variables
	Definir	tieneLote, tipoAyuda, sexo, estadoCivil como Caracter; 
	Definir nombre, cedula, ocupacion, tipoGestion, mensajeError como Cadena;
	Definir edad, hijos como Entero;
	Definir ingresos, montoAyuda como Real;
	//Incializacion de Variables
	tieneLote = "";
	tipoAyuda = "";
	sexo = "";
	estadoCivil = "";
	nombre = "";
	cedula = "";
	ocupacion = "";
	tipoGestion = "";
	mensajeError = "";
	edad = 0;
	hijos = -1;
	ingresos = 0;
	montoAyuda = 0;

	Repetir
		Limpiar Pantalla;
		Escribir "BIENVENIDO AL SISTEMA DE AYUDA";
		Escribir "**************************************";
		Escribir "Digite el tipo de Ayuda que Necesita";
		Escribir "Cuenta con lote para construir? Presione 1=SI o 2=NO";
		Leer tieneLote;
		Si (tieneLote <> "1") Y (tieneLote <> "2") Entonces		//Verifica opciones validas
			Escribir "Indique una opcion valida (1 o 2)";
			Escribir "Presione cualquier tecla para continuar...";
			Esperar Tecla;
		FinSi
	Hasta Que (tieneLote == "1") O (tieneLote == "2")
	Si (tieneLote = "2") Entonces
		Escribir "Estos programas no aplican para familias sin lote";
	SiNo
		Escribir "1. Ayuda Adulto Tercera Edad";
		Escribir "2. Pobreza Extrema";
		Repetir
			Leer tipoAyuda;
			Si (tipoAyuda <> "1") Y (tipoAyuda <> "2") Entonces	//Verifica opciones validas
				Escribir "Indique una opcion valida (1 o 2)";
			FinSi
		Hasta Que (tipoAyuda == "1") O (tipoAyuda == "2")
		Escribir "Digite su nombre";
		Repetir
			Leer nombre;
			Si (nombre == "") Entonces				//Verifica que nombre no sea nulo
				Escribir "Debe digitar su nombre";
			FinSi
		Hasta Que (nombre <> "")
		Escribir "Digite su cedula";
		Repetir
			Leer cedula;
			Si (cedula == "") Entonces				//Verifica que cedula no sea nulo
				Escribir "Debe digitar su cedula";
			FinSi
		Hasta Que (cedula <> "")
		Escribir "Edad";
		Repetir
			Leer edad;
			Si (edad <= 0) Entonces					//Verifica que edad sea mayor a cero
				Escribir "Indique una edad valida mayor a cero ";
			FinSi
		Hasta Que (edad > 0)
		//validar opciones respecto a edad
		Si (tipoAyuda == "1") Entonces
			Si (edad < 65) Entonces
				tipoGestion = "Invalido";
				mensajeError = "Debe ser Adulto mayor para el tipo de ayuda indicado";
			FinSi
		Sino
			Si (edad < 18) O (edad >= 65) Entonces
				tipoGestion = "Invalido";
				mensajeError = "Debe ser mayor de edad y menor que 65 para el tipo de ayuda indicado";
			FinSi
		FinSi
		Si (tipoGestion == "Invalido") Entonces
			Escribir mensajeError;
		SiNo
			Escribir "Sexo";
			Escribir "1. Masculino";
			Escribir "2. Femenino";
			Repetir
				Leer sexo;
				Si (sexo <> "1") Y (sexo <> "2") Entonces	//Verifica opcion valida
					Escribir "Indique una opcion valida (1 o 2)";
				FinSi
			Hasta Que (sexo == "1") O (sexo == "2")	
			Escribir "Ocupacion";
			Repetir
				Leer ocupacion;
				Si (ocupacion == "") Entonces			//Verifica que ocupacion no este vacia
					Escribir "Debe digitar una ocupacion";
				FinSi
			Hasta Que (ocupacion <> "")	
			Escribir "Ingresos";
			Repetir
				Leer ingresos;
				Si (ingresos <= 0) Entonces			//Verifica que ingresos no sea menor a cero
					Escribir "Ingresos deben ser mayor a cero";
				FinSi
			Hasta Que (ingresos > 0)
			//validar opciones respecto a ingresos
			Si (ingresos > 300000) Entonces
				tipoGestion = "Invalido";
				mensajeError = "Monto de ingresos excede al maximo para ayudas";
			SiNo
				Si (ingresos <= 200000) Entonces
					Si (tipoAyuda == "1") Entonces
						tipoGestion = "TerceraEdad-AyudaTotal";
					SiNo
						tipoGestion = "PobrezaExtrema-AyudaTotal";
					FinSi
					montoAyuda = 10000000;
				SiNo
					Si (tipoAyuda == "1") Entonces
						tipoGestion = "TerceraEdad-AyudaParcial";
					SiNo
						tipoGestion = "PobrezaExtrema-AyudaParcial";
					FinSi
					montoAyuda = 5000000;
				FinSi					
			FinSi
			Si (tipoGestion == "Invalido") Entonces
				Escribir mensajeError;
			SiNo
				Escribir "Cantidad de Hijos";
				Repetir
					Leer hijos;
					Si (hijos < 0) Entonces			//Verifica que hijos no sea negativo
						Escribir "Digite 0 o mas hijos";
					FinSi
				Hasta Que (hijos >= 0)				
				//validar opciones respecto a hijos e tipo ayuda
				Si (tipoAyuda == "2") Entonces
					Si (hijos <= 2) Entonces
						tipoGestion = "Invalido";
						mensajeError = "Cantidad de hijos menor al minimo para ayudas";
					SiNo
						Si (hijos <= 4) Entonces
							tipoGestion = "PobrezaExtrema-AyudaParcial";
							montoAyuda = 5000000;
						FinSi
					FinSi
				FinSi	
				Si (tipoGestion == "Invalido") Entonces
					Escribir mensajeError;
				SiNo
					Escribir "*******************************************************************";
					Escribir "Usted podra tramitar la siguiente ayuda segun los datos ingresados:";
					Escribir "Nombre Ayuda ", tipoGestion;
					Escribir "Nombre: ", nombre;
					Escribir "Edad: ", edad;
					Escribir "Monto de ayuda que podrias gestionar: ", montoAyuda;
					Escribir "*******************************************************************";
				FinSi
			FinSi
		FinSi
	FinSi

FinAlgoritmo

