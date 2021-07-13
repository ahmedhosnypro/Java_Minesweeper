package minesweeper;

import java.util.Scanner;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int mines = scanner.nextInt();
        MinesField minesField = new MinesField(mines);
        System.out.println(minesField.getOptimizedMinesField());
    }
}

