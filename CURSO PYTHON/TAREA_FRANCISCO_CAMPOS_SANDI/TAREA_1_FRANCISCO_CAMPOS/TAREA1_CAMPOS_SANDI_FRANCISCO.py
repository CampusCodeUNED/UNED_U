# Francisco Campos Sandi
# 114750560
# Tarea 1: Calculadora básica



# Calculadora básica

while True:
    # Se solicitan los valores al usuario
    a = float(input("Ingresa el valor de a: "))
    b = float(input("Ingresa el valor de b: "))

    # Mostramos las opciones de operación
    print("Elige la operación:")
    print("1. Sumar")
    print("2. Restar")
    print("3. Multiplicar")
    print("4. Dividir")
    print("5. Salir")

    # Pedimos al usuario que elija una operación
    operacion = int(input("Ingresa el número de la operación (1-5): "))

    # Usamos match para realizar la operación elegida o salir
    match operacion:
        case 1:
            print("Resultado de sumar: ", a + b)
        case 2:
            print("Resultado de restar: ", a - b)
        case 3:
            print("Resultado de multiplicar: ", a * b)
        case 4:
            if b != 0:
                print("Resultado de dividir: ", a / b)
            else:
                print("Error: No se puede dividir entre 0")
        case 5:
            print("Saliendo de la calculadora...")
            break
        case _:
            print("Operación no válida, elige un número del 1 al 5")

    print()  # Línea en blanco para separar
