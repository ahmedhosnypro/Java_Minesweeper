package minesweeper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? ");
        int mines = scanner.nextInt();
        MinesField minesField = new MinesField(mines);
//        System.out.println();
//        System.out.println(minesField.getOptimizedMinesField());
//        System.out.println();
//        System.out.println(minesField.getOptimizedBinaryField());
//        System.out.println();
//        System.out.println(minesField.getOptimizedHint());
        System.out.println();
        System.out.println(minesField.getOptimizedExploredField());
        PLay.play(minesField);
    }
}

