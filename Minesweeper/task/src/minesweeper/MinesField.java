package minesweeper;

import java.util.Arrays;
import java.util.Random;

public class MinesField {
    Random random = new Random();
    private char[][] minesField = new char[9][9];

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

    public char[][] getMinesField() {
        return minesField;
    }

    public String getOptimizedMinesField(){
        StringBuilder out = new StringBuilder();
        for (char[] chars :minesField){
            out.append(new String(chars));
            out.append('\n');
        }
        return out.toString().trim();
    }
}
