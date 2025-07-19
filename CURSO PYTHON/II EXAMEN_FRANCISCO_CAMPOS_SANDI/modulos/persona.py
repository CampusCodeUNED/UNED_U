#Curso Python Uned-Miccit
#Docente: Adrián Solano Sánchez
# Francisco Campos Sandi
# 114750560
# II Prueba


class Persona:
    def __init__(self, nombre, cedula, edad):
        self.nombre = nombre
        self.cedula = cedula
        self.edad = edad

    def mostrarInformacion(self):
        print(f"Nombre: {self.nombre}, Cédula: {self.cedula}, Edad: {self.edad}")
