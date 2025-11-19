package ar.edu.unju.escmi.tp8.util;

import java.util.Scanner;

public class InputUtil {
    private static final Scanner sc = new Scanner(System.in);

    public static int inputInt(String msg) {
        System.out.print(msg + ": ");
        while (!sc.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            sc.next();
        }
        int v = sc.nextInt();
        sc.nextLine();
        return v;
    }

    public static long inputLong(String msg) {
        System.out.print(msg + ": ");
        while (!sc.hasNextLong()) {
            System.out.print("Ingrese un número válido: ");
            sc.next();
        }
        long v = sc.nextLong();
        sc.nextLine();
        return v;
    }

    public static String inputString(String msg) {
        System.out.print(msg + ": ");
        return sc.nextLine();
    }

    public static double inputDouble(String msg) {
        System.out.print(msg + ": ");
        while (!sc.hasNextDouble()) {
            System.out.print("Ingrese un número decimal válido: ");
            sc.next();
        }
        double v = sc.nextDouble();
        sc.nextLine();
        return v;
    }
}
