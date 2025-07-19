/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso:Sistemas operativos
Código: 0881
Proyecto final:  Algorítmo del banquero
Tutor:Bernarda Delgado Molina
Grupo:03
Estudiante: Francisco Campos Sandi
Cédula: 114750560
II Cuatrimestre 2024
*/




package algoritmobanquero;

class Carro {

    private static int contadorId = 1; // Contador estático para IDs únicos
    private int id;
    private int tiempoEstacionamiento; // en segundos
    private int indiceEspacio;  // índice del espacio ocupado

    public Carro(int tiempoEstacionamiento) {
        this.id = contadorId++;
        this.tiempoEstacionamiento = tiempoEstacionamiento;
    }

    public int getId() {
        return id;
    }

    public int getTiempoEstacionamiento() {
        return tiempoEstacionamiento;
    }

    public int getIndiceEspacio() {
        return indiceEspacio;
    }

    public void setIndiceEspacio(int spaceIndex) {
        this.indiceEspacio = spaceIndex;
    }
}
