import com.mysql.cj.xdevapi.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Coches {
    private int añoFabricacion, numeroPlazas, numeroPuertas,grandariaMaletero;
    private String matricula,numeroBastidor,marca,modelo,color,combustible;
    private static final Scanner scanner = new Scanner(System.in).useDelimiter("\n");

    public Coches(String matricula) {
        this.matricula = matricula;
    }

    public Coches(String matricula, String numeroBastidor, String marca, String modelo, int añoFabricacion, String color, int numeroPlazas, int numeroPuertas, int grandariaMaletero, String combustible) {
        this.añoFabricacion = añoFabricacion;
        this.numeroPlazas = numeroPlazas;
        this.numeroPuertas = numeroPuertas;
        this.grandariaMaletero = grandariaMaletero;
        this.matricula = matricula;
        this.numeroBastidor = numeroBastidor;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.combustible = combustible;
    }

    public static TableList table(){
        return new TableList(9,"Matrícula","Nº de Bastidor","Marca y Modelo","Año de Fabricación","Color",
                "Nº de Plazas","Nº de Puertas","Grandaria Maletero","Tipo de Combustible").sortBy(0).withUnicode(true);
    }

    public static void listarCoches(){
        try{
            Connection conexion = (Connection) Conexion.conectarBd();
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery("SELECT * FROM coches");
            System.out.println("---- Listado de Vehiculos ----");
            TableList tabla = table();
            while(resultado.next()){
                tabla.addRow(resultado.getString("matricula"), resultado.getString("numeroBastidor"),
                        resultado.getString("marca") + " " +  resultado.getString("modelo"),
                        resultado.getString("añoFabricacion"), resultado.getString("color"),
                        resultado.getString("numeroPlazas"),resultado.getString("numeroPuertas"),
                        resultado.getString("grandariaMaletero") + "L",resultado.getString("combustible"));
            }
            tabla.print();
            conexion.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public static void listarUno(){
        try{
            Connection conexion = (Connection) Conexion.conectarBd();
            String matricula;
            String consulta = "SELECT * FROM coches where matricula = ?";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce la Matrícula del Coche a buscar: ");
            matricula = scanner.next();
            Coches c = new Coches(matricula);
            sentencia.setString(1, c.getMatricula());
            ResultSet resultado = sentencia.executeQuery();
            System.out.println(resultado);
            if (resultado.next() == false){
                System.out.println("Error, No existe ningún coche con Matrícula: " + matricula);
                return;
            }
            TableList tabla = table();
                tabla.addRow(resultado.getString("matricula"), resultado.getString("numeroBastidor"),
                        resultado.getString("marca") + " " +  resultado.getString("modelo"),
                        resultado.getString("añoFabricacion"), resultado.getString("color"),
                        resultado.getString("numeroPlazas"),resultado.getString("numeroPuertas"),
                        resultado.getString("grandariaMaletero") + "L",resultado.getString("combustible"));

            tabla.print();
            conexion.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void listarPorAsientos(){
        try{
            Connection conexion = (Connection) Conexion.conectarBd();
            int asientos;
            String consulta = "SELECT * FROM coches where numeroPlazas = ?";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce el Nº de Asientos a Filtrar [2 / 5]: ");
            try{
                asientos = scanner.nextInt();
            }catch(Exception e){
                asientos = 0;
                scanner.next();
            }
            sentencia.setInt(1, asientos);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next() == false){
                System.out.println("No tenemos ningún vehiculo de " + asientos + " plazas disponible");
                return;
            }
            TableList tabla = table();
            while(resultado.next()){
                tabla.addRow(resultado.getString("matricula"), resultado.getString("numeroBastidor"),
                        resultado.getString("marca") + " " +  resultado.getString("modelo"),
                        resultado.getString("añoFabricacion"), resultado.getString("color"),
                        resultado.getString("numeroPlazas"),resultado.getString("numeroPuertas"),
                        resultado.getString("grandariaMaletero") + "L",resultado.getString("combustible"));
            }
            tabla.print();
            conexion.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void insertarRegistro(){
        try{
            String matricula, numeroBastidor, marca, modelo, color, combustible, añoFabricacion,  numeroPlazas,  numeroPuertas,  grandariaMaletero;
            Connection conexion = (Connection) Conexion.conectarBd();
            String consulta = "insert into coches (matricula,numeroBastidor,marca,modelo,añoFabricacion,color,numeroPlazas,numeroPuertas,grandariaMaletero,combustible) values(?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce la Matrícula: ");
            matricula = scanner.next();
            System.out.printf("Introduce el Nº de Bastidor [17 Caracteres]: ");
            numeroBastidor = scanner.next();
            System.out.printf("Introduce la Marca del Vehículo: ");
            marca = scanner.next();
            System.out.printf("Introduce el Modelo del Vehículo: ");
            modelo = scanner.next();
            System.out.printf("Introduce el Año de Fabricación [Formato: YYYY]: ");
            añoFabricacion = scanner.next();
            System.out.printf("Introduce el Color del vehículo: ");
            color = scanner.next();
            System.out.printf("Numero de plazas del vehículo: ");
            numeroPlazas = scanner.next();

            System.out.printf("Numero de Puertas del vehículo: ");
            numeroPuertas = scanner.next();
            System.out.printf("Grandaria del Maletero: ");
            grandariaMaletero = scanner.next();
            TableList tablaCombustible = new TableList(2,"Valor", "Tipo de Combustible").sortBy(0).withUnicode(true);
            tablaCombustible.addRow("1","Gasolina");
            tablaCombustible.addRow("2","Diésel");
            System.out.println("Inserta el combustible: ");
            tablaCombustible.print();
            System.out.printf("Selecciona una opción: ");
            combustible = scanner.next();
            switch(combustible){
                case "1":
                    combustible = "Gasolina";
                    break;
                case "2":
                    combustible = "Diésel";
                    break;
                default:
                    System.out.println("Error no has introducido una opción válida.");
                    System.out.println("Se ha aplicado el valor Gasolina por defecto.");
                    combustible = "Gasolina";
                    break;
            }
            try{
                Coches c = new Coches(matricula,numeroBastidor,marca,modelo,Integer.parseInt(añoFabricacion),color,Integer.parseInt(numeroPlazas)
                        ,Integer.parseInt(numeroPuertas),Integer.parseInt(grandariaMaletero),combustible);
                sentencia.setString(1, c.getMatricula());
                sentencia.setString(2, c.getNumeroBastidor());
                sentencia.setString(3, c.getMarca());
                sentencia.setString(4, c.getModelo());
                sentencia.setInt(5, c.getAñoFabricacion());
                sentencia.setString(6, c.getColor());
                sentencia.setInt(7, c.getNumeroPlazas());
                sentencia.setInt(8, c.getNumeroPuertas());
                sentencia.setInt(9, c.getGrandariaMaletero());
                sentencia.setString(10, c.getCombustible());
                int row = sentencia.executeUpdate();
                System.out.println("Se ha insertado el vehículo correctamente");
            }catch(Exception e){
                System.out.println("Error, no se ha podido instanciar el vehículo, alguno de los datos introducidos era .");
            }
            conexion.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void modificarRegistro(){
        try{
            String matricula, numeroBastidor, marca, modelo, color, combustible, añoFabricacion,  numeroPlazas,  numeroPuertas,  grandariaMaletero;
            String numeroBastidorMod, marcaMod, modeloMod, colorMod, combustibleMod, añoFabricacionMod,  numeroPlazasMod,  numeroPuertasMod,  grandariaMaleteroMod;

            Connection conexion = (Connection) Conexion.conectarBd();
            String preConsulta = "SELECT * FROM coches WHERE matricula = ?";
            String consulta = "UPDATE coches SET numeroBastidor = ?, marca = ?, modelo = ?," +
                    " añoFabricacion = ?, color = ?, numeroPlazas = ?, numeroPuertas = ?, grandariaMaletero = ?," +
                    " combustible = ? WHERE matricula = ?";

            PreparedStatement preSentencia = conexion.prepareStatement(preConsulta);
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce la matrícula del coche: ");
            matricula = scanner.next();
            preSentencia.setString(1, matricula);
            ResultSet resultado = preSentencia.executeQuery();

            if (resultado.next() == false){
                System.out.println("Error, No existe ningún vehículo con matrícula: " + matricula);
                return;
            }
            numeroBastidor = resultado.getString("numeroBastidor");
            marca = resultado.getString("marca");
            modelo = resultado.getString("modelo");
            color = resultado.getString("color");
            combustible = resultado.getString("combustible");
            añoFabricacion = resultado.getString("añoFabricacion");
            numeroPlazas = resultado.getString("numeroPlazas");
            numeroPuertas = resultado.getString("numeroPuertas");
            grandariaMaletero = resultado.getString("grandariaMaletero");

            System.out.println("---- DATOS DEL VEHÍCULO ANTES DE MODIFICAR ----");
            TableList tablaAntes = table();
            tablaAntes.addRow(matricula, numeroBastidor, marca + " " + modelo, añoFabricacion,color, numeroPlazas, numeroPuertas, grandariaMaletero + "L",combustible);
            tablaAntes.print();
            main.pause();

            System.out.printf("Introduce el Nº de Bastidor [Deja en blanco para no modificar]: ");
            numeroBastidorMod = scanner.next();
            if (numeroBastidorMod.isEmpty()) numeroBastidorMod = numeroBastidor; // Si esta vacio le aplicamos el que estaba guardado
            System.out.printf("Introduce la Marca del Vehículo [Deja en blanco para no modificar]: ");
            marcaMod = scanner.next();
            if (marcaMod.isEmpty()) marcaMod = marca; // Si esta vacio le aplicamos el que estaba guardado
            System.out.printf("Introduce el Modelo del Vehículo [Deja en blanco para no modificar]: ");
            modeloMod = scanner.next();
            if (modeloMod.isEmpty()) modeloMod = modelo; // Si esta vacio le aplicamos el que estaba guardado
            System.out.printf("Introduce el Año de Fabricación [Formato: YYYY] [Deja en blanco para no modificar]: ");
            añoFabricacionMod = scanner.next();
            if (añoFabricacionMod.isEmpty()) añoFabricacionMod = añoFabricacion; // Si esta vacio le aplicamos el que estaba guardado
            System.out.printf("Introduce el Color del vehículo [Deja en blanco para no modificar]: ");
            colorMod = scanner.next();
            if (colorMod.isEmpty()) colorMod = color; // Si esta vacio le aplicamos el que estaba guardado
            System.out.printf("Numero de plazas del vehículo [Deja en blanco para no modificar]: ");
            numeroPlazasMod = scanner.next();
            if (numeroPlazasMod.isEmpty()) numeroPlazasMod = numeroPlazas; // Si esta vacio le aplicamos el que estaba guardado
            System.out.printf("Numero de Puertas del vehículo [Deja en blanco para no modificar]: ");
            numeroPuertasMod = scanner.next();
            if (numeroPuertasMod.isEmpty()) numeroPuertasMod = numeroPuertas; // Si esta vacio le aplicamos el que estaba guardado
            System.out.printf("Grandaria del Maletero [Deja en blanco para no modificar]: ");
            grandariaMaleteroMod = scanner.next();
            if (grandariaMaleteroMod.isEmpty()) grandariaMaleteroMod = grandariaMaletero;
            TableList tablaCombustible = new TableList(2,"Valor", "Tipo de Combustible").sortBy(0).withUnicode(true);
            tablaCombustible.addRow("1","Gasolina");
            tablaCombustible.addRow("2","Diésel");
            System.out.println("Inserta el combustible: ");
            tablaCombustible.print();
            System.out.printf("Selecciona una opción [Deja en blanco para no modificar]: ");
            combustibleMod = scanner.next();
            switch(combustibleMod){
                case "1":
                    combustibleMod = "Gasolina";
                    break;
                case "2":
                    combustibleMod = "Diésel";
                    break;
                default:
                    combustibleMod = combustible;
                    break;
            }
            try{
                Coches c = new Coches(matricula,numeroBastidorMod,marcaMod,modeloMod,Integer.parseInt(añoFabricacionMod),colorMod,Integer.parseInt(numeroPlazasMod)
                        ,Integer.parseInt(numeroPuertasMod),Integer.parseInt(grandariaMaleteroMod),combustibleMod);
                sentencia.setString(1, c.getNumeroBastidor());
                sentencia.setString(2, c.getMarca());
                sentencia.setString(3, c.getModelo());
                sentencia.setInt(4, c.getAñoFabricacion());
                sentencia.setString(5, c.getColor());
                sentencia.setInt(6, c.getNumeroPlazas());
                sentencia.setInt(7, c.getNumeroPuertas());
                sentencia.setInt(8, c.getGrandariaMaletero());
                sentencia.setString(9, c.getCombustible());
                sentencia.setString(10, c.getMatricula());
                int row = sentencia.executeUpdate();
                System.out.println("Se ha actualizado el registro correctamente");
            }catch(Exception e){
                System.out.println("Error, no se ha podido actualizar el vehículo, alguno de los datos introducidos era .");
            }
            conexion.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void eliminarRegistro(){
        try{
            String matricula;
            Connection conexion = (Connection) Conexion.conectarBd();
            String consulta = "DELETE FROM coches where matricula = ?";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce la Matrícula: ");
            matricula = scanner.next();
            Coches c = new Coches(matricula);
            sentencia.setString(1, c.getMatricula());
            int row = sentencia.executeUpdate();
            System.out.println("Registro eliminado correctamente");
            conexion.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public int getAñoFabricacion() {
        return añoFabricacion;
    }

    public void setAñoFabricacion(int añoFabricacion) {
        this.añoFabricacion = añoFabricacion;
    }

    public int getNumeroPlazas() {
        return numeroPlazas;
    }

    public void setNumeroPlazas(int numeroPlazas) {
        this.numeroPlazas = numeroPlazas;
    }

    public int getNumeroPuertas() {
        return numeroPuertas;
    }

    public void setNumeroPuertas(int numeroPuertas) {
        this.numeroPuertas = numeroPuertas;
    }

    public int getGrandariaMaletero() {
        return grandariaMaletero;
    }

    public void setGrandariaMaletero(int grandariaMaletero) {
        this.grandariaMaletero = grandariaMaletero;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNumeroBastidor() {
        return numeroBastidor;
    }

    public void setNumeroBastidor(String numeroBastidor) {
        this.numeroBastidor = numeroBastidor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }
}
