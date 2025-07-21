from personaje import Personaje

class Sanador(Personaje):
    """Clase que representa a un Sanador, que hereda de la clase Personaje."""

    def __init__(self, nombre, clase="Sanador", fuerza=2, inteligencia=2, defensa_fisica=12, defensa_magica=12,
                 vida=120, talisman=0.10, arma="Marfil"):
        super().__init__(nombre, clase, fuerza, inteligencia, defensa_fisica, defensa_magica, vida)
        self.talisman = talisman  # Talismán que aumenta la curación
        self.espada = arma  # Arma inicial
        self.seleccionar_arma = {"Marfil": 0.15, "Velka": 0.20, "Lunar": 0.25}  # Armas disponibles

    def mostrar_personaje(self):
        """Muestra los detalles del Sanador."""
        print("------------------------------------------------------------------------------------------------------------")
        super().mostrar_personaje()  # Muestra información del personaje
        print(f"Arma equipada: {self.espada} (Daño: {self.seleccionar_arma[self.espada]:.2f})")
        print("Armas disponibles:")
        for arma, valor in self.seleccionar_arma.items():
            print(f"{arma}: {valor:.2f}")  # Muestra las armas y sus valores
        print("------------------------------------------------------------------------------------------------------------")

    def cambiar_arma(self):
        """Permite al usuario cambiar el arma equipada."""
        print("Armas disponibles:")
        for arma, valor in self.seleccionar_arma.items():
            print(f"{arma}: {valor:.2f}")

        while True:
            seleccion = input("Elige un arma (o 'salir' para cancelar): ").strip().capitalize()
            if seleccion == 'Salir':
                print("Selección cancelada.")
                break

            if seleccion in self.seleccionar_arma:
                self.espada = seleccion  # Cambia el arma equipada
                print(f"Has cambiado tu arma a: {seleccion} con daño {self.seleccionar_arma[seleccion]:.2f}")
                break
            else:
                print("Arma no válida. Intenta de nuevo.")

    def danno_fisico(self, enemigo):
        """Calcula el daño físico considerando la defensa del enemigo."""
        danno = self.fuerza * self.seleccionar_arma[self.espada] - enemigo.defensa_fisica
        return max(danno, 0.1)  # Asegura que el daño mínimo sea 0.1

    def danno_magico(self, enemigo):
        """Calcula el daño mágico considerando la defensa mágica del enemigo."""
        danno = self.inteligencia - enemigo.defensa_magica
        return max(danno, 0.1)  # Asegura que el daño mínimo sea 0.1

    def atacar(self, enemigo):
        """Ataca a un enemigo, causando daño físico y mágico."""
        if enemigo.esta_vivo():  # Verifica si el enemigo está vivo
            danno_fisico = self.danno_fisico(enemigo)  # Calcula daño físico
            danno_magico = self.danno_magico(enemigo)  # Calcula daño mágico

            enemigo.vida -= danno_fisico + danno_magico  # Aplica el daño
            print(f"{self.nombre} ataca a {enemigo.nombre} causando {danno_fisico:.1f} de daño físico "
                  f"y {danno_magico:.1f} de daño mágico.")

            if enemigo.vida <= 0:
                enemigo.morir()  # Llama al metodo morir() si el enemigo cae
        else:
            print(f"{enemigo.nombre} ya ha muerto y no puede ser atacado.")

    def curar_vida(self, aliado):
        """Cura la vida de un aliado."""
        if aliado.esta_vivo():  # Verifica si el aliado está vivo
            curacion = self.vida * self.talisman  # Calcula la curación
            aliado.vida += curacion  # Aplica la curación
            print(f"{self.nombre} cura a {aliado.nombre} en {curacion:.2f}. Nueva vida: {aliado.vida:.2f}.")
        else:
            print(f"{aliado.nombre} ya está muerto y no puede ser curado.")

    def subir_defensa_fisica(self, aliado):
        """Aumenta la defensa física de un aliado."""
        if aliado.esta_vivo():  # Verifica si el aliado está vivo
            aumento = self.defensa_fisica * self.talisman  # Calcula aumento de defensa
            aliado.defensa_fisica += aumento  # Aplica aumento
            print(f"{self.nombre} aumenta la defensa física de {aliado.nombre} en {aumento:.2f}. "
                  f"Nueva defensa física: {aliado.defensa_fisica:.2f}.")
        else:
            print(f"{aliado.nombre} ya está muerto y no puede recibir aumento de defensa.")

    def subir_defensa_magica(self, aliado):
        """Aumenta la defensa mágica de un aliado."""
        if aliado.esta_vivo():  # Verifica si el aliado está vivo
            aumento = self.defensa_magica * self.talisman  # Calcula aumento de defensa
            aliado.defensa_magica += aumento  # Aplica aumento
            print(f"{self.nombre} aumenta la defensa mágica de {aliado.nombre} en {aumento:.2f}. "
                  f"Nueva defensa mágica: {aliado.defensa_magica:.2f}.")
        else:
            print(f"{aliado.nombre} ya está muerto y no puede recibir aumento de defensa.")

