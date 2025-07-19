//leer un archivo temporal

#include <iostream>
#include <fstream> // manejo de archivos
#include <stdlib.h> //system("CLS");
#include <locale.h>//  setlocale(LC_ALL, "");
#include <iomanip>


//El programa utiliza
using namespace std;


//Variables utilizadas

int cedula;
char nombre [30];
char apellido [30];


//La función main empieza a ejecutarse
int main()
{

    setlocale(LC_ALL, "");//Hace que salgan los acentos en pantalla

    system("CLS");            //Limpia la pantalla


    ifstream regClientes("Clientes.txt", ios::in);// Lee y abre el archivo

    if (!regClientes)//Por si no puede abrir el archivo
        {
            cout << "No se puede abrir el archivo Clientes.txt" << endl; //Comentario mostrado en pantalla
            exit(1);//Sale del programa
        }
    cout <<"\nLos clientes ingresados al sistema son las siguiente\n\n";
    cout <<left << setw(10) << "Cédula" <<  setw(10) << "Nombre" <<  setw(10) << "Apellido"  <<  endl;
    while (regClientes >> cedula >> nombre >> apellido )
        {
            cout <<left << setw(10) << cedula << setw(10) << nombre << setw(10) << apellido << endl;
        }


}





