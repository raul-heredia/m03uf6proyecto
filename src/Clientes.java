import java.sql.*;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Clientes {
    private String dni, nombreCompleto, fechaNacimiento,telefono, direccion, ciudad, pais, email;
    private int puntosCarnet;

    private static final Scanner scanner = new Scanner(System.in).useDelimiter("\n");
    private static final DecimalFormat formatter = new DecimalFormat("00");

    public Clientes(String dni) {
        this.dni = dni;
    }

    public Clientes(String dni, String nombreCompleto, String fechaNacimiento, String telefono, String direccion, String ciudad, String pais, String email, int puntosCarnet) {
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.pais = pais;
        this.email = email;
        this.puntosCarnet = puntosCarnet;
    }

    public static void listarClientes(){
        try{
            Connection conexion = (Connection) Conexion.conectarBd();
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery("SELECT * FROM clientes");
            System.out.println("---- Listado de Clientes ----");
            TableList tabla = new TableList(9,"DNI","Nombre Completo","Fecha de Nacimiento",
                    "Teléfono","Dirección","Ciudad","País","Email","Puntos Carnet").sortBy(0).withUnicode(true);
            while(resultado.next()){
                //Sacamos los resultados
                tabla.addRow(resultado.getString("dni"), resultado.getString("nombreCompleto"),
                        resultado.getString("fechaNacimiento"), resultado.getString("telefono"),
                        resultado.getString("direccion"), resultado.getString("ciudad"),resultado.getString("pais"),
                        resultado.getString("email"), formatter.format(Integer.parseInt(resultado.getString("puntosCarnet"))) + " Puntos");
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
            TableList tabla = new TableList(9,"DNI","Nombre Completo","Fecha de Nacimiento",
                    "Teléfono","Dirección","Ciudad","País","Email","Puntos Carnet").sortBy(0).withUnicode(true);
            String dni;
            String consulta = "SELECT * FROM clientes where dni = ?";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce el DNI del Cliente a buscar: ");
            dni = scanner.next();
            Clientes c = new Clientes(dni);
            sentencia.setString(1, c.getDni());
            ResultSet resultado = sentencia.executeQuery();
            while(resultado.next()){
                //Sacamos los resultados
                tabla.addRow(resultado.getString("dni"), resultado.getString("nombreCompleto"),
                        resultado.getString("fechaNacimiento"), resultado.getString("telefono"),
                        resultado.getString("direccion"), resultado.getString("ciudad"),resultado.getString("pais"),
                        resultado.getString("email"), formatter.format(Integer.parseInt(resultado.getString("puntosCarnet"))) + " Puntos");
            }
            tabla.print();
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

    public int getPuntosCarnet() {
        return puntosCarnet;
    }

    public void setPuntosCarnet(int puntosCarnet) {
        this.puntosCarnet = puntosCarnet;
    }
}
