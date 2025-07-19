def lista_ej1():
    lista = []
    n = int(input("Ingrese el número de elementos en la lista: "))
    for i in range(n):
        num = int(input("Ingrese un número entero positivo: "))
        while num <= 0:
            num = int(input("El número debe ser mayor que 0. Ingrese de nuevo: "))
        lista.append(num)
    print("Lista ingresada:", lista)
    lista_ordenada = sorted(lista, reverse=True)
    print("Lista ordenada de mayor a menor:", lista_ordenada)

def ejecutar_eje1():
    lista_ej1()
