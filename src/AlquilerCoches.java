import java.sql.PreparedStatement;
import java.util.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AlquilerCoches {
    private String matricula, dni, lugarDevolucion, tipoSeguro;
    private Date fechaInico, fechaFinal;
    private double precioPorDia;
    private Boolean isRetornDipositPle;

    private static final Scanner scanner = new Scanner(System.in).useDelimiter("\n");


    public AlquilerCoches(String matricula, String dni) {
        this.matricula = matricula;
        this.dni = dni;
    }

    public AlquilerCoches(String matricula, String dni, String lugarDevolucion, String tipoSeguro, int numeroDias,double precioPorDia, Boolean isRetornDipositPle) {
        this.matricula = matricula;
        this.dni = dni;
        this.lugarDevolucion = lugarDevolucion;
        this.tipoSeguro = tipoSeguro;
        this.precioPorDia = precioPorDia;
        this.isRetornDipositPle = isRetornDipositPle;
        this.fechaInico = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(this.fechaInico);
        c.add(Calendar.DATE, numeroDias);
        this.fechaFinal = c.getTime();
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
            TableList tabla = new TableList(10,"Matrícula","Marca y Modelo","DNI","Nombre Completo Cliente","Fecha Inicio Alquiler",
                    "Fecha Fin Alquiler","Precio Por Día","Lugar de Devolución","Retorno Deposito Lleno","Tipo de Seguro").sortBy(0).withUnicode(true);
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
            TableList tabla = new TableList(10,"Matrícula","Marca y Modelo","DNI","Nombre Completo Cliente","Fecha Inicio Alquiler",
                    "Fecha Fin Alquiler","Precio Por Día","Lugar de Devolución","Retorno Deposito Lleno","Tipo de Seguro").sortBy(0).withUnicode(true);
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

    public Date getFechaInico() {
        return fechaInico;
    }

    public void setFechaInico(Date fechaInico) {
        this.fechaInico = fechaInico;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public double getPrecioPorDia() {
        return precioPorDia;
    }

    public void setPrecioPorDia(double precioPorDia) {
        this.precioPorDia = precioPorDia;
    }

    public Boolean getRetornDipositPle() {
        return isRetornDipositPle;
    }

    public void setRetornDipositPle(Boolean retornDipositPle) {
        isRetornDipositPle = retornDipositPle;
    }
}
