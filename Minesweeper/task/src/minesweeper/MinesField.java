package minesweeper;

import java.util.Arrays;
import java.util.Random;

public class MinesField {
    Random random = new Random();
    private final char[][] minesField = new char[9][9];

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
    }

    public String getOptimizedMinesField() {
        StringBuilder out = new StringBuilder();
        for (char[] chars : minesField) {
            out.append(new String(chars));
            out.append('\n');
        }
        return out.toString().trim();
    }

    private int[][] minesFieldToBinary() {
        int[][] out = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (minesField[i][j] == 'X')
                    out[i][j] = 1;
                else
                    out[i][j] = 0;
            }
        }
        return out;
    }

    private char[][] getHint() {
        int[][] binaryField = minesFieldToBinary();
        char[][] hints = new char[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int x;
                if (binaryField[i][j] == 1) {
                    hints[i][j] = 'X';
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
                    if(x ==0)
                        hints[i][j] = '.';
                    else
                        hints[i][j] = (char) (x +48);

                }

            }
        }

        return hints;
    }

    public String getOptimizedHint(){
        StringBuilder out = new StringBuilder();
        for (char[] chars : getHint()) {
            out.append(new String(chars));
            out.append('\n');
        }
        return out.toString().trim();
    }
}
