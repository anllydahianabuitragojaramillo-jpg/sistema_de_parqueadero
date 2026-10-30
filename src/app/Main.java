package app;

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class Main {

    static Scanner scanner = new Scanner(System.in);



    public static void main(String[] args) {

        int opcion;

        do {

            System.out.println("\n===== SISTEMA DE PARQUEADERO =====");
            System.out.println("1. Registrar entrada");
            System.out.println("2. Registrar salida");
            System.out.println("3. Reporte de ocupacion");
            System.out.println("4. Reporte financiero");
            System.out.println("5. Salir");

            opcion = leerEntero("Seleccione una opcion: ", 1, 5);

            switch (opcion) {

                case 1:
                    registroEntrada();
                    break;

                case 2:
                    registroSalida();
                    break;

                case 3:
                    reporteOcupacion();
                    break;

                case 4:
                    reporteFinanciero();
                    break;

                case 5:
                    System.out.println("Programa finalizado.");
                    break;
            }

        } while (opcion != 5);

        scanner.close();
    }



    public static int leerEntero(String mensaje, int minimo, int maximo) {

        int numero;

        while (true) {

            System.out.print(mensaje);

            try {

                numero = Integer.parseInt(scanner.nextLine());

                if (numero >= minimo && numero <= maximo) {
                    return numero;
                }

                System.out.println("Ingrese un valor entre "
                        + minimo + " y " + maximo + ".");

            } catch (NumberFormatException e) {

                System.out.println("Error: debe ingresar un numero entero.");

            }
        }
    }


    static final int CAPACIDAD_TOTAL = 10;

    // Tarifas para carros

    static final int TARIFA_HORA_CARRO = 4000;
    static final int TARIFA_DIA_CARRO = 30000;



    static final int TARIFA_HORA_MOTO = 2000;
    static final int TARIFA_DIA_MOTO = 15000;



    static final int UMBRAL_HORAS_DIA = 8;
    static final int HORA_APERTURA = 8;
    static final int HORA_CIERRE = 22;



    static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT);



    static String[] tiposVehiculo =
            new String[CAPACIDAD_TOTAL];

    static LocalDateTime[] fechasIngreso =
            new LocalDateTime[CAPACIDAD_TOTAL];

    // Variables de ocupacion

    static int celdasOcupadas = 0;
    static int celdasLibres = CAPACIDAD_TOTAL;

    // Variables de recaudo

    static long recaudoCarros = 0;
    static long recaudoMotos = 0;

    // COMMIT 3: REGISTRO DE ENTRADA

    public static void registroEntrada() {

        if (celdasLibres == 0) {

            System.out.println("El parqueadero esta lleno.");
            return;
        }

        System.out.println("\n--- REGISTRO DE ENTRADA ---");
        System.out.println("1. Carro");
        System.out.println("2. Moto");

        int opcion = leerEntero(
                "Seleccione el tipo de vehiculo: ", 1, 2);

        String tipo;

        if (opcion == 1) {
            tipo = "Carro";
        } else {
            tipo = "Moto";
        }

        LocalDateTime ingreso = leerFechaHora(
                "Fecha y hora de ingreso (yyyy-MM-dd HH:mm): ");

        int posicion = celdaDisponible();

        if (posicion == -1) {

            System.out.println("No hay celdas disponibles.");
            return;
        }

        tiposVehiculo[posicion] = tipo;
        fechasIngreso[posicion] = ingreso;

        celdasOcupadas++;
        celdasLibres--;

        System.out.println("\nEntrada registrada correctamente.");
        System.out.println("Celda asignada: " + (posicion + 1));
        System.out.println("Tipo de vehiculo: " + tipo);
        System.out.println("Fecha de ingreso: "
                + ingreso.format(FORMATO_FECHA));
    }


    public static int celdaDisponible() {

        for (int i = 0; i < CAPACIDAD_TOTAL; i++) {

            if (tiposVehiculo[i] == null) {
                return i;
            }
        }

        return -1;
    }



    public static LocalDateTime leerFechaHora(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String fechaTexto = scanner.nextLine();

            try {

                return LocalDateTime.parse(
                        fechaTexto, FORMATO_FECHA);

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Fecha u hora incorrecta. "
                                + "Use el formato yyyy-MM-dd HH:mm.");

            }
        }
    }



    public static void registroSalida() {

        System.out.println("\nModulo de registro de salida.");
    }

    public static void reporteOcupacion() {

        System.out.println("\nModulo de reporte de ocupacion.");
    }

    public static void reporteFinanciero() {

        System.out.println("\nModulo de reporte financiero.");
    }

}