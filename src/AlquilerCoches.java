import java.sql.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AlquilerCoches {
    private String matricula, dni, lugarDevolucion, tipoSeguro, fechaInico, fechaFinal;
    private double precioPorDia;
    private int isRetornDipositPle;
    private static final SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
    private static final Scanner scanner = new Scanner(System.in).useDelimiter("\n");


    public AlquilerCoches(String matricula, String dni) {
        this.matricula = matricula;
        this.dni = dni;
    }

    public AlquilerCoches(String matricula, String dni, String lugarDevolucion, String tipoSeguro, int numeroDias,double precioPorDia, int isRetornDipositPle) {
        this.matricula = matricula;
        this.dni = dni;
        this.lugarDevolucion = lugarDevolucion;
        this.tipoSeguro = tipoSeguro;
        this.precioPorDia = precioPorDia;
        this.isRetornDipositPle = isRetornDipositPle;
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE, numeroDias);
        this.fechaInico = sm.format(today);
        this.fechaFinal = sm.format(c.getTime());
    }

    public static TableList table(){
        return new TableList(10,"Matrícula","Marca y Modelo","DNI","Nombre Completo Cliente","Fecha Inicio Alquiler",
                "Fecha Fin Alquiler","Precio Por Día","Lugar de Devolución","Retorno Deposito Lleno","Tipo de Seguro").sortBy(0).withUnicode(true);
    }

    public static void listarAlquileres(){
        try{
            Connection conexion = (Connection) Conexion.conectarBd();
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery("SELECT alquilercoches.matricula, alquilercoches.dni, alquilercoches.fechaInicio," +
                    "alquilercoches.fechaFinal,alquilercoches.precioPorDia,alquilercoches.lugarDevolucion,alquilercoches.isRetornDipositPle," +
                    "alquilercoches.tipoSeguro, clientes.nombreCompleto, coches.marca, coches.modelo FROM alquilercoches " +
                    "INNER JOIN clientes ON alquilerCoches.dni = clientes.dni INNER JOIN coches ON alquilerCoches.matricula = coches.matricula");
            System.out.println("---- Listado de Vehiculos Alquilados ----");
            TableList tabla = table();
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

    public static void listarUno(){
        try{
            Connection conexion = (Connection) Conexion.conectarBd();
            String matricula, dni;
            String consulta = "SELECT alquilercoches.matricula, alquilercoches.dni, alquilercoches.fechaInicio," +
            "alquilercoches.fechaFinal,alquilercoches.precioPorDia,alquilercoches.lugarDevolucion,alquilercoches.isRetornDipositPle," +
                    "alquilercoches.tipoSeguro, clientes.nombreCompleto, coches.marca, coches.modelo FROM alquilercoches " +
                    "INNER JOIN clientes ON alquilerCoches.dni = clientes.dni INNER JOIN coches ON alquilerCoches.matricula = coches.matricula " +
                    "WHERE alquilercoches.matricula = ? AND alquilercoches.dni = ?";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce la matrícula del coche alquilado: ");
            matricula = scanner.next();
            System.out.printf("Introduce el DNI del Cliente que ha realizado el alquiler: ");
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
                String isDeposito = "No";
                if(resultado.getString("isRetornDipositPle").equals("1")){
                    isDeposito = "Sí";
                }
                tabla.addRow(resultado.getString("matricula"),resultado.getString("marca") + " " +
                                resultado.getString("modelo") , resultado.getString("dni"),
                        resultado.getString("nombreCompleto"),resultado.getString("fechaInicio"),
                        resultado.getString("fechaFInal"),resultado.getString("precioPorDia") + "€",
                        resultado.getString("lugarDevolucion"),isDeposito,resultado.getString("tipoSeguro"));

            tabla.print();
            conexion.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void insertarRegistro(){
        try{
            String matricula, dni, lugarDevolucion, tipoSeguro;
            int numeroDias, isRetornDipositPle = 1;
            double precioPorDia = 50;
            boolean isCochePrestadoAhora = false;
            Connection conexion = (Connection) Conexion.conectarBd();

            String coche = "SELECT * FROM coches where matricula = ?";

            String cliente = "SELECT * FROM clientes where dni = ?";

            String isCochePrestado = "SELECT fechaFinal FROM alquilercoches where matricula = ?";

            PreparedStatement sentenciaCoche = conexion.prepareStatement(coche);

            PreparedStatement sentenciaPrestado = conexion.prepareStatement(isCochePrestado);

            PreparedStatement sentenciaIsCliente = conexion.prepareStatement(cliente);

            String consulta = "insert into alquilercoches (matricula,dni,fechaInicio,fechaFinal,precioPorDia,lugarDevolucion,isRetornDipositPle,tipoSeguro) values(?,?,?,?,?,?,?,?);";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);

            System.out.printf("Introduce la Matrícula del Vehículo: ");
            matricula = scanner.next();
            sentenciaCoche.setString(1, matricula);
            ResultSet resultCoche = sentenciaCoche.executeQuery();
            if (!resultCoche.next()){
                System.out.println("Error, El coche solicitado no existe");
                return;
            }
            sentenciaPrestado.setString(1, matricula);
            ResultSet resultPrestado = sentenciaPrestado.executeQuery();
            while(resultPrestado.next()){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Date fechaFinal = sdf.parse(resultPrestado.getString("fechaFInal"));
                Date hoy = new Date();
                long diff = hoy.getTime() - fechaFinal.getTime();
                TimeUnit time = TimeUnit.DAYS;
                long diferencia = time.convert(diff, TimeUnit.MILLISECONDS);
                //System.out.println("Dias: "+ diferencia);
                if(diferencia <= 0){
                    isCochePrestadoAhora = true;
                }
                if(isCochePrestadoAhora){
                    System.out.println("Error, el vehículo solicitado se encuentra prestado actualmente");
                    return;
                }
            }
            System.out.printf("Introduce el DNI del cliente: ");
            dni = scanner.next();
            sentenciaIsCliente.setString(1, dni);
            ResultSet resultCliente = sentenciaIsCliente.executeQuery();
            if (!resultCliente.next()){
                System.out.println("Error, El cliente solicitado no existe");
                return;
            }
            System.out.printf("Nº de días: ");
            try{
                numeroDias = scanner.nextInt();
            }catch(Exception e){
                System.out.println("Error, numero de dias no valido, se ha aplicado el valor 1 por defecto");
                numeroDias = 1;
                scanner.next();
            }System.out.printf("Precio por día: ");
            try{
                precioPorDia = scanner.nextDouble();
            }catch(Exception e){
                System.out.println("Error, precio por día no valido, se ha aplicado el valor 50.00 por defecto");
                scanner.next();
            }
            TableList tLugarDevolucion = new TableList(2,"Valor", "Ciudad").sortBy(0).withUnicode(true);
            tLugarDevolucion.addRow("1","Barcelona");
            tLugarDevolucion.addRow("2","Madrid");
            tLugarDevolucion.addRow("3","Sevilla");
            tLugarDevolucion.addRow("4","Zaragoza");
            tLugarDevolucion.addRow("5","Santander");
            tLugarDevolucion.addRow("6","Tarragona");
            System.out.println("Inserta el lugar de devolución: ");
            tLugarDevolucion.print();
            System.out.printf("Selecciona una opción: ");
            lugarDevolucion = scanner.next();
            switch (lugarDevolucion) {
                case "1" -> lugarDevolucion = "Barcelona";
                case "2" -> lugarDevolucion = "Madrid";
                case "3" -> lugarDevolucion = "Sevilla";
                case "4" -> lugarDevolucion = "Zaragoza";
                case "5" -> lugarDevolucion = "Santander";
                case "6" -> lugarDevolucion = "Tarragona";
                default -> {
                    System.out.println("Error, no has seleccionado una opción válida. Se ha aplicado Barcelona como por defecto.");
                    lugarDevolucion = "Barcelona";
                }
            }
            TableList tTipoSeguro = new TableList(2,"Valor", "Tipo de Seguro").sortBy(0).withUnicode(true);
            tTipoSeguro.addRow("1","Franquícia");
            tTipoSeguro.addRow("2","Sin Franquícia");
            System.out.println("Inserta el tipo de seguro: ");
            tTipoSeguro.print();
            System.out.printf("Selecciona una opción: ");
            tipoSeguro = scanner.next();
            switch (tipoSeguro) {
                case "1" -> tipoSeguro = "Franquícia";
                case "2" -> tipoSeguro = "Sin Franquícia";
                default -> {
                    System.out.println("Error, no has seleccionado una opción válida. Se ha aplicado Sín Franquicia como por defecto.");
                    tipoSeguro = "Sin Franquícia";
                }
            }
            System.out.printf("Debe devolver el depósito lleno? 0 -> No | 1 -> Sí : ");
            try{
                isRetornDipositPle = scanner.nextInt();
                if(isRetornDipositPle < 0 || isRetornDipositPle > 1){
                    System.out.println("Error, no se ha introducido un número válido, se ha aplicado el valor 0 por defecto");
                    numeroDias = 0;
                }
            }catch(Exception e){
                System.out.println("Error, no se ha introducido un número válido, se ha aplicado el valor 0 por defecto");
                scanner.next();
            }

            AlquilerCoches a = new AlquilerCoches(matricula, dni, lugarDevolucion, tipoSeguro, numeroDias,precioPorDia,isRetornDipositPle);
            sentencia.setString(1, a.getMatricula());
            sentencia.setString(2, a.getDni());
            sentencia.setString(3, a.getFechaInico());
            sentencia.setString(4, a.getFechaFinal());
            sentencia.setDouble(5, a.getPrecioPorDia());
            sentencia.setString(6, a.getLugarDevolucion());
            sentencia.setInt(7, a.getRetornDipositPle());
            sentencia.setString(8, a.getTipoSeguro());
            int row = sentencia.executeUpdate();
            System.out.println("Se ha alquilado el coche correctamente");
            conexion.close();
        }catch (Exception e){
            System.out.println("Error, no se ha podido insertar el registro, comprueba que los datos introducidos son correctos");
            System.out.println(e);
        }
    }
    public static void devolverCoche(){
        try{
            String matricula, dni, fechaInicio, fechaFinal, lugarDevolucion, isRetornDipositPle, fechaDevuelto, lugarDevuelto;
            int devuelto;
            double precioPorDia, precioFinal;

            Connection conexion = (Connection) Conexion.conectarBd();
            String coche = "SELECT * FROM coches where matricula = ?";
            String cliente = "SELECT * FROM clientes where dni = ?";
            String getDatosAlquiler = "SELECT * FROM alquilercoches where matricula = ? and dni = ?";
            String consulta = "UPDATE alquilercoches SET devuelto = 1, fechaDevuelto = ?, lugarDevuelto = ?, precioFinal = ? WHERE matricula = ? AND dni = ?";
            PreparedStatement sentenciaCoche = conexion.prepareStatement(coche);
            PreparedStatement sentenciaIsCliente = conexion.prepareStatement(cliente);
            PreparedStatement sentenciaDatosPrestamo = conexion.prepareStatement(getDatosAlquiler);
            PreparedStatement sentencia = conexion.prepareStatement(consulta);



            System.out.printf("Introduce la Matrícula del Vehículo: ");
            matricula = scanner.next();
            sentenciaCoche.setString(1, matricula);
            ResultSet resultCoche = sentenciaCoche.executeQuery();
            if (!resultCoche.next()){
                System.out.println("Error, El coche solicitado no existe");
                return;
            }
            System.out.printf("Introduce el DNI del cliente: ");
            dni = scanner.next();
            sentenciaIsCliente.setString(1, dni);
            ResultSet resultCliente = sentenciaIsCliente.executeQuery();
            if (!resultCliente.next()){
                System.out.println("Error, El cliente solicitado no existe");
                return;
            }
            sentenciaDatosPrestamo.setString(1, matricula);
            sentenciaDatosPrestamo.setString(2, dni);
            ResultSet resultDatosPrestamo = sentenciaDatosPrestamo.executeQuery();
            if (!resultDatosPrestamo.next()){
                System.out.println("Error, No existe ningún alquiler con los datos solicitados");
                return;
            }
            if(resultDatosPrestamo.getString("devuelto").equals("1")){
                System.out.println("Error, Este alquiler ya ha sido marcado como Devuelto.");
                System.out.println("Fecha de devolución del alquiler solicitado: " + resultDatosPrestamo.getString("fechaDevuelto"));
                return;
            }
            fechaInicio = resultDatosPrestamo.getString("fechaInicio");
            fechaFinal = resultDatosPrestamo.getString("fechaFinal");
            precioPorDia = Double.parseDouble(resultDatosPrestamo.getString("precioPorDia"));
            lugarDevolucion = resultDatosPrestamo.getString("lugarDevolucion");
            isRetornDipositPle = resultDatosPrestamo.getString("isRetornDipositPle");


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date fechaInicialD = sdf.parse(fechaInicio);
            Date fechaFinalD = sdf.parse(fechaFinal);
            Date hoy = new Date();
            long diffDeMas = hoy.getTime() - fechaFinalD.getTime();
            long diffTotalDias = fechaFinalD.getTime() - fechaInicialD.getTime();
            TimeUnit time = TimeUnit.DAYS;
            long totalDias = time.convert(diffTotalDias, TimeUnit.MILLISECONDS);
            long diferenciaDeMas = time.convert(diffDeMas, TimeUnit.MILLISECONDS);


            precioFinal = precioPorDia * totalDias;
            if(diferenciaDeMas > 0){
                System.out.println("El vehículo se ha entregado con un retraso de: " + diferenciaDeMas + " dias");
                System.out.println("Atención, Se va a proceder al cobro de 49.99€ por cada día de retraso");
                precioFinal = (49.99 * diferenciaDeMas) + precioFinal;
            }

            if(isRetornDipositPle.equals("1")) {
                System.out.printf("El cliente ha devuelto el vehículo con el deposito lleno? 0 -> No | 1 -> Sí: ");
                switch (scanner.next()) {
                    case "0" -> {
                        System.out.println("Atención, El vehículo debía ser entregado con el deposito lleno");
                        System.out.println("Atención, Aplicando tasa de 59,99€ por no entregarlo con el deposito lleno.");
                        precioFinal = precioFinal + 59.99;
                    }
                    case "1" -> {

                    }
                    default -> {
                        System.out.println("Error, no has seleccionado una opción válida. Se ha marcado entrega con depósito lleno por defecto.");
                    }
                }
            }
            System.out.printf("El vehículo ha sido devuelto en " + lugarDevolucion + "? 0 -> No | 1 -> Sí: ");
            switch (scanner.next()) {
                case "0" -> {
                    System.out.println("Atención, El vehículo debía no ha sido entregado en el lugar acordado");
                    System.out.println("Atención, Aplicando tasa de 89,99€ por no entregarlo en el lugar acordado.");
                    System.out.printf("Introduce el lugar de devolución [Barcelona, Madrid, Sevilla, Zaragoza, Santander, Tarragona]: ");
                    lugarDevuelto = scanner.next();
                    precioFinal = precioFinal + 89.99;
                }
                case "1" -> lugarDevuelto = lugarDevolucion;
                default -> {
                    System.out.println("Error, no has seleccionado una opción válida. Se ha marcado lugar de devolución como correcto.");
                    lugarDevuelto = lugarDevolucion;
                }
            }

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            DecimalFormat def=new DecimalFormat("0.00");
            double precioFinalRound = Double.parseDouble(def.format(precioFinal).replace(",","."));
            fechaDevuelto = df.format(hoy);

            sentencia.setString(1, fechaDevuelto);
            sentencia.setString(2, lugarDevuelto);
            sentencia.setDouble(3, precioFinalRound);
            sentencia.setString(4, matricula);
            sentencia.setString(5, dni);
            int row = sentencia.executeUpdate();
            System.out.println("La devolución se ha completado correctamente");
            conexion.close();
            System.out.println("El Precio Total Asciende A : " + precioFinalRound + "€");

        }catch(Exception e){
            System.out.println(e);
        }
    }
    public static void modificarRegistro(){
        try {
            Connection conexion = (Connection) Conexion.conectarBd();
            String matricula, dni, lugarDevolucion, tipoSeguro, fechaInicio, fechaFinal, isRetornDipositPle, precioPorDia;
            String matriculaMod, dniMod, lugarDevolucionMod, tipoSeguroMod, fechaInicioMod, fechaFinalMod, isRetornDipositPleMod, precioPorDiaMod;
            String preconsulta = "SELECT * FROM alquilercoches WHERE matricula = ? AND dni = ?;";
            String consulta = "UPDATE alquilercoches SET fechaInicio = ?, fechaFinal = ?, precioPorDia = ?, lugarDevolucion = ?, isRetornDipositPle = ?," +
                    " tiposeguro = ? where matricula = ? AND dni = ?;";
            PreparedStatement preSentencia = conexion.prepareStatement(preconsulta);
            PreparedStatement sentencia = conexion.prepareStatement(consulta);



            String coche = "SELECT * FROM coches where matricula = ?";
            String cliente = "SELECT * FROM clientes where dni = ?";

            PreparedStatement sentenciaCoche = conexion.prepareStatement(coche);
            PreparedStatement sentenciaCliente = conexion.prepareStatement(cliente);


            System.out.printf("Introduce la Matrícula del Vehículo: ");
            matricula = scanner.next();
            sentenciaCoche.setString(1, matricula);
            ResultSet resultCoche = sentenciaCoche.executeQuery();
            if (!resultCoche.next()){
                System.out.println("Error, El coche solicitado no existe");
                return;
            }
            System.out.printf("Introduce el DNI del cliente: ");
            dni = scanner.next();
            sentenciaCliente.setString(1, dni);
            ResultSet resultCliente = sentenciaCliente.executeQuery();
            if (!resultCliente.next()){
                System.out.println("Error, El cliente solicitado no existe");
                return;
            }

            preSentencia.setString(1, matricula);
            preSentencia.setString(2, dni);
            ResultSet resultado = preSentencia.executeQuery();

            if (resultado.next() == false){
                System.out.println("Error, No existe ningun alquiler con Matrícula: " + matricula + " y DNI: " + dni);
                return;
            }
            fechaInicio = resultado.getString("fechaInicio");
            fechaFinal = resultado.getString("fechaFinal");
            precioPorDia = resultado.getString("precioPorDia");
            lugarDevolucion = resultado.getString("lugarDevolucion");
            isRetornDipositPle = resultado.getString("isRetornDipositPle");
            tipoSeguro = resultado.getString("tipoSeguro");

            System.out.println("---- DATOS ALQUILER ANTES DE MODIFICAR ----");
            TableList tablaAntes = new TableList(8,"Matrícula","DNI","Fecha Inicio Alquiler","Fecha Final Alquiler","Precio Por Día","Lugar de Devolución","Retorno Deposito Lleno","Tipo de Seguro").sortBy(0).withUnicode(true);
            tablaAntes.addRow(matricula, dni, fechaInicio, fechaFinal, precioPorDia  + "€", lugarDevolucion, isRetornDipositPle.equals("1") ? "Sí" : "No", tipoSeguro);
            tablaAntes.print();
            main.pause();
            System.out.printf("Introduce la Fecha de Inicio [YYYY-MM-DD] [Deja en blanco para no modificar]: ");
            fechaInicioMod = scanner.next();
            if(fechaInicioMod.isEmpty()) fechaInicioMod = fechaInicio;
            System.out.printf("Introduce la Fecha Final [YYYY-MM-DD] [Deja en blanco para no modificar]: ");
            fechaFinalMod = scanner.next();
            if(fechaFinalMod.isEmpty()) fechaFinalMod = fechaFinal;
            System.out.printf("Introduce el Precio por Día [Deja en blanco para no modificar]: ");
            precioPorDiaMod = scanner.next();
            if(precioPorDiaMod.isEmpty()) precioPorDiaMod = precioPorDia;
            TableList tLugarDevolucion = new TableList(2,"Valor", "Ciudad").sortBy(0).withUnicode(true);
            tLugarDevolucion.addRow("1","Barcelona");
            tLugarDevolucion.addRow("2","Madrid");
            tLugarDevolucion.addRow("3","Sevilla");
            tLugarDevolucion.addRow("4","Zaragoza");
            tLugarDevolucion.addRow("5","Santander");
            tLugarDevolucion.addRow("6","Tarragona");
            System.out.println("Inserta el lugar de devolución: ");
            tLugarDevolucion.print();
            System.out.printf("Selecciona una opción [Deja en blanco para no modificar]: ");
            lugarDevolucionMod = scanner.next();
            switch (lugarDevolucionMod) {
                case "1" -> lugarDevolucionMod = "Barcelona";
                case "2" -> lugarDevolucionMod = "Madrid";
                case "3" -> lugarDevolucionMod = "Sevilla";
                case "4" -> lugarDevolucionMod = "Zaragoza";
                case "5" -> lugarDevolucionMod = "Santander";
                case "6" -> lugarDevolucionMod = "Tarragona";
                default -> {
                    lugarDevolucionMod = lugarDevolucion;
                }
            }
            System.out.printf("Debe devolver el depósito lleno? 0 -> No | 1 -> Sí [Deja en blanco para no modificar]: ");
            isRetornDipositPleMod = scanner.next();
            if(isRetornDipositPle.isEmpty()) isRetornDipositPleMod = isRetornDipositPle;
            System.out.printf("Tipo de Seguro: 0 -> Sin Franquícia | 1 -> Con Franquícia [Deja en blanco para no modificar]: ");
            tipoSeguroMod = scanner.next();
            switch (tipoSeguroMod) {
                case "1" -> tipoSeguroMod = "Franquícia";
                case "2" -> tipoSeguroMod = "Sin Franquícia";
                default -> {
                    tipoSeguroMod = tipoSeguro;
                }
            }
            AlquilerCoches a = new AlquilerCoches(matricula, dni, lugarDevolucionMod, tipoSeguroMod, 0,Double.parseDouble(precioPorDiaMod),Integer.parseInt(isRetornDipositPleMod));
            sentencia.setString(1, fechaInicioMod);
            sentencia.setString(2, fechaFinalMod);
            sentencia.setDouble(3, a.getPrecioPorDia());
            sentencia.setString(4, a.getLugarDevolucion());
            sentencia.setInt(5, a.getRetornDipositPle());
            sentencia.setString(6, a.getTipoSeguro());
            sentencia.setString(7, a.getMatricula());
            sentencia.setString(8, a.getDni());
            int row = sentencia.executeUpdate();
            System.out.println("Se ha modificado el registro correctamente");
            conexion.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static void eliminarRegistro(){
        try{
            String matricula, dni;
            Connection conexion = (Connection) Conexion.conectarBd();
            String consulta = "DELETE FROM alquilercoches where matricula = ? AND dni = ?";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            System.out.printf("Introduce la Matrícula del coche: ");
            matricula = scanner.next();
            System.out.printf("Introduce el DNI del Cliente: ");
            dni = scanner.next();
            AlquilerCoches a = new AlquilerCoches(matricula,dni);
            sentencia.setString(1, a.getMatricula());
            sentencia.setString(2, a.getDni());
            int row = sentencia.executeUpdate();
            System.out.println("Registro eliminado correctamente");
            conexion.close();
        }catch (Exception e){
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

    public String getLugarDevolucion() {
        return lugarDevolucion;
    }

    public void setLugarDevolucion(String lugarDevolucion) {
        this.lugarDevolucion = lugarDevolucion;
    }

    public String getTipoSeguro() {
        return tipoSeguro;
    }

    public void setTipoSeguro(String tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }

    public String getFechaInico() {
        return fechaInico;
    }

    public void setFechaInico(String fechaInico) {
        this.fechaInico = fechaInico;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public double getPrecioPorDia() {
        return precioPorDia;
    }

    public void setPrecioPorDia(double precioPorDia) {
        this.precioPorDia = precioPorDia;
    }

    public int getRetornDipositPle() {
        return isRetornDipositPle;
    }

    public void setRetornDipositPle(int retornDipositPle) {
        isRetornDipositPle = retornDipositPle;
    }
}
