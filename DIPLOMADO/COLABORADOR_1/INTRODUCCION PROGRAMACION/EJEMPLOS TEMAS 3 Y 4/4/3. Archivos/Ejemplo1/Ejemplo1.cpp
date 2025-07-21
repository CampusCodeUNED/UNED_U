//crear un archivo temporal

#include <iostream>
#include <fstream> // manejo de archivos
#include <stdlib.h> //system("CLS");
#include <locale.h>//  setlocale(LC_ALL, "");


//El programa utiliza
using namespace std;

//Variables utilizadas

char resp;
char idCliente[]="";
int cedula;
char nombre [30];
char apellido [30];


//La función main empieza a ejecutarse
int main()
{

    setlocale(LC_ALL, "");//Hace que salgan los acentos en pantalla

    system("CLS");            //Limpia la pantalla

    ofstream registro("Clientes.txt", ios::out);// Crea el archivo y lo abre

        if (!registro)//Por si no se abre el archivo
            {
                cout << "No se puede abrir el archivo Clientes.txt" << endl; //Comentario mostrado en pantalla
                exit(1);//Sale del programa
                }

    do//Devuelve para empezar de nuevo la función main
	{
        system("CLS");
        cout << "Este programa permite el registro de clientes: "<<endl;
        cout << "Digite la cédula del cliente: ";
        cin >> (idCliente);
        if (isdigit(idCliente[0])){//Válida que sean solo números
                cedula = atoi (idCliente); //Convierte string to integer
            }

        else{
                cout << "\nSolo se permiten números, debe volver a iniciar\n\n";
                system("pause");
                return 0;
            }
        cout << "Nombre: ";
        cin >> (nombre);
        cout << "Apellido: ";
        cin >> (apellido);

        registro << cedula <<' ' << nombre <<' ' << apellido << endl;//escribir en el archivo

        cout << "Desea ingresar otro cliente S/N: ";
        cin >> resp;

      }while (resp == 'S' or resp == 's' );

}





