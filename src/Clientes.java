import java.sql.*;
import java.text.DecimalFormat;

public class Clientes {
    public static void listarClientes(){
        try{
            Connection conexion = (Connection) Conexion.conectarBd();
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery("SELECT * FROM clientes");
            System.out.println("---- Listado de Clientes ----");
            DecimalFormat formatter = new DecimalFormat("00");
            TableList tabla = new TableList(9,"DNI","Nombre Completo","Fecha de Nacimiento","Teléfono","Dirección","Ciudad","País","Email","Puntos Carnet").sortBy(0).withUnicode(true);
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
}
