class Personaje:
    def __init__(self, nombre, clase="Defecto", fuerza=6, inteligencia=6, defensa_fisica=4, defensa_magica=4, vida=100):
        # Inicialización de los atributos del personaje
        self.nombre = nombre
        self.clase = clase
        self.fuerza = fuerza
        self.inteligencia = inteligencia
        self.defensa_fisica = defensa_fisica
        self.defensa_magica = defensa_magica
        self.vida = vida

    def mostrar_personaje(self):
        """Mostrar los datos del personaje."""
        print(f"Nombre: {self.nombre}, Clase: {self.clase}, "
              f"Fuerza: {self.fuerza}, Inteligencia: {self.inteligencia}, "
              f"Defensa Física: {self.defensa_fisica}, Defensa Mágica: {self.defensa_magica}, "
              f"Vida: {self.vida}")

    def subir_nivel(self, fuerza=0, inteligencia=0, defensa_fisica=0, defensa_magica=0, vida=0):
        """Aumentar los atributos del personaje."""
        self.fuerza += fuerza
        self.inteligencia += inteligencia
        self.defensa_fisica += defensa_fisica
        self.defensa_magica += defensa_magica
        self.vida += vida

    def esta_vivo(self) -> bool:
        """Retornar True si el personaje está vivo, False en caso contrario."""
        return self.vida > 0

    def morir(self):
        """Activarse solo si la vida es 0 o menos."""
        if self.vida <= 0:
            print(f"{self.nombre} ha muerto.")
            return True
        return False

    def danno_fisico(self, enemigo):
        """Calcular el daño físico al enemigo, garantizando que el mínimo daño sea 0.1."""
        danno = self.fuerza - enemigo.defensa_fisica
        if danno <= 0:
            danno = 0.1
        return danno

    def danno_magico(self, enemigo):
        """Calcular el daño mágico al enemigo, garantizando que el mínimo daño sea 0.1."""
        danno = self.inteligencia - enemigo.defensa_magica
        if danno <= 0:
            danno = 0.1
        return danno

    def atacar(self, enemigo):
        """Atacar a un enemigo, causando daño físico y mágico."""
        if enemigo.esta_vivo():
            # Calcular el daño físico y mágico
            danno_fisico = self.danno_fisico(enemigo)
            danno_magico = self.danno_magico(enemigo)

            # Aplicar el daño al enemigo
            enemigo.vida -= danno_fisico + danno_magico

            print(f"{self.nombre} ataca a {enemigo.nombre} causando "
                  f"{danno_fisico:.1f} de daño físico y {danno_magico:.1f} de daño mágico.")

            # Verificar si el enemigo ha llegado a 0 o menos de vida y llamar a morir si es el caso
            if enemigo.vida <= 0:
                enemigo.morir()
        else:
            # Imprimir mensaje si el enemigo ya está muerto
            print(f"{enemigo.nombre} ya ha muerto y no puede ser atacado.")

