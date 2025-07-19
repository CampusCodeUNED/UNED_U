# Curso Python Uned-Miccit
# Docente: Adrián Solano Sánchez
# Francisco Campos Sandi
# 114750560
# Proyecto final

from modulos.guerrero import Guerrero
from modulos.mago import Mago
from sanador import Sanador
from modulos.artillero import Artillero
from modulos.paladin import Paladin


def validar_entero(mensaje):
    """Solicita al usuario un número entero y lo valida."""
    while True:
        entrada = input(mensaje)
        if entrada.isdigit():
            return int(entrada)
        else:
            print("Entrada no válida. Por favor ingrese un número entero.")


def crear_personaje():
    """Crea un personaje basado en la selección del usuario."""
    print("\nSeleccione el tipo de personaje:")
    print("1. Guerrero")
    print("2. Mago")
    print("3. Sanador")
    print("4. Artillero")
    print("5. Paladín")

    while True:
        tipo = input("Ingrese el número de tipo de personaje: ")
        nombre = input("Ingrese el nombre del personaje: ")

        # Validar que el nombre no esté vacío
        if not nombre.strip():
            print("El nombre no puede estar vacío. Intente de nuevo.")
            continue

        match tipo:
            case "1":
                return Guerrero(nombre)
            case "2":
                return Mago(nombre)
            case "3":
                return Sanador(nombre)
            case "4":
                return Artillero(nombre)
            case "5":
                return Paladin(nombre)
            case _:
                print("Opción inválida. Intente de nuevo.")


def seleccionar_personaje(personajes, mensaje):
    """Permite al usuario seleccionar un personaje de la lista."""
    print(mensaje)
    for i, p in enumerate(personajes):
        print(f"{i + 1}. {p.nombre} ({p.clase})")

    while True:
        seleccion = input("Número de personaje: ")
        if seleccion.isdigit():
            seleccion = int(seleccion) - 1
            if 0 <= seleccion < len(personajes):
                return personajes[seleccion]
            else:
                print("Selección fuera de rango. Intente de nuevo.")
        else:
            print("Entrada no válida. Por favor ingrese un número.")


def menu():
    """Muestra el menú principal y gestiona las acciones del usuario."""
    personajes = []

    while True:
        print("\n+------------------------------------------------------+")
        print("|               Menú de Opciones                      |")
        print("| 1. Crear personaje                                  |")
        print("| 2. Mostrar personajes                               |")
        print("| 3. Atacar                                           |")
        print("| 4. Cambiar arma de personaje                        |")
        print("| 5. Subir nivel de un personaje                      |")
        print("| 6. Salir                                            |")
        print("+------------------------------------------------------+")

        opcion = input("Seleccione una opción: ")

        match opcion:
            case "1":
                personaje = crear_personaje()
                personajes.append(personaje)
                print(f"Personaje {personaje.nombre} creado con éxito.")

            case "2":
                if personajes:
                    for p in personajes:
                        p.mostrar_personaje()

                else:
                    print("No hay personajes para mostrar.")

            case "3":
                if len(personajes) < 2:
                    print("Debe haber al menos dos personajes para atacar.")
                    continue

                atacante = seleccionar_personaje(personajes, "Seleccione el atacante:")
                objetivo = seleccionar_personaje([p for p in personajes if p != atacante], "Seleccione el objetivo:")
                atacante.atacar(objetivo)

            case "4":
                if not personajes:
                    print("No hay personajes para cambiar de arma.")
                    continue

                personaje = seleccionar_personaje(personajes, "Seleccione el personaje para cambiar de arma:")
                personaje.cambiar_arma()
                personaje.mostrar_personaje()  # Muestra el personaje después de cambiar el arma

            case "5":
                if not personajes:
                    print("No hay personajes para subir de nivel.")
                    continue

                personaje = seleccionar_personaje(personajes, "Seleccione el personaje para subir de nivel:")
                print(f"\nAsignar puntos de mejora para {personaje.nombre}")
                fuerza = validar_entero("Fuerza adicional: ")
                inteligencia = validar_entero("Inteligencia adicional: ")
                defensa_fisica = validar_entero("Defensa física adicional: ")
                defensa_magica = validar_entero("Defensa mágica adicional: ")
                vida = validar_entero("Vida adicional: ")

                personaje.subir_nivel(fuerza, inteligencia, defensa_fisica, defensa_magica, vida)
                print(f"{personaje.nombre} ha subido de nivel!")

            case "6":
                print("Saliendo del programa...")
                break

            case _:
                print("Opción no válida. Intente de nuevo.")


# Iniciar el menú
menu()
