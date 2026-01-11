package PascalAnalizador.Proyecto2;

public enum TipoToken {
    // Estructuras de control específicas del Proyecto 2
    FOR, TO, DO,      // Para el bucle FOR
    IF, THEN, ELSE,   // Para la estructura IF
    REPEAT, UNTIL,    // Para el bucle REPEAT
    
    // Operadores necesarios para las estructuras de control
    ASSIGN(":="),     // Asignación
    EQUAL("="),       // Igual
    NOT_EQUAL("<>"),  // Diferente
    LESS("<"),        // Menor que
    GREATER(">"),     // Mayor que
    LESS_EQUAL("<="), // Menor o igual que
    GREATER_EQUAL(">="), // Mayor o igual que
    
    // Delimitadores básicos
    SEMICOLON(";"),
    BEGIN("begin"),
    END("end"),
    
    // Otros tokens necesarios
    IDENTIFIER,       // Identificadores
    INTEGER_LITERAL,  // Números enteros
    ERROR;           // Token de error

    private final String simbolo;

    TipoToken() {
        this.simbolo = null;
    }

    TipoToken(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getSimbolo() {
        return simbolo;
    }
}