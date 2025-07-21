#Curso Python Uned-Miccit
#Docente: Adrián Solano Sánchez
# Francisco Campos Sandi
# 114750560
# I Prueba


# Menú de ejercicios
opcion = 0

while opcion != 5:
    print("\n+----------------------------------------------------+")
    print("|               Menú de Ejercicios:                    |")
    print("| 1. Primer ejercicio                                  |")
    print("| 2. Segundo ejercicio                                 |")
    print("| 3. Tercer ejercicio                                  |")
    print("| 4. cuarto ejercicio                                  |")
    print("| 5. Salir                                             |")
    print("+------------------------------------------------------+")

    opcion = int(input("Digite la acción que desea realizar: "))

    match opcion:
        case 1:
            print("Calcular Factorial")
            numero = int(input("Introduce un número mayor que 0: "))
            if numero  > 0:
                factorial = 1    #siempre se inicia en 1
                multiplicacion = ""
                for i in range(1, numero  + 1):
                    if i > 1:
                        multiplicacion += " * "
                    multiplicacion += str(i)#convierte a cadena para imprimir
                    factorial *= i          #calcula el factorial
                print(multiplicacion, "=", factorial)
            else:
                print("El número debe ser mayor que 0.")
        case 2:
            # Ejercicio 2: Contar negativos y su suma
            print("Contar números negativos y su suma")
            cantidad = int(input("Cantidad de números: "))
            contadorNegativos = 0
            sumaNegativos = 0
            for n in range(cantidad):
                num = int(input("Ingrese un número: "))
                if num < 0:
                    contadorNegativos += 1
                    sumaNegativos += num
            print(f"Números negativos: {contadorNegativos}, Suma de negativos: {sumaNegativos}")
        case 3:
            print("Contar ocurrencias de una letra en una palabra")
            palabra = input("Ingrese una palabra: ").lower()  # se cnovierte a minúsculas
            letra = input("Ingrese una letra: ").lower()
            ocurrencias = 0
            for caracter in palabra:
                if caracter == letra:
                    ocurrencias += 1
            print(f"Las ocurrencias de la letra '{letra}' en la palabra '{palabra}' equivalen a '{ocurrencias}'.")

        case 4:
            # Ejercicio 4: Verificar número par e impar
            print("Verificar números pares e impares")
            par = int(input("Introduce un número par: "))
            impar = int(input("Introduce un número impar: "))
            if par % 2 == 0:
                print(f"El número '{par}' es par.")
            else:
                print(f"El número '{par}' no es par.")
            if impar % 2 != 0:
                print(f"El número '{impar}' es impar.")
            else:
                print(f"El número '{impar}' no es impar.")
        case 5:
            # Salir
            print("Saliendo del menú...")
            print(" ")
            print("Muchas gracias por usar el programa.")
        case _:
            print("Opción no válida. Intenta de nuevo.")