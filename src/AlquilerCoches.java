import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AlquilerCoches {
    public static void listarAlquileres(){
        try{
            Connection conexion = (Connection) Conexion.conectarBd();
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery("SELECT alquilercoches.matricula, alquilercoches.dni, alquilercoches.fechaInicio," +
                    "alquilercoches.fechaFinal,alquilercoches.precioPorDia,alquilercoches.lugarDevolucion,alquilercoches.isRetornDipositPle," +
                    "alquilercoches.tipoSeguro, clientes.nombreCompleto, coches.marca, coches.modelo FROM alquilercoches " +
                    "INNER JOIN clientes ON alquilerCoches.dni = clientes.dni INNER JOIN coches ON alquilerCoches.matricula = coches.matricula");
            System.out.println("---- Listado de Vehiculos Alquilados ----");
            TableList tabla = new TableList(10,"Matrícula","Marca y Modelo","DNI","Nombre Completo Cliente","Fecha Inicio Alquiler",
                    "Fecha Fin Alquiler","Precio Por Día","Lugar de Devolución","Retorno Deposito Lleno","Tipo de Seguro").sortBy(0).withUnicode(true);
            while(resultado.next()){
                String isDeposito = "No";
                if(resultado.getString("isRetornDipositPle").equals("1")){
                    isDeposito = "Sí";
                }
                tabla.addRow(resultado.getString("matricula"),resultado.getString("marca") + " " +
                        resultado.getString("modelo") , resultado.getString("dni"),
                        resultado.getString("nombreCompleto"),resultado.getString("fechaInicio"),
                        resultado.getString("fechaFInal"),resultado.getString("precioPorDia") + "€",
                        resultado.getString("lugarDevolucion"),isDeposito,resultado.getString("tipoSeguro"));
            }
            tabla.print();
            conexion.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
