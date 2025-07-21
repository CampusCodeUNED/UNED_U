#Curso Python Uned-Miccit
#Docente: Adrián Solano Sánchez
# Francisco Campos Sandi
# 114750560
# II Prueba

from persona import Persona

class Empleado(Persona):
    def __init__(self, nombre, cedula, edad, puesto, salario):
        super().__init__(nombre, cedula, edad)
        self.puesto = puesto
        self.salario = salario

    def mostrarInformacion(self):
        print(f"Nombre: {self.nombre}, Cédula: {self.cedula}, Edad: {self.edad}, Puesto: {self.puesto}, Salario: ₡{self.salario}")
