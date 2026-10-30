package app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    // ---- Constantes ----
    static final int CAPACIDAD_TOTAL = 10;
    static final double TARIFA_HORA_CARRO = 4_000;
    static final double TARIFA_DIA_CARRO = 30_000;
    static final double TARIFA_HORA_MOTO = 2_000;
    static final double TARIFA_DIA_MOTO = 15_000;
    static final int UMBRAL_HORAS_DIA = 8;
    static final int HORA_APERTURA = 8;
    static final int HORA_CIERRE = 22;

    static final DateTimeFormatter FORMATO_FECHA_HORA =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    static final Scanner SC = new Scanner(System.in);


    static String[] tipoVehiculo = new String[CAPACIDAD_TOTAL];
    static LocalDateTime[] fechaIngreso = new LocalDateTime[CAPACIDAD_TOTAL];


    static int celdasOcupadas = 0;
    static int celdasLibres = CAPACIDAD_TOTAL;
    static double recaudoCarros = 0;
    static double recaudoMotos = 0;

    public static void main(String[] args) {

    }
}
