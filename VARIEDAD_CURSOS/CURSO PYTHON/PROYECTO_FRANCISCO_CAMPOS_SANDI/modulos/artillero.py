import random
from modulos.personaje import Personaje

class Artillero(Personaje):
    """Clase que representa a un Artillero, que hereda de la clase Personaje."""

    def __init__(self, nombre, clase="Artillero", fuerza=10, inteligencia=4, defensa_fisica=4, defensa_magica=4,
                 vida=100, arco="Pesado"):
        super().__init__(nombre, clase, fuerza, inteligencia, defensa_fisica, defensa_magica, vida)
        self.arco = arco  # Inicializa el arco
        self.probabilidad = 0.5  # Probabilidad del 50% para un ataque doble
        self.seleccionar_arma = {"Pesado": 2, "Compuesto": 2.5, "Pharis": 3}  # Arcos disponibles

    def mostrar_personaje(self):
        """Muestra los detalles del Artillero."""
        print("------------------------------------------------------------------------------------------------------------")
        super().mostrar_personaje()  # Muestra los atributos del personaje
        print(f"Arco equipado: {self.arco} (Valor: {self.seleccionar_arma.get(self.arco, 0):.2f})")  # Muestra el arco equipado

        print("Armas disponibles:")
        for arma, valor in self.seleccionar_arma.items():
            print(f"{arma}: {valor:.2f}")  # Muestra los arcos disponibles

        print("------------------------------------------------------------------------------------------------------------")

    def cambiar_arma(self):
        """Permite al usuario elegir un arco de la lista disponible."""
        print("Armas disponibles:")
        for arma, valor in self.seleccionar_arma.items():
            print(f"{arma}: {valor:.2f}")  # Muestra las armas disponibles

        while True:
            eleccion = input("Elige un arco (o 'salir' para cancelar): ").strip().title()  # Captura la elección del usuario
            if eleccion.lower() == 'salir':
                print("Selección cancelada.")
                break

            if eleccion in self.seleccionar_arma:
                self.arco = eleccion  # Cambia el arco a su nombre
                print(f"Has cambiado tu arco a: {eleccion} con valor {self.seleccionar_arma[eleccion]:.2f}")  # Confirma el cambio
                break
            else:
                print("Arma no válida. Intenta de nuevo.")  # Valida la elección

    def danno_fisico(self, enemigo):
        """Calcula el daño físico considerando la defensa del enemigo."""
        danno = self.fuerza * self.seleccionar_arma[self.arco]  # Usa el valor del arco actual
        if enemigo.defensa_fisica > 0.7 * danno:  # Compara con el 70% del daño
            return 0.1  # Retorna un daño mínimo si la defensa es alta
        return danno  # Retorna el daño calculado

    def danno_magico(self, enemigo):
        """Calcula el daño mágico considerando la defensa mágica del enemigo."""
        danno = self.inteligencia  # El daño mágico se basa en la inteligencia
        if enemigo.defensa_magica > danno:  # Compara con la defensa mágica del enemigo
            return 0.1  # Retorna un daño mínimo si la defensa es alta
        return danno  # Retorna el daño calculado

    def atacar(self, enemigo):
        """Ataca a un enemigo, causando daño físico y mágico."""
        if enemigo.esta_vivo():  # Verifica si el enemigo está vivo
            dano_fisico = self.danno_fisico(enemigo)  # Calcula el daño físico

            # Aplicar probabilidad de ataque doble
            if random.random() < self.probabilidad:
                dano_fisico *= 2  # Duplicar el daño físico
                print("¡Ataque doble activado!")

            dano_magico = self.danno_magico(enemigo)  # Calcula el daño mágico
            enemigo.vida = max(0, enemigo.vida - (dano_fisico + dano_magico))  # Actualiza la vida del enemigo

            print(f"{self.nombre} ataca a {enemigo.nombre} causando {dano_fisico:.1f} de daño físico "
                  f"y {dano_magico:.1f} de daño mágico.")

            if enemigo.vida <= 0:
                enemigo.morir()  # Llama al metodo morir() si el enemigo ha caído
        else:
            print(f"{enemigo.nombre} ya ha muerto y no puede ser atacado.")
