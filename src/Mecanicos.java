import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

public class Mecanicos {
    public static void listarMecanicos() {
        try {
            Connection conexion = (Connection) Conexion.conectarBd();
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery("SELECT * FROM mecanicos");
            System.out.println("---- Listado de Mecánicos ----");
            DecimalFormat formatter = new DecimalFormat("00");
            TableList tabla = new TableList(13, "DNI", "Nombre Completo", "Fecha de Nacimiento", "Teléfono", "Dirección",
                    "Ciudad", "País", "Email", "Puntos Carnet", "Salario", "Nº Seguridad Social", "Titulación", "Fecha de Contratación").sortBy(0).withUnicode(true);
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
}
