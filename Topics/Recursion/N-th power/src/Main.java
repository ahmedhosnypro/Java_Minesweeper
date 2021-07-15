import java.util.Scanner;

public class Main {

    public static double pow(double a, long n) {
        return Math.pow(a, n);
    }

    /* Do not change code below */
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final double a = Double.parseDouble(scanner.nextLine());
        final int n = Integer.parseInt(scanner.nextLine());
        System.out.printf("%.3f", pow(a, n));
    }
}