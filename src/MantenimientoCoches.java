import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Scanner;

public class MantenimientoCoches {

    String matricula,dni,fechaInMantenimiento,fechaFiMantenimiento;

    private static final Scanner scanner = new Scanner(System.in).useDelimiter("\n");

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
            AlquilerCoches a = new AlquilerCoches(matricula, dni);
            sentencia.setString(1, a.getMatricula());
            sentencia.setString(2, a.getDni());
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
            System.out.println(e);
        }
    }
}
