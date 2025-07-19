#Curso Python Uned-Miccit
#Docente: Adrián Solano Sánchez
# Francisco Campos Sandi
# 114750560
# II Prueba

class Empresa:
    def __init__(self, nombre):
        self.nombre = nombre
        self.empleados = []

    def agregarEmpleado(self, empleado):
        if len(self.empleados) >= 5:
            print("La empresa está saturada, no se pueden agregar más empleados.")
            return
        for emp in self.empleados:
            if emp.cedula == empleado.cedula:
                print("Ya existe un empleado con esa cédula.")
                return
        self.empleados.append(empleado)
        print(f"Empleado {empleado.nombre} agregado con éxito.")

    def mostrarEmpleados(self):
        if not self.empleados:
            print("No hay empleados en la empresa.")
        else:
            for emp in self.empleados:
                emp.mostrarInformacion()

    def buscarEmpleadoPorCedula(self, cedula):
        if not self.empleados:
            print("No hay empleados en la empresa.")
            return
        for emp in self.empleados:
            if emp.cedula == cedula:
                emp.mostrarInformacion()
                return
        print("No se encontró un empleado con esa cédula.")

    def salarioTotalPlanilla(self):
        if not self.empleados:
            print("No hay empleados en la empresa.")
        else:
            total_salarios = sum(emp.salario for emp in self.empleados)
            print(f"El salario total de la planilla es: ₡{total_salarios}")

    def despedirEmpleado(self, cedula):
        if not self.empleados:
            print("No hay empleados en la empresa.")
            return
        for emp in self.empleados:
            if emp.cedula == cedula:
                self.empleados.remove(emp)
                print(f"Empleado {emp.nombre} despedido.")
                return
        print("No se encontró un empleado con esa cédula.")
