import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Coches {
    int añoFabricacion, numeroPlazas, numeroPuertas,grandariaMaletero;
    String matricula,numeroBastidor,marca,modelo,color,combustible;
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

    public static void listarCoches(){
        try{
            Connection conexion = (Connection) Conexion.conectarBd();
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery("SELECT * FROM coches");
            System.out.println("---- Listado de Vehiculos ----");
            TableList tabla = new TableList(9,"Matrícula","Nº de Bastidor","Marca y Modelo","Año de Fabricación","Color",
                    "Nº de Plazas","Nº de Puertas","Grandaria Maletero","Tipo de Combustible").sortBy(0).withUnicode(true);
            while(resultado.next()){
                //Sacamos los resultados
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
            TableList tabla = new TableList(9,"Matrícula","Nº de Bastidor","Marca y Modelo","Año de Fabricación","Color",
                    "Nº de Plazas","Nº de Puertas","Grandaria Maletero","Tipo de Combustible").sortBy(0).withUnicode(true);
            String matricula;
            String consulta = "SELECT * FROM coches where matricula = ?";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce la Matrícula del Coche a buscar: ");
            matricula = scanner.next();
            Coches c = new Coches(matricula);
            sentencia.setString(1, c.getMatricula());
            ResultSet resultado = sentencia.executeQuery();
            while(resultado.next()){
                //Sacamos los resultados
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

    public static void listarPorAsientos(){
        try{
            Connection conexion = (Connection) Conexion.conectarBd();
            TableList tabla = new TableList(9,"Matrícula","Nº de Bastidor","Marca y Modelo","Año de Fabricación","Color",
                    "Nº de Plazas","Nº de Puertas","Grandaria Maletero","Tipo de Combustible").sortBy(0).withUnicode(true);
            int asientos;
            String consulta = "SELECT * FROM coches where numeroPlazas = ?";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce el Nº de Asientos a Filtrar [2 / 5]: ");
            try{
                asientos = scanner.nextInt();
            }catch(Exception e){
                asientos = 0;
            }
            sentencia.setInt(1, asientos);
            ResultSet resultado = sentencia.executeQuery();
            while(resultado.next()){
                //Sacamos los resultados
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
