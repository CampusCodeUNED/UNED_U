//crear un archivo temporal

#include <iostream>
#include <fstream> // manejo de archivos
#include <stdlib.h> //system("CLS");
#include <locale.h>//  setlocale(LC_ALL, "");
#include <iomanip>


//El programa utiliza
using namespace std;


//Variables utilizadas

char idCliente[]="";
int cedula;
int valida;
int consulta;
char nombre [30];
char apellido [30];

//La funci�n main empieza a ejecutarse
int main()
{

    system("CLS");            //Limpia la pantalla

	setlocale(LC_ALL, "");//Hace que salgan los acentos en pantalla

    ifstream consultar("Clientes.txt", ios::in);// Lee y abre el archivo

    if (!consultar)//Por si no se abre el archivo
        {
            cout << "No se puede abrir el archivo de Clientes.txt" << endl; //Comentario mostrado en pantalla
            exit(1);//Sale del programa
        }

        cout <<"\nConsulta de Clientes\n"<<endl;
        cout <<"\nDigite la c�dula a consultar: ";
        cin >> idCliente;
        if (isdigit(idCliente[0]))//V�lida que se un n�mero
                    {
                        consulta = atoi (idCliente);
                    }
                 else
                    {
                        cout << "\nSolo se permiten n�meros, debe volver a iniciar\n";
                        system("pause");
                        return 0;
                    }
          {
            while (consultar >> cedula >> nombre >> apellido)
                {
                 if (cedula == consulta)
                    {
                        cout << "\n" <<left << setw(10) << "C�dula" <<  setw(10) << "Nombre" <<  setw(10) << "Apellido"  << endl;
                        cout <<left << setw(10) << cedula << setw(10) << nombre << setw(10) << apellido << endl;
                        valida = 1;

                    }
                }

          }
            try {
            if (valida != 1) throw 123;
               }
               catch(int e) {
                    cout << "Error: " << e << ". El n�mero de c�dula no existe"<< endl;
                    system("pause");
                }
}





