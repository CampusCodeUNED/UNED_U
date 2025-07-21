#Curso Python Uned-Miccit
#Docente: Adrián Solano Sánchez
# Francisco Campos Sandi
# 114750560
# Tarea 2

def imprimir_histograma(numeros):
    for numero in numeros:   # Imprime el histograma para cada número de la lista
        print('*' * numero)  # Imprime los asteriscos según el valor


def menu_histograma():#Menú para los ejemplo en forma de función
    opcion = 0
    while opcion != 4:
        print("\n+----------------------------------------------------+")
        print("|               Menú de Ejercicios:                  |")
        print("| 1. Mostrar ejemplo 1 de histograma [4, 9, 7]       |")
        print("| 2. Mostrar ejemplo 2 de histograma [3, 6, 2, 8]    |")
        print("| 3. Mostrar ejemplo 3 de histograma [5, 1, 4]       |")
        print("| 4. Salir                                           |")
        print("+----------------------------------------------------+")

        opcion = int(input("Digite la acción que desea realizar: "))

        match opcion:
            case 1:
                print("Ejemplo de histograma con la lista [4, 9, 7]:")
                imprimir_histograma([4, 9, 7]) # Muestra el ejemplo 1 lo que solicitan en la tarea
            case 2:
                print("Ejemplo de histograma con la lista [3, 6, 2, 8]:")
                imprimir_histograma([3, 6, 2, 8])# Muestra el ejemplo adicional
            case 3:
                print("Ejemplo de histograma con la lista [5, 1, 4]:")
                imprimir_histograma([5, 1, 4])# Muestra el ejemplo adicional
            case 4:
                print("Saliendo del menú...")
            case _:
                print("Opción no válida. Intente de nuevo.")


# Llama a la función del menú
menu_histograma()
