/*      UNIVERSIDAD ESTATAL A DISTANCIA
Curso: Introducción a la Programación
Código: 0831
Proyecto #1: (Control de Peso del paciente)
Tutor:  MAXIMILIANO VARGAS LEITON
Grupo: 01
Estudiante: Francisco Campos Sandi
Cédula: 114750560
I Cuatrimestre 2024
*/
// Se incluyen las librerias necesarias
#include <iostream>
#include <string>
#include <iomanip>
#include <limits>
using namespace std;

int main()
{
    string cedula;
    string nombre;
    double peso = 0.0;
    double talla = 0.0;
    double imc = 0.0;

    setlocale(LC_ALL, "spanish"); // Se establece para el uso de las tildes tomado de: https://es.stackoverflow.com/questions/151363/leer-escribir-caracteres-especiales-del-espa%C3%B1ol-acentos-%C3%B1-en-una-aplicaci
    int opcion;

    do
    {
        cout << " Menú:" << endl;                                           // Se muestran las opciones del menú
        cout << "1. Ingresar datos de la persona." << endl;
        cout << "2. Ingresar datos físicos." << endl;
        cout << "3. Calcular el índice de masa corporal." << endl;
        cout << "4. Reporte de información del paciente." << endl;
        cout << "5. Salir." << endl;
        cout << "Ingrese su opción: ";


        if (!(cin>> opcion))  // Verifica si la entrada para la opción del menú no es válida (por ejemplo, si el usuario ingresa una letra en lugar de un número)
        {
            cout << "Debe ingresar un número válido. Por favor, intente nuevamente." << endl;
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');        //para el uso del ignore. tomado de: https://es.stackoverflow.com/questions/193829/porque-es-necesario-usar-el-comando-cin-ignore-despu%C3%A9s-de-usar-cin-y-lue
            continue;
        }

        // Verifica si la opción está en el rango válido
        if (opcion < 1 || opcion > 5)
        {
            cout << "Debe ingresar un número válido del 1 al 5. Por favor, intente nuevamente." << endl;
            continue;
        }

        switch (opcion)
        {
        case 1:
            cout << "Ingrese número de cédula: ";
            cin >> cedula;
            cout << "Ingrese nombre completo: ";
            cin.ignore();
            getline(cin, nombre);                        //para que usuario ingrese el nombre completo de una persona tomado de: https://es.stackoverflow.com/questions/35285/guardar-en-una-variable-un-nombre-y-apellido-que-ingrese-el-usuario
            break;
        case 2:
            if (cedula.empty() || nombre.empty())
            {
                cout << "Debe ingresar primero la información del paciente." << endl;
                break;
            }
            cout << "Ingrese su peso en kilogramos: ";
            if (!(cin >> peso))
            {
                cout << "Error: El peso debe ser un número." << endl;
                cin.clear();                                                         //para que no de error si ingresamos una letra, usamos ese ignore
                cin.ignore(numeric_limits<streamsize>::max(), '\n');                //para el uso del ignore. tomado de: https://es.stackoverflow.com/questions/193829/porque-es-necesario-usar-el-comando-cin-ignore-despu%C3%A9s-de-usar-cin-y-lue
                break;
            }
            cout << "Ingrese su talla en metros: ";
            if (!(cin >> talla))
            {
                cout << "Error: La talla debe ser un número." << endl;
                cin.clear();                                                        //para que no de error si ingresamos una letra, usamos ese ignore
                cin.ignore(numeric_limits<streamsize>::max(), '\n');                //para el uso del ignore. tomado de: https://es.stackoverflow.com/questions/193829/porque-es-necesario-usar-el-comando-cin-ignore-despu%C3%A9s-de-usar-cin-y-lue
                break;
            }
            if (peso < 30 || peso > 350 || talla < 0.30 || talla > 3.00)            // se valida que el peso y la talla cumpla los rangos establecidos
            {
                cout << "Error: Los valores de peso y talla deben estar dentro de los rangos válidos." << endl;
                break;
            }
            break;
        case 3:                                                            // para validar que las viariables no esten vacías, tomado de: https://www.geeksforgeeks.org/list-empty-function-in-c-stl/
            if (cedula.empty() || nombre.empty())
            {
                cout << "Debe ingresar primero la información del paciente." << endl;
                break;
            }
            imc = peso / (talla * talla);                                         //cálculo del IMC
            cout << "El IMC ha sido calculado." << endl;
            break;
        case 4:
            if (cedula.empty() || nombre.empty())                                       // para validar que las viariables no esten vacías, tomado de: https://www.geeksforgeeks.org/list-empty-function-in-c-stl/
            {
                cout << "Debe ingresar primero la información del paciente." << endl;
                break;                                                                    //Reporte general
            }
            cout << "|---------------------------------------------------------------|" << endl;
            cout << "|                  Control de Peso del paciente                 |" << endl;
            cout << "|---------------------------------------------------------------|" << endl;
            cout << "| Cédula:      | " << cedula << setw(33) << " |" << endl;                   //para el uso del setw //Tomado set de: https://es.stackoverflow.com/questions/146605/cual-es-el-prop%C3%B3sito-de-la-funcion-setw-de-la-biblioteca-iomanip-en-c
            cout << "|---------------------------------------------------------------|" << endl;
            cout << "| Nombre:      | " << setw(15) << nombre << setw(33) << " |" << endl;
            cout << "|---------------------------------------------------------------|" << endl;
            cout << "| Peso (kg)    | Talla (m)           | IMC                      |" << endl;
            cout << "|---------------------------------------------------------------|" << endl;
            cout << fixed << setprecision(2);
            cout << "| " << setw(12) << peso << " | " << setw(19) << talla << " | " << setw(24) << imc << " |" << endl;
            cout << "|---------------------------------------------------------------|" << endl;
            cout << "|                          Diagnóstico                          |" << endl;
            cout << "|---------------------------------------------------------------|" << endl;
            if (imc < 18.5)
                cout << "|Se encuentra dentro del rango de peso insuficiente             |" << endl;
            else if (imc >= 18.5 && imc <= 24.9)
                cout << "|Se encuentra dentro del rango de peso normal o saludable       |" << endl;
            else if (imc >= 25.0 && imc <= 29.9)
                cout << "|Se encuentra dentro del rango de sobrepeso                     |" << endl;
            else
                cout << "|Se encuentra dentro del rango de obesidad                      |" << endl;
            cout << "|                                                               |" << endl;
            cout << "|---------------------------------------------------------------|" << endl;
            break;
        case 5:
            cout << "Saliendo del programa..." << endl;
            break;
        default:
            cout << "Opción inválida, vuelva a intentarlo." << endl;
        }
    }
    while (opcion != 5);

    return 0;
}
