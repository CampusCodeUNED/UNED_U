package PascalAnalizador.Proyecto2;

public class Token {
    private TipoToken tipo;
    private String valor;
    private int linea;
    private int columna;
    private boolean tieneEspacioAntes;
    private boolean tieneEspacioDespues;

    public Token(TipoToken tipo, String valor, int linea, int columna) {
        this.tipo = tipo;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
        this.tieneEspacioAntes = false;
        this.tieneEspacioDespues = false;
    }

    public TipoToken getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }

    public boolean tieneEspacioAntes() {
        return tieneEspacioAntes;
    }

    public void setTieneEspacioAntes(boolean tieneEspacioAntes) {
        this.tieneEspacioAntes = tieneEspacioAntes;
    }

    public boolean tieneEspacioDespues() {
        return tieneEspacioDespues;
    }

    public void setTieneEspacioDespues(boolean tieneEspacioDespues) {
        this.tieneEspacioDespues = tieneEspacioDespues;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tipo=" + tipo +
                ", valor='" + valor + '\'' +
                ", linea=" + linea +
                ", columna=" + columna +
                ", espacioAntes=" + tieneEspacioAntes +
                ", espacioDespues=" + tieneEspacioDespues +
                '}';
    }
}