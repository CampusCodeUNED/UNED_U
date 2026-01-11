program sin_errores_nuevo;
uses crt;

const MAX = 10;
const MENSAJE = 'Programa sin errores';

var i : integer;
var suma : integer;
var resultado : integer;

begin
    i := 1;
    suma := 0;
    resultado := 0;
    
    while i <= MAX do
    begin
        suma := suma + i;
        i := i + 1;
    end;
    
    resultado := suma * 2;
    
    if resultado > 100 then
        begin
            writeln('El resultado es mayor que 100: ', resultado);
        end
    else
        begin
            writeln('El resultado es: ', resultado);
        end;
    
    writeln('Presione una tecla para continuar...');
    readln;
end.