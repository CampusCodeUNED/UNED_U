/*      UNIVERSIDAD ESTATAL A DISTANCIA
Curso: Introducci�n a la Programaci�n
C�digo: 0831
Proyecto #2: (Farmacia)
Tutor:  MAXIMILIANO VARGAS LEITON
Grupo: 19
Estudiante: Francisco Campos Sandi
C�dula: 114750560
I Cuatrimestre 2024
*/
// Se incluyen las librerias necesarias
#include <iostream>
#include <fstream> // Librer�a de flujo de archivos
#include <sstream>
#include <string>
#include <vector>
#include <iomanip>
#include <stdexcept> // Librer�a para excepciones
#include <cctype>
#include <limits>

using namespace std;

// Estructuras de Datos
struct Producto
{
    int codigo;
    string nombre;
    int cantidad;
};

struct ClaveAsociada
{
    int codigoProducto;
    string palabraAsociada;
};

struct Receta
{
    int numero;
    string paciente;
    int codigoProducto;
    string indicacion;
    float cantidad;
};

// Declaraci�n de Funciones
bool esNumerico(const string& str);
bool validarOpcionSN(char opcion);
bool validarOpcionMenu(int opcion);
string convertirAMayusculas(const string& str);
void agregarProductosVector(vector<Producto>& productos);
void ingresarProductos(vector<Producto>& productos);
void agregarClavesVector(vector<ClaveAsociada>& claves);
void ingresarAsociaciones(vector<Producto>& productos);
bool validarAsociacion(int codigoProducto, const string& indicacion, const vector<ClaveAsociada>& claves);
void registrarReceta(vector<Producto>& productos, vector<ClaveAsociada>& claves);
void reporteCatalogoProductos();
void reporteReceta();

int main()
{
    system("Color F0");//color del fondo
    setlocale(LC_ALL, "spanish");//tildes y caracteres
    vector<Producto> productos;   //Inicializar los vectores
    vector<ClaveAsociada> claves;

    int opcion;
    do
    {
        try
        {
            cout << "\n====================== MEN� ======================" << endl;
            cout << " 1. Ingresar nuevos productos                      " << endl;
            cout << " 2. Ingresar asociaciones de palabras              " << endl;
            cout << " 3. Registrar receta                               " << endl;
            cout << " 4. Generar reporte de cat�logo de productos       " << endl;
            cout << " 5. Generar reporte de recetas                     " << endl;
            cout << " 6. Salir                                          " << endl;
            cout << "\n=================================================" << endl;
            cout << " " << endl;
            cout << "Ingrese su opci�n: ";
            cin >> opcion;

            if (!cin)
            {
                throw runtime_error("Entrada no valida.. Por favor, ingrese un n�mero."); //Uso de la excepci�n si se ingresa una entrada no num�rica
            }

            if (!validarOpcionMenu(opcion))
            {
                cerr << "Error: Opcion inv�lida. Por favor, ingrese un n�mero del 1 al 6." << endl;
                continue;
            }

            switch (opcion)
            {
            case 1:
                ingresarProductos(productos);
                break;
            case 2:
                ingresarAsociaciones(productos);
                break;
            case 3:
                registrarReceta(productos, claves);
                break;
            case 4:
                reporteCatalogoProductos();
                break;
            case 5:
                reporteReceta();
                break;
            case 6:
                cout << "Saliendo del programa..." << endl;
                break;
            }
        }
        catch(const exception& e)     //Capta y muestra la excepci�n
        {
            cerr << "Error: " << e.what() << endl;
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
        }
    }
    while (opcion != 6);

    return 0;
}

// Funciones para validaciones
bool esNumerico(const string& str)             //funci�n tomada de :https://www.studocu.com/es-mx/document/universidad-del-valle-de-mexico/programacion-orientada-a-objetos/act3poo/89874211
{
    for (char c : str)
    {
        if (!isdigit(c))
        {
            return false;
        }
    }
    return true;
}

bool validarOpcionSN(char opcion)
{
    return (opcion == 'S' || opcion == 's' || opcion == 'N' || opcion == 'n');
}

bool validarOpcionMenu(int opcion)
{
    return (opcion >= 1 && opcion <= 6);
}

string convertirAMayusculas(const string& str)       //convertir toda una palabra a may�sculas, adaptada con la ayuda de la funci�n esNumerico
{
    string resultado = str;
    for (char& c : resultado)
    {
        c = toupper(c);
    }
    return resultado;
}

// Implementaci�n de Funciones Principales
void agregarProductosVector(vector<Producto>& productos)
{
    int codigo;
    string nombre;
    int cantidad;
    char comma; // Para consumir la coma entre los campos

    ifstream archivo("MEDICAMENTOS.TXT");
    if (!archivo)
    {
        cerr << "Se ha creado el archivo MEDICAMENTOS.txt" << endl;
        return;
    }

    productos.clear(); // Limpiar la lista antes de cargar los nuevos productos


    while (archivo >> codigo >> comma >> ws && getline(archivo, nombre, ',') && archivo >> cantidad)
    {
        Producto producto;                       // Se crea un nuevo objeto tipo Producto
        producto.codigo = codigo;
        producto.nombre = nombre;
        producto.cantidad = cantidad;

        productos.push_back(producto);//p�gina 323 libro, los va agregando al final
    }

    archivo.close(); // Cerrar el archivo despu�s de cargar los productos
}

void ingresarProductos(vector<Producto>& productos)
{
    Producto producto;
    string codigoProductoStr;
    char continuar = 'S';
    int codigoProducto;
    int cantidad;
    try
    {

        agregarProductosVector(productos);
        ofstream archivo("MEDICAMENTOS.TXT", ios::app);
        if (!archivo)
        {
            cerr << "Se ha creado el archivo MEDICAMENTOS.txt" << endl;
            return;
        }

        while (continuar == 'S')
        {
            cout << "Ingrese el codigo del producto: ";
            cin >> codigoProductoStr;
            if (!esNumerico(codigoProductoStr))
            {
                cerr << "Error: El c�digo del producto debe ser num�rico." << endl;
                return;
            }
            codigoProducto = stoi(codigoProductoStr);

            // Verificar si el codigo del producto ya existe
            for (const Producto& p : productos)
            {
                if (p.codigo == codigoProducto)
                {
                    cerr << "Error: El c�digo del producto ya existe. Ingrese un c�digo �nico." << endl;
                    return;
                }
            }

            producto.codigo = codigoProducto;

            cout << "Ingrese el nombre del producto: ";
            cin.ignore();
            getline(cin, producto.nombre);
            if (producto.nombre.empty())
            {
                cerr << "Error: El nombre del producto no puede estar vac�o." << endl;
                return;
            }

            while (true)
            {
                cout << "Ingrese la cantidad del producto: ";
                if (cin >> producto.cantidad)
                {
                    break;
                }
                else
                {
                    cerr << "Error: La cantidad debe ser un numero." << endl;
                    cin.clear();
                    cin.ignore(numeric_limits<streamsize>::max(), '\n');
                }
            }


            archivo << producto.codigo << "," << producto.nombre << "," << producto.cantidad << endl;

            productos.push_back(producto);

            cout << "�Ingresar otro producto? (S/N): ";
            cin >> continuar;
            continuar = toupper(continuar);
            if (!validarOpcionSN(continuar))
            {
                cerr << "Error: Opcion invalida, debe ingresar 'S' o 'N'." << endl;
                return;
            }
        }

        archivo.close();

    }
    catch(const exception& e)
    {
        cerr << "Error: " << e.what() << endl;
    }
}

void agregarClavesVector(vector<ClaveAsociada>& claves)
{
    ifstream archivo("CLAVES.TXT");
    if (!archivo)
    {
        cerr << "Error al abrir el archivo de CLAVES.TXT" << endl;
        return;
    }

    claves.clear(); // Limpiar la lista antes de cargar las nuevas claves

    string linea;
    while (getline(archivo, linea))
    {
        stringstream ss(linea);
        ClaveAsociada clave;
        string codigoProductoStr;

        getline(ss, codigoProductoStr, ',');
        clave.codigoProducto = stoi(codigoProductoStr);
        getline(ss, clave.palabraAsociada);

        claves.push_back(clave);
    }

    archivo.close();
}

void ingresarAsociaciones(vector<Producto>& productos)
{
    int contadorAsociaciones = 0;
    char continuar = 'S';
    int codigoProducto;
    bool productoExistente = false;
    string nombreProducto;
    try
    {
        agregarProductosVector(productos);

        ofstream archivo("CLAVES.TXT", ios::app);
        if (!archivo)
        {
            cerr << "Se ha creado el archivo CLAVES.TXT" << endl;
            return;
        }

        while (continuar == 'S')
        {
            ClaveAsociada asociacion;
            string codigoProductoStr;

            cout << "Ingrese el codigo del producto: ";
            cin >> codigoProductoStr;
            if (!esNumerico(codigoProductoStr))
            {
                cerr << "Error: El c�digo del producto debe ser num�rico." << endl;
                return;
            }
            codigoProducto = stoi(codigoProductoStr);

            // Verificar si el codigo del producto existe en el cat�logo de productos

            for (const Producto& producto : productos)
            {
                if (producto.codigo == codigoProducto)
                {
                    productoExistente = true;
                    nombreProducto = producto.nombre;
                    cout << "Producto encontrado: " << nombreProducto << endl;
                    break;
                }
            }

            if (!productoExistente)
            {
                cerr << "Error: El c�digo del producto no existe en el cat�logo." << endl;
                return;
            }

            asociacion.codigoProducto = codigoProducto;

            cout << "Ingrese la palabra asociativa: ";
            cin.ignore();
            getline(cin, asociacion.palabraAsociada);
            asociacion.palabraAsociada = convertirAMayusculas(asociacion.palabraAsociada);

            archivo << asociacion.codigoProducto << "," << asociacion.palabraAsociada << endl;

            cout << "Asociacion registrada: Codigo " << asociacion.codigoProducto << ", Palabra asociativa: " << asociacion.palabraAsociada << endl;
            contadorAsociaciones++;

            if (contadorAsociaciones >= 5)
            {
                break;

            }
            //cout << contadorAsociaciones<< endl;

            cout << "�Ingresar otra asociaci�n? (S/N): ";
            cin >> continuar;
            continuar = toupper(continuar);
            if (!validarOpcionSN(continuar))
            {
                cerr << "Error: Opci�n inv�lida, debe ingresar 'S' o 'N'." << endl;
                return;
            }
        }

        archivo.close();

    }
    catch(const exception& e)
    {
        cerr << "Error: " << e.what() << endl;
    }
}

bool validarAsociacion(int codigoProducto, const string& indicacion, const vector<ClaveAsociada>& claves)
{
    string indicacionMayusculas;
    indicacionMayusculas = convertirAMayusculas(indicacion);// convertir las indicaciones a may�sculas
    for (const auto& clave : claves)  //P�gina 309 itera sobre cada clave en el vector de claves
    {
        if (clave.codigoProducto == codigoProducto)
        {
            if (indicacion.find(clave.palabraAsociada) != string::npos)// Si la indicaci�n contiene la palabra asociada  se retorna verdadero
            {
                return true; // La asociaci�n es v�lida
            }
            else
            {
                cout << "La indicaci�n no es correcta. La palabra asociada es: " << clave.palabraAsociada << endl;
                return false;
            }
        }
    }

    cerr << "No se encontr� ninguna asociaci�n para el producto con c�digo " << codigoProducto << endl;
    return false;
}

void registrarReceta(vector<Producto>& productos, vector<ClaveAsociada>& claves)
{
    Receta receta;
    bool recetaExistente;
    bool volverAPedir;
    int numeroReceta;
    char opcion;
    string codigoProductoStr;
    bool productoExistente = false;
    string nombreMedicamento;
    try
    {
        agregarClavesVector(claves);
        agregarProductosVector(productos);

        ofstream archivo("RECETA.TXT", ios::app);
        if (!archivo)
        {
            cerr << "Se ha creado el archivo RECETA.txt" << endl;
            return;
        }


        do
        {
            recetaExistente = false; // Reiniciar la bandera de receta existente
            volverAPedir = false; // Reiniciar la bandera para volver a pedir el n�mero de receta

            while (true)
            {
                cout << "Ingrese el numero de receta (a partir de 1000): ";
                if (cin >> numeroReceta)
                {
                    break;
                }
                else
                {
                    cerr << "Error: El numero de la receta debe ser un numero." << endl;
                    cin.clear();
                    cin.ignore(numeric_limits<streamsize>::max(), '\n');
                }
            }
            receta.numero = numeroReceta;

            // Validar que el n�mero de receta sea a partir de 1000
            if (receta.numero < 1000)
            {
                cerr << "Error: El numero de receta debe ser un numero a partir de 1000." << endl;
                volverAPedir = true; // Establecer la bandera para repetir la pregunta
            }

            if (!volverAPedir)
            {
                // Verificar si la receta ya existe
                ifstream archivoReceta("RECETA.TXT");
                string linea;
                while (getline(archivoReceta, linea))
                {
                    stringstream ss(linea);
                    int numero;
                    ss >> numero;
                    if (numero == receta.numero)
                    {
                        recetaExistente = true;
                        break;
                    }
                }
                archivoReceta.close();

                if (recetaExistente)
                {
                    cout << "RECETA YA EXISTE, �DESEA INTENTAR CON OTRO N�MERO DE RECETA? (S/N): ";
                    cin >> opcion;
                    opcion = toupper(opcion);
                    if (opcion == 'N')
                    {
                        archivo.close();
                        return;
                    }
                    else
                    {
                        volverAPedir = true; // Establecer la bandera para repetir la pregunta
                    }
                }
            }
        }
        while (volverAPedir);


        cout << "Ingrese el nombre del paciente: ";
        cin.ignore();
        getline(cin, receta.paciente);
        if (receta.paciente.empty())
        {
            cerr << "Error: El nombre del paciente no puede estar vacio." << endl;
            return;
        }

        cout << "Ingrese el c�digo del producto: ";
        cin >> codigoProductoStr;
        if (!esNumerico(codigoProductoStr))
        {
            cerr << "Error: El c�digo del producto debe ser num�rico." << endl;
            return;
        }
        receta.codigoProducto = stoi(codigoProductoStr);

        // Verificar si el c�digo del producto existe en el cat�logo de productos
        for (Producto& producto : productos)
        {
            if (producto.codigo == receta.codigoProducto)
            {
                productoExistente = true;
                nombreMedicamento = producto.nombre;
                break;
            }
        }

        if (!productoExistente)
        {
            cout << "El c�digo de producto ingresado no existe en el cat�logo." << endl;
            archivo.close();
            return;
        }

        cout << "Nombre del medicamento: " << nombreMedicamento << endl;

        cout << "Ingrese la indicaci�n: ";
        cin.ignore();
        getline(cin, receta.indicacion);
        receta.indicacion = convertirAMayusculas(receta.indicacion);

        cout << "Ingrese la cantidad: ";
        cin >> receta.cantidad;

        // Validar la asociaci�n de la receta
        bool asociacionValida = false;
        for (const auto& clave : claves)
        {
            if (clave.codigoProducto == receta.codigoProducto)
            {
                if (receta.indicacion.find(clave.palabraAsociada) != string::npos)
                {
                    asociacionValida = true;

                    // Disminuir la cantidad del producto solo si la asociaci�n es v�lida
                    for (Producto& producto : productos)
                    {
                        if (producto.codigo == receta.codigoProducto)
                        {
                            if (receta.cantidad <= producto.cantidad)
                            {
                                // Reducir la cantidad disponible en el inventario
                                producto.cantidad -= receta.cantidad;

                                // Actualizar el archivo de productos con la nueva cantidad
                                ofstream archivoProductos("MEDICAMENTOS.TXT");
                                if (!archivoProductos)
                                {
                                    cerr << "Error al abrir el archivo de productos." << endl;
                                    return;
                                }

                                for (const Producto& p : productos)
                                {
                                    archivoProductos << p.codigo << "," << p.nombre << "," << p.cantidad << endl;
                                }
                                archivoProductos.close();

                                break;
                            }
                            else
                            {
                                cout << "La cantidad solicitada (" << receta.cantidad << ") es mayor que la cantidad disponible (" << producto.cantidad << ") para el producto '" << nombreMedicamento << "'." << endl;
                                archivo.close();
                                return;
                            }
                        }
                    }

                    break;
                }
            }
        }

        if (!asociacionValida)
        {
            cout << "No se pudo registrar la receta debido a una asociacion incorrecta." << endl;
            archivo.close();
            return;
        }

        archivo << receta.numero << "," << receta.paciente << "," << receta.codigoProducto << "," << receta.indicacion << "," << fixed << setprecision(2) << receta.cantidad << endl;
        cout << "Receta registrada con �xito." << endl;


        cout << "�Registrar otra receta? (S/N): ";
        cin >> opcion;
        opcion = toupper(opcion);
        if (opcion == 'S')
        {
            registrarReceta(productos, claves);
        }
        else
        {
            archivo.close();
        }

    }
    catch(const exception& e)
    {
        cerr << "Error: " << e.what() << endl;
    }
}


void reporteCatalogoProductos()
{
    int codigo;
    string nombre;
    int cantidad;
    ifstream archivo("MEDICAMENTOS.TXT");
    if (!archivo)
    {
        cerr << "Error al abrir el archivo." << endl;
        return;
    }
    cout << "====================== Reporte de Cat�logo de Productos ====================" << endl;
    cout << " " << endl;
    cout << "---------------------------------------------------------------------------" << endl;
    cout << setw(10) << "C�digo" << setw(40) << "Descripci�n" << setw(25) << "Cantidad" << endl;
    cout << "---------------------------------------------------------------------------" << endl;

    char comma; // Para consumir la coma entre los campos

    while (archivo >> codigo >> comma >> ws && getline(archivo, nombre, ',') && archivo >> cantidad)
    {
        nombre = convertirAMayusculas(nombre); // Convertir el nombre a may�sculas
        cout << setw(7) << " " << codigo << setw(40)  << nombre << setw(25) << right << cantidad << endl;
        cout << "---------------------------------------------------------------------------" << endl;
    }
    cout << "-------------------------------------------------------------------------------" << endl;
    cout << "                              FIN                                              " << endl;
    cout << "-------------------------------------------------------------------------------" << endl;
    archivo.close();
}

void reporteReceta()
{
    int numero;
    string paciente;
    int codigoProducto;
    string indicacion;
    float cantidad;
    int codigo;
    string nombre;
    int cantidadProducto;
    char comaProductos;
    ifstream archivo("RECETA.TXT");
    string nombreMedicamento;
    if (!archivo)
    {
        cerr << "Error al abrir el archivo." << endl;
        return;
    }
    cout << "--------------------------------------------------------------------" << endl;
    cout << "                  Reporte de las Recetas:" << endl;
    cout << "--------------------------------------------------------------------" << endl;


    char comma; // Para consumir la coma entre los campos

    while (archivo >> numero >> comma >> ws && getline(archivo, paciente, ',') && archivo >> codigoProducto >> comma >> ws && getline(archivo, indicacion, ',') && archivo >> cantidad)
    {
        // Obtener el nombre del medicamento correspondiente al c�digo del producto

        bool productoEncontrado = false;
        ifstream productosFile("MEDICAMENTOS.TXT");
        if (!productosFile)
        {
            cerr << "Error al abrir el archivo de productos." << endl;
        }
        else
        {

            while (productosFile >> codigo >> comaProductos >> ws && getline(productosFile, nombre, ',') && productosFile >> cantidadProducto)
            {
                if (codigo == codigoProducto)
                {
                    nombreMedicamento = nombre;
                    productoEncontrado = true;
                    break;
                }
            }
            productosFile.close();
        }

        if (!productoEncontrado)
        {
            cerr << "No se pudo encontrar el medicamento correspondiente al codigo " << codigoProducto << "." << endl;
            continue;
        }


        // Imprimir la receta
        cout << "___________________________________________________________________" << endl;
        cout << "|Receta No.|" << setw(38) << left << "     " << fixed << setw(16) << right << numero << setw(2) << right << "" << endl;
        cout << "|__________________________________________________________________" << endl;
        cout << "|                        Paciente   " << setw(20) << right << "     " << setw(13) << right << "" << endl;
        cout << "|------------------------------------------------------------------" << endl;
        cout << "| " << setw(25) << "" << paciente<< setw(15)  << right << "     " << setw(31) << right << "" << endl;
        cout << "|------------------------------------------------------------------" << endl;
        cout << "| " << endl;
        cout << "|------------------------------------------------------------------" << endl;
        cout << "|                          Medicamento                             " << endl;
        cout << "|------------------------------------------------------------------" << endl;
        cout << "| " << setw(15)<< left << codigoProducto << "|     " << setw(5)<<""<< left << nombreMedicamento << setw(19)<< right << "" << endl;
        cout << "|------------------------------------------------------------------" << endl;
        cout << "|"  << indicacion <<endl;
        cout << "|------------------------------------------------------------------" << endl;
        cout << "|Cantidad   |" << fixed << setw(15) << setprecision(2)<<cantidad << setw(40) << "" << endl;
        cout << "|__________________________________________________________________" << endl;
        cout << " " << endl;
        cout << " " << endl;
    }
    cout << "-------------------------------------------------------------------" << endl;
    cout << "                              FIN                                  " << endl;
    cout << "-------------------------------------------------------------------" << endl;
    archivo.close();
}


