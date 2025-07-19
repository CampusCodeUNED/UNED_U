import matplotlib.pyplot as plt


def es_primo(n):
    """Función para determinar si un número es primo."""
    if n <= 1:
        return False
    for i in range(2, int(n ** 0.5) + 1):
        if n % i == 0:
            return False
    return True


def goldbach(n):
    """Función para obtener las descomposiciones de un número par n como suma de dos primos."""
    descomposiciones = []
    for i in range(2, n):
        if es_primo(i) and es_primo(n - i):
            descomposiciones.append((i, n - i))
    return descomposiciones


def graficar_goldbach(limite):
    """Función para graficar las descomposiciones de números pares como suma de dos primos."""
    pares = range(4, limite + 1, 2)
    plt.figure(figsize=(10, 6))

    for n in pares:
        descomposiciones = goldbach(n)
        for (p1, p2) in descomposiciones:
            plt.scatter(n, p1, color='blue', s=50,
                        label='Primer Primo' if n == 4 and p1 == descomposiciones[0][0] else "")
            plt.scatter(n, p2, color='red', s=50,
                        label='Segundo Primo' if n == 4 and p2 == descomposiciones[0][1] else "")

    plt.xlabel('Número Par', fontsize=14)
    plt.ylabel('Primos', fontsize=14)
    plt.title('Descomposición de Números Pares en Dos Primos (Conjetura de Goldbach)', fontsize=16)
    plt.legend(loc='upper right')
    plt.grid(True)
    plt.show()


# Parámetro: hasta qué número par queremos graficar
graficar_goldbach(100)
