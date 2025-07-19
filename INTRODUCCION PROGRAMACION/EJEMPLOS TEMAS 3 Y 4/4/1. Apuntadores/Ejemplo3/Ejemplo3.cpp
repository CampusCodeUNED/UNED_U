// Paso por referencia con un argumento de apuntador utilizado para elevar

// al cubo el valor de una variable.
#include <iostream>

using namespace std;

void cuboPorReferencia(int*); // prototipo

int main() {

    int numero{5};
    cout << "El valor original del numero es " << numero;
    cuboPorReferencia(&numero); // void cuboPorReferencia(int*);
    cout << "\nEl nuevo valor del numero es " << numero << endl;
return 0;
}

// calcula el cubo de *nPtr; modifica el número de la variable en main

void cuboPorReferencia(int* nPtr) {
    *nPtr = *nPtr * *nPtr * *nPtr; // cubo *nPtr
}
