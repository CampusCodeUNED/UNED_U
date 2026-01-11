program test_token;

var
  x : integer;
  
begin
  x := 5;
  
  { IF sin THEN - debe dar Error 911 }
  if x > 0 
    writeln('Positivo');
  
  { FOR sin espacios en := - debe dar Error 900 }
  for i:=1 to 10 do
    writeln('Iteracion');
    
  { REPEAT sin UNTIL - debe dar Error 922 }
  repeat
    writeln('Repetir');
  
end.
