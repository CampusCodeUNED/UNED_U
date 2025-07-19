//Universidad Estatal a Distancia - Costa Rica
//Catedra de Desarrollo de Sistemas
//Grado Academico: Diplomado
//lll Cuatrimestre, 2021
//Codigo: 03071 - Logica para Computacion
//Proyecto No.3 - Tipo Individual
//Temas: 1, 2, 3, 4 (Algoritmos, Estructuras Decision-Repeticion, Arreglos)
//Grupo: 08, Profesor: Mauricio Ruiz Perez
//Estudiante: Leon Alaric Fernandez Henley
//Pseudocodigo realizado en PSeint Version pseint-w32-20200501
//Perfil Personalizado con requerimientos del Curso (imagen adjunta)
//Fecha: 30/10/2021
//Segun Foro Consultas: El monto (precio) puede ser declarado como cadena, no se valida que se digiten numeros
//Puede realizar un menu para aclaracion al usuario que sea intuitivo mientras se apegue al enunciado
//No se controla que codigo no este repetido ni vacio

Algoritmo ListaProductos_Almacen
	//Declaracion de Variables
	Definir vacio, lleno, encontrado como Logicas;
	Definir salir, respuesta, opcion como Caracter;
	Definir reng, col, cantFilas, cantColumnas, usoFilas, usoColumnas, posicion, localizado  como Entero;
	Definir abarrotes, buscar, codigo, nombre, descripcion, precio como Cadena;
	//Inicializacion de Variables
	vacio = VERDADERO;		//Booleano para indicar si matriz esta vacia
	lleno = FALSO;			//Booleano para indicar si matriz esta llena
	encontrado = Falso;		//Booleano para indicar si elemento buscado se encuentra
	salir = "N";
	respuesta = "S";
	opcion = "";
	reng = 0;
	col = 0;
	cantFilas = 5;
	cantColumnas = 5;
	usoFilas = 5;			//Marcador para indicar la cantidad de filas a usar
	usoColumnas = 4;		//Marcador para indicar la cantidad de columnas a usar
	posicion = 0;			//Esta variable me controla la cantidad real de posiciones en el arreglo
	localizado = 0;			//Marcador para indicar el lugar donde un codigo buscado se localiza si es el caso
	buscar = "";			//Cadena para buscar elemento en matriz digitado por usuario
	codigo = "";
	nombre = "";
	descripcion = "";
	precio = "";
	Dimension abarrotes[cantFilas,cantColumnas];	//Declaro matriz con cantidad de filas y columnas solicitado

	//Inicializo Matriz abarrotes - todos los campos
	Para reng=0 Hasta cantFilas-1 con Paso 1 Hacer
		Para col=0 Hasta cantColumnas-1 con Paso 1 Hacer
			abarrotes[reng,col] = "";
		FinPara
	FinPara

	//Inicio con un menu para escoger opciones
	Repetir
		Repetir
			Limpiar Pantalla;
			Escribir "    Almacen de Abarrotes Central";
			Escribir "Listado de Productos - Menu Principal";
			Escribir "  Cantidad de productos en lista: ", posicion;
			Escribir "";
			Escribir "1.Ver Lista de Productos";
			Escribir "2.Ingresar un Producto";
			Escribir "3.Modificar un Producto";
			Escribir "4.Salir";
			Escribir "";
			Escribir "Digite una opcion";
			Leer opcion;
			Si  (opcion <> "1") Y (opcion <> "2") Y (opcion <> "3") Y (opcion <> "4") Entonces	//Valido opciones correctas
				Escribir "Digite solo 1,2,3 o 4, intentelo de nuevo";
				Escribir "Presiones una tecla para continuar";
				Esperar Tecla;
			FinSi
		Hasta Que (opcion == "1") O (opcion == "2") O (opcion == "3") O (opcion == "4")
		Limpiar Pantalla;
		//Muestro el seguimiento del algoritmo segun seleccion de opcion respectiva
		Segun ConvertirANumero(opcion) Hacer
			1: 
				//Listado de Productos
				Escribir "Cantidad de productos en lista: ", posicion;
				Escribir "";
				//Con el booleano despliego si esta vacio sino muestro lista
				Si (vacio == VERDADERO) Entonces
					Escribir "La lista se encuentra actualmente vacia.";
					Escribir "Seleccione ingresar productos del menu si lo desea";
					Escribir "";
				SiNo
					//Coloco Encabezado Generico
					Escribir "*****************************************************************";
					Escribir "|   ALMACEN DE ABARROTES CENTRAL    -   LISTADO DE PRODUCTOS    |";
					Escribir "*****************************************************************";
					Escribir "|    CODIGO     |   PRODUCTO    |  DESCRIPCION  |    PRECIO     |";
					Escribir "*****************************************************************";
					Escribir "|", Sin Saltar;
					//Recorro matriz hasta cantidad de productos ingresados hasta el momento
					Para reng=0 Hasta posicion-1 con Paso 1 Hacer
						//Recorro matriz hasta cantidad de columnas utilizadas segun enunciado
						Para col=0 Hasta usoColumnas-1 con Paso 1 Hacer
								Segun Longitud(abarrotes[reng, col]) Hacer
									//Si no tiene campo ocupado (vacio, es decir 0 caracteres) le indico al usuario
									0: Escribir "      N.D.     |", sin saltar;
									1: Escribir abarrotes[reng, col], "              |", sin saltar;
									2: Escribir abarrotes[reng, col], "             |", sin saltar;
									3: Escribir abarrotes[reng, col], "            |", sin saltar;
									4: Escribir abarrotes[reng, col], "           |", sin saltar;
									5: Escribir abarrotes[reng, col], "          |", sin saltar;
									6: Escribir abarrotes[reng, col], "         |", sin saltar;
									7: Escribir abarrotes[reng, col], "        |", sin saltar;
									8: Escribir abarrotes[reng, col], "       |", sin saltar;
									9: Escribir abarrotes[reng, col], "      |", sin saltar;
									10: Escribir abarrotes[reng, col], "     |", sin saltar;
									11: Escribir abarrotes[reng, col], "    |", sin saltar;
									12: Escribir abarrotes[reng, col], "   |", sin saltar;
									13: Escribir abarrotes[reng, col], "  |", sin saltar;
									14: Escribir abarrotes[reng, col], " |", sin saltar;
									15: Escribir abarrotes[reng, col], "|", sin saltar;
									De Otro Modo:
										//si tiene mas caracteres acorto a 14 espacios para que no corra los demas espacios
										Escribir subcadena(abarrotes[reng, col], 0, 14), "|", sin saltar;
								FinSegun
						FinPara
						//Si todavia hay datos en el arreglo brinco linea e imprimo el inicio con caracter |
						Si (reng+1 < posicion) Entonces
							Escribir "";
							Escribir "|", Sin Saltar;
						FinSi
					FinPara
					Escribir "";
					Escribir "*****************************************************************";
					Escribir "Los campos identificados con N.D.= No Digitado";
					Escribir "";
				FinSi
				Escribir "Presione una tecla para continuar...";
				Esperar Tecla;
			2: 
				//Ingreso de Productos
				Repetir
					Escribir "Cantidad de productos en lista: ", posicion;
					Escribir "";
					//Con este booleano controlo si ya se lleno la matriz no puede insertar mas datos
					Si (lleno == VERDADERO) Entonces
						Escribir "La lista se encuentra actualmente llena.";
						Escribir "(No se permite el ingreso de mas productos)";
						Escribir "";
						respuesta = "N";		//para salir del repetir
					SiNo
						//Solicito datos de ingreso para productos para asignar en posiciones correspondientes del arreglo
						Escribir "INGRESAR DATOS";
						Escribir "==============";
						//Aunque no hay que validar tipo de datos MINIMO tenemos que validar que el codigo no este repetido para busquedas apropiadas
						Repetir
							encontrado = FALSO;
							Escribir "CODIGO: ";
							Leer codigo;
							Para reng=0 Hasta posicion-1 con Paso 1 Hacer
								Si (codigo == abarrotes[reng,0]) Entonces
										encontrado = VERDADERO;
								FinSi	
							FinPara
							Si (encontrado == VERDADERO) Entonces
								Escribir "Codigo ya existe en lista, intente de nuevo";
							FinSi
						Hasta Que (encontrado == FALSO)
						abarrotes[posicion, 0] = codigo;
						Escribir "NOMBRE PRODUCTO: ";
						Leer nombre;
						abarrotes[posicion, 1] = nombre;
						Escribir "DESCRIPCION: ";
						Leer descripcion;
						abarrotes[posicion, 2] = descripcion;
						Escribir "PRECIO: ";
						Leer precio;
						abarrotes[posicion, 3] = precio;
						Escribir "";
						//Incremento en 1 la variable posicion para determinar cantidad de productos y determinar cuando se llena
						posicion = posicion + 1;
						//Si ya metimos al menos un articulo en la matriz ya no esta llena por ende permite modificar y listar
						Si (vacio == VERDADERO) Entonces
							vacio = FALSO;
						FinSi
						//Determino si he alcanzado cantidad de filas en matriz para ver si esta lleno y por ende no permitir insertar mas
						Si (posicion == cantFilas) Entonces
							lleno = VERDADERO;
							Escribir "La lista se encuentra llena con ", posicion, " productos";
							respuesta = "N";		//para salir del repetir
						SiNo
							Repetir
								Escribir "Desea ingresar otro Registro (S/N)?";
								Leer respuesta;
								Si (Mayusculas(respuesta) <> "S") Y (Mayusculas(respuesta) <> "N") Entonces
									Escribir "Debe digitar S o N unicamente, intente de nuevo.";
								FinSi
							Hasta Que (Mayusculas(respuesta) == "S") O (Mayusculas(respuesta) == "N")
							Si (Mayusculas(respuesta) == "S") Entonces
								Limpiar Pantalla;
							FinSi
						FinSi
					FinSi
				Hasta Que (Mayusculas(respuesta) == "N")
				Escribir "Presione una tecla para continuar...";
				Esperar Tecla;
			3: 
				//Modificar un Producto
				Escribir "Cantidad de productos en lista: ", posicion;
				Escribir "";
				//Verifico si lista esta vacia en cuyo caso ni listo ni modifico nada
				Si (vacio == VERDADERO) Entonces
					Escribir "La lista se encuentra actualmente vacia.";
					Escribir "Seleccione ingresar productos del menu si lo desea";
					Escribir "";
				SiNo
					Repetir
						//Coloco Encabezado Generico
						Escribir "*****************************************************************";
						Escribir "|   ALMACEN DE ABARROTES CENTRAL   -   MODIFICADO DE PRODUCTOS  |";
						Escribir "*****************************************************************";
						Escribir "|    CODIGO     |   PRODUCTO    |  DESCRIPCION  |    PRECIO     |";
						Escribir "*****************************************************************";
						Escribir "|", Sin Saltar;
						Para reng=0 Hasta posicion-1 con Paso 1 Hacer
							//Recorro matriz hasta cantidad de columnas utilizadas segun enunciado
							Para col=0 Hasta usoColumnas-1 con Paso 1 Hacer
								Segun Longitud(abarrotes[reng, col]) Hacer
									//Si no tiene campo ocupado (vacio, es decir 0 caracteres) le indico al usuario
									0: Escribir "      N.D.     |", sin saltar;
									1: Escribir abarrotes[reng, col], "              |", sin saltar;
									2: Escribir abarrotes[reng, col], "             |", sin saltar;
									3: Escribir abarrotes[reng, col], "            |", sin saltar;
									4: Escribir abarrotes[reng, col], "           |", sin saltar;
									5: Escribir abarrotes[reng, col], "          |", sin saltar;
									6: Escribir abarrotes[reng, col], "         |", sin saltar;
									7: Escribir abarrotes[reng, col], "        |", sin saltar;
									8: Escribir abarrotes[reng, col], "       |", sin saltar;
									9: Escribir abarrotes[reng, col], "      |", sin saltar;
									10: Escribir abarrotes[reng, col], "     |", sin saltar;
									11: Escribir abarrotes[reng, col], "    |", sin saltar;
									12: Escribir abarrotes[reng, col], "   |", sin saltar;
									13: Escribir abarrotes[reng, col], "  |", sin saltar;
									14: Escribir abarrotes[reng, col], " |", sin saltar;
									15: Escribir abarrotes[reng, col], "|", sin saltar;
									De Otro Modo:
										//si tiene mas caracteres acorto a 14 espacios para que no corra los demas espacios
										Escribir subcadena(abarrotes[reng, col], 0, 14), "|", sin saltar;
								FinSegun
							FinPara
							//Si todavia hay datos en el arreglo brinco linea e imprimo el inicio con caracter |
							Si (reng+1 < posicion) Entonces
								Escribir "";
								Escribir "|", Sin Saltar;
							FinSi
						FinPara
						Escribir "";
						Escribir "*****************************************************************";
						Escribir "Los campos identificados con N.D.= No Digitado";
						Escribir "";
						Repetir
							Escribir "Desea realizar alguna modificacion a la informacion ingresada (S/N)";
							Leer respuesta;
							Si (Mayusculas(respuesta) <> "S") Y (Mayusculas(respuesta) <> "N") Entonces
								Escribir "Digite S o N unicamente, intente de nuevo";
							FinSi
						Hasta Que (Mayusculas(respuesta) == "S") O (Mayusculas(respuesta) == "N")		//ciclo para validar S o N
						Si (Mayusculas(respuesta) == "S") Entonces
							//Solicito codigo para modificar productos
							Escribir "Ingrese Codigo >>";
							Leer buscar;
							//busco el dato digitado a ver si efectivamente se encuentra en la matriz en la primera columna (codigo)
							Para reng=0 Hasta cantFilas-1 con Paso 1 Hacer
								Si (buscar == abarrotes[reng,0]) Entonces
									//Marco con booleano si se encuentra
									Si (encontrado == FALSO) Entonces
										encontrado = VERDADERO;
									FinSi
									//asigno la ubicacion de renglon donde se localiza el dato encontrado
									localizado = reng;
									Escribir "DATO ENCONTRADO";
									Escribir "===============";
									//Si el campo esta vacio le indico al usuario para que se de cuenta, sino imprimo lo que tiene
									Si (Longitud(abarrotes[reng,0]) == 0) Entonces
										Escribir " (N.D.) - ", Sin Saltar;
									SiNo
										Escribir abarrotes[reng,0], " - ", Sin Saltar;
									FinSi
									Si (Longitud(abarrotes[reng,1]) == 0) Entonces
										Escribir " (N.D.) - ", Sin Saltar;
									SiNo
										Escribir abarrotes[reng,1], " - ", Sin Saltar;
									FinSi
									Si (Longitud(abarrotes[reng,2]) == 0) Entonces
										Escribir " (N.D.) - ", Sin Saltar;
									SiNo
										Escribir abarrotes[reng,2], " - ", Sin Saltar;
									FinSi
									Si (Longitud(abarrotes[reng,3]) == 0) Entonces
										Escribir " (N.D.) - ";
									SiNo
										Escribir abarrotes[reng,3];
									FinSi
									//Escribir abarrotes[reng,0], " - ", abarrotes[reng,1], " - ", abarrotes[reng,2], " - ", abarrotes[reng,3];
									Escribir "(campos con N.D.= No Digitado)";
									Escribir "";
									reng=cantFilas-1;		
								FinSi
							FinPara
							//Si no se encontro el dato en la matriz lo indico, de lo contrario permito modificar los datos excepto codigo
							Si (encontrado == FALSO) Entonces
								Escribir "CODIGO NO ENCONTRADO";
							SiNo
								Escribir "ACTUALIZAR DATOS";
								Escribir "================";
								Escribir "CODIGO...........: ", buscar;
								Escribir "No se permite cambiar Codigo";
								Escribir "(registros con N.D.= No Digitado)";
								Escribir "NOMBRE PRODUCTO..: ", Sin Saltar;
								//Si el campo esta vacio le indico al usuario para que se de cuenta, sino imprimo lo que tiene
								Si (Longitud(abarrotes[localizado,1]) == 0) Entonces
									Escribir "(registrado: N.D.) ";
								SiNo
									Escribir "(registrado: ",abarrotes[localizado, 1],")";
								FinSi
								Leer nombre;
								abarrotes[localizado, 1] = nombre;
								Escribir "DESCRIPCION......: ", Sin Saltar;
								Si (Longitud(abarrotes[localizado,2]) == 0) Entonces
									Escribir "(registrado: N.D.) ";
								SiNo
									Escribir "(registrado: ",abarrotes[localizado, 2],")";
								FinSi
								Leer descripcion;
								abarrotes[localizado, 2] = descripcion;
								Escribir "PRECIO...........: ", Sin Saltar;
								Si (Longitud(abarrotes[localizado,3]) == 0) Entonces
									Escribir "(registrado: N.D.) ";
								SiNo
									Escribir "(registrado: ",abarrotes[localizado, 3],")";
								FinSi
								Leer precio;
								abarrotes[localizado, 3] = precio;
								Escribir "";
								Escribir "El producto con el código ", buscar, " fue actualizado de forma correcta";
								Escribir "";
								Escribir "REPORTE DE DATOS ACTUALIZADOS";
								Escribir "=============================";
								Escribir abarrotes[localizado,0], " - ", abarrotes[localizado,1], " - ", abarrotes[localizado,2], " - ", abarrotes[localizado,3];
								Escribir "";
								encontrado = FALSO;
							FinSi
							Escribir "Presione una tecla para continuar";
							Esperar Tecla;
							Limpiar Pantalla;
						FinSi
					Hasta Que (Mayusculas(respuesta) == "N")
				FinSi
				Escribir "Presione una tecla para continuar...";
				Esperar Tecla;
			4: 
				Repetir
					Escribir "Esta seguro que desea salir? (S/N)";
					Leer salir;
					Si (Mayusculas(salir) <> "S") Y (Mayusculas(salir) <> "N") Entonces
						Escribir "Digite S o N, intente de nuevo.";
					FinSi
				Hasta Que (Mayusculas(salir) == "S") O (Mayusculas(salir) == "N")		//Verifico si efectivamente desea salir del programa
			De Otro Modo:
				Escribir "Esto no deberia salir, presione una tecla";
				Esperar Tecla;
		FinSegun
	Hasta Que (Mayusculas(salir) == "S")
	Escribir "Saliendo...";
	Escribir "-------------------------GRACIAS-----------------------";
	Escribir "*******************************************************";

FinAlgoritmo


