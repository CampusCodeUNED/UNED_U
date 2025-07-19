#Curso Python Uned-Miccit
#Docente: Adrián Solano Sánchez
# Francisco Campos Sandi
# 114750560
# Quiz I
import modulos.ejercicio_1 as ejercicio_1
import modulos.ejercicio_2 as ejercicio_2
import modulos.ejercicio_3 as ejercicio_3


def mostrar_menu():
    print("\n+----------------------------------------------------+")
    print("|               Menú de Ejercicios:                  |")
    print("| 1. Primer ejercicio                                |")
    print("| 2. Segundo ejercicio                               |")
    print("| 3. Tercer ejercicio                                |")
    print("| 4. Salir                                           |")
    print("+----------------------------------------------------+")


opcion = 0
while opcion != 4:
    mostrar_menu()

    opcion_str = input("Digite la acción que desea realizar: ")

    if opcion_str.isdigit():
        opcion = int(opcion_str)
    else:
        print("Por favor, ingrese un número válido.")
        continue

    match opcion:
        case 1:
            print("\nEjecutando Ejercicio 1:")
            ejercicio_1.ejecutar_eje1()
        case 2:
            print("\nEjecutando Ejercicio 2:")
            ejercicio_2.ejecutar_eje2()
        case 3:
            print("\nEjecutando Ejercicio 3:")
            ejercicio_3.ejecutar_eje3()
        case 4:
            print("Saliendo del programa...")
        case _:
            print("Opción no válida. Por favor, intente de nuevo.")
