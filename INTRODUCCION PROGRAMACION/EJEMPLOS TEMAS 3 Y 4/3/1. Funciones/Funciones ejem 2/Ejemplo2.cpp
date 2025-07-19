// Pasar argumentos por valor y por referencia

#include <iostream>

using namespace std;

int main() {

    int cuadradoPorValor(int); // prototipo de función (paso por valor)
    void cuadradoPorReferencia(int&); // prototipo de función (paso por referencia)

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

// cuadradoPorValor multiplica el número por sí mismo, almacena el
// resultado en número y devuelve el nuevo valor de número

int cuadradoPorValor(int numero) {
    return numero *= numero; // no se modificó el argumento de la función que hizo la llamada
}

// cuadradoPorReferencia multiplica refNumero por sí mismo y almacena el resultado
// en la variable a la que refNumero hace referencia en función main
void cuadradoPorReferencia(int& refNumero) {
    refNumero *= refNumero; // se modificó el argumento de la función que hizo la llamada
}
