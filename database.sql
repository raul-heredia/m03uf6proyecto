CREATE DATABASE m03uf6proyecto;
USE m03uf6proyecto;
CREATE TABLE clientes(
    dni VARCHAR(9) NOT NULL PRIMARY KEY,
    nombreCompleto VARCHAR(100) NOT NULL,
    fechaNacimiento DATE NOT NULL,
    telefono VARCHAR(9) NOT NULL,
    direccion VARCHAR(30) NOT NULL,
    ciudad VARCHAR(30) NOT NULL,
    pais VARCHAR(30) NOT NULL,
    email VARCHAR(30) NOT NULL,
    puntosCarnet INT NOT NULL
);
CREATE TABLE coches(
    matricula VARCHAR(7) NOT NULL PRIMARY KEY,
    numeroBastidor VARCHAR(17) NOT NULL,
    marca VARCHAR(25) NOT NULL,
    modelo VARCHAR(25) NOT NULL,
    añoFabricacion INT NOT NULL,
    color VARCHAR(20) NOT NULL,
    numeroPlazas INT NOT NULL,
    numeroPuertas INT NOT NULL,
    grandariaMaletero INT NOT NULL,
    combustible ENUM('Gasolina','Diésel') NOT NULL
);
CREATE TABLE alquilerCoches(
    matricula VARCHAR(7),
    dni VARCHAR (9),
    fechaInicio DATE,
    fechaFinal DATE,
    precioPorDia DOUBLE,
    lugarDevolucion ENUM('Barcelona','Madrid','Sevilla','Zaragoza','Santander','Tarragona'),
    isRetornDipositPle TINYINT,
    tipoSeguro ENUM('Franquícia','Sin Franquícia'),
    PRIMARY KEY (matricula, dni),
    FOREIGN KEY (matricula) REFERENCES coches(matricula) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (dni) REFERENCES clientes(dni) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE mecanicos(
    dni VARCHAR(9) NOT NULL PRIMARY KEY,
    nombreCompleto VARCHAR(100) NOT NULL,
    fechaNacimiento DATE NOT NULL,
    telefono VARCHAR(9) NOT NULL,
    direccion VARCHAR(30) NOT NULL,
    ciudad VARCHAR(30) NOT NULL,
    pais VARCHAR(30) NOT NULL,
    email VARCHAR(30) NOT NULL,
    puntosCarnet INT NOT NULL,
    salario DOUBLE NOT NULL,
    numSS INT NOT NULL,
    titulacion VARCHAR(30) NOT NULL,
    fechaContratacion DATE NOT NULL
);

CREATE TABLE mantenimientoCoches(
    matricula VARCHAR(7),
    dni VARCHAR (9),
    fechaInMantenimiento DATE,
    fechaFiMantenimiento DATE,
    PRIMARY KEY (matricula, dni),
    FOREIGN KEY (matricula) REFERENCES coches(matricula) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (dni) REFERENCES mecanicos(dni) ON DELETE CASCADE ON UPDATE CASCADE
);