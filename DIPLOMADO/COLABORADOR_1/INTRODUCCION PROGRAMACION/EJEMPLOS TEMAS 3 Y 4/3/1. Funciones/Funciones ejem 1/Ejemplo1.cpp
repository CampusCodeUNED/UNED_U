// Funci�n maximo con un prototipo de funci�n.

#include <iostream>
#include <iomanip>

using namespace std;

int maximo(int x, int y, int z); // prototipo de funci�n

int main() {
    int int1, int2, int3;
    cout << "Introduzca tres valores enteros: ";
    cin >> int1 >> int2 >> int3;

    // invocar a maximo

    cout << "El valor entero maximo es: " << maximo(int1, int2, int3) << endl;
}
// devuelve el mayor de tres enteros
int maximo(int x, int y, int z) {
           //  5      8      6
    int valorMaximo{x}; // asume que x es el mayor para comenzar

    // determinar si y es mayor que valorMaximo
    if (y > valorMaximo) {
        valorMaximo = y; // hacer y el nuevo valorMaximo
    }

    // determinar si z es mayor que valorMaximo
    if (z > valorMaximo) {
        valorMaximo = z; // hace a z el nuevo valorMaximo
    }

    return valorMaximo;
}
