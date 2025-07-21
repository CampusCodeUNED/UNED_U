//*******************************************************************************************************************************************************************
//*******************************************************************************************************************************************************************
//*******************************************************************************************************************************************************************
//Universidad Estatal a Distancia - Costa Rica
//Catedra de Desarrollo de Sistemas
//Grado Academico: Diplomado
//III Cuatrimestre, 2023
//Codigo: 03071 - Logica para Computaci�n
//Proyecto No.2
//Temas: 1, 2, 3
//Grupo: 08, Profesor: Mauricio Ru�z P�rez
//Estudiante: Francisco Campos Sandi
//Pseudocodigo realizado en PSeint Versi�n pseint-w32-20210609
//*******************************************************************************************************************************************************************
//Se inicia el algoritmo y se declaran las variables
Algoritmo VentaBoletosCinepolis  
    Definir Edad, TipoPelicula, CantidadBoletos, BoletosRechazados, BoletosNinno, BoletosAdulto, BoletosAdultoMayor,MontoTotal, MontoTarifa Como Entero;
    Definir Respuesta,TipoBoleto Como Caracter;
	//*****************************************************************************************************************************************************************	
	//Se inicializan las variables en 0
	Edad = 0; 
	TipoPelicula = 0;
	CantidadBoletos = 0;
    BoletosRechazados = 0;
    BoletosNinno = 0;
    BoletosAdulto = 0;
    BoletosAdultoMayor = 0;
	Respuesta = "";
	TipoBoleto = "";
    MontoTotal = 0;
	MontoTarifa = 0;
	//******************************************************************************************************************************************************************
	//Se inicia solicitando al usuario a digitar la Edad para luego ofrecer la cartelera del cine
    Repetir
        Limpiar Pantalla;
        Escribir "*** Bienvenidos a Cinepolis ***";
        Escribir " Por favor Ingrese su edad: ";
        Leer Edad;
        Escribir "***Cartelera de pel�culas***";
        Escribir "1.Pel�cula de Acci�n M18.";
        Escribir "2.Pel�cula de Rom�nce M15.";
        Escribir "3.Pel�cula de Comedia TP.";
        Escribir "Ingrese la sala deseada (1/2/3): ";
        Leer tipoPelicula;
		//Se valida que solo ingrese una de las 3 opcciones de la cartelera, si ingresa una que no esta vuelve a preguntar hasta ingresar una correcta (1,2,3)
		Repetir 
			Si tipoPelicula < 0 O tipoPelicula > 3  Entonces 
				Escribir "Sala no disponible. Ingrese una sala v�lida (1/2/3).";
				Leer tipoPelicula;
			FinSi 
		Hasta Que tipoPelicula > 0 Y tipoPelicula < 4 ;
		//*************************************************************************************************************************************************************
		// Se verifica que la Edad del usuario este entre el rango permitido de acuerdo a la cartelera. Si no lo es, se rechaza la venta del boleto
		// adem�s se detalla un desglose del boleto comprado y el precio en cada compra
        Segun TipoPelicula Hacer
            1:
                Si Edad < 18 Entonces
                    Escribir "Pel�cula no permitida para la Edad del cliente.";
                    BoletosRechazados = BoletosRechazados + 1;
                Sino
					Si Edad >= 65 Entonces
						TipoBoleto = "Adulto Mayor";
						MontoTarifa = 2800;
						BoletosAdultoMayor = BoletosAdultoMayor + 1;
					Sino
						TipoBoleto = "Adulto";
						MontoTarifa = 3500;
						BoletosAdulto = BoletosAdulto + 1;
					FinSi
					MontoTotal = MontoTotal + MontoTarifa;
                    Escribir "Venta concretada.Boleto de ", TipoBoleto, ", el monto a pagar por el boleto es de ", MontoTarifa, " colones";
                    CantidadBoletos = CantidadBoletos + 1;
                FinSi
            2:
                Si Edad < 15 Entonces
                    Escribir "Pel�cula no permitida para la Edad del cliente.";
                    BoletosRechazados = BoletosRechazados + 1;
                Sino
					Si Edad >= 65 Entonces
						TipoBoleto = "Adulto Mayor";
						MontoTarifa = 2800;
						BoletosAdultoMayor = BoletosAdultoMayor + 1;
					Sino
						TipoBoleto = "Adulto";
						MontoTarifa = 3500;
						BoletosAdulto = BoletosAdulto + 1;
					FinSi
                    MontoTotal = MontoTotal + MontoTarifa;
                    Escribir "Venta concretada.Boleto de ", TipoBoleto, ", el monto a pagar por el boleto es de ", MontoTarifa, " colones";
                    CantidadBoletos = CantidadBoletos + 1;
                FinSi
            3:
                Si Edad <= 12 Entonces
                    TipoBoleto = "Ni�o";
                    MontoTarifa = 2800;
                    BoletosNinno = BoletosNinno + 1;
                Sino
                    Si Edad >= 65 Entonces
                        TipoBoleto = "Adulto Mayor";
                        MontoTarifa = 2800;
                        BoletosAdultoMayor = BoletosAdultoMayor + 1;
                    Sino
                        TipoBoleto = "Adulto";
                        MontoTarifa = 3500;
                        BoletosAdulto = BoletosAdulto + 1;
                    FinSi
                FinSi
                MontoTotal = MontoTotal + MontoTarifa;
                Escribir "Venta concretada.Boleto de ", TipoBoleto, ", el monto a pagar por el boleto es de ", MontoTarifa, " colones";
                CantidadBoletos = CantidadBoletos + 1;
				
        FinSegun
		//***************************************************************************************************************************************************************	
		//Luego se  pregunta al usuario si desea comprar un nuevo boleto, si afirmativo se devuelve a realizar otra compra, 
       //caso contrario se da un desglose de las compras echas. 	
        Repetir
            Escribir "�Desea comprar un nuevo boleto boleto? (S/N): ";
            Leer Respuesta;
            Si (Mayusculas(Respuesta ) <> "S") Y (Mayusculas(Respuesta) <> "N") Entonces
                Escribir "Respuesta inv�lida. Ingrese S/N.";
            FinSi
        Hasta Que (Mayusculas(Respuesta) = "S") O (Mayusculas(Respuesta) = "N");
    Hasta Que Respuesta = "N" O Respuesta = "n"
	//********************************************************************************************************************************************************************	
	// Pantalla Final de las compras realizadas por el cliente incluyendo los boletos por censura
	Limpiar Pantalla;
    Escribir "*** Reporte final de Ventas ***";
    Escribir "Cantidad de boletos vendidos: ", CantidadBoletos;
    Escribir "Cantidad de boletos rechazados por censura: ", BoletosRechazados;
    Escribir "Cantidad de boletos de ni�o: ", BoletosNinno;
    Escribir "Cantidad de boletos de adulto: ", BoletosAdulto;
	Escribir "Cantidad de boletos de adulto mayor: ", BoletosAdultoMayor;
    Escribir "Monto total recaudado: ", MontoTotal;
	Escribir "*** Gracias por su Compra, vuelva pronto!!!***";
FinAlgoritmo
//************************************************************************************************************************************************************************
//************************************************************************************************************************************************************************	
//************************************************************************************************************************************************************************	

