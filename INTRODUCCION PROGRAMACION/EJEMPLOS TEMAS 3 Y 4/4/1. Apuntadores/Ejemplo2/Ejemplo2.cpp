// Paso por valor utilizado para elevar al cubo el valor d
#include <iostream>

using namespace std;

int cuboPorValor(int); // prototipo

int main() {

    int numero{5};
    cout << "El valor original del numero es " << numero;
    numero = cuboPorValor(numero); // pasar número por valor a cuboPorValor
    cout << "\nEl nuevo valor del numero es " << numero << endl;
}

// calcula y devuelve el cubo del argumento entero

int cuboPorValor(int n) {
    return n * n * n; // calcula el cubo de la variable local n y devuelve el resultado
}
