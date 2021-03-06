import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Scanner;

public class MantenimientoCoches {

    private String matricula,dni,fechaInMantenimiento,fechaFiMantenimiento;

    private static final Scanner scanner = new Scanner(System.in).useDelimiter("\n");

    public MantenimientoCoches(String matricula, String dni) {
        this.matricula = matricula;
        this.dni = dni;
    }

    public MantenimientoCoches(String matricula, String dni, String fechaInMantenimiento, String fechaFiMantenimiento) {
        this.matricula = matricula;
        this.dni = dni;
        this.fechaInMantenimiento = fechaInMantenimiento;
        this.fechaFiMantenimiento = fechaFiMantenimiento;
    }

    public static TableList table(){
        return new TableList(6,"Matrícula","Marca y Modelo","DNI","Nombre Completo Mecánico","Fecha de Inicio Mantenimiento","Fecha Fin del Mantenimiento").sortBy(0).withUnicode(true);
    }

    public static void listarMantenimientos(){
        try{
            Connection conexion = (Connection) Conexion.conectarBd();
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery("SELECT mantenimientocoches.matricula,mantenimientocoches.dni, " +
                    "mantenimientocoches.fechaInMantenimiento, mantenimientocoches.fechaFiMantenimiento, mecanicos.nombreCompleto, " +
                    "coches.marca, coches.modelo FROM mantenimientocoches INNER JOIN mecanicos ON mantenimientocoches.dni = mecanicos.dni " +
                    "INNER JOIN coches ON mantenimientocoches.matricula = coches.matricula");
            System.out.println("---- Listado de Mantenimientos ----");
            DecimalFormat formatter = new DecimalFormat("00");
            TableList tabla = table();
            while(resultado.next()){
                //Sacamos los resultados
                tabla.addRow(resultado.getString("matricula"), resultado.getString("marca") + " " + resultado.getString("modelo"),
                        resultado.getString("dni"), resultado.getString("nombreCompleto"),
                        resultado.getString("fechaInMantenimiento"), resultado.getString("fechaFiMantenimiento"));
            }
            tabla.print();
            conexion.close();
        }catch (Exception e){
            System.out.println("Error, no se han podido listar los mantenimientos");
            System.out.println(e);
        }
    }
    public static void listarUno(){
        try{
            Connection conexion = (Connection) Conexion.conectarBd();
            String matricula, dni;
            String consulta = "SELECT mantenimientocoches.matricula,mantenimientocoches.dni, " +
                    "mantenimientocoches.fechaInMantenimiento, mantenimientocoches.fechaFiMantenimiento, mecanicos.nombreCompleto, " +
                    "coches.marca, coches.modelo FROM mantenimientocoches INNER JOIN mecanicos ON mantenimientocoches.dni = mecanicos.dni " +
                    "INNER JOIN coches ON mantenimientocoches.matricula = coches.matricula WHERE mantenimientocoches.matricula = ? AND mantenimientocoches.dni = ?";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce la matrícula del coche: ");
            matricula = scanner.next();
            System.out.printf("Introduce el DNI del mecánico: ");
            dni = scanner.next();
            MantenimientoCoches m = new MantenimientoCoches(matricula, dni);
            sentencia.setString(1, m.getMatricula());
            sentencia.setString(2, m.getDni());
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next() == false){
                System.out.println("Error, No hemos encontrado ningún registro con los datos seleccionados");
                return;
            }
            TableList tabla = table();
                tabla.addRow(resultado.getString("matricula"), resultado.getString("marca") + " " + resultado.getString("modelo"),
                        resultado.getString("dni"), resultado.getString("nombreCompleto"),
                        resultado.getString("fechaInMantenimiento"), resultado.getString("fechaFiMantenimiento"));

            tabla.print();
            conexion.close();
        }catch (Exception e){
            System.out.println("Error, no se han podido listar los mantenimientos");
            System.out.println(e);
        }
    }
    public static void insertarRegistro(){
        try{
        Connection conexion = (Connection) Conexion.conectarBd();
        String matricula, dni, fechaInMantenimiento, fechaFiMantenimiento;

        String coche = "SELECT * FROM coches where matricula = ?";
        String mecanico = "SELECT * FROM mecanicos where dni = ?";
        String consulta = "INSERT INTO mantenimientocoches (matricula,dni,fechaInMantenimiento,fechaFiMantenimiento) values(?,?,?,?)";
        PreparedStatement sentenciaCoche = conexion.prepareStatement(coche);
        PreparedStatement sentenciaMecanico = conexion.prepareStatement(mecanico);
        PreparedStatement sentencia = conexion.prepareStatement(consulta);

            System.out.printf("Introduce la Matrícula del Vehículo: ");
            matricula = scanner.next();
            sentenciaCoche.setString(1, matricula);
            ResultSet resultCoche = sentenciaCoche.executeQuery();
            if (!resultCoche.next()){
                System.out.println("Error, El coche solicitado no existe");
                return;
            }

            System.out.printf("Introduce el DNI del mecánico: ");
            dni = scanner.next();
            sentenciaMecanico.setString(1, dni);
            ResultSet resultMecanico = sentenciaMecanico.executeQuery();
            if (!resultMecanico.next()){
                System.out.println("Error, El mecánico solicitado no existe");
                return;
            }
            System.out.printf("Introduce la Fecha de Inicio del Mantenimiento [Formato YYYY-MM-DD]: ");
            fechaInMantenimiento = scanner.next();
            System.out.printf("Introduce la Fecha de Finalización del Mantenimiento [Formato YYYY-MM-DD]: ");
            fechaFiMantenimiento = scanner.next();
            MantenimientoCoches m = new MantenimientoCoches(matricula,dni,fechaInMantenimiento,fechaFiMantenimiento);
            sentencia.setString(1, m.getMatricula());
            sentencia.setString(2, m.getDni());
            sentencia.setString(3, m.getFechaInMantenimiento());
            sentencia.setString(4, m.getFechaFiMantenimiento());
            int row = sentencia.executeUpdate();
            System.out.println("Se ha añadido el mantenimiento correctamnete");
            conexion.close();
        }catch(Exception e){
            System.out.println("Error, no se ha podido insertar el registro");
            System.out.println(e);
        }
    }
    public static void modificarRegistro(){
        try{
        Connection conexion = (Connection) Conexion.conectarBd();
        String matricula, dni, fechaInMantenimiento, fechaFiMantenimiento;

        String coche = "SELECT * FROM coches where matricula = ?";
        String mecanico = "SELECT * FROM mecanicos where dni = ?";
        String consulta = "UPDATE mantenimientocoches SET fechaInMantenimiento = ?, fechaFiMantenimiento = ? WHERE matricula = ? AND dni = ?";

        PreparedStatement sentenciaCoche = conexion.prepareStatement(coche);
        PreparedStatement sentenciaMecanico = conexion.prepareStatement(mecanico);
        PreparedStatement sentencia = conexion.prepareStatement(consulta);

            System.out.printf("Introduce la Matrícula del Vehículo: ");
            matricula = scanner.next();
            sentenciaCoche.setString(1, matricula);
            ResultSet resultCoche = sentenciaCoche.executeQuery();
            if (!resultCoche.next()){
                System.out.println("Error, El coche solicitado no existe");
                return;
            }

            System.out.printf("Introduce el DNI del mecánico: ");
            dni = scanner.next();
            sentenciaMecanico.setString(1, dni);
            ResultSet resultMecanico = sentenciaMecanico.executeQuery();
            if (!resultMecanico.next()){
                System.out.println("Error, El mecánico solicitado no existe");
                return;
            }

            System.out.printf("Introduce la nueva Fecha de Inicio del Mantenimiento [Formato YYYY-MM-DD]: ");
            fechaInMantenimiento = scanner.next();
            System.out.printf("Introduce la nueva Fecha de Finalización del Mantenimiento [Formato YYYY-MM-DD]: ");
            fechaFiMantenimiento = scanner.next();
            MantenimientoCoches m = new MantenimientoCoches(matricula,dni,fechaInMantenimiento,fechaFiMantenimiento);
            sentencia.setString(1, m.getFechaInMantenimiento());
            sentencia.setString(2, m.getFechaFiMantenimiento());
            sentencia.setString(3, m.getMatricula());
            sentencia.setString(4, m.getDni());
            int row = sentencia.executeUpdate();
            System.out.println("Se ha modificado el registro correctamente");
            conexion.close();
        }catch(Exception e){
            System.out.println("Error, no se ha podido modificar el registro");
            System.out.println(e);
        }
    }
    public static void eliminarRegistro(){
        try{
            String matricula, dni;
            Connection conexion = (Connection) Conexion.conectarBd();
            String consulta = "DELETE FROM mantenimientocoches where matricula = ? AND dni = ?";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce la Matrícula del coche: ");
            matricula = scanner.next();
            System.out.printf("Introduce el DNI del mecánico: ");
            dni = scanner.next();
            MantenimientoCoches m = new MantenimientoCoches(matricula,dni);
            sentencia.setString(1, m.getMatricula());
            sentencia.setString(2, m.getDni());
            int row = sentencia.executeUpdate();
            System.out.println("Registro eliminado correctamente");
            conexion.close();
        }catch (Exception e){
            System.out.println("Error, no se ha podido eliminar el registro");
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

    public String getFechaInMantenimiento() {
        return fechaInMantenimiento;
    }

    public void setFechaInMantenimiento(String fechaInMantenimiento) {
        this.fechaInMantenimiento = fechaInMantenimiento;
    }

    public String getFechaFiMantenimiento() {
        return fechaFiMantenimiento;
    }

    public void setFechaFiMantenimiento(String fechaFiMantenimiento) {
        this.fechaFiMantenimiento = fechaFiMantenimiento;
    }
}
