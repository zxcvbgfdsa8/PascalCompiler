PROGRAM hello (output);

{Write 'Hello, world.' ten times.}

VAR 
	i : integer;
	
begin {hello}
	FOR i := 1 TO 10 DO BEGIN
		writeln('Hello World');
	END;
END{hello}.