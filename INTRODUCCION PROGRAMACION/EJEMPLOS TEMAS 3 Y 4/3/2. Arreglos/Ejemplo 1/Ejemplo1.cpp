// Llena un arreglo aleatoriamente y lo ordena mediante el método de burbuja
#include <iostream>
#include <stdlib.h>

using namespace std; //Palabras reservadas

int main(){

    int arreglo[4];
    int aux;

    //llenar el arreglo
    for (int i = 0; i <= 3; i ++)
        arreglo[i] = rand() % 50;

    //mostramos los resultados en pantalla.
    for(int n=0; n < 4; n++)
        cout << (n+1) << ") " << arreglo[n] << endl;

    //Uso del método burbuja
    for (int j = 0; j < 4; j++)
        for (int z = j; z < 4; z++)
            if (arreglo[j] > arreglo[z]){
                aux = arreglo[j];
                arreglo[j] = arreglo[z];
                arreglo[z] = aux;
    }

    //mostramos los resultados en pantalla.
    cout << endl;
    for(int n=0; n<4; n++)
        cout << (n+1) << ") " << arreglo[n] << endl;
        cout << endl;
    return 0;
}
