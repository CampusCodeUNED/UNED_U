/* Ejemplo de uso de punteros y referencia de variables*/

#include <iostream>

using namespace std; // es para poder usar palabras reservadas

int main(){

    setlocale(LC_ALL, "Spanish"); //Tildes

    int a{7}; // inicializar a con 7
    int* aPtr = &a; // inicializa aPtr con la direcci�n de la variable int a

    cout << "La direcci�n de a es " << &a
    << "\nEl valor de aPtr es " << aPtr;
    cout << "\n\nEl valor de a es " << a
    << "\nEl valor de *aPtr es " << *aPtr << endl;

return 0;
}
