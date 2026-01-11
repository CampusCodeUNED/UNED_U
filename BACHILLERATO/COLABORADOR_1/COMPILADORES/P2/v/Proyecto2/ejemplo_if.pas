program ejemplo_if;

uses crt;

var
  numero : integer;
  
begin
  numero := 10;
  
  // Estructura IF-THEN correcta sin ELSE
  if numero > 5 then
    begin
      writeln('El numero es mayor que 5');
      writeln('Esto es un bloque IF correcto');
    end
    
  // Estructura IF-THEN-ELSE correcta
  if numero < 20 then
    begin
      writeln('El numero es menor que 20');
      numero := numero + 5;
    end
  else
    begin
      writeln('El numero es mayor o igual que 20');
      numero := numero - 5;
    end;
    
  // Estructura IF-THEN con errores de tabulación
  if numero = 15 then
   begin
     writeln('El numero es igual a 15')
      numero := 0;
   end
    
  // Estructura IF-THEN-ELSE con errores de puntuación
  if numero = 0 then
    begin
      writeln('El numero es cero');
    end
  else
    begin
      writeln('El numero no es cero')
    end;
    
  // Estructura IF-THEN sin sentencias (bloque vacío)
  if numero < 0 then
    begin
    end
    
  writeln('Programa finalizado');
end.