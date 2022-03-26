import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

public class MantenimientoCoches {
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
            TableList tabla = new TableList(6,"Matrícula","Marca y Modelo","DNI","Nombre Completo Mecánico","Fecha de Inicio Mantenimiento","Fecha Fin del Mantenimiento").sortBy(0).withUnicode(true);
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
}
