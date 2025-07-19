//Universidad Estatal a Distancia - Costa Rica
//Catedra de Desarrollo de Sistemas
//Grado Academico: Diplomado
//lll Cuatrimestre, 2021
//Codigo: 03071 - Logica para Computacion
//Proyecto No.2 - Tipo Individual
//Temas: 1, 2, 3 (Algoritmos Programacion, Estructuras Decision y Repeticion)
//Grupo: 08, Profesor: Mauricio Ruiz Perez
//Estudiante: Leon Alaric Fernandez Henley
//Pseudocodigo realizado en PSeint Version pseint-w32-20200501
//Perfil Personalizado con requerimientos del Curso (imagen adjunta)
//Fecha: 25/10/2021
//Segun Foro de Consultas:
//Si el estudiante no se encuentra en el rango de edad de 1 a 5 mostrar mensaje de respectivo y salir en vez de seguir solicitando datos

Algoritmo Caritas_Felices
	//Declaracion de Variables
	Definir nivel, opcion, salir, respuesta como Caracter;
	Definir nombre, direccion, madre, padre, encargado como Cadena;
	Definir edad como Entero;
	Definir libros, material, deporte, musica, arte, seguro como Logicas;
	Definir librosMonto, materialMonto, deporteMonto, musicaMonto, arteMonto, seguroMonto como Real;
	Definir matricula, gastos, descNivel1, descNivel2, descNivel3, descuentos, subTotal, total, pendiente, totalGeneral como Real;
	//Inicializacion de Variables
	nivel = "";
	opcion = "";
	salir = "N";
	respuesta = "N";	//Controlo si usuario por decision desea eliminar de los rubros adicionales alguna opcion previamente escogido
	nombre = "";
	direccion = "";
	madre = "";
	padre = "";
	encargado = "";
	edad = 0;
	libros = FALSO;		//Estos booleanos lo inicializamos en Falso para controlar si se desea cancelar o no con la matricula
	material = FALSO;
	deporte = FALSO;
	musica = FALSO;
	arte = FALSO;
	seguro = FALSO;
	librosMonto = 20000;
	materialMonto = 10000;
	deporteMonto = 15000;
	musicaMonto = 10000;
	arteMonto = 5000;
	seguroMonto = 15000;
	matricula = 130000;
	gastos = 0;		//Con esta variable acumula lo que usuario va a cancelar en el momento de matricula
	descNivel1 = 20;
	descNivel2 = 10;
	descNivel3 = 10;
	descuentos = 0;
	subTotal = 0;
	total = 0;
	pendiente = 0;		//Aqui inicialmente van todos los rubros adicionales y se deducen en el momento que el usuario los selecciona		
	totalGeneral = 0;	//Una variable adicional simplemente para mostrar el total general (cancelado y pendiente)

	//Ingreso de datos del Estudiante
	Limpiar Pantalla;
	Escribir "Escuela Caritas Felices - Ingreso Estudiante";
	Escribir "--------------------------------------------";
	Repetir
		Escribir "Nombre del Estudiante";
		Leer nombre;
		Si  (nombre == "") Entonces					//Verifica que el campo no este vacio
			Escribir "Favor digitar nombre, intentelo de nuevo";
		FinSi
	Hasta Que (nombre <> "")
	Repetir
		Escribir "Edad";
		Leer edad;
		Si  (edad <= 0) Entonces					//Verifica que edad no sea menor a uno
			Escribir "Edad debe ser mayor a cero, intentelo de nuevo";
		FinSi			
	Hasta Que (edad > 0)
	//Segun Foro de consultas, el estudiante debe cumplir con estar con edad entre 1 y 5
	//Si no cumple dicho requerimiento debe desplegar un mensaje y terminar de lo contrario seguir pidiendo lo demas
	Si (edad > 5) Entonces
		Escribir "Lo sentimos, por reglamento esta institucion solo permite edades entre 1 y 5";
	SiNo
		//No se solicita validar los siguientes campos, sin embargo incluyo que al menos no dejarlo vacio 
		Repetir
			Escribir "Direccion";
			Leer direccion;
			Si  (direccion == "") Entonces
				Escribir "Digite al menos X por ejemplo, intentelo de nuevo";
			FinSi
		Hasta Que (direccion <> "")
		Repetir
			Escribir "Nombre de la Madre";
			Leer madre;
			Si  (madre == "") Entonces
				Escribir "Digite al menos X por ejemplo, intentelo de nuevo";
			FinSi
		Hasta Que (madre <> "")
		Repetir
			Escribir "Nombre del Padre";
			Leer padre;
			Si  (padre == "") Entonces
				Escribir "Digite al menos X por ejemplo, intentelo de nuevo";
			FinSi
		Hasta Que (padre <> "")
		Repetir
			Escribir "Nombre del Encargado";
			Leer encargado;
			Si  (encargado == "") Entonces
				Escribir "Digite al menos X por ejemplo, intentelo de nuevo";
			FinSi
		Hasta Que (encargado <> "")
		Repetir
			//Segun enunciado se despliega menu, sin embargo segun logica el sistema deberia escoger automaticamente el nivel ya que es directo por edad
			Escribir "Indique el nivel del estudiante";
			Escribir "1.Interactivo1 (Edades 1-2)";
			Escribir "2.Interactivo2 (Edades 3-4)";
			Escribir "3.Transicion  (Edad 5)";
			Escribir "";
			Escribir "Debe ingresar alguno de los siguientes niveles:";
			Escribir "(Por la edad indicada puede escoger esta opcion)";	//Aqui encapsulamos el nivel dependiendo de las edades
			Si (edad == 1) O (edad == 2) Entonces
				Repetir
					Escribir "1.Interactivo1 (Edades 1-2), digite nivel";
					Leer nivel;
					Si (nivel <> "1") Entonces			//Verifico que escoga la opcion valida
						Escribir "Favor seleccionar 1 para continuar, intentelo de nuevo.";
					FinSi
				Hasta Que (nivel == "1")
			FinSi
			Si (edad == 3) O (edad == 4) Entonces
				Repetir
					Escribir "2.Interactivo2 (Edades 3-4), digite nivel";
					Leer nivel;
					Si (nivel <> "2") Entonces			//Verifico que escoga la opcion valida
						Escribir "Favor seleccionar 2 para continuar, intentelo de nuevo.";
					FinSi
				Hasta Que (nivel == "2")
			FinSi
			Si (edad == 5) Entonces
				Repetir
					Escribir "3.Transicion (Edad 5)";
					Leer nivel;
					Si (nivel <> "3") Entonces			//Verifico que escoga la opcion valida
						Escribir "Favor seleccionar 3 para continuar, intentelo de nuevo.";
					FinSi
				Hasta Que (nivel == "3")
			FinSi
			//Segun enunciado original habria que pedir solo de 1 a 3 aunque no tiene logica por diferencia de edades, de ser asi seria:
			//Escribir "p o s i b i l i d a d e s";
			//Repetir
			//	Leer nivel;
			//	Si  (nivel <> "1") Y (nivel <> "2") Y (nivel <> "3") Entonces
			//		Escribir "Favor digitar Nivel, intentelo de nuevo";
			//	FinSi
			//Hasta que (nivel == "1") O (nivel == "2") O (nivel == "3")
		Hasta Que (nivel == "1") O (nivel == "2") O (nivel == "3")
		//Controlamos el monto del descuento dependiendo del nivel respectivo y lo imprimo en pantalla para usuario
		Si (nivel == "1") Entonces
			descuentos = matricula * (descNivel1 / 100);
			Escribir "Descuento sobre matricula ", descNivel1, "% = ", descuentos;
		SiNo
			Si (nivel == "2") Entonces
				descuentos = matricula * (descNivel2 / 100);
				Escribir "Descuento sobre matricula ", descNivel2, "% = ", descuentos;
			SiNo
				descuentos = matricula * (descNivel3 / 100);
				Escribir "Descuento sobre matricula ", descNivel3, "% = ", descuentos;
			FinSi
		FinSi
		Escribir "";
		//Otra manera de hacerlo es la siguiente, pero no podria desplegar el porcentaje respectivo, solo monto
		//Escribir "Descuento sobre la matricula: ", descuentos;
		Escribir "----------Ingreso de Datos concluido----------";
		Escribir "Presione una tecla para continuar....";
		Esperar Tecla;
		//Despliegue de Rubros Adicionales, comienzo sumando por default todos los montos respectivos a pendiente
		pendiente = librosMonto + materialMonto + deporteMonto + musicaMonto + arteMonto + seguroMonto;
		Repetir
			Repetir
				Limpiar Pantalla;
				Escribir "Montos escogidos para cancelar con Matricula: ", gastos;
				Escribir "Montos pendientes de pago: ", pendiente;
				Escribir "========================================================";
				Escribir "Favor indicar los rubros adicionales que desea cancelar:";
				//Estos condicionales controlan el despliegue en pantalla de lo que esta pendiente y lo que esta escogido ya
				//respecto a los rubros de gastos adicionales que cada estudiante debe cancelar
				Escribir "1.Compra de Libros ",librosMonto," colones...", Sin Saltar;
				Si (libros == Falso) Entonces
					Escribir "pendiente";
				SiNo
					Escribir "[X]";
				FinSi
				Escribir "2.Materiales ",materialMonto," colones.........", Sin Saltar;
				Si (material == Falso) Entonces
					Escribir "pendiente";
				SiNo
					Escribir "[X]";
				FinSi
				Escribir "3.Club de Deportes ",deporteMonto," colones...", Sin Saltar;
				Si (deporte == Falso) Entonces
					Escribir "pendiente";
				SiNo
					Escribir "[X]";
				FinSi
				Escribir "4.Club de Musica ",musicaMonto," colones.....", Sin Saltar;
				Si (musica == Falso) Entonces
					Escribir "pendiente";
				SiNo
					Escribir "[X]";
				FinSi
				Escribir "5.Club de Arte ",arteMonto," colones........", Sin Saltar;
				Si (arte == Falso) Entonces
					Escribir "pendiente";
				SiNo
					Escribir "[X]";
				FinSi
				Escribir "6.Seguro estudiantil ",seguroMonto," colones.", Sin Saltar;
				Si (seguro == Falso) Entonces
					Escribir "pendiente";
				SiNo
					Escribir "[X]";
				FinSi
				Escribir "7.Salir";
				Leer opcion;
				//Valido que opcion digitado este en el rango dado
				Si (opcion <> "1") Y (opcion <> "2") Y (opcion <> "3") Y (opcion <> "4") Y (opcion <> "5") Y (opcion <> "6") Y (opcion <> "7") Entonces
					Escribir "Digite del 1 al 7 como opciones validas";
					Escribir "Presione una tecla para continuar...";
					Esperar Tecla;
				FinSi
			Hasta Que (opcion == "1") O (opcion == "2") O (opcion == "3") O (opcion == "4") O (opcion == "5") O (opcion == "6") O (opcion == "7")
			Escribir "";
			//Con este segun controlo las opciones booleanas de la escogencia de cada gasto adicional
			//mostrando si ya fuese cancelado o no, en cuyo caso cambia de valor y suma a gastos a cancelar y resta a pendientes cada uno
			//Si fuese alrevez, que el usuario metio un dedazo en su seleccion o cambia de opinion validamos que pueda revertir esa decision
			//y por ende los gastos y pendientes se actualizan si es el caso, ademas del valor booleano respectivo que vuelve a FALSO
			Segun ConvertirANumero(opcion) Hacer
				1: 
					Si (libros == VERDADERO) Entonces
						respuesta = "N";
						Repetir
							Escribir "Ya incluido [marcado con X], desea quitarlo?";
							Leer respuesta;
							Si (Mayusculas(respuesta) <> "N") Y (Mayusculas(respuesta) <> "S") Entonces
								Escribir "Digite S o N unicamente, intente de nuevo";
							FinSi
						Hasta Que (Mayusculas(respuesta) == "N") O (Mayusculas(respuesta) == "S")
						Si (Mayusculas(respuesta) == "S") Entonces
							libros = FALSO;
							gastos = gastos - librosMonto;
							pendiente = pendiente + librosMonto;							
						FinSi
					SiNo
						libros = VERDADERO;
						gastos = gastos + librosMonto;
						pendiente = pendiente - librosMonto;
					FinSi
				2: 
					Si (material == VERDADERO) Entonces
						respuesta = "N";
						Repetir
							Escribir "Ya incluido [marcado con X], desea quitarlo?";
							Leer respuesta;
							Si (Mayusculas(respuesta) <> "N") Y (Mayusculas(respuesta) <> "S") Entonces
								Escribir "Digite S o N unicamente, intente de nuevo";
							FinSi
						Hasta Que (Mayusculas(respuesta) == "N") O (Mayusculas(respuesta) == "S")
						Si (Mayusculas(respuesta) == "S") Entonces
							material = FALSO;
							gastos = gastos - materialMonto;
							pendiente = pendiente + materialMonto;							
						FinSi
					SiNo
						material = VERDADERO;
						gastos = gastos + materialMonto;
						pendiente = pendiente - materialMonto;
					FinSi
				3: 
					Si (deporte == VERDADERO) Entonces
						respuesta = "N";
						Repetir
							Escribir "Ya incluido [marcado con X], desea quitarlo?";
							Leer respuesta;
							Si (Mayusculas(respuesta) <> "N") Y (Mayusculas(respuesta) <> "S") Entonces
								Escribir "Digite S o N unicamente, intente de nuevo";
							FinSi
						Hasta Que (Mayusculas(respuesta) == "N") O (Mayusculas(respuesta) == "S")
						Si (Mayusculas(respuesta) == "S") Entonces
							deporte = FALSO;
							gastos = gastos - deporteMonto;
							pendiente = pendiente + deporteMonto;							
						FinSi
					SiNo
						deporte = VERDADERO;
						gastos = gastos + deporteMonto;
						pendiente = pendiente - deporteMonto;
					FinSi
				4: 
					Si (musica == VERDADERO) Entonces
						respuesta = "N";
						Repetir
							Escribir "Ya incluido [marcado con X], desea quitarlo?";
							Leer respuesta;
							Si (Mayusculas(respuesta) <> "N") Y (Mayusculas(respuesta) <> "S") Entonces
								Escribir "Digite S o N unicamente, intente de nuevo";
							FinSi
						Hasta Que (Mayusculas(respuesta) == "N") O (Mayusculas(respuesta) == "S")
						Si (Mayusculas(respuesta) == "S") Entonces
							musica = FALSO;
							gastos = gastos - musicaMonto;
							pendiente = pendiente + musicaMonto;							
						FinSi
					SiNo
						musica = VERDADERO;
						gastos = gastos + musicaMonto;
						pendiente = pendiente - musicaMonto;
					FinSi
				5: 
					Si (arte == VERDADERO) Entonces
						respuesta = "N";
						Repetir
							Escribir "Ya incluido [marcado con X], desea quitarlo?";
							Leer respuesta;
							Si (Mayusculas(respuesta) <> "N") Y (Mayusculas(respuesta) <> "S") Entonces
								Escribir "Digite S o N unicamente, intente de nuevo";
							FinSi
						Hasta Que (Mayusculas(respuesta) == "N") O (Mayusculas(respuesta) == "S")
						Si (Mayusculas(respuesta) == "S") Entonces
							arte = FALSO;
							gastos = gastos - arteMonto;
							pendiente = pendiente + arteMonto;							
						FinSi
					SiNo
						arte = VERDADERO;
						gastos = gastos + arteMonto;
						pendiente = pendiente - arteMonto;
					FinSi
				6: 
					Si (seguro == VERDADERO) Entonces
						respuesta = "N";
						Repetir
							Escribir "Ya incluido [marcado con X], desea quitarlo?";
							Leer respuesta;
							Si (Mayusculas(respuesta) <> "N") Y (Mayusculas(respuesta) <> "S") Entonces
								Escribir "Digite S o N unicamente, intente de nuevo";
							FinSi
						Hasta Que (Mayusculas(respuesta) == "N") O (Mayusculas(respuesta) == "S")
						Si (Mayusculas(respuesta) == "S") Entonces
							seguro = FALSO;
							gastos = gastos - seguroMonto;
							pendiente = pendiente + seguroMonto;							
						FinSi
					SiNo
						seguro = VERDADERO;
						gastos = gastos + seguroMonto;
						pendiente = pendiente - seguroMonto;
					FinSi
				7: 
					Repetir
						Escribir "Esta seguro que desea salir? (S/N)";
						Leer salir;
						Si (Mayusculas(salir) <> "S") Y (Mayusculas(salir) <> "N") Entonces		//Verifico que en realidad desea salir
							Escribir "Digite S o N, intente de nuevo";
						FinSi
					Hasta Que (Mayusculas(salir) == "S") O (Mayusculas(salir) == "N")
				De Otro Modo:
					Escribir "Esto no deberia salir segun la logica del algoritmo, presione una tecla";
					Esperar Tecla;
			FinSegun
		Hasta Que (Mayusculas(salir) == "S")
		//Factura Final de lo Cancelado y Pendiente
		Limpiar Pantalla;
		Escribir "*******************************************************";
		Escribir "************ Factura de Cancelacion Matricula *********";
		Escribir "Escuela Caritas Felices - Detalle de Factura";
		Escribir "Nombre del Estudiante: ", nombre;
		Escribir "Edad: ", edad;
		Escribir "Nivel matriculado: ", nivel;
		Escribir "Monto de Matricula:..............................", matricula;
		//En los rubros adicionales los booleanos me controlan como despliego la informacion, sea esta a cancelar o pendiente
		//Estos fueron agregados o no en el menu de opciones de gastos adicionales por el usuario en pantalla anterior
		Escribir "Rubros Adicionales:";
		Escribir "===================";
		Escribir "Libros: ....", Sin Saltar;
		Si (libros == Falso) Entonces
			Escribir " Cuenta por pagar: ", librosMonto;
		SiNo
			Escribir "......................................", librosMonto;
		FinSi
		Escribir "Materiales: ", Sin Saltar;
		Si (material == Falso) Entonces
			Escribir " Cuenta por pagar: ", materialMonto;
		SiNo
			Escribir "......................................", materialMonto;
		FinSi
		Escribir "Deportes: ..", Sin Saltar;
		Si (deporte == Falso) Entonces
			Escribir " Cuenta por pagar: ", deporteMonto;
		SiNo
			Escribir "......................................", deporteMonto;
		FinSi
		Escribir "Musica: ....", Sin Saltar;
		Si (musica == Falso) Entonces
			Escribir " Cuenta por pagar: ", musicaMonto;
		SiNo
			Escribir "......................................", musicaMonto;
		FinSi
		Escribir "Arte: ......", Sin Saltar;
		Si (arte == Falso) Entonces
			Escribir " Cuenta por pagar: ", arteMonto;
		SiNo
			Escribir "......................................", arteMonto;
		FinSi
		Escribir "Seguro: ....", Sin Saltar;
		Si (seguro == Falso) Entonces
			Escribir " Cuenta por pagar: ", seguroMonto;
		SiNo
			Escribir "......................................", seguroMonto;
		FinSi
		Escribir "Gastos Pagados....................................", gastos;
		subTotal = matricula + gastos;
		Escribir "SubTotal: .......................................", subTotal;
		Escribir "Descuento sobre matricula: ", Sin Saltar;
		//Este condicional es para desplegar no solo el monto de descuento sino tambien el porcentaje respectivo segun nivel escogido
		Si (nivel == "1") Entonces
			Escribir descNivel1, "% =                  ", descuentos;
		SiNo
			Si (nivel == "2") Entonces
				Escribir descNivel2, "% =                  ", descuentos;
			SiNo
				Escribir descNivel3, "% =                  ", descuentos;
			FinSi
		FinSi
		Escribir "-------------------------------------------------------";
		total = subTotal - descuentos;
		Escribir "Total a pagar: ..................................", total;
		Escribir "-------------------------------------------------------";
		Escribir "Monto Pendiente de pago:.......", pendiente;
		totalGeneral = total + pendiente;
		Escribir "Total General:...................................",totalGeneral;
		Escribir "---------------------------GRACIAS---------------------";
		Escribir "*******************************************************";
	FinSi
FinAlgoritmo

