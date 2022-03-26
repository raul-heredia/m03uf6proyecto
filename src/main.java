public class main {
    public static void pressAnyKeyToContinue(){
        System.out.print("Presiona Enter Para Continuar...");
        try{
            System.in.read();
        } catch(Exception e){}
    }
    public static void main(String[] args) {
        Clientes.listarClientes();
        Coches.listarCoches();
        AlquilerCoches.listarAlquileres();
    }
}
