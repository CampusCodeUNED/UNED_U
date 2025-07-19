//Universidad Estatal a Distancia - Costa Rica
//Catedra de Desarrollo de Sistemas
//Grado Academico: Diplomado
//lll Cuatrimestre, 2021
//Codigo: 03071 - Logica para Computacion
//Proyecto No.4 - Tipo Individual
//Temas: 1, 2, 3, 4 y 5 (Algoritmos Programacion, Estructuras Decision y Repeticion, Arreglos y Funciones)
//Grupo: 08, Profesor: Mauricio Ruiz Perez
//Estudiante: Leon Alaric Fernandez Henley
//Pseudocodigo realizado en PSeint Version pseint-w32-20200501
//Perfil Personalizado con requerimientos del Curso (imagen adjunta)
//Fecha: 15/11/2021
//Segun Foro de Consultas:
//

Algoritmo Facturas_Varias
	menu();			//Muestro Menu con opciones
	Limpiar Pantalla;
	Escribir "Saliendo del Sistema...";
	Escribir "";
FinAlgoritmo

//Menu Generico
Subproceso menu()
	//Definicion de variables
	Definir opcion, salir como Caracter;
	Definir consecutivo como Entero;
	//Inicializacion de Variables
	opcion = '';
	salir = 'N';
	consecutivo = 1;
	Repetir
		//Solicitud al usuario si desea seguir en el programa o no
		Repetir	
				encabezado();
				Escribir "-----------------------------------------------------------";
				Escribir "INGRESE EL TIPO DE FACTURA QUE DESEA REALIZAR";
				Escribir "-----------------------------------------------------------";
				Escribir "1. Factura de Articulos";
				Escribir "2. Factura de Servicios Profesionales";
				Escribir "3. Salir";
				Escribir "";
				Escribir "Seleccione una opcion ( 1 / 2 / 3 )";
				Leer opcion;
				Si ( opcion <> '1' ) Y ( opcion <> '2' ) Y ( opcion <> '3' ) Entonces
					error_opcion();
				FinSi
		Hasta Que ( opcion == '1' ) O ( opcion == '2' ) O ( opcion == '3' )
		Segun ConvertirANumero(opcion) Hacer
			1: 	
				articulos(consecutivo);					//llamo a funcion para facturar articulos
			2: 	
				servicios(consecutivo);					//llamo a funcion para facturar servicios
			3: 	
				finalizar(salir);				//llamo a funcion para corroborar que desea salir por si fue un dedazo
			De Otro Modo:
				Escribir "OOPPPS, meti la pata en algo";	//No deberia llegar hasta aqui si la validacion esta correcta
				piepagina();
		FinSegun
	Hasta Que (Mayusculas(salir) = 'S')
FinSubProceso

//Funcion para Facturar Articulos
SubProceso articulos(consecutivo por Referencia)
	//Usamos un arreglo para ir guardando los datos de la factura (item, cantidad y precio)
	Definir nombre, cedula, direccion, datos como Cadena;
	Definir tipo, maxfila, maxcolumna como Entero;
	//Inicializo variables
	nombre = "";
	cedula = "";
	direccion = "";
	tipo = 1;					//tipo factura 1: articulos
	maxfila = 10;					//numero de filas maximo de matriz (cantidad maxima de articulos)
	maxcolumna = 3;					//numero de columnas maximo de matriz (item, cantidad y precio como columnas)
	Dimension datos[maxfila,maxcolumna];		//Creo arreglo de datos para la factura, al llenar tiene que generar otra factura para lo que falta
	//Inicio matriz
	inicializar(datos, maxfila, maxcolumna);
	//Llamo a funcion cliente para verificar datos
	cliente(nombre, cedula, direccion, tipo);
	//llamar a funcion para facturar
	facturar(datos, maxfila, maxcolumna, tipo);
	//Imprimimos la factura como tal
	imprimir(datos,maxfila,maxcolumna,nombre,cedula,direccion,consecutivo);
	piepagina();
FinSubProceso

//Funcion para Facturar Servicios
SubProceso servicios(consecutivo por Referencia)
	//Usamos un arreglo para ir guardando los datos de la factura (item, cantidad y precio)
	Definir nombre, cedula, direccion, datos como Cadena;
	Definir tipo, maxfila, maxcolumna como Entero;
	//Inicializo variables
	nombre = "";
	cedula = "";
	direccion = "";
	tipo = 2;					//tipo factura 2: servicios
	maxfila = 10;					//numero de filas maximo de matriz (cantidad maxima de servicios)
	maxcolumna = 3;					//numero de columnas maximo de matriz (item, cantidad y precio como columnas)
	Dimension datos[maxfila,maxcolumna];		//Creo arreglo de datos para la factura, al llenar tiene que generar otra factura para lo que falta
	//Inicio matriz
	inicializar(datos, maxfila, maxcolumna);
	//Llamo a funcion cliente para verificar datos
	cliente(nombre, cedula, direccion, tipo);
	//llamar a funcion para facturar
	facturar(datos, maxfila, maxcolumna, tipo);
	//Imprimimos la factura com tal
	imprimir(datos,maxfila,maxcolumna,nombre,cedula,direccion,consecutivo);
	piepagina();
FinSubProceso

//Funcion para inicializar matrices en cero
SubProceso inicializar(MatrizN,filas,columnas)
	//Definicion de Variables temporales para subproceso
	Definir reng, col como Entero;
	reng=0;
	col=0;
	Para reng=0 Hasta filas-1 con Paso 1 Hacer
		Para col=0 Hasta columnas-1 con Paso 1 Hacer
			MatrizN[reng,col] = "";
		FinPara
	FinPara
FinSubproceso

//Funcion para ingresar datos de cliente
SubProceso cliente(nombre por Referencia, cedula por Referencia, direccion por Referencia, tipoN)
	//Imprimimos encabezado y comenzamos con las preguntas y validaciones
	encabezado();
	Si (tipoN == 1) Entonces
		Escribir "Proceso de Facturacion de Articulos";
	SiNo
		Escribir "Proceso de Facturacion de Servicios";
	FinSi
	Escribir "";
	Escribir "INGRESE DATOS DEL CLIENTE";
	Escribir "=========================";
	Repetir
		Escribir "Nombre: ", Sin Saltar;
		Leer nombre;
		Si (nombre == "") Entonces
			error_vacio();
		FinSi
	Hasta Que (nombre <> "")
	Repetir
		Escribir "Cedula: ", Sin Saltar;
		Leer cedula;
		Si (cedula == "") Entonces
			error_vacio();
		FinSi
	Hasta Que (cedula <> "")
	Repetir
		Escribir "Direccion: ", Sin Saltar;
		Leer direccion;
		Si (direccion == "") Entonces
			error_vacio();
		FinSi
	Hasta Que (direccion <> "")
	Escribir "";
	//piepagina();
FinSubProceso

//Funcion que recoge lineas de facturacion
SubProceso facturar(MatrizN,filas,columnas,tipoN)
	Definir verifica como Logico;
	Definir insertar como Entero;
	Definir terminar como Caracter;
	Definir item, cantidad, precio como Cadena;
	insertar=0;
	terminar = 'S';
	encabezado();
	Repetir
		Limpiar Pantalla;
		Si (tipoN == 1) Entonces
			Escribir "Proceso de Facturacion de Articulos";
		SiNo
			Escribir "Proceso de Facturacion de Servicios";
		FinSi
		Escribir "";
		//Inicializo variables de lectura para cada linea a facturar
		item = "";
		cantidad = "";
		precio	= "";
		//Verificamos que no este llena una factura, sino serian lineas interminables y genera un error en subindices
		Si (insertar == filas) Entonces
			Escribir "Ya no hay campo para mas articulos, favor generar otra factura al terminar este.";
			//Forzamos salida inmediata del ciclo
			terminar = 'N';
		SiNo
			Si (tipoN == 1) Entonces
				Escribir "INGRESE ARTICULOS";
				Escribir "=================";
				Repetir
					Escribir "Articulo ",insertar+1,": ", Sin Saltar;
					Leer item;
					Si (item == "") Entonces
						error_vacio;
					FinSi
				Hasta Que (item <> "")
				Repetir
					Escribir "Cantidad: ", Sin Saltar;
					Leer cantidad;
					//Verificamos que efectivamente se trata de un numero entero positivo
					verifica = validarEntero(cantidad);
					Si (verifica == FALSO) Entonces
						error_noesnumero();
					FinSi
				Hasta Que (verifica == VERDADERO)
				Repetir
					Escribir "Precio Unitario: ", Sin Saltar;
					Leer precio;
					//Verificamos que efectivamente se trata de un numero entero positivo
					verifica = validarEntero(precio);
					Si (verifica == FALSO) Entonces
						error_noesnumero();
					FinSi
				Hasta Que (verifica == VERDADERO) 
			SiNo
				Escribir "INGRESE SERVICIOS";
				Escribir "=================";
				Repetir
					Escribir "Servicio Prestado ",insertar+1,": ", Sin Saltar;
					Leer item;
					Si (item == "") Entonces
						error_vacio;
					FinSi
				Hasta Que (item <> "")
				Repetir
					Escribir "Cantidad de Horas: ", Sin Saltar;
					Leer cantidad;
					//Verificamos que efectivamente se trata de un numero entero positivo
					verifica = validarEntero(cantidad);
					Si (verifica == FALSO) Entonces
						error_noesnumero();
					FinSi
				Hasta Que (verifica == VERDADERO)
				Repetir
					Escribir "Monto por Hora: ", Sin Saltar;
					Leer precio;
					//Verificamos que efectivamente se trata de un numero entero positivo
					verifica = validarEntero(precio);
					Si (verifica == FALSO) Entonces
						error_noesnumero();
					FinSi
				Hasta Que (verifica == VERDADERO)		
			FinSi
			//Insertamos datos en la matriz
			MatrizN[insertar,0] = item;
			MatrizN[insertar,1] = cantidad;
			MatrizN[insertar,2] = precio;
			//Incrementamos contador de inserciones
			insertar = insertar + 1;
			//Le damos la opcion de dar por finalizado la factura si asi lo desea, si todavia hay espacio
			Si (insertar < filas-1) Entonces
				yanomas(terminar);
			FinSi
		FinSi
	Hasta Que (Mayusculas(terminar) == 'N')
	piepagina();
FinSubproceso

//Funcion para validar que una cadena efectivamente tiene un numero entero positivo
SubProceso retorno = validarEntero(x)
	Definir retorno como Logico;
	Definir temp como Cadena;
	Definir i,j como Entero;
	//Evaluacion de la cadena especifica
	Para i=0 hasta Longitud(x)-1 con paso 1 Hacer
		//suponemos que no es un numero entero positivo
		retorno = Falso;
		temp = subcadena(x, i, i);
		Para j=0 Hasta 9 con paso 1 Hacer
			//Comparamos con el valor de j a ver si es igual a alguno de la serie 1 a 9
			Si temp = ConvertirATexto(j) Entonces
				retorno = Verdadero;
				//Si corresponde salimos del ciclo interno
				j = 25;
			FinSi
		FinPara
		//Si la suposicion sigue en falso suponemos que algun digito no corresponde y forzamos salida
		Si retorno = Falso Entonces
			i = 40;
		FinSi
	FinPara
FinSubProceso

//Pantalla de opcion TERMINAR de insertar lineas en factura
SubProceso yanomas(terminar Por Referencia)
	Repetir
		//Escribir " ";
		Escribir "Desea ingresar mas lineas (S/N)? :" sin saltar;
		Leer terminar;
		Si (Mayusculas(terminar) <> 'S') Y (Mayusculas(terminar) <> 'N') Entonces
			error_son();
		FinSi
	Hasta Que (Mayusculas(terminar) = 'S') O (Mayusculas(terminar) = 'N')
FinSubProceso

//Funcion que muestra los valores del arreglo pasado por parametro
SubProceso imprimir(MatrizN,filas,columnas,nombre,cedula,direccion,consecutivo por Referencia)
	//Definicion de Variables temporales para subproceso
	Definir reng, col como Entero;
	Definir monto, subTotal, IVA, Total como Real;
	reng=0;
	col=0;
	monto = 0;
	subTotal = 0;
	IVA = 0.13;
	Total = 0;
	Limpiar Pantalla;
	//Indica profesor que es mejor que sea consecutivo
	//Escribir "FACTURA  000-",Aleatorio(1,1000);
	Escribir "Factura 000-",consecutivo;
	//Incrementamos consecutivo
	consecutivo = consecutivo + 1;
	Escribir "=====================================================================";
	Escribir "EMPRESA: ", Sin Saltar;
	empresa();
	//Cortamos nombre si tiene mas de 25 caracteres para no romper formato de factura
	Escribir "CLIENTE: ", Sin Saltar;
	Escribir subcadena(nombre, 0, 25), "     ", sin saltar;
	//Cortamos numero de cedula si excede 15 caracteres para no romper formato factura
	Escribir "CEDULA: ", Sin Saltar;
	Escribir subcadena(cedula, 0, 15);
	//Cortamos direccion si tiene mas de 50 caracteres para no romper formato de factura
	Escribir "DIRECCION: ",Sin Saltar;
	Escribir subcadena(direccion, 0, 50);
	Escribir "=====================================================================";
	Escribir "CANTIDAD  DESCRIPCION               PRECIO             PRECIO TOTAL";
	Para reng=0 Hasta filas-1 Con Paso 1 Hacer
		Si (MatrizN[reng,0] = "") Entonces
			Escribir "          ---------- Ultima Linea ----------          ";
			//Forzamos salida del ciclo
			reng = filas-1;
		SiNo
			//determinamos precio total en cada linea
			monto = ( (ConvertirANumero(MatrizN[reng, 1])) * (ConvertirANumero(MatrizN[reng, 2])) );
			//Escribimos cantidad
			Segun Longitud(MatrizN[reng,1]) Hacer
				//Si no tiene campo ocupado (vacio, es decir 0 caracteres) le indico al usuario
				0: Escribir "  N.D.   ", sin saltar;
				1: Escribir MatrizN[reng, 1], "         ", sin saltar;
				2: Escribir MatrizN[reng, 1], "        ", sin saltar;
				3: Escribir MatrizN[reng, 1], "       ", sin saltar;
				4: Escribir MatrizN[reng, 1], "      ", sin saltar;
				5: Escribir MatrizN[reng, 1], "     ", sin saltar;
				6: Escribir MatrizN[reng, 1], "    ", sin saltar;
				7: Escribir MatrizN[reng, 1], "   ", sin saltar;
				8: Escribir MatrizN[reng, 1], "  ", sin saltar;
				De Otro Modo:
				//si tiene mas caracteres acorto a 14 espacios para que no corra los demas espacios
				Escribir subcadena(MatrizN[reng, 1], 0, 8), " ", sin saltar;
			FinSegun
			//Escribimos Descripcion
			Segun Longitud(MatrizN[reng,0]) Hacer
					//Si no tiene campo ocupado (vacio, es decir 0 caracteres) le indico al usuario
				0: Escribir "            N.D.          ", sin saltar;
				1: Escribir MatrizN[reng, 0], "                         ", sin saltar;
				2: Escribir MatrizN[reng, 0], "                        ", sin saltar;
				3: Escribir MatrizN[reng, 0], "                       ", sin saltar;
				4: Escribir MatrizN[reng, 0], "                      ", sin saltar;
				5: Escribir MatrizN[reng, 0], "                     ", sin saltar;
				6: Escribir MatrizN[reng, 0], "                    ", sin saltar;
				7: Escribir MatrizN[reng, 0], "                   ", sin saltar;
				8: Escribir MatrizN[reng, 0], "                  ", sin saltar;
				9: Escribir MatrizN[reng, 0], "                 ", sin saltar;	
				10: Escribir MatrizN[reng, 0], "                ", sin saltar;	
				11: Escribir MatrizN[reng, 0], "               ", sin saltar;	
				12: Escribir MatrizN[reng, 0], "              ", sin saltar;	
				13: Escribir MatrizN[reng, 0], "             ", sin saltar;	
				14: Escribir MatrizN[reng, 0], "            ", sin saltar;	
				15: Escribir MatrizN[reng, 0], "           ", sin saltar;		
				De Otro Modo:
					//si tiene mas caracteres acorto a 15 espacios para que no corra los demas espacios
					Escribir subcadena(MatrizN[reng, 0], 0, 15), "          ", sin saltar;
			FinSegun
			//Escribir subcadena(MatrizN[reng, 0], 0, 25), " ", sin saltar;
			//Escribimos precio y monto (precio total)
			Segun Longitud(MatrizN[reng,2]) Hacer
					//Si no tiene campo ocupado (vacio, es decir 0 caracteres) le indico al usuario
				0: Escribir "  N.D.   ", sin saltar;
				1: Escribir "CRC/",MatrizN[reng, 2], "          ", "    CRC/",monto;
				2: Escribir "CRC/",MatrizN[reng, 2], "         ", "    CRC/",monto;
				3: Escribir "CRC/",MatrizN[reng, 2], "        ", "    CRC/",monto;
				4: Escribir "CRC/",MatrizN[reng, 2], "       ", "    CRC/",monto;
				5: Escribir "CRC/",MatrizN[reng, 2], "      ", "    CRC/",monto;
				6: Escribir "CRC/",MatrizN[reng, 2], "     ", "    CRC/",monto;
				7: Escribir "CRC/",MatrizN[reng, 2], "    ", "    CRC/",monto;
				8: Escribir "CRC/",MatrizN[reng, 2], "   ", "    CRC/",monto;
				9: Escribir "CRC/",MatrizN[reng, 2], "  ", "    CRC/",monto;
				10: Escribir "CRC/",MatrizN[reng, 2], " ", "    CRC/",monto;
				De Otro Modo:
					//si tiene mas caracteres acorto a 14 espacios para que no corra los demas espacios
					Escribir subcadena(MatrizN[reng, 2], 0, 10), "    CRC/",monto;
			FinSegun
			subTotal = subTotal + monto;
		FinSi
		//inicio monto en cero para cada linea despues de imprimir previa
		monto = 0;
	FinPara
	Escribir "=====================================================================";
	Escribir "                                          Subtotal: CRC/",subTotal;
	Escribir "                                     Impuesto ", IVA*100," %: CRC/ ",subTotal*IVA;
	Total = (subTotal + (subTotal * IVA));
	Escribir "                                             Total: CRC/",Total;
	Escribir "=====================================================================";
	Escribir "Notas: CRC/ significa Costa Rica Colon";
	Escribir "";
	Escribir "Gracias por su compra, estamos a la orden cualquier cosa...";
FinSubProceso

//Nombre de la empresa en forma generica por si deseamos cambiarla
SubProceso empresa()
	Escribir "Articulos y Servicios Magicos S.A.";
FinSubProceso

//Encabezado Generico
SubProceso encabezado()
	Limpiar Pantalla;
	empresa();
	Escribir "";
FinSubProceso

//Pie de Pagina Generico
SubProceso piepagina()
	Escribir " ";
	Escribir "Presione una Tecla para continuar";
	Esperar Tecla; 
FinSubProceso

//Pantalla de FINALIZAR
SubProceso finalizar(salir por Referencia)
	Repetir
		encabezado();
		Escribir "Seguro que desea salir del programa (S/N)? :" sin saltar;
		Leer salir;
		Si (Mayusculas(salir) <> 'S') Y (Mayusculas(salir) <> 'N') Entonces
			error_son();
		FinSi
	Hasta Que (Mayusculas(salir) = 'S') O (Mayusculas(salir) = 'N')
FinSubProceso

//1.Error en Digito/No disponible
SubProceso error_digito()
	Escribir "Campo seleccionado no disponible, intente de nuevo.";
FinSubProceso

//2.Error en Opcion
SubProceso error_opcion()
	Escribir "(Opcion Invalida, digite de nuevo por favor)...", Sin Saltar;
	piepagina();
FinSubProceso

//3.Error al ser distinto de Si-No
SubProceso error_son()
	Escribir "Debe digitar S o N unicamente.", Sin Saltar;
	piepagina();
FinSubProceso

//4.Error campo vacio
SubProceso error_vacio()
	Escribir "Campo no puede quedar vacio, intentelo de nuevo";
FinSubProceso

//5.Error no es numero valido para el caso
SubProceso error_noesnumero()
	Escribir "No corresponde a un numero entero valido, intentelo de nuevo";
FinSubProceso


