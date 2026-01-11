program ejemplo_repeat;

uses crt;

var
  contador : integer;
  
begin
  contador := 0;
  
  // Estructura REPEAT correcta 
  repeat
    begin
      contador := contador + 1;
      writeln('Contador: ', contador);
    end;
  until contador >= 5;
  
  // Estructura REPEAT con errores de tabulación 
  repeat
   begin
    contador := contador - 1;
    writeln('Contador: ', contador);
   end;
  until contador <= 0;
  
  // Estructura REPEAT con errores de punto y coma
  repeat
    begin
      contador := 10;
      writeln('Valor final: ', contador);
    end
  until contador = 10;
  
  writeln('Programa finalizado.');
end.