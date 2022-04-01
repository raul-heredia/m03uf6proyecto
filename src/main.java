import java.util.Scanner;

public class main {
    public static void pause(){
        System.out.print("Presiona Enter Para Continuar...");
        try{
            System.in.read();
        } catch(Exception e){}
    }
    public static void error(){
        System.out.println("Error, Selecciona una opción válida!");
    }
    public static void main(String[] args) {

        Scanner teclado=new Scanner(System.in);
        String op1="";
        String op2="";

        System.out.println("Bienvenido a la tienda virtual supermercados Exito");
        System.out.println("seleccione una categoria de su interes.");



        do {
            System.out.println("---- Selecciona una Opción ----");
            System.out.println("1 - Gestión Clientes");
            System.out.println("2 - Gestión Coches");
            System.out.println("3 - Gestión Alquileres");
            System.out.println("4 - Gestión Mecánicos");
            System.out.println("5 - Gestión Mantenimientos");
            System.out.println("0 - Salir");
            System.out.printf("Selecciona una opción: ");

            op1=teclado.next();
            op2 = ""; //Reinicia el valor de op2!.

            switch (op1){
                case "0":
                    System.out.println("Hasta la Proxima! :)");
                    break;
                case "1":
                    while (!op2.equals("0")) {
                        System.out.println("---- Clientes ----");
                        System.out.println("1 - Listar Clientes");
                        System.out.println("2 - Listar un Cliente por DNI");
                        System.out.println("3 - Añadir Cliente");
                        System.out.println("4 - Modificar Cliente");
                        System.out.println("5 - Eliminar Cliente");
                        System.out.println("0 - Volver Atrás");
                        System.out.printf("Selecciona una opción: ");

                        op2=teclado.next();
                        switch(op2){
                            case "0":
                                break;
                            case "1":
                                Clientes.listarClientes();
                                pause();
                                break;
                            case "2":
                                Clientes.listarUno();
                                pause();
                                break;
                            case "3":
                                Clientes.insertarRegistro();
                                pause();
                                break;
                            case "4":
                                Clientes.modificarRegistro();
                                pause();
                                break;
                            case "5":
                                Clientes.eliminarRegistro();
                                pause();
                                break;
                            default:
                                error();
                        }
                    }
                    break;
                case "2":
                    while (!op2.equals("0")) {
                        System.out.println("---- Coches ----");
                        System.out.println("1 - Listar Coches");
                        System.out.println("2 - Listar un Coche por Matrícula");
                        System.out.println("3 - Listar Coches por Nº de Plazas");
                        System.out.println("4 - Añadir Coche");
                        System.out.println("5 - Modificar Coche");
                        System.out.println("6 - Eliminar Coche");
                        System.out.println("0 - Volver Atrás");
                        System.out.printf("Selecciona una opción: ");

                        op2=teclado.next();
                        switch(op2){
                            case "0":
                                break;
                            case "1":
                                Coches.listarCoches();
                                pause();
                                break;
                            case "2":
                                Coches.listarUno();
                                pause();
                                break;
                            case "3":
                                Coches.listarPorAsientos();
                                pause();
                                break;
                            case "4":
                                Coches.insertarRegistro();
                                pause();
                                break;
                            case "5":
                                Coches.modificarRegistro();
                                pause();
                                break;
                            case "6":
                                Coches.eliminarRegistro();
                                pause();
                                break;
                            default:
                                error();
                        }
                    }
                    break;
                case "3":
                    while (!op2.equals("0")) {
                        System.out.println("---- Alquileres ----");
                        System.out.println("1 - Listar Alquileres");
                        System.out.println("2 - Listar un Alquiler");
                        System.out.println("3 - Listar Alquileres por Matricula");
                        System.out.println("4 - Listar Alquileres por DNI");
                        System.out.println("5 - Alquilar Coche");
                        System.out.println("6 - Modificar Alquiler");
                        System.out.println("7 - Eliminar Alquiler");
                        System.out.println("0 - Volver Atrás");
                        System.out.printf("Selecciona una opción: ");

                        op2=teclado.next();
                        switch(op2){
                            case "0":
                                break;
                            case "1":
                                AlquilerCoches.listarAlquileres();
                                pause();
                                break;
                            case "2":
                                AlquilerCoches.listarUno();
                                pause();
                                break;
                            case "3":
                                AlquilerCoches.listarPorMatricula();
                                pause();
                                break;
                            case "7":
                                AlquilerCoches.eliminarRegistro();
                                pause();
                                break;
                            default:
                                error();
                        }
                    }
                    break;
                case "4":
                    while (!op2.equals("0")) {
                        System.out.println("---- Mecánicos ----");
                        System.out.println("1 - Listar Mecánicos");
                        System.out.println("2 - Listar un Mecánico");
                        System.out.println("3 - Añadir Mecánico");
                        System.out.println("4 - Modificar Mecánico");
                        System.out.println("5 - Eliminar Mecánico");
                        System.out.println("0 - Volver Atrás");
                        System.out.printf("Selecciona una opción: ");

                        op2=teclado.next();
                        switch(op2){
                            case "0":
                                break;
                            case "1":
                                Mecanicos.listarMecanicos();
                                pause();
                                break;
                            case "2":
                                Mecanicos.listarUno();
                                pause();
                                break;
                            case "5":
                                Mecanicos.eliminarRegistro();
                                pause();
                                break;
                            default:
                                error();
                        }
                    }
                    break;
                case "5":
                    while (!op2.equals("0")) {
                        System.out.println("---- Mantenimientos ----");
                        System.out.println("1 - Listar Mantenimientos");
                        System.out.println("2 - Listar un Mantenimiento");
                        System.out.println("3 - Añadir Mantenimiento");
                        System.out.println("4 - Modificar Mantenimiento");
                        System.out.println("5 - Eliminar Mantenimiento");
                        System.out.println("0 - Volver Atrás");
                        System.out.printf("Selecciona una opción: ");

                        op2=teclado.next();
                        switch(op2){
                            case "0":
                                break;
                            case "1":
                                MantenimientoCoches.listarMantenimientos();
                                pause();
                                break;
                            case "2":
                                MantenimientoCoches.listarUno();
                                pause();
                                break;
                            case "5":
                                MantenimientoCoches.eliminarRegistro();
                                pause();
                                break;
                            default:
                                error();
                        }
                    }
                    break;
                default:
                    error();

            }
        } while (!op1.equals("0"));


    }
}
