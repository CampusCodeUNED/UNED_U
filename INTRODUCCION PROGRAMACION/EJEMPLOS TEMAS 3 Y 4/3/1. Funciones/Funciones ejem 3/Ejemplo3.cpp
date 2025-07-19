/* Funcion factorial recursiva */

#include <iostream>
#include <iomanip>

using namespace std; //Palabras reservadas

long factorial(long);

main()
{
    long resultado, numero;
    cout << "Ingrese un numero: ";
    cin >> numero;
    resultado = factorial(numero);
    cout << "Factorial(" << numero << ") = " << resultado << "\n";
    return 0;
}

/* Funcion factorial recursiva */
long factorial(long n)
{
    if (n == 1)
        return n;
    else
        return n * factorial(n - 1); //5 * 4 * 3 * 2 * 1
}
