
// Demostración: excepciones mediante un número
#include <iostream>
#include <string.h>

using namespace std;

int main()
{
    setlocale(LC_ALL, "Spanish");
    char *s = "Hola";
    int n;
    try {
        cout << "Digite una posición del arreglo: ";
        cin >> n;
        if (n < 0) throw 2;
        if (n >= strlen(s)) throw 1;
        cout << s[n] << endl;
    }
    catch(int e) {
        if (e == 1)
            cout << "El número es mayor o igual que el tamaño del arreglo." <<endl;
        else
            if (e == 2)
            cout << "El número no puede ser negativo." << endl;
    }
    return 0;
}
