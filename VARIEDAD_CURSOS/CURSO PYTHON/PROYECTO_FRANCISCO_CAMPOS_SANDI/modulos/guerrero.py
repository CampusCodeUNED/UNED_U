from personaje import Personaje

class Guerrero(Personaje):
    """Clase que representa a un Guerrero, que hereda de la clase Personaje."""

    def __init__(self, nombre, clase="Guerrero", fuerza=12, inteligencia=1, defensa_fisica=6, defensa_magica=3,
                 vida=100, espada="Astora"):
        # Inicializa la clase padre Personaje
        super().__init__(nombre, clase, fuerza, inteligencia, defensa_fisica, defensa_magica, vida)
        self.espada = espada  # Nombre del arma inicial
        self.seleccionar_arma = {"Astora": 2, "Cristal": 2.5, "Solar": 3}  # Diccionario de armas disponibles

    def mostrar_personaje(self):
        """Muestra los detalles del guerrero."""
        print("------------------------------------------------------------------------------------------------------------")
        super().mostrar_personaje()
        # Usar el nombre del arma para obtener su valor
        valor_arma = self.seleccionar_arma.get(self.espada, 'No definido')
        print(f"Espada equipada: {self.espada} (Valor: {valor_arma:.2f})")
        print("Armas disponibles:")
        for arma, valor in self.seleccionar_arma.items():
            print(f"{arma}: {valor:.2f}")
        print("------------------------------------------------------------------------------------------------------------")

    def cambiar_arma(self):
        """Permite al usuario cambiar el arma equipada."""
        print("Armas disponibles:")
        for arma, valor in self.seleccionar_arma.items():
            print(f"{arma}: {valor:.2f}")

        while True:
            seleccion = input("Elige una espada (o 'salir' para cancelar): ").strip().capitalize()
            if seleccion.lower() == 'salir':
                print("Selección cancelada.")
                break

            if seleccion in self.seleccionar_arma:
                self.espada = seleccion  # Cambia el nombre de la espada
                print(f"Has cambiado tu espada a: {seleccion} con valor {self.seleccionar_arma[seleccion]:.2f}")
                break
            else:
                print("Arma no válida. Intenta de nuevo.")

    def danno_fisico(self, enemigo):
        """Calcula el daño físico al enemigo considerando su defensa física."""
        danno = self.fuerza * self.seleccionar_arma[self.espada] - enemigo.defensa_fisica
        return max(danno, 0.1)  # Retorna 0.1 si el daño es menor o igual a 0

    def dano_magico(self, enemigo):
        """Calcula el daño mágico al enemigo considerando su defensa mágica."""
        danno = self.inteligencia - enemigo.defensa_magica
        return max(danno, 0.1)  # Retorna 0.1 si el daño es menor o igual a 0

    def atacar(self, enemigo):
        """Realiza la acción de atacar, informando del daño realizado al enemigo."""
        if enemigo.esta_vivo():
            danno_fisico = self.danno_fisico(enemigo)
            danno_magico = self.danno_magico(enemigo)

            enemigo.vida -= (danno_fisico + danno_magico)

            print(f"{self.nombre} ataca a {enemigo.nombre} causando {danno_fisico:.1f} de daño físico "
                  f"y {danno_magico:.1f} de daño mágico.")

            if enemigo.vida <= 0:
                enemigo.morir()
        else:
            print(f"{enemigo.nombre} ya ha muerto y no puede ser atacado.")
