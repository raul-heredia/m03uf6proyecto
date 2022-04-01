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
    public static void insertarRegistro(){
        try{
            String dni, nombreCompleto,  fechaNacimiento,  telefono,  direccion,  ciudad,  pais,  email,  titulacion,  fechaContratacion;
            int puntosCarnet = 0, numSS= 0;
            double salario = 0;
            Connection conexion = (Connection) Conexion.conectarBd();
            String consulta = "insert into mecanicos (dni,nombreCompleto,fechaNacimiento,telefono,direccion,ciudad,pais,email,titulacion,fechaContratacion, puntosCarnet, numSS, salario) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce el DNI: ");
            dni = scanner.next();
            System.out.printf("Introduce el Nombre Completo: ");
            nombreCompleto = scanner.next();
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
            System.out.printf("Introduce la Titulacion: ");
            titulacion = scanner.next();
            System.out.printf("Introduce la Fecha de Contratacion formato [YYYY-MM-DD]: ");
            fechaContratacion = scanner.next();
            System.out.printf("Introduce los Puntos del Carnet: ");
            try{
                puntosCarnet = scanner.nextInt();
            }catch(Exception e){
                System.out.println("Error no has introducido un número válido.");
                System.out.println("Se ha aplicado el valor 0 por defecto.");
                scanner.next();
            }
            System.out.printf("Introduce el numero de Seguridad Social: ");
            try{
                numSS = scanner.nextInt();
            }catch(Exception e){
                System.out.println("Error no has introducido un número válido.");
                System.out.println("Se ha aplicado el valor 0 por defecto.");
                scanner.next();
            }
            System.out.printf("Introduce el salario: ");
            try{
                numSS = (int) scanner.nextDouble();
            }catch(Exception e){
                System.out.println("Error no has introducido un número válido.");
                System.out.println("Se ha aplicado el valor 0 por defecto.");
                scanner.next();
            }

            Mecanicos m = new Mecanicos(dni, nombreCompleto,  fechaNacimiento,  telefono,  direccion,  ciudad,  pais,  email,  titulacion,  fechaContratacion, puntosCarnet, numSS, salario);
            sentencia.setString(1, m.getDni());
            sentencia.setString(2, m.getNombreCompleto());
            sentencia.setString(3, m.getFechaNacimiento());
            sentencia.setString(4, m.getTelefono());
            sentencia.setString(5, m.getDireccion());
            sentencia.setString(6, m.getCiudad());
            sentencia.setString(7, m.getPais());
            sentencia.setString(8, m.getEmail());
            sentencia.setString(9, m.getTitulacion());
            sentencia.setString(10, m.getFechaContratacion());
            sentencia.setInt(11, m.getPuntosCarnet());
            sentencia.setInt(12, m.getNumSS());
            sentencia.setDouble(13, m.getSalario());

            int row = sentencia.executeUpdate();
            System.out.println("Se ha insertado el Mecanico correctamente");
            conexion.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public static void modificarRegistro(){
        try{
            String dni, nombreCompleto,  fechaNacimiento,  telefono,  direccion,  ciudad,  pais,  email,  titulacion,  fechaContratacion;
            int puntosCarnet = 0, numSS= 0;
            double salario = 0;
            String nombreCompletoMod, fechaNacimientoMod,telefonoMod, direccionMod, ciudadMod, paisMod, emailMod, titulacionMod, fechaContratacionMod;
            int puntosCarnetMod, numSSMod;
            double salarioMod;
            Connection conexion = (Connection) Conexion.conectarBd();
            String preConsulta = "SELECT * FROM mecanicos WHERE dni = ?";
            String consulta = "UPDATE mecanicos SET nombrecompleto = ?, fechaNacimiento = ?, telefono = ?, direccion = ?," +
                    " ciudad = ?, pais = ?, email = ?, titulacion = ?, puntosCarnet = ? puntosCarnet = ?, numSS = ?, salario = ? WHERE dni = ?";
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
            titulacion = resultado.getString("titulacion");
            fechaContratacion = resultado.getString("fechaContratacion");
            puntosCarnet = Integer.parseInt(resultado.getString("puntosCarnet"));
            numSS = Integer.parseInt(resultado.getString("numSS"));
            salario = Double.parseDouble(resultado.getString("salario"));

            /*System.out.println("---- DATOS CLIENTE ANTES DE MODIFICAR ----");
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
                scanner.next();
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
             */
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
