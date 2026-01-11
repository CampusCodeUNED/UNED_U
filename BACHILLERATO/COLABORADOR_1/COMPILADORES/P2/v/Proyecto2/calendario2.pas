program calendari2o2;
{ Este archivo tiene errores intencionales para probar el analizador }
uses crt, dos;

var meses : array[0..11] of integer;    { Error: var antes de const }
var tecla : integer                      { Error: falta punto y coma }
var i : integer;
var dia : integer;
var modulo : integer;
var day : word;
var year : word;
var mes : word;
var dayofweek : word;

const borde = #205#205#205#205#205#205#205#205#205;    { Error: const después de var }
const months       : array[0..11] of integer = (31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
const year_regular : array[0..11] of integer = ( 0,  3,  3,  6,  1,  4,  6,  2,  5,  0,  3,  5);
const year_bisiesto : array[0..11] of integer = ( 0,  3,  4,  0,  2,  5,  0,  3,  6,  1,  4,  6);
const nombres      : array[0..11] of string  = (
    '  Enero   ', ' Febrero  ', '  Marzo   ', '  Abril   ',
    '   Mayo   ', '  Junio   ', '  Julio   ', '  Agosto  ',
    'Septiembre', ' Octubre  ', 'Noviembre ', 'Diciembre ');

begin
{inicio del programa sin espaciado correcto}
    for i:=0 to 11 do     { Error: sintaxis incorrecta }
       begin              { Error: tabulación incorrecta }
            meses[i] := months[i];
        end;              { Error: tabulación no coincide con begin }
    getdate (year, mes , day, dayofweek);
    dec (mes);
    
    { Estructura repeat con error }
    repeat               { Error: falta begin después del repeat }
        if (((year mod 4) = 0) and ((year mod 100) <> 0) or ((year mod 400) = 0) then     { Error: paréntesis desbalanceados }
           begin         { Error: tabulación incorrecta }
                meses[1] := 29;
                modulo := year_bisiesto[mes];
            end
         else           { Error: tabulación incorrecta }
          begin        { Error: tabulación incorrecta }
                meses[1] := 28;
                modulo := year_regular[mes];
           end;          { Error: tabulación no coincide con begin }
        dia := 1 - ((year - 1) mod 7 + ((year - 1) div 4 - (3 * ((year - 1) div 100 + 1)) div 4) mod 7 + modulo + 1) mod 7;
        clrscr;
        writeln ('          '#17, nombres[mes], #16'                       '#30, year, #31);    { Error: cadena no válida }
        writeln (#201, borde, #203, borde, #203, borde, #203, borde, #203, borde, #203, borde, #203, borde, #187);
        write   (#186" Domingo "#186"  Lunes  "#186" Martes  "#186"Mi'#130'rcoles"#186" Jueves  "#186" Viernes ");   { Error: mezcla de comillas }
        writeln (#186" S'#160'bado  "#186);
        writeln (#204, borde, #206, borde, #206, borde, #206, borde, #206, borde, #206, borde, #206, borde, #185);
        
        { For anidado con errores }
        while dia <= meses[mes] do
            begin
                for i:=0 to 6 do     { Error: sintaxis incorrecta }
                 begin              { Error: tabulación incorrecta }
                        if (dia<1) or (dia>meses[mes]) then
                            begin
                                write (#186"         ")   { Error: falta punto y coma }
                            end	
                        else
                           begin
                                write (#186"   ", dia:2, "    ");    { Error: comillas incorrectas }
                            end;	
                        inc (dia);
                  end;              { Error: tabulación no coincide con begin }
                writeln (#186);
                
                { If con errores }
                if dia<=meses[mes] then
                   begin            { Error: tabulación incorrecta }
                        writeln (#204, borde, #206, borde, #206, borde, #206, borde, #206, borde, #206, borde, #206, borde, #185)
                    end	           { Error: falta punto y coma }
               else                { Error: tabulación incorrecta }
                    begin          { Error: tabulación incorrecta }
                        writeln (#200, borde, #202, borde, #202, borde, #202, borde, #202, borde, #202, borde, #202, borde, #188);
                    end; 
            end;
            
        { Mensajes con errores de comillas }
        writeln ("Presione '#27' y '#26' para cambiar de mes.");    { Error: mezcla de comillas }
        writeln ('Presione '#24' y '#25' para cambiar de a'#164'o.');
        writeln ("Presione ESC para salir.");                       { Error: comillas incorrectas }
        
        { Repeat anidado con errores }
        repeat
           begin                    { Error: tabulación incorrecta }
                tecla := ord (readkey);
            end;
        until (tecla=0) or (tecla=27);
        
        { If con errores múltiples }
        if tecla = 0 then
           begin                     { Error: tabulación incorrecta }
                case ord (readkey) of
                    72: inc (year);
                    80: dec (year);
                    77:
                    if mes<11 then   { Error: indentación incorrecta del if anidado }
                       begin         { Error: tabulación incorrecta }
                            inc (mes);
                        end	        { Error: tabulación incorrecta }
                    else             { Error: tabulación incorrecta }
                       begin         { Error: tabulación incorrecta }
                            inc (year);
                            mes := 0;
                        end;
                    75:
                        if mes <> 0 then
                            begin
                                dec (mes);
                            end
                        else
                           begin     { Error: tabulación incorrecta }
                                dec (year);
                                mes := 11;
                            end;
                end;
          end;	                     { Error: tabulación incorrecta }
    end;                            { Error: no corresponde con ningún begin }
    until tecla = 27;
    //comentario al final     { Error: comentario con estilo incorrecto }
end. { Error: comentario después del end. }