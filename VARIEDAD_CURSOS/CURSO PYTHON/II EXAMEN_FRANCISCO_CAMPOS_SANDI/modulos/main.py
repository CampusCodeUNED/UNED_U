#Curso Python Uned-Miccit
#Docente: Adrián Solano Sánchez
# Francisco Campos Sandi
# 114750560
# II Prueba


from empresa import Empresa
from empleado import Empleado


def mostrarMenu(empresa):
    print("\n+----------------------------------------------------+")
    print(f"|                  Menú de {empresa.nombre}         |")
    print("| 1. Agregar empleado                                |")
    print("| 2. Mostrar empleados                               |")
    print("| 3. Buscar empleado por cédula                      |")
    print("| 4. Mostrar salario total de la planilla            |")
    print("| 5. Despedir empleado                               |")
    print("| 6. Salir                                           |")
    print("+----------------------------------------------------+")


# Solicitar el nombre de la empresa
nombreEmpresa = input("Ingrese el nombre de la empresa: ")
empresa = Empresa(nombreEmpresa)

# Bucle principal para mostrar el menú
while True:
    mostrarMenu(empresa)
    opcion = input("Seleccione una opción: ")

    match opcion:
        case "1":
            nombre = input("Nombre: ")
            cedula = input("Cédula: ")
            # Validación de edad
            while True:
                edad_input = input("Edad: ")
                if edad_input.isdigit() and int(edad_input) > 0:
                    edad = int(edad_input)
                    break
                print("Error: Por favor, ingrese una edad válida (número positivo).")

            puesto = input("Puesto: ")
            # Validación de salario
            while True:
                salario_input = input("Salario: ")
                if salario_input.replace('.', '', 1).isdigit() and float(salario_input) >= 0:
                    salario = float(salario_input)
                    break
                print("Error: Por favor, ingrese un salario válido (número positivo).")

            empleado = Empleado(nombre, cedula, edad, puesto, salario)
            empresa.agregarEmpleado(empleado)

        case "2":
            empresa.mostrarEmpleados()

        case "3":
            cedula = input("Ingrese la cédula del empleado a buscar: ")
            empresa.buscarEmpleadoPorCedula(cedula)

        case "4":
            empresa.salarioTotalPlanilla()

        case "5":
            cedula = input("Ingrese la cédula del empleado a despedir: ")
            empresa.despedirEmpleado(cedula)

        case "6":
            print("Saliendo del sistema...")
            break

        case _:
            print("Opción no válida, intente de nuevo.")
