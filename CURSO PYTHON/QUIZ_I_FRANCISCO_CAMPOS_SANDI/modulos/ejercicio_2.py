def paises_eje2():
    n = int(input("Ingrese el número de países: "))
    paises = []
    habitantes = []

    for i in range(n):
        pais = input("Ingrese el nombre del país: ")

        habitantes_pais_str = input(f"Ingrese la cantidad de habitantes en {pais}: ")
        while not habitantes_pais_str.isdigit():  # Validación para que solo se ingresen números
            habitantes_pais_str = input(
                f"Por favor, ingrese un número válido para la cantidad de habitantes en {pais}: ")

        habitantes_pais = int(habitantes_pais_str)  # Convertir la entrada válida a entero
        paises.append(pais)
        habitantes.append(habitantes_pais)

    for i in range(n):
        print(f"{paises[i]}: {habitantes[i]} habitantes")


def ejecutar_eje2():
    paises_eje2()
