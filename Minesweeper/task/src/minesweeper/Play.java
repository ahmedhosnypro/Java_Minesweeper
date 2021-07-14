package minesweeper;

import java.util.Scanner;

public class Play {
    static Scanner scanner = new Scanner(System.in);

    public static void play(MinesField minesField, int mines) {
        int xCord;
        int yCord;

        boolean markedEmptyCell = false;
        int x = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c1 = minesField.getMinesField()[i][j];
                char c2 = minesField.getHiddenMinesField()[i][j];
                if (c1 == '.' && c2 == '*') {
                    markedEmptyCell = true;
                    break;
                } else if (c1 == 'X' && c2 == '*') {
                    x++;
                }
            }
        }
        if (!markedEmptyCell && x == mines) {
            System.out.println("Congratulations! You found all mines!");
        } else {
            while (true) {
                int[] cords = getCoordinates();
                if (cords[0] != -1 || cords[1] != -1) {
                    xCord = cords[0];
                    yCord = cords[1];
                    break;
                } else
                    System.out.println("check input");
            }
            if (markCell(minesField.getHiddenMinesField(), yCord - 1, xCord - 1)) {
                System.out.println();
                System.out.println(minesField.getOptimizedHint());
            }
            play(minesField, mines);
        }
    }

    public static boolean markCell(char[][] field, int xCord, int yCord) {
        boolean isMarked = false;
        char ch = field[xCord][yCord];
        if (ch >= 48 && ch <= 57) {
            System.out.println("There is a number here!");
        } else if (ch == '*') {
            field[xCord][yCord] = '.';
            isMarked = true;
        } else {
            field[xCord][yCord] = '*';
            isMarked = true;
        }
        return isMarked;
    }

    private static int[] getCoordinates() {
        System.out.print("Set/delete mines marks (x and y coordinates): ");
        int xCord = -1;
        int yCord = -1;
        try {
            xCord = Integer.parseInt(scanner.next());
            yCord = Integer.parseInt(scanner.next());
        } catch (IllegalArgumentException ignored) {
        }
        if (xCord > 9 || yCord > 9) {
            xCord = -1;
            yCord = -1;
        }
        return new int[]{xCord, yCord};
    }

}
