from personaje import Personaje

class Paladin(Personaje):
    """Clase que representa a un Paladín, que hereda de la clase Personaje."""

    def __init__(self, nombre, clase="Paladín", fuerza=8, inteligencia=8, defensa_fisica=5, defensa_magica=5,
                 vida=100, arma="Gough"):  # Inicializa el arma con un valor por defecto
        super().__init__(nombre, clase, fuerza, inteligencia, defensa_fisica, defensa_magica, vida)
        self.arma = arma  # Asigna el valor del arma
        self.seleccionar_arma = {"Gough": 1.30, "Caos": 1.40, "Quelaag": 1.50}  # Armas disponibles

    def mostrar_personaje(self):
        """Muestra los detalles del Paladín."""
        print("------------------------------------------------------------------------------------------------------------")
        super().mostrar_personaje()  # Muestra atributos comunes
        print(f"Arma equipada: {self.arma} (Daño: {self.seleccionar_arma.get(self.arma, 0):.2f})")  # Muestra el arma equipada

        print("Armas disponibles:")
        for arma, valor in self.seleccionar_arma.items():
            print(f"{arma}: {valor:.2f}")  # Muestra las armas disponibles

        print("------------------------------------------------------------------------------------------------------------")

    def cambiar_arma(self):
        """Permite al usuario elegir un arma de la lista disponible."""
        print("Armas disponibles:")
        for arma, valor in self.seleccionar_arma.items():
            print(f"{arma}: {valor:.2f}")  # Muestra las armas

        while True:
            seleccion = input("Elija el nombre del arma (o 'salir' para cancelar): ").strip().title()  # Captura la elección

            if seleccion.lower() == 'salir':
                print("Selección cancelada.")
                break

            if seleccion in self.seleccionar_arma:
                self.arma = seleccion  # Cambia el arma
                print(f"Arma cambiada a: {seleccion} - Daño: {self.seleccionar_arma[seleccion]:.2f}")  # Confirma el cambio
                break
            else:
                print("Arma no válida. Intente de nuevo.")  # Valida la elección

    def danno_fisico(self, enemigo):
        """Calcula el daño físico considerando la defensa del enemigo."""
        danno = self.fuerza * self.seleccionar_arma[self.arma] - enemigo.defensa_fisica
        return max(danno, 0.1) if enemigo.defensa_fisica >= danno else danno  # Devuelve daño

    def danno_magico(self, enemigo):
        """Calcula el daño mágico considerando la defensa mágica del enemigo."""
        danno = self.inteligencia * self.seleccionar_arma[self.arma] - enemigo.defensa_magica
        return max(danno, 0.1) if enemigo.defensa_magica >= danno else danno  # Devuelve daño

    def atacar(self, enemigo):
        """Ataca a un enemigo, causando daño físico y mágico."""
        if enemigo.esta_vivo():  # Verifica si el enemigo está vivo
            danno_fisico = self.danno_fisico(enemigo)  # Calcula el daño físico
            danno_magico = self.danno_magico(enemigo)  # Calcula el daño mágico

            enemigo.vida -= danno_fisico + danno_magico  # Resta el daño a la vida del enemigo

            print(f"{self.nombre} ataca a {enemigo.nombre} causando {danno_fisico:.1f} de daño físico "
                  f"y {danno_magico:.1f} de daño mágico.")

            if enemigo.vida <= 0:
                enemigo.morir()  # Si el enemigo ha caído
        else:
            print(f"{enemigo.nombre} ya ha muerto y no puede ser atacado.")

    def aumentar_fuerza(self, aliado):
        """Aumenta la fuerza de un aliado."""
        aliado.fuerza += self.fuerza * 0.30  # Aumenta fuerza en un 30%
        print(f"{self.nombre} aumenta la fuerza de {aliado.nombre} en {self.fuerza * 0.30:.2f}.")

    def aumentar_inteligencia(self, aliado):
        """Aumenta la inteligencia de un aliado."""
        aliado.inteligencia += self.inteligencia * 0.30  # Aumenta inteligencia en un 30%
        print(f"{self.nombre} aumenta la inteligencia de {aliado.nombre} en {self.inteligencia * 0.30:.2f}.")


