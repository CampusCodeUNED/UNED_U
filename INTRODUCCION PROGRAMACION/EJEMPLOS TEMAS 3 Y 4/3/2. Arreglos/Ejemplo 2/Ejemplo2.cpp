// Captura datos de una matriz, los suma e imprime la matriz
#include <iostream>
#include <stdlib.h>
#include <conio.h>

using namespace std; //Palabras reservadas

int matriz[3][3]={{0}};

void llenarMatriz (){
    int i,j;
    for(i=0;i<3;i++){//filas
            for(j=0;j<3;j++){//columnas
            cout << "Introduzca el valor [" << i <<"][" << j <<"]: ";
            cin >> matriz[i][j];
            } //end segundo for
    } //end primer for
} //end procedimiento

main(){
    int i,j,suma=0;
    llenarMatriz ();
    cout << "\n";
    for(i=0;i<3;i++){
        for(j=0;j<3;j++){
        cout <<matriz[i][j] << " " ;
        suma+= matriz[i][j];
        }
        cout << "\n";
    }
    cout << "\nValor total: " << suma <<endl;
}
