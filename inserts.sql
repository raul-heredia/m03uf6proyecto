use m03uf6proyecto;
# Clientes
insert into clientes (dni,nombreCompleto,fechaNacimiento,telefono,direccion,ciudad,pais,email,puntosCarnet) values("21761046X","Raúl Heredia Maza","2001-10-02","629623372","C/ Mallorca 654","Barcelona","España","raul.heredia@hotmail.com",12);
insert into clientes (dni,nombreCompleto,fechaNacimiento,telefono,direccion,ciudad,pais,email,puntosCarnet) values("92681529Q","Marc Carbonell Sariola","1999-03-28","665135129","C/ Pixapins 33","Barcelona","España","marc.carbonell@hotmail.com",8);
insert into clientes (dni,nombreCompleto,fechaNacimiento,telefono,direccion,ciudad,pais,email,puntosCarnet) values("95360384S","Felipe Meseguer Barrena","1969-05-12","654223795","C/ Mario Bros 43","Zaragoza","España","felipe.meseguer@gmail.com",15);
insert into clientes (dni,nombreCompleto,fechaNacimiento,telefono,direccion,ciudad,pais,email,puntosCarnet) values("29489507B","Marta González Ruiz","1993-06-01","644521398","Av/ De Madrid 12","Madrid","España","marta.goru@outlook.com",10);
insert into clientes (dni,nombreCompleto,fechaNacimiento,telefono,direccion,ciudad,pais,email,puntosCarnet) values("15206819R","Judith Pérez Alonso","1989-01-06","639781245","Av/ Real Betis 10","Sevilla","España","jupeal@yahoo.com",11);
insert into clientes (dni,nombreCompleto,fechaNacimiento,telefono,direccion,ciudad,pais,email,puntosCarnet) values("36753465V","Pol González Sainz","2001-02-10","662745263","Av/ Barcelona 17","Tarragona","España","pol.gonzalez@gmail.com",12);
# Coches
insert into coches (matricula,numeroBastidor,marca,modelo,añoFabricacion,color,numeroPlazas,numeroPuertas,grandariaMaletero,combustible) values("B8563UL","F7RCPWEWJ4PVYW96T","Chevrolet","Corvette Stingray",1967,"Rojo","2","3",250,"Gasolina");
insert into coches (matricula,numeroBastidor,marca,modelo,añoFabricacion,color,numeroPlazas,numeroPuertas,grandariaMaletero,combustible) values("M2769ZP","AHF3TMEQCHGUMVU41","Chevrolet","Camaro ZL1",1969,"Amarillo","2","3",200,"Gasolina");
insert into coches (matricula,numeroBastidor,marca,modelo,añoFabricacion,color,numeroPlazas,numeroPuertas,grandariaMaletero,combustible) values("B2254CL","13IL97RGTUBHFKH4Y","Ferrari","Testarossa",1990,"Rojo","2","3",110,"Gasolina");
insert into coches (matricula,numeroBastidor,marca,modelo,añoFabricacion,color,numeroPlazas,numeroPuertas,grandariaMaletero,combustible) values("6178CBY","WXEKGUITVOKH0FAXK","Ford","Mustang Fastback",1966,"Negro",4,3,220,"Gasolina");
insert into coches (matricula,numeroBastidor,marca,modelo,añoFabricacion,color,numeroPlazas,numeroPuertas,grandariaMaletero,combustible) values("M5512DS","TENZ6KL0CDH7P01KD","Chevrolet","Bel Air",1957,"Azul Turquesa",4,5,280,"Gasolina");
insert into coches (matricula,numeroBastidor,marca,modelo,añoFabricacion,color,numeroPlazas,numeroPuertas,grandariaMaletero,combustible) values("B1123CS","WC5ARH4KUORX7K64T","Mercedes","300SL",1955,"Gris",2,3,180,"Gasolina");
insert into coches (matricula,numeroBastidor,marca,modelo,añoFabricacion,color,numeroPlazas,numeroPuertas,grandariaMaletero,combustible) values("B8956LM","MZ05RMROFZJR4VIDM","BMW","M30 E30",1988,"Negro",4,3,210,"Gasolina");
insert into coches (matricula,numeroBastidor,marca,modelo,añoFabricacion,color,numeroPlazas,numeroPuertas,grandariaMaletero,combustible) values("M4563ZZ","28TKPI9UM42V45IK2","SEAT","600D",1965,"Rojo",4,3,125,"Gasolina");
insert into coches (matricula,numeroBastidor,marca,modelo,añoFabricacion,color,numeroPlazas,numeroPuertas,grandariaMaletero,combustible) values("B1245CC","B9N7VXF5Z040CH2IY","Mercedes","190 E 2.5-16 Evo II",1990,"Negro",4,5,200,"Gasolina");
insert into coches (matricula,numeroBastidor,marca,modelo,añoFabricacion,color,numeroPlazas,numeroPuertas,grandariaMaletero,combustible) values("M0000FG","AG6G99WISORCSLLZQ","Porsche","911 Turbo",1988,"Blanco",2,3,150,"Gasolina");
insert into coches (matricula,numeroBastidor,marca,modelo,añoFabricacion,color,numeroPlazas,numeroPuertas,grandariaMaletero,combustible) values("B8452LD","5CXGOYEAU8X6GHO76","Lamborghini","Murcielago",2002,"Verde Lima",2,3,140,"Gasolina");

# alquilerCoches
insert into alquilerCoches (matricula,dni,fechaInicio,fechaFinal,precioPorDia,lugarDevolucion,isRetornDipositPle,tipoSeguro) values("B8452LD","21761046X","2022-03-26","2022-04-02",85,"Barcelona",0,"Franquícia");
insert into alquilerCoches (matricula,dni,fechaInicio,fechaFinal,precioPorDia,lugarDevolucion,isRetornDipositPle,tipoSeguro) values("B2254CL","92681529Q","2022-03-26","2022-03-31",92,"Barcelona",0,"Franquícia");
insert into alquilerCoches (matricula,dni,fechaInicio,fechaFinal,precioPorDia,lugarDevolucion,isRetornDipositPle,tipoSeguro) values("M2769ZP","15206819R","2022-03-26","2022-03-27",55,"Barcelona",0,"Sin Franquícia");

# mecanicos
insert into mecanicos (dni,nombreCompleto,fechaNacimiento,telefono,direccion,ciudad,pais,email,puntosCarnet,salario,numSS,titulacion,fechaContratacion) values("25256867S","Paco Sanz Bullo","1969-07-12","662113365","C/ Valencia 680","Barcelona","España","paco.sanz@gmail.com",15,1899.12,123785964212,"CFGS Automoción","1990-04-12");	

# mantenimientoCoches
insert into mantenimientoCoches (matricula,dni,fechaInMantenimiento,fechaFiMantenimiento) values("M0000FG","25256867S","2022-01-01","2022-01-05");	
