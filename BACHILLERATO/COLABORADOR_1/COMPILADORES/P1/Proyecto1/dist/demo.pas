program test_var_listas_y_espacios;
uses crt;

// --- Listas correctas
var a, b, c : integer;               // OK
var nombre, apellido : string;       // OK
var x, y, z : word;                  // OK

// --- Lista con errores
var a,,b : integer;                  // ERROR: identificador vacío (doble coma)
var sin_espacio:integer;             // ERROR: falta espacio antes y después de ':'
var sin_tipo : ;                     // ERROR: falta tipo
var tipo_malo : real;                // ERROR: tipo no permitido
var reservado, begin : integer;      // ERROR: 'begin' es reservada

begin
  writeln(a, b, c);                  // OK
  writeln(nombre, ' ', apellido);    // OK
  writeln(x, y, z);                  // OK

  writeln(sin_espacio);              // ERROR: no declarado
  writeln(sin_tipo);                 // ERROR: no declarado
  writeln(tipo_malo);                // ERROR: no declarado
  writeln(begin);                    // ERROR: no declarado (además reservada)

end.
