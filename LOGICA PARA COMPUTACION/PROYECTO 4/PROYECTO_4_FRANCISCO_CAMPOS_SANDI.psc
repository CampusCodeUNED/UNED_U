// *******************************************************************************************************************************************************************
// Universidad Estatal a Distancia - Costa Rica
// Catedra de Desarrollo de Sistemas
// Grado Academico: Diplomado
// III Cuatrimestre, 2023
// Codigo: 03071 - Logica para Computación
// Proyecto No.4
// Temas: 1, 2, 3,4
// Grupo: 08, Profesor: Mauricio Ruíz Pérez
// Estudiante: Francisco Campos Sandi
// Pseudocodigo realizado en PSeint Versión pseint-w32-20210609
// *******************************************************************************************************************************************************************
Algoritmo Planilla
	menu();			//Se muestra el Menú con las opciones
FinAlgoritmo
// *******************************************************************************************************************************************************************
//Menu Generico
Subproceso menu()
	Definir MatrizHoras, opcion,validacion,validacion2 como Entero;//Definicion de variables
    Definir VectorSalarioBruto,VectorDeducciones,VectorSalarioNeto como Real;
    Dimension MatrizHoras[10, 5];//Dimension  de matriz y los vectores
    Dimension VectorSalarioBruto[10];
    Dimension VectorDeducciones[10]; 
    Dimension VectorSalarioNeto[10]; 
	validacion=0;//Inicializacion de Variables
	validacion2=0;
	opcion = 0;
	Repetir //Menú principal
		Escribir '|==========================|';
		Escribir '|     Menú Principal       |';
		Escribir '|==========================|';
		Escribir '| 1. Inicializar datos     |';
		Escribir '| 2. Generar planilla      |';
		Escribir '| 3. Reporte planilla      |';
		Escribir '| 4. Salir:                |';
		Escribir '|__________________________|';
		Escribir ' Ingrese la opción deseada: ';
        Leer opcion;
        Repetir
			Si opcion <= 0 O opcion > 4 Entonces               // se valida que solo se ingrese valores de 1/2/3/4                      
				ErrorOpcion();
				Leer opcion;
			FinSi
		Hasta Que opcion > 0 Y opcion < 5
        Segun opcion Hacer
            1: validacion <- 1;                                  //se valida que primero se genere las entradas
                CargarHoras(MatrizHoras );                      //se llama al subproceso para cargar las horas
                ImprimirMatriz(MatrizHoras);                    //se llama al subproceso para Imprimir la matriz con las horas cargadas
				opcion<- 1;
				Escribir " ";
            2:  Borrar Pantalla;       
				Si validacion <>1  Entonces       //se valida que primero se inicialice los datos en 1
					ErrorInicializar();
				Sino
					GenerarPlanilla(MatrizHoras,VectorSalarioBruto, VectorDeducciones, VectorSalarioNeto);//se llama al subproceso para generar la planilla con sus vectores
					opcion <- 2;
					validacion2<- 2;
				FinSi
				Escribir " ";
			3: Borrar Pantalla;       
				Si  validacion = 1  Y  validacion2 = 2  Entonces          //se valida que primero se genere la planilla 
					ReportePlanilla(VectorSalarioBruto, VectorDeducciones, VectorSalarioNeto);  //se llama al subproceso para imprimir el reporte de la planilla 
					opcion <- 3;
				Sino
					ErrorGenerarPlanilla();
				FinSi
				Escribir " ";
				EsperarT()  ;
            4:SalirSistema();
            De Otro Modo:
                ErrorOpcion();
        FinSegun
    Hasta Que opcion = 4
FinSubProceso
// *******************************************************************************************************************************************************************
SubProceso InicializarMatriz(Matriz Por Referencia, tamannoX Por Referencia, tamannoY Por Referencia)
    Definir fila, columna Como Entero;
	fila=0;
	columna=0;
    // Inicializar la matriz con ceros
    Para fila = 0 Hasta tamannoX-1 Con Paso 1 Hacer
        Para columna = 0 Hasta tamannoY-1 Con Paso 1 Hacer
            Matriz[fila, columna] <- 0;
        FinPara
    FinPara
FinSubProceso
SubProceso InicializarVectoress(VectorSalarioBruto, VectorDeducciones, VectorSalarioNeto ) //Se inicializan los vectores con 0
	Definir tamannoX Como Entero;
    tamannoX <- 10; // cantidad de colaboradores
    Para  tamannoX <- 0 Hasta  tamannoX- 1 Con Paso 1 Hacer
		VectorSalarioBruto[ tamannoX] <- 0;
		VectorDeducciones[ tamannoX] <- 0;
		VectorSalarioNeto[ tamannoX] <- 0;
    FinPara
FinSubProceso
// *******************************************************************************************************************************************************************	
SubProceso  CargarHoras(MatrizHoras Por Referencia)   //proceso que carga la matriz con las horas de los colaboradores con valores aleatorios entre 6 y 12
    Definir tamannoX, tamannoY Como Entero;
    tamannoX <- 10; // cantidad de colaboradores
    tamannoY <- 5; // cant días 
    InicializarMatriz(MatrizHoras, tamannoX, tamannoY);
    Para tamannoX <- 0 Hasta tamannoX-1 Con Paso 1 Hacer
        Para tamannoY <- 0 Hasta tamannoY-1 Con Paso 1 Hacer
            MatrizHoras[tamannoX, tamannoY] <- Aleatorio(6, 12);
        FinPara
    FinPara
FinSubProceso
// *******************************************************************************************************************************************************************
SubProceso  ImprimirMatriz(MatrizHoras Por Referencia)    //Proceso que imprime la matriz con las horas de los colaboradores
	Definir tamannoX,tamannoY Como Entero;
	tamannoX <- 10;//cantidad de colaboradores
	tamannoY <- 5;//cant días
	Escribir "=====================================";
	Escribir "  Registro Semanal de Horas Laboradas";
	Escribir '=====================================';    
	Escribir ' N° Colaborador     L  K  M  J  V   ' ;
	Escribir '=====================================';
    Para tamannoX  <- 0 Hasta tamannoX-1 Con Paso 1 Hacer
		Escribir '  ',tamannoX,'                ' Sin Saltar;
        Para tamannoY <- 0 Hasta tamannoY-1 Con Paso 1 Hacer
            Si MatrizHoras[tamannoX,tamannoY] > 9 Entonces
                Escribir  MatrizHoras[tamannoX, tamannoY], " ", Sin Saltar;
            Sino
                Escribir " ", MatrizHoras[tamannoX, tamannoY], " ", Sin Saltar;
            FinSi
        FinPara
        Escribir " ";
		Escribir '-------------------------------------';
    FinPara
FinSubProceso
// *******************************************************************************************************************************************************************
SubProceso  GenerarPlanilla(MatrizHoras,VectorSalarioBruto, VectorDeducciones, VectorSalarioNeto)   // En este subproceso se calculan las horas ordinaria y las horas extras,
    Definir tamannoX,tamannoY , horaOrdinarias, horaExtra, SalarioBruto como real;  // también se calculan los montos de los salarios brutos, las deducciones y los salarios netos y se almacenan en sus respectivos vectores
	tamannoX <- 10;//cantidad de colaboradores
	tamannoY <- 5;//cant días
	horaOrdinarias=0;
	horaExtra=0;
	SalarioBruto=0;
	InicializarVectoress(VectorSalarioBruto,VectorDeducciones, VectorSalarioNeto); //Primero se inicializan los vectores llamando al subproceso 
    Para tamannoX <- 0 Hasta tamannoX-1 Con Paso 1 Hacer           
        SalarioBruto <- 0;
        Para tamannoY <- 0 Hasta tamannoY-1 Con Paso 1 Hacer
            Si MatrizHoras[tamannoX, tamannoY] <= 8 Entonces
                horaOrdinarias <- MatrizHoras[tamannoX, tamannoY];
            Sino
                horaOrdinarias <- 8;
            FinSi
            Si MatrizHoras[tamannoX, tamannoY] > 8 Entonces
                horaExtra <- MatrizHoras[tamannoX, tamannoY] - 8;
            Sino
                horaExtra <- 0;
            FinSi
			SalarioBruto <-SalarioBruto + CalcularSalBruto(horaOrdinarias, horaExtra);
            
        FinPara
		VectorSalarioBruto[tamannoX] <- SalarioBruto;
		VectorDeducciones[tamannoX] <- CalcularDeduccion(SalarioBruto); // Modificado
		VectorSalarioNeto[tamannoX] <- SalarioBruto - VectorDeducciones[tamannoX]; // Agregado
    FinPara
	Escribir "================================================================";
	Escribir " Inicializar datos. La planilla fue generada sastifactoriamente ";
	Escribir "================================================================";
	Escribir "   ";
FinSubProceso
// *******************************************************************************************************************************************************************
Funcion   SalarioBruto <- CalcularSalBruto(horaOrdinarias, horaExtra Por Referencia)  // CalcularSalBruto: Recibe las horas ordinarias y las horas extras y calcula y devuelve el monto del salario bruto.
    Definir SalarioBruto como Real;                                                  // El salario bruto se calcula de la siguiente manera: (Horas Ordinarias × 5000) + (Horas Extras x 1.5 x 5000)
	SalarioBruto=0;
    SalarioBruto <- (horaOrdinarias * 5000) + (horaExtra * 1.5 * 5000);
FinFuncion
// *******************************************************************************************************************************************************************
Funcion    Deducciones<-CalcularDeduccion(SalarioBruto Por Referencia) // CalcularDeduccion: Recibe el salario bruto y calcula y devuelve el monto de las deducciones.
	Definir Deducciones como Real;                                    // Las deducciones se calculan de la siguiente manera: 5.5% del salario bruto + 3.5% del salario bruto + 1% del salario bruto.
	Deducciones=0;
    Deducciones <- 0.055 * SalarioBruto+ 0.035 * SalarioBruto+ 0.01 * SalarioBruto;
FinFuncion
// *******************************************************************************************************************************************************************
SubProceso  ReportePlanilla(VectorSalarioBruto, VectorDeducciones, VectorSalarioNeto)  //se imprime el reporte de planilla con la informacion de los vectores 
	Definir tamannoX Como Real;
	tamannoX=10;
	Escribir "==============================================================";
	Escribir "             	          PLANILLA SEMANAL             	         ";
	Escribir "==============================================================";
	Escribir "  Colaborador | Salario Bruto | Deducciones | Salario Neto |";
	Escribir "==============================================================";
	Para tamannoX <- 0 Hasta tamannoX-1 Con Paso 1 Hacer
		Escribir "      ", tamannoX,  "             ",VectorSalarioBruto[tamannoX],  "        ", VectorDeducciones[tamannoX], "        CRC   " ,VectorSalarioNeto[tamannoX] ;
		Escribir '--------------------------------------------------------------';
	FinPara
	Escribir "Nota: Todos los calculos de los salarios son en colónes (CRC)";
FinSubProceso
// *******************************************************************************************************************************************************************
SubProceso ErrorOpcion()                                     //subproceso de error de opción no válida
	Escribir "===========================================================";
	Escribir 'opción no disponible. Ingrese una opción válida (1/2/3/4).';
	Escribir "===========================================================";
FinSubProceso   
SubProceso ErrorInicializar()                                //subproceso de error  de inicializar los datos de 2
	Escribir "============================================";
	Escribir "Error: Debe inicializar los datos primero.";
	Escribir "============================================";
FinSubProceso   
SubProceso ErrorGenerarPlanilla()                          //subproceso de error  de inicializar los datos de 3
	Escribir "==========================================";
	Escribir "Error: Debe generar la planilla primero.";
	Escribir "==========================================";
FinSubProceso  
SubProceso EsperarT()                                   //subproceso del paso 3 para no mostrar el menú y se pueda visualizar mejor la planilla	
Escribir "=========================================";	
Escribir "Presione cualquier tecla para continuar";
Escribir "=========================================";
Esperar Tecla; 
Borrar Pantalla;
FinSubProceso
SubProceso SalirSistema()                                //subproceso de salida del sistema 
	Limpiar Pantalla;
	Escribir "=========================";
	Escribir "Saliendo del Sistema...";
	Escribir "=========================";
	Escribir "";
FinSubProceso 
// *******************************************************************************************************************************************************************