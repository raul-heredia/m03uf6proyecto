import java.sql.*;

public class Conexion {
    public static Object conectarBd(){
        try{
            String miDriver="com.mysql.cj.jdbc.Driver";
            String miUrl = "jdbc:mysql://localhost/m03uf6proyecto";
            Class.forName(miDriver);
            Connection conexion = DriverManager.getConnection(miUrl, "root", "root");
            return conexion;
        }catch(Exception e){
            System.out.println("Error, no se ha podido conectar con la base de datos.");
            System.out.println(e);
            return null;
        }
    }
}
