// Pasar argumentos por valor y por referencia

#include <iostream>

using namespace std;

int main() {

    int cuadradoPorValor(int); // prototipo de funci�n (paso por valor)
    void cuadradoPorReferencia(int&); // prototipo de funci�n (paso por referencia)

    int x{2}; // valor al cuadrado usando cuadradoPorValor
    int z{4}; // valor al cuadrado usando cuadradoPorReferencia

    // demostrar cuadradoPorValor
    cout << "x = " << x << " antes de cuadradoPorValor\n";
    cout << "Valor devuelto por cuadradoPorValor: " << cuadradoPorValor(x) << endl;
    cout << "x = " << x << " despues de cuadradoPorValor\n" << endl;

    // demostrar cuadradoPorReferencia
    cout << "z = " << z << " antes de cuadradoPorReferencia" << endl;
    cuadradoPorReferencia(z);
    cout << "z = " << z << " despues de cuadradoPorReferencia" << endl;
}

// cuadradoPorValor multiplica el n�mero por s� mismo, almacena el
// resultado en n�mero y devuelve el nuevo valor de n�mero

int cuadradoPorValor(int numero) {
    return numero *= numero; // no se modific� el argumento de la funci�n que hizo la llamada
}

// cuadradoPorReferencia multiplica refNumero por s� mismo y almacena el resultado
// en la variable a la que refNumero hace referencia en funci�n main
void cuadradoPorReferencia(int& refNumero) {
    refNumero *= refNumero; // se modific� el argumento de la funci�n que hizo la llamada
}
