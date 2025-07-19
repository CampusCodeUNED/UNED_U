def ordenar_eje3():
    lista = [213, 44, 578, 123, 100, 56, 78, 1000, 2345, 1278]
    print("Lista original:", lista)
    lista_ordenada = sorted(lista)
    print("Lista ordenada de menor a mayor:", lista_ordenada)

def filtrar_lista():
    lista = [213, 44, 578, 123, 100, 56, 78, 1000, 2345, 1278]
    lista_filtrada = []
    for num in lista:
        if 100 <= num <= 1000:
            lista_filtrada.append(num)
    print("Números entre 100 y 1000:", lista_filtrada)

def ejecutar_eje3():
    ordenar_eje3()
    filtrar_lista()
