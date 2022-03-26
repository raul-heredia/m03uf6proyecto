import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Coches {
    public static void listarCoches(){
        try{
            Connection conexion = (Connection) Conexion.conectarBd();
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery("SELECT * FROM coches");
            System.out.println("---- Listado de Vehiculos ----");
            TableList tabla = new TableList(9,"Matrícula","Nº de Bastidor","Marca y Modelo","Año de Fabricación","Color",
                    "Nº de Plazas","Nº de Puertas","Grandaria Maletero","Tipo de Combustible").sortBy(0).withUnicode(true);
            while(resultado.next()){
                //Sacamos los resultados
                tabla.addRow(resultado.getString("matricula"), resultado.getString("numeroBastidor"),
                        resultado.getString("marca") + " " +  resultado.getString("modelo"),
                        resultado.getString("añoFabricacion"), resultado.getString("color"),
                        resultado.getString("numeroPlazas"),resultado.getString("numeroPuertas"),
                        resultado.getString("grandariaMaletero") + "L",resultado.getString("combustible"));
            }
            tabla.print();
            conexion.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
