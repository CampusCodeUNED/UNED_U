program demo2;
uses crt;

const
  MAX_VALOR = 10;
  MENSAJE = 'Prueba de validadores';

var
  i : integer;
  j : integer;
  contador : integer;
  opcion : integer;
  resultado : boolean;

begin
  { Prueba de bucles FOR }
  writeln('Iniciando pruebas de bucles FOR:');
  
  { FOR correcto }
  for i := 1 to MAX_VALOR do
  begin
    writeln('Iteración: ', i);
  end;
  
  { FOR con error de tabulación }
  for j := 1 to 5 do
   begin
      writeln('Tabulación incorrecta');
  end;
  
  { FOR sin contenido }
  for contador := 10 downto 1 do
  begin
  end;
  
  { Prueba de estructuras IF }
  writeln('Iniciando pruebas de estructuras IF:');
  
  { IF correcto }
  if i > 5 then
  begin
    writeln('i es mayor que 5');
  end;
  
  { IF-ELSE correcto }
  if j < 3 then
  begin
    writeln('j es menor que 3');
  end
  else
  begin
    writeln('j es mayor o igual a 3');
  end;
  
  { IF con error de tabulación }
  if opcion = 1 then
     begin
      writeln('Opción 1 seleccionada');
    end;
  
  { IF-ELSE con errores de tabulación }
  if opcion = 2 then
  begin
    writeln('Opción 2');
  end
 else
    begin
    writeln('No es opción 2');
  end;
  
  { Prueba de estructuras REPEAT }
  writeln('Iniciando pruebas de estructuras REPEAT:');
  
  { REPEAT correcto }
  contador := 0;
  repeat
    begin
      contador := contador + 1;
      writeln('Contador: ', contador);
    end;
  until contador >= 5;
  
  { REPEAT con error de tabulación }
  j := 10;
  repeat
   begin
    j := j - 1;
    writeln('j = ', j);
   end;
  until j <= 0;
  
  { REPEAT sin punto y coma }
  i := 0;
  repeat
    begin
      i := i + 2;
      writeln('i = ', i);
    end
  until i > 10
  
  { Prueba de estructuras anidadas }
  writeln('Iniciando pruebas de estructuras anidadas:');
  
  { FOR con IF anidado }
  for i := 1 to 3 do
  begin
    if i mod 2 = 0 then
    begin
      writeln('i es par: ', i);
    end
    else
    begin
      writeln('i es impar: ', i);
    end;
  end;
  
  { REPEAT con FOR anidado }
  contador := 0;
  repeat
    begin
      contador := contador + 1;
      for j := 1 to contador do
      begin
        write(j, ' ');
      end;
      writeln;
    end;
  until contador >= 3;
  
  writeln('Fin de las pruebas');
end.
