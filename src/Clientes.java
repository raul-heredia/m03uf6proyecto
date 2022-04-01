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

    public static TableList table(){
        return new TableList(9,"DNI","Nombre Completo","Fecha de Nacimiento",
                "Teléfono","Dirección","Ciudad","País","Email","Puntos Carnet").sortBy(0).withUnicode(true);
    }

    public static void listarClientes(){
        try{
            Connection conexion = (Connection) Conexion.conectarBd();
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery("SELECT * FROM clientes");
            System.out.println("---- Listado de Clientes ----");
            TableList tabla = table();
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
            String dni;
            String consulta = "SELECT * FROM clientes where dni = ?";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce el DNI del Cliente a buscar: ");
            dni = scanner.next();
            Clientes c = new Clientes(dni);
            sentencia.setString(1, c.getDni());
            ResultSet resultado = sentencia.executeQuery();
            TableList tabla = table();
            if (resultado.next() == false){
                System.out.println("Error, No existe ningún cliente con DNI " + dni);
                return;
            }

                //Sacamos los resultados
                tabla.addRow(resultado.getString("dni"), resultado.getString("nombreCompleto"),
                        resultado.getString("fechaNacimiento"), resultado.getString("telefono"),
                        resultado.getString("direccion"), resultado.getString("ciudad"),resultado.getString("pais"),
                        resultado.getString("email"), formatter.format(Integer.parseInt(resultado.getString("puntosCarnet"))) + " Puntos");

            tabla.print();
            conexion.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void insertarRegistro(){
        try{
            String dni, nombre, fechaNacimiento, telefono, direccion, ciudad, pais, email;
            int puntosCarnet = 0;
            Connection conexion = (Connection) Conexion.conectarBd();
            String consulta = "insert into clientes (dni,nombreCompleto,fechaNacimiento,telefono,direccion,ciudad,pais,email,puntosCarnet) values(?,?,?,?,?,?,?,?,?);";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce el DNI: ");
            dni = scanner.next();
            System.out.printf("Introduce el Nombre Completo: ");
            nombre = scanner.next();
            System.out.printf("Introduce la Fecha de Nacimiento formato [YYYY-MM-DD]: ");
            fechaNacimiento = scanner.next();
            System.out.printf("Introduce el Teléfono: ");
            telefono = scanner.next();
            System.out.printf("Introduce la Dirección: ");
            direccion = scanner.next();
            System.out.printf("Introduce la Ciudad: ");
            ciudad = scanner.next();
            System.out.printf("Introduce el País: ");
            pais = scanner.next();
            System.out.printf("Introduce el Email: ");
            email = scanner.next();
            System.out.printf("Introduce los Puntos del Carnet: ");
            try{
                puntosCarnet = scanner.nextInt();
            }catch(Exception e){
                System.out.println("Error no has introducido un número válido.");
                System.out.println("Se ha aplicado el valor 0 por defecto.");
            }

            Clientes c = new Clientes(dni,nombre,fechaNacimiento,telefono,direccion,ciudad,pais,email,puntosCarnet);
            sentencia.setString(1, c.getDni());
            sentencia.setString(2, c.getNombreCompleto());
            sentencia.setString(3, c.getFechaNacimiento());
            sentencia.setString(4, c.getTelefono());
            sentencia.setString(5, c.getDireccion());
            sentencia.setString(6, c.getCiudad());
            sentencia.setString(7, c.getPais());
            sentencia.setString(8, c.getEmail());
            sentencia.setInt(9, c.getPuntosCarnet());
            int row = sentencia.executeUpdate();
            System.out.println("Se ha insertado el cliente correctamente");
            conexion.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public static void modificarRegistro(){
        try{
            String dni, nombreCompleto, fechaNacimiento,telefono, direccion, ciudad, pais, email;
            int puntosCarnet;
            String nombreCompletoMod, fechaNacimientoMod,telefonoMod, direccionMod, ciudadMod, paisMod, emailMod;
            int puntosCarnetMod;
            Connection conexion = (Connection) Conexion.conectarBd();
            String preConsulta = "SELECT * FROM clientes WHERE dni = ?";
            String consulta = "UPDATE clientes SET nombrecompleto = ?, fechaNacimiento = ?, telefono = ?, direccion = ?," +
                    " ciudad = ?, pais = ?, email = ?, puntosCarnet = ? WHERE dni = ?";
            PreparedStatement preSentencia = conexion.prepareStatement(preConsulta);
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce el DNI: ");
            dni = scanner.next();
            preSentencia.setString(1, dni);
            ResultSet resultado = preSentencia.executeQuery();

            if (resultado.next() == false){
                System.out.println("Error, No existe ningún cliente con DNI " + dni);
                return;
            }
            nombreCompleto = resultado.getString("nombreCompleto");
            fechaNacimiento = resultado.getString("fechaNacimiento");
            telefono = resultado.getString("telefono");
            direccion = resultado.getString("direccion");
            ciudad = resultado.getString("ciudad");
            pais = resultado.getString("pais");
            email = resultado.getString("email");
            puntosCarnet = Integer.parseInt(resultado.getString("puntosCarnet"));

            System.out.println("---- DATOS CLIENTE ANTES DE MODIFICAR ----");
            TableList tablaAntes = table();
            tablaAntes.addRow(dni, nombreCompleto, fechaNacimiento, telefono, direccion, ciudad, pais, email, Integer.toString(puntosCarnet));
            tablaAntes.print();
            main.pause();
            System.out.printf("Nuevo Nombre [Deja en blanco para no modificar]: ");
            nombreCompletoMod = scanner.next();
            if (nombreCompletoMod.isEmpty()) nombreCompletoMod = nombreCompleto; // Si esta vacio le aplicamos el que estaba guardado

            System.out.printf("Nueva Fecha de Nacimiento [Formato YYYY-MM-DD] [Deja en blanco para no modificar]: ");
            fechaNacimientoMod = scanner.next();
            if (nombreCompletoMod.isEmpty()) fechaNacimientoMod = fechaNacimiento; // Si esta vacio le aplicamos el que estaba guardado

            System.out.printf("Nuevo Telefono [Deja en blanco para no modificar]: ");
            telefonoMod = scanner.next();
            if (telefonoMod.isEmpty()) telefonoMod = telefono; // Si esta vacio le aplicamos el que estaba guardado

            System.out.printf("Nueva Dirección [Deja en blanco para no modificar]: ");
            direccionMod = scanner.next();
            if (direccionMod.isEmpty()) direccionMod = direccion; // Si esta vacio le aplicamos el que estaba guardado

            System.out.printf("Nueva Ciudad [Deja en blanco para no modificar]: ");
            ciudadMod = scanner.next();
            if (ciudadMod.isEmpty()) ciudadMod = ciudad; // Si esta vacio le aplicamos el que estaba guardado

            System.out.printf("Nuevo País [Deja en blanco para no modificar]: ");
            paisMod = scanner.next();
            if (paisMod.isEmpty()) paisMod = pais; // Si esta vacio le aplicamos el que estaba guardado

            System.out.printf("Nuevo Email [Deja en blanco para no modificar]: ");
            emailMod = scanner.next();
            if (emailMod.isEmpty()) emailMod = email; // Si esta vacio le aplicamos el que estaba guardado

            System.out.printf("Nuevos Puntos del Carnet [Deja en blanco para no modificar]: ");
            try{
                puntosCarnetMod = scanner.nextInt();
            }catch(Exception e){
                puntosCarnetMod = puntosCarnet;
            }
            Clientes c = new Clientes(dni,nombreCompletoMod,fechaNacimientoMod,telefonoMod,direccionMod,ciudadMod,paisMod,emailMod,puntosCarnetMod);
            sentencia.setString(1, c.getNombreCompleto());
            sentencia.setString(2, c.getFechaNacimiento());
            sentencia.setString(3, c.getTelefono());
            sentencia.setString(4, c.getDireccion());
            sentencia.setString(5, c.getCiudad());
            sentencia.setString(6, c.getPais());
            sentencia.setString(7, c.getEmail());
            sentencia.setInt(8, c.getPuntosCarnet());
            sentencia.setString(9, c.getDni());
            int row = sentencia.executeUpdate();
            System.out.println("Se ha modificado el registro correctamente");
            conexion.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public static void eliminarRegistro(){
        try{
            String dni;
            Connection conexion = (Connection) Conexion.conectarBd();
            String consulta = "DELETE FROM clientes where dni = ?";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce el DNI: ");
            dni = scanner.next();
            Clientes c = new Clientes(dni);
            sentencia.setString(1, c.getDni());
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

    public int getPuntosCarnet() {
        return puntosCarnet;
    }

    public void setPuntosCarnet(int puntosCarnet) {
        this.puntosCarnet = puntosCarnet;
    }
}
