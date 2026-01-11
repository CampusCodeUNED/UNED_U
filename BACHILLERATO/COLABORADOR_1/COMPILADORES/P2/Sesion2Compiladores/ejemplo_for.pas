program ejemplo_for;

uses
  crt;

var
  i : integer;
  j : integer;
  suma : integer;
  
const
  MAX = 10;
  
begin
  suma := 0;
  
  { Bucle for correcto }
  for i := 1 to MAX do
  begin
    suma := suma + i;
    writeln('Suma parcial: ', suma);
  end;
  
  { Bucle for con errores }
  for z := 1 to 'diez' do
  begin
   writeln('Este bucle tiene errores');
    end;
  
  { Bucle for sin sentencias }
  for j := 5 to 10 do
  begin
  end;
  
  writeln('Suma total: ', suma);
end.
