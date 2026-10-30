
package app;

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.format.DateTimeParseException;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    // COMMIT 1: CREACION DEL PROYECTO Y MAIN

    public static void main(String[] args) {

        int opcion;

        do {

            System.out.println("\n===== SISTEMA DE PARQUEADERO =====");
            System.out.println("1. Registrar entrada");
            System.out.println("2. Registrar salida");
            System.out.println("3. Salir");

            opcion = leerEntero("Seleccione una opcion: ", 1, 3);

            switch (opcion) {

                case 1:
                    registroEntrada();
                    break;

                case 2:
                    registroSalida();
                    break;

                case 3:
                    System.out.println("Programa finalizado.");
                    break;
            }

        } while (opcion != 3);

        scanner.close();
    }

    // COMMIT 2: CONSTANTES, VARIABLES Y ARREGLOS INICIALES

    static final int CAPACIDAD_TOTAL = 10;

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

    static int celdasOcupadas = 0;
    static int celdasLibres = CAPACIDAD_TOTAL;

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

        if (ingreso.isAfter(LocalDateTime.now())) {

            System.out.println("No se permiten fechas futuras.");
            return;
        }

        int hora = ingreso.getHour();

        if (hora < HORA_APERTURA || hora >= HORA_CIERRE) {

            System.out.println("El parqueadero funciona de "
                    + HORA_APERTURA + ":00 a "
                    + HORA_CIERRE + ":00.");

            return;
        }

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

    // BUSCAR CELDA DISPONIBLE

    public static int celdaDisponible() {

        for (int i = 0; i < CAPACIDAD_TOTAL; i++) {

            if (tiposVehiculo[i] == null) {
                return i;
            }
        }

        return -1;
    }

    // COMMIT 4: REGISTRO DE SALIDA

    public static void registroSalida() {

        System.out.println("\n--- REGISTRO DE SALIDA ---");

        if (celdasOcupadas == 0) {

            System.out.println("No hay vehiculos registrados.");
            return;
        }

        int celda = leerEntero(
                "Ingrese el numero de celda: ",
                1, CAPACIDAD_TOTAL);

        int posicion = celda - 1;

        if (tiposVehiculo[posicion] == null) {

            System.out.println("La celda esta libre.");
            return;
        }

        LocalDateTime ingreso = fechasIngreso[posicion];

        LocalDateTime salida = leerFechaHora(
                "Fecha y hora de salida (yyyy-MM-dd HH:mm): ");

        if (salida.isBefore(ingreso)) {

            System.out.println(
                    "La salida no puede ser anterior al ingreso.");
            return;
        }

        if (salida.isAfter(LocalDateTime.now())) {

            System.out.println(
                    "No se permiten fechas futuras.");
            return;
        }

        // CALCULAR TIEMPO DE PERMANENCIA

        long minutos = Duration.between(
                ingreso, salida).toMinutes();

        long horas = Math.max(1, (minutos + 59) / 60);

        long cobro;

        String tipo = tiposVehiculo[posicion];

        // CALCULAR TARIFA

        if (tipo.equals("Carro")) {

            if (horas > UMBRAL_HORAS_DIA) {

                long dias = (horas + 23) / 24;

                cobro = dias * TARIFA_DIA_CARRO;

            } else {

                cobro = horas * TARIFA_HORA_CARRO;
            }

            recaudoCarros += cobro;

        } else {

            if (horas > UMBRAL_HORAS_DIA) {

                long dias = (horas + 23) / 24;

                cobro = dias * TARIFA_DIA_MOTO;

            } else {

                cobro = horas * TARIFA_HORA_MOTO;
            }

            recaudoMotos += cobro;
        }

        // MOSTRAR DETALLE DE SALIDA

        System.out.println("\n--- DETALLE DE SALIDA ---");

        System.out.println("Celda: " + celda);
        System.out.println("Vehiculo: " + tipo);

        System.out.println("Ingreso: "
                + ingreso.format(FORMATO_FECHA));

        System.out.println("Salida: "
                + salida.format(FORMATO_FECHA));

        System.out.println("Tiempo de permanencia: "
                + horas + " horas");

        System.out.println("Total a pagar: $" + cobro);

        // LIBERAR CELDA

        tiposVehiculo[posicion] = null;
        fechasIngreso[posicion] = null;

        celdasOcupadas--;
        celdasLibres++;

        System.out.println("Salida registrada correctamente.");
    }

    // VALIDACION DE NUMEROS ENTEROS

    public static int leerEntero(
            String mensaje, int minimo, int maximo) {

        int numero;

        while (true) {

            System.out.print(mensaje);

            try {

                numero = Integer.parseInt(scanner.nextLine());

                if (numero >= minimo && numero <= maximo) {

                    return numero;

                }

                System.out.println("Ingrese un numero entre "
                        + minimo + " y " + maximo + ".");

            } catch (NumberFormatException e) {

                System.out.println(
                        "Debe ingresar un numero entero.");
            }
        }
    }

    // VALIDACION DE FECHA Y HORA

    public static LocalDateTime leerFechaHora(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String fecha = scanner.nextLine();

            try {

                return LocalDateTime.parse(
                        fecha, FORMATO_FECHA);

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Formato incorrecto. Use yyyy-MM-dd HH:mm.");
            }
        }
    }
}
