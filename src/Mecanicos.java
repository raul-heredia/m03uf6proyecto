import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Mecanicos {

    private String dni, nombreCompleto, fechaNacimiento, telefono, direccion,ciudad,pais,email,titulacion, fechaContratacion;
    private int puntosCarnet, numSS;
    private double salario;

    private static final Scanner scanner = new Scanner(System.in).useDelimiter("\n");
    private static final DecimalFormat formatter = new DecimalFormat("00");

    public Mecanicos(String dni) {
        this.dni = dni;
    }

    public Mecanicos(String dni, String nombreCompleto, String fechaNacimiento, String telefono, String direccion, String ciudad, String pais, String email, String titulacion, String fechaContratacion, int puntosCarnet, int numSS, double salario) {
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.pais = pais;
        this.email = email;
        this.titulacion = titulacion;
        this.fechaContratacion = fechaContratacion;
        this.puntosCarnet = puntosCarnet;
        this.numSS = numSS;
        this.salario = salario;
    }

    public static TableList table(){
        return new TableList(13, "DNI", "Nombre Completo", "Fecha de Nacimiento", "Teléfono", "Dirección",
                "Ciudad", "País", "Email", "Puntos Carnet", "Salario", "Nº Seguridad Social", "Titulación", "Fecha de Contratación").sortBy(0).withUnicode(true);
    }
    public static void listarMecanicos() {
        try {
            Connection conexion = (Connection) Conexion.conectarBd();
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery("SELECT * FROM mecanicos");
            System.out.println("---- Listado de Mecánicos ----");
            DecimalFormat formatter = new DecimalFormat("00");
            TableList tabla = table();
            while (resultado.next()) {
                //Sacamos los resultados
                tabla.addRow(resultado.getString("dni"), resultado.getString("nombreCompleto"),
                        resultado.getString("fechaNacimiento"),resultado.getString("telefono"),
                        resultado.getString("direccion"),resultado.getString("ciudad"),
                        resultado.getString("pais"),resultado.getString("email"),
                        formatter.format(Integer.parseInt(resultado.getString("puntosCarnet"))) + " Puntos",resultado.getString("salario"),
                        resultado.getString("numSS"),resultado.getString("titulacion"),
                        resultado.getString("fechaContratacion"));
            }
            tabla.print();
            conexion.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void listarUno(){
        try{
            Connection conexion = (Connection) Conexion.conectarBd();
            String dni;
            String consulta = "SELECT * FROM mecanicos where dni = ?";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce el DNI del Mecánico a buscar: ");
            dni = scanner.next();
            Mecanicos m = new Mecanicos(dni);
            sentencia.setString(1, m.getDni());
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next() == false){
                System.out.println("Error, No existe ningún mecánico con DNI: " + dni);
                return;
            }
            TableList tabla = table();
                tabla.addRow(resultado.getString("dni"), resultado.getString("nombreCompleto"),
                        resultado.getString("fechaNacimiento"),resultado.getString("telefono"),
                        resultado.getString("direccion"),resultado.getString("ciudad"),
                        resultado.getString("pais"),resultado.getString("email"),
                        formatter.format(Integer.parseInt(resultado.getString("puntosCarnet"))) + " Puntos",resultado.getString("salario"),
                        resultado.getString("numSS"),resultado.getString("titulacion"),
                        resultado.getString("fechaContratacion"));
            tabla.print();
            conexion.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void eliminarRegistro(){
        try{
            String dni;
            Connection conexion = (Connection) Conexion.conectarBd();
            String consulta = "DELETE FROM mecanicos where dni = ?";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce el DNI a eliminar: ");
            dni = scanner.next();
            Mecanicos m = new Mecanicos(dni);
            sentencia.setString(1, m.getDni());
            int row = sentencia.executeUpdate();
            System.out.println("Registro eliminado correctamente");
            conexion.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitulacion() {
        return titulacion;
    }

    public void setTitulacion(String titulacion) {
        this.titulacion = titulacion;
    }

    public String getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(String fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public int getPuntosCarnet() {
        return puntosCarnet;
    }

    public void setPuntosCarnet(int puntosCarnet) {
        this.puntosCarnet = puntosCarnet;
    }

    public int getNumSS() {
        return numSS;
    }

    public void setNumSS(int numSS) {
        this.numSS = numSS;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
}
