package minesweeper;

import java.util.Arrays;
import java.util.Random;

public class MinesField {
    Random random = new Random();
    private final char[][] minesField = new char[9][9];
    int[][] binaryField = new int[9][9];
    private final char[][] hiddenMinesField = new char[9][9];

    public MinesField(int mines) {
        for (char[] chars : minesField) {
            Arrays.fill(chars, '.');
        }
        for (int i = 0; i < mines; i++) {
            while (true) {
                int x = random.nextInt(9);
                int y = random.nextInt(9);
                if (minesField[x][y] == '.') {
                    minesField[x][y] = 'X';
                    break;
                }
            }
        }

        //binaryField
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (minesField[i][j] == 'X')
                    binaryField[i][j] = 1;
                else
                    binaryField[i][j] = 0;
            }
        }

        //hiddenMinesField
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int x;
                if (binaryField[i][j] == 1) {
                    hiddenMinesField[i][j] = '.';
                } else {
                    if (i == 0 && j == 0) { //fst Corner
                        x = binaryField[i][j + 1]
                                + binaryField[i + 1][j] + binaryField[i + 1][j + 1];
                    } else if (i == 0 && j == 8) { //snd Corner
                        x = binaryField[i][j - 1] +
                                binaryField[i + 1][j - 1] + binaryField[i + 1][j];
                    } else if (i == 8 && j == 0) { //3rd Corner
                        x = binaryField[i - 1][j] + binaryField[i - 1][j + 1]
                                + binaryField[i][j + 1];
                    } else if (i == 8 && j == 8) { // 4th Corner
                        x = binaryField[i - 1][j - 1] + binaryField[i - 1][j]
                                + binaryField[i][j - 1];
                    } else if (i == 0) { //fst rom
                        x = binaryField[i][j - 1] + binaryField[i][j + 1]
                                + binaryField[i + 1][j - 1] + binaryField[i + 1][j] + binaryField[i + 1][j + 1];
                    } else if (i == 8) { //last row
                        x = binaryField[i - 1][j - 1] + binaryField[i - 1][j] + binaryField[i - 1][j + 1]
                                + binaryField[i][j - 1] + binaryField[i][j + 1];

                    } else if (j == 0) { // fst clm
                        x = binaryField[i - 1][j] + binaryField[i - 1][j + 1]
                                + binaryField[i][j + 1]
                                + binaryField[i + 1][j] + binaryField[i + 1][j + 1];
                    } else if (j == 8) { // last clm
                        x = binaryField[i - 1][j - 1] + binaryField[i - 1][j]
                                + binaryField[i][j - 1]
                                + binaryField[i + 1][j - 1] + binaryField[i + 1][j];
                    } else {
                        x = binaryField[i - 1][j - 1] + binaryField[i - 1][j] + binaryField[i - 1][j + 1]
                                + binaryField[i][j - 1] + binaryField[i][j + 1]
                                + binaryField[i + 1][j - 1] + binaryField[i + 1][j] + binaryField[i + 1][j + 1];
                    }
                    if (x == 0)
                        hiddenMinesField[i][j] = '.';
                    else
                        hiddenMinesField[i][j] = (char) (x + 48);
                }
            }
        }

    }


    public char[][] getMinesField() {
        return minesField;
    }

    public char[][] getHiddenMinesField() {
        return hiddenMinesField;
    }

    public String getOptimizedMinesField() {
        StringBuilder out = new StringBuilder();
        out.append(" |123456789|\n" +
                "-|---------|\n");
        int i = 1;
        for (char[] chars : minesField) {
            out.append(i).append('|');
            out.append(new String(chars)).append('|').append('\n');
            i++;
        }
        out.append("-|---------|");
        return out.toString().trim();
    }

    public String getOptimizedBinaryField() {
        StringBuilder out = new StringBuilder();
        out.append(" |123456789|\n" +
                "-|---------|\n");
        int i = 1;
        for (int[] ints : binaryField) {
            out.append(i).append('|');
            for (int f : ints) {
                out.append(f);
            }
            out.append('|').append('\n');
            i++;
        }
        out.append("-|---------|");
        return out.toString();
    }

    public String getOptimizedHint() {
        StringBuilder out = new StringBuilder();
        out.append(" |123456789|\n" +
                "-|---------|\n");
        int i = 1;
        for (char[] chars : hiddenMinesField) {
            out.append(i).append('|');
            out.append(new String(chars)).append('|').append('\n');
            i++;
        }
        out.append("-|---------|");
        return out.toString();
    }
}
