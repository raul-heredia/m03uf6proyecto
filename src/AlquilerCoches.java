import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AlquilerCoches {
    private String matricula, dni, lugarDevolucion, tipoSeguro, fechaInico, fechaFinal;
    private double precioPorDia;
    private int isRetornDipositPle;
    private static final SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
    private static final Scanner scanner = new Scanner(System.in).useDelimiter("\n");


    public AlquilerCoches(String matricula, String dni) {
        this.matricula = matricula;
        this.dni = dni;
    }

    public AlquilerCoches(String matricula, String dni, String lugarDevolucion, String tipoSeguro, int numeroDias,double precioPorDia, int isRetornDipositPle) {
        this.matricula = matricula;
        this.dni = dni;
        this.lugarDevolucion = lugarDevolucion;
        this.tipoSeguro = tipoSeguro;
        this.precioPorDia = precioPorDia;
        this.isRetornDipositPle = isRetornDipositPle;
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE, numeroDias);
        this.fechaInico = sm.format(today);
        this.fechaFinal = sm.format(c.getTime());
    }

    public static TableList table(){
        return new TableList(10,"Matrícula","Marca y Modelo","DNI","Nombre Completo Cliente","Fecha Inicio Alquiler",
                "Fecha Fin Alquiler","Precio Por Día","Lugar de Devolución","Retorno Deposito Lleno","Tipo de Seguro").sortBy(0).withUnicode(true);
    }

    public static void listarAlquileres(){
        try{
            Connection conexion = (Connection) Conexion.conectarBd();
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery("SELECT alquilercoches.matricula, alquilercoches.dni, alquilercoches.fechaInicio," +
                    "alquilercoches.fechaFinal,alquilercoches.precioPorDia,alquilercoches.lugarDevolucion,alquilercoches.isRetornDipositPle," +
                    "alquilercoches.tipoSeguro, clientes.nombreCompleto, coches.marca, coches.modelo FROM alquilercoches " +
                    "INNER JOIN clientes ON alquilerCoches.dni = clientes.dni INNER JOIN coches ON alquilerCoches.matricula = coches.matricula");
            System.out.println("---- Listado de Vehiculos Alquilados ----");
            TableList tabla = table();
            while(resultado.next()){
                String isDeposito = "No";
                if(resultado.getString("isRetornDipositPle").equals("1")){
                    isDeposito = "Sí";
                }
                tabla.addRow(resultado.getString("matricula"),resultado.getString("marca") + " " +
                        resultado.getString("modelo") , resultado.getString("dni"),
                        resultado.getString("nombreCompleto"),resultado.getString("fechaInicio"),
                        resultado.getString("fechaFInal"),resultado.getString("precioPorDia") + "€",
                        resultado.getString("lugarDevolucion"),isDeposito,resultado.getString("tipoSeguro"));
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
            String matricula, dni;
            String consulta = "SELECT alquilercoches.matricula, alquilercoches.dni, alquilercoches.fechaInicio," +
            "alquilercoches.fechaFinal,alquilercoches.precioPorDia,alquilercoches.lugarDevolucion,alquilercoches.isRetornDipositPle," +
                    "alquilercoches.tipoSeguro, clientes.nombreCompleto, coches.marca, coches.modelo FROM alquilercoches " +
                    "INNER JOIN clientes ON alquilerCoches.dni = clientes.dni INNER JOIN coches ON alquilerCoches.matricula = coches.matricula " +
                    "WHERE alquilercoches.matricula = ? AND alquilercoches.dni = ?";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce la matrícula del coche alquilado: ");
            matricula = scanner.next();
            System.out.printf("Introduce el DNI del Cliente que ha realizado el alquiler: ");
            dni = scanner.next();
            AlquilerCoches a = new AlquilerCoches(matricula, dni);
            sentencia.setString(1, a.getMatricula());
            sentencia.setString(2, a.getDni());
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next() == false){
                System.out.println("Error, No hemos encontrado ningún registro con los datos seleccionados");
                return;
            }
            TableList tabla = table();
                String isDeposito = "No";
                if(resultado.getString("isRetornDipositPle").equals("1")){
                    isDeposito = "Sí";
                }
                tabla.addRow(resultado.getString("matricula"),resultado.getString("marca") + " " +
                                resultado.getString("modelo") , resultado.getString("dni"),
                        resultado.getString("nombreCompleto"),resultado.getString("fechaInicio"),
                        resultado.getString("fechaFInal"),resultado.getString("precioPorDia") + "€",
                        resultado.getString("lugarDevolucion"),isDeposito,resultado.getString("tipoSeguro"));

            tabla.print();
            conexion.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public static void listarPorMatricula(){
        try{
            Connection conexion = (Connection) Conexion.conectarBd();
            String matricula;
            String consulta = "SELECT alquilercoches.matricula, alquilercoches.dni, alquilercoches.fechaInicio," +
            "alquilercoches.fechaFinal,alquilercoches.precioPorDia,alquilercoches.lugarDevolucion,alquilercoches.isRetornDipositPle," +
                    "alquilercoches.tipoSeguro, clientes.nombreCompleto, coches.marca, coches.modelo FROM alquilercoches " +
                    "INNER JOIN clientes ON alquilerCoches.dni = clientes.dni INNER JOIN coches ON alquilerCoches.matricula = coches.matricula " +
                    "WHERE alquilercoches.matricula = ?";

            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce la matrícula del coche alquilado: ");
            matricula = scanner.next();
            AlquilerCoches a = new AlquilerCoches(matricula, "");
            sentencia.setString(1, a.getMatricula());
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next() == false){
                System.out.println("Error, No hemos encontrado ningún alquiler asociado al coche con matrícula: " + matricula);
                return;
            }
            TableList tabla = table();
            while(resultado.next()){
                String isDeposito = "No";
                if(resultado.getString("isRetornDipositPle").equals("1")){
                    isDeposito = "Sí";
                }
                tabla.addRow(resultado.getString("matricula"),resultado.getString("marca") + " " +
                                resultado.getString("modelo") , resultado.getString("dni"),
                        resultado.getString("nombreCompleto"),resultado.getString("fechaInicio"),
                        resultado.getString("fechaFInal"),resultado.getString("precioPorDia") + "€",
                        resultado.getString("lugarDevolucion"),isDeposito,resultado.getString("tipoSeguro"));
            }
            tabla.print();
            conexion.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void insertarRegistro(){
        try{
            String matricula, dni, lugarDevolucion, tipoSeguro;
            int numeroDias, isRetornDipositPle = 1;
            double precioPorDia = 50;

            Connection conexion = (Connection) Conexion.conectarBd();
            String consulta = "insert into alquilercoches (matricula,dni,fechaInicio,fechaFinal,precioPorDia,lugarDevolucion,isRetornDipositPle,tipoSeguro) values(?,?,?,?,?,?,?,?);";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce la Matrícula del Vehículo: ");
            matricula = scanner.next();
            System.out.printf("Introduce el DNI del cliente: ");
            dni = scanner.next();
            System.out.printf("Nº de días: ");
            try{
                numeroDias = scanner.nextInt();
            }catch(Exception e){
                System.out.println("Error, numero de dias no valido, se ha aplicado el valor 1 por defecto");
                numeroDias = 1;
            }System.out.printf("Precio por día: ");
            try{
                precioPorDia = scanner.nextDouble();
            }catch(Exception e){
                System.out.println("Error, precio por día no valido, se ha aplicado el valor 50.00 por defecto");
            }
            TableList tLugarDevolucion = new TableList(2,"Valor", "Ciudad").sortBy(0).withUnicode(true);
            tLugarDevolucion.addRow("1","Barcelona");
            tLugarDevolucion.addRow("2","Madrid");
            tLugarDevolucion.addRow("3","Sevilla");
            tLugarDevolucion.addRow("4","Zaragoza");
            tLugarDevolucion.addRow("5","Santander");
            tLugarDevolucion.addRow("6","Tarragona");
            System.out.println("Inserta el lugar de devolución: ");
            tLugarDevolucion.print();
            System.out.printf("Selecciona una opción: ");
            lugarDevolucion = scanner.next();
            switch (lugarDevolucion) {
                case "1" -> lugarDevolucion = "Barcelona";
                case "2" -> lugarDevolucion = "Madrid";
                case "3" -> lugarDevolucion = "Sevilla";
                case "4" -> lugarDevolucion = "Zaragoza";
                case "5" -> lugarDevolucion = "Santander";
                case "6" -> lugarDevolucion = "Tarragona";
                default -> {
                    System.out.println("Error, no has seleccionado una opción válida. Se ha aplicado Barcelona como por defecto.");
                    lugarDevolucion = "Barcelona";
                }
            }
            TableList tTipoSeguro = new TableList(2,"Valor", "Tipo de Seguro").sortBy(0).withUnicode(true);
            tTipoSeguro.addRow("1","Franquícia");
            tTipoSeguro.addRow("2","Sin Franquícia");
            System.out.println("Inserta el tipo de seguro: ");
            tTipoSeguro.print();
            System.out.printf("Selecciona una opción: ");
            tipoSeguro = scanner.next();
            switch (tipoSeguro) {
                case "1" -> tipoSeguro = "Franquícia";
                case "2" -> tipoSeguro = "Sin Franquícia";
                default -> {
                    System.out.println("Error, no has seleccionado una opción válida. Se ha aplicado Sín Franquicia como por defecto.");
                    tipoSeguro = "Sin Franquícia";
                }
            }
            System.out.printf("Debe devolver el depósito lleno? 0 -> No | 1 -> Sí : ");
            try{
                isRetornDipositPle = scanner.nextInt();
                if(isRetornDipositPle < 0 || isRetornDipositPle > 1){
                    System.out.println("Error, no se ha introducido un número válido, se ha aplicado el valor 0 por defecto");
                    numeroDias = 0;
                }
            }catch(Exception e){
                System.out.println("Error, no se ha introducido un número válido, se ha aplicado el valor 0 por defecto");
            }

            AlquilerCoches a = new AlquilerCoches(matricula, dni, lugarDevolucion, tipoSeguro, numeroDias,precioPorDia,isRetornDipositPle);
            sentencia.setString(1, a.getMatricula());
            sentencia.setString(2, a.getDni());
            sentencia.setString(3, a.getFechaInico());
            sentencia.setString(4, a.getFechaFinal());
            sentencia.setDouble(5, a.getPrecioPorDia());
            sentencia.setString(6, a.getLugarDevolucion());
            sentencia.setInt(7, a.getRetornDipositPle());
            sentencia.setString(8, a.getTipoSeguro());
            int row = sentencia.executeUpdate();
            System.out.println("Se ha alquilado el coche correctamente");
            conexion.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void eliminarRegistro(){
        try{
            String matricula, dni;
            Connection conexion = (Connection) Conexion.conectarBd();
            String consulta = "DELETE FROM alquilercoches where matricula = ? AND dni = ?";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce la Matrícula del coche: ");
            matricula = scanner.next();
            System.out.printf("Introduce el DNI del Cliente: ");
            dni = scanner.next();
            AlquilerCoches a = new AlquilerCoches(matricula,dni);
            sentencia.setString(1, a.getMatricula());
            sentencia.setString(2, a.getDni());
            int row = sentencia.executeUpdate();
            System.out.println("Registro eliminado correctamente");
            conexion.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getLugarDevolucion() {
        return lugarDevolucion;
    }

    public void setLugarDevolucion(String lugarDevolucion) {
        this.lugarDevolucion = lugarDevolucion;
    }

    public String getTipoSeguro() {
        return tipoSeguro;
    }

    public void setTipoSeguro(String tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }

    public String getFechaInico() {
        return fechaInico;
    }

    public void setFechaInico(String fechaInico) {
        this.fechaInico = fechaInico;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public double getPrecioPorDia() {
        return precioPorDia;
    }

    public void setPrecioPorDia(double precioPorDia) {
        this.precioPorDia = precioPorDia;
    }

    public int getRetornDipositPle() {
        return isRetornDipositPle;
    }

    public void setRetornDipositPle(int retornDipositPle) {
        isRetornDipositPle = retornDipositPle;
    }
}
