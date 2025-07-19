from personaje import Personaje


class Mago(Personaje):
    """Clase que representa a un Mago, que hereda de la clase Personaje."""

    def __init__(self, nombre, clase="Mago", fuerza=1, inteligencia=12, defensa_fisica=3, defensa_magica=6,
                 vida=100, espada="Oolacile"):
        # Inicializa la clase padre Personaje
        super().__init__(nombre, clase, fuerza, inteligencia, defensa_fisica, defensa_magica, vida)

        self.espada = espada  # Arma inicial
        # Diccionario que contiene las armas disponibles y sus valores
        self.seleccionar_arma = {"Oolacile": 2,"Izalith": 2.5,  "Manus": 3  }

        self.catalizador = 1.5  # Inicializa el catalizador

    def mostrar_personaje(self):
        """Muestra los detalles del mago."""
        print(
            "------------------------------------------------------------------------------------------------------------")
        super().mostrar_personaje()
        print(f"Arma equipada: {self.espada} (Valor: {self.seleccionar_arma[self.espada]:.2f})")
        print("Armas disponibles:")
        for arma, valor in self.seleccionar_arma.items():
            print(f"{arma}: {valor:.2f}")
        print(
            "------------------------------------------------------------------------------------------------------------")

    def cambiar_arma(self):
        """Permite al usuario cambiar el arma equipada."""
        while True:
            print("Armas disponibles:")
            for arma, valor in self.seleccionar_arma.items():
                print(f"{arma}: {valor:.2f}")

            seleccion = input("Elige un arma (o 'salir' para cancelar): ").strip().capitalize()

            if seleccion == 'Salir':
                print("Selección cancelada.")
                break

            # Verifica si la selección es válida
            if seleccion in self.seleccionar_arma:
                self.espada = seleccion  # Cambia el arma equipada
                print(f"Has cambiado tu arma a: {self.espada} con valor {self.seleccionar_arma[self.espada]:.2f}")
                break  # Salimos del bucle después de un cambio exitoso
            else:
                print("Arma no válida. Intenta de nuevo.")

    def danno_fisico(self, enemigo):
        """Calcula el daño físico al enemigo considerando su defensa física."""
        danno = self.fuerza - enemigo.defensa_fisica
        return max(danno, 0.1)  # Retorna 0.1 si el daño es menor o igual a 0

    def danno_magico(self, enemigo):
        """Calcula el daño mágico al enemigo considerando su defensa mágica."""
        danno = (self.inteligencia * self.catalizador) - enemigo.defensa_magica
        return max(danno, 0.1)  # Retorna 0.1 si el daño es menor o igual a 0

    def atacar(self, enemigo):
        """Realiza la acción de atacar, informando del daño realizado al enemigo."""
        if enemigo.esta_vivo():
            danno_fisico = self.danno_fisico(enemigo)
            danno_magico = self.danno_magico(enemigo)

            enemigo.vida -= danno_fisico + danno_magico

            print(f"{self.nombre} ataca a {enemigo.nombre} causando {danno_fisico:.1f} de daño físico "
                  f"y {danno_magico:.1f} de daño mágico.")

            if enemigo.vida <= 0:
                enemigo.morir()
        else:
            print(f"{enemigo.nombre} ya ha muerto y no puede ser atacado.")
