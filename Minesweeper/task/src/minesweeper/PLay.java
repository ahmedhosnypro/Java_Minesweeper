package minesweeper;

import java.util.Scanner;

public class PLay {
    static Scanner scanner = new Scanner(System.in);

    public static void play(MinesField minesField) {
        if (isAllFree(minesField) || isOnlyMinesMarked(minesField)) {
            System.out.println("Congratulations! You found all the mines!");
        } else {
            int xCord;
            int yCord;
            while (true) {
                int[] cords = getCoordinates();
                if (cords[0] != -1 || cords[1] != -1) {
                    xCord = cords[0];
                    yCord = cords[1];
                    break;
                } else
                    System.out.println("check input");
            }

            String options = scanner.next();
            switch (options) {
                case "free":
                    free(minesField, xCord, yCord);
                    break;
                case "mine":
                    mine(minesField, xCord, yCord);
                    break;
                default:
                    System.out.println("check input");
            }
        }

    }

    private static boolean isAllFree(MinesField minesField) {
        boolean isAllFree = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char explored = minesField.getExploredField()[i][j];
                char original = minesField.getMinesField()[i][j];
                if ((original != '.' || (explored != '/' && (explored < 48 || explored > 57)))
                        && (original != 'X' || explored != '.')) {
                    isAllFree = false;
                    break;
                }
            }
        }
        return isAllFree;
    }

    private static boolean isOnlyMinesMarked(MinesField minesField) {
        boolean isOnlyMinesMarked = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char explored = minesField.getExploredField()[i][j];
                char original = minesField.getMinesField()[i][j];
                if (original != 'X' || explored != '*') {
                    if (original == 'X') {
                        isOnlyMinesMarked = false;
                        break;
                    } else if (explored == '*') {
                        isOnlyMinesMarked = false;
                        break;
                    }
                }
            }
        }

        return isOnlyMinesMarked;
    }

    private static void free(MinesField minesField, int xCord, int yCord) {
        if (isMine(minesField, xCord, yCord)) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (minesField.getMinesField()[i][j] == 'X')
                        minesField.getExploredField()[i][j] = 'X';
                }
            }
            System.out.println();
            System.out.println(minesField.getOptimizedExploredField());
            System.out.println("You stepped on a mine and failed!");
        } else if (isEmptyAndMinesAround(minesField, xCord, yCord)) {
            minesField.getExploredField()[xCord][yCord] = minesField.getHiddenMinesField()[xCord][yCord];
            System.out.println();
            System.out.println(minesField.getOptimizedExploredField());
            play(minesField);
        } else if (isEmptyAndNoMinesAround(minesField, xCord, yCord)) {
            minesField.getExploredField()[xCord][yCord] = '/';
            for (int i = 0; i < 81; i++) {
                patch(minesField);
            }
            System.out.println();
            System.out.println(minesField.getOptimizedExploredField());
            play(minesField);
        }
    }

    private static void mine(MinesField minesField, int xCord, int yCord) {
        if (isMarked(minesField, xCord, yCord)) {
            minesField.getMarkedMines()[xCord][yCord] = '.';
            minesField.getExploredField()[xCord][yCord] = '.';
        } else {
            minesField.getMarkedMines()[xCord][yCord] = '*';
            minesField.getExploredField()[xCord][yCord] = '*';
        }

        System.out.println();
        System.out.println(minesField.getOptimizedExploredField());
        play(minesField);
    }

    private static void patchFreeTool(MinesField minesField, int xCord, int yCord) {
        if (isEmptyAndMinesAround(minesField, xCord, yCord)) {
            minesField.getExploredField()[xCord][yCord] = minesField.getHiddenMinesField()[xCord][yCord];
        } else if (isEmptyAndNoMinesAround(minesField, xCord, yCord)) {
            minesField.getExploredField()[xCord][yCord] = '/';
        }
    }

    private static void patch(MinesField minesField) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char notExplored = minesField.getExploredField()[i][j];
                if (notExplored == '.' || notExplored == '*') {
                    char north, south, east, west, northEast, northWest, southEast, southWest;
                    if (i == 0 && j == 0) { //fst Corner
                        south = minesField.getExploredField()[i + 1][j];
                        east = minesField.getExploredField()[i][j + 1];
                        southEast = minesField.getExploredField()[i + 1][j + 1];
                        if (south == '/' || east == '/' || southEast == '/') {
                            patchFreeTool(minesField, i, j);
                        }
                    } else if (i == 0 && j == 8) { //snd Corner
                        south = minesField.getExploredField()[i + 1][j];
                        west = minesField.getExploredField()[i][j - 1];
                        southWest = minesField.getExploredField()[i + 1][j - 1];
                        if (south == '/' || west == '/' || southWest == '/') {
                            patchFreeTool(minesField, i, j);
                        }
                    } else if (i == 8 && j == 0) { //3rd corner
                        north = minesField.getExploredField()[i - 1][j];
                        east = minesField.getExploredField()[i][j + 1];
                        northEast = minesField.getExploredField()[i - 1][j + 1];
                        if (north == '/' || east == '/' || northEast == '/') {
                            patchFreeTool(minesField, i, j);
                        }
                    } else if (i == 8 && j == 8) { //4th corner
                        north = minesField.getExploredField()[i - 1][j];
                        west = minesField.getExploredField()[i][j - 1];
                        northWest = minesField.getExploredField()[i - 1][j - 1];
                        if (north == '/' || west == '/' || northWest == '/') {
                            patchFreeTool(minesField, i, j);
                        }
                    } else if (i == 0) { //fst Row
                        south = minesField.getExploredField()[i + 1][j];
                        east = minesField.getExploredField()[i][j + 1];
                        southEast = minesField.getExploredField()[i + 1][j + 1];
                        west = minesField.getExploredField()[i][j - 1];
                        southWest = minesField.getExploredField()[i + 1][j - 1];
                        if (south == '/' || east == '/' || southEast == '/' || southWest == '/' || west == '/') {
                            patchFreeTool(minesField, i, j);
                        }
                    } else if (i == 8) {//last Row
                        east = minesField.getExploredField()[i][j + 1];
                        west = minesField.getExploredField()[i][j - 1];
                        north = minesField.getExploredField()[i - 1][j];
                        northEast = minesField.getExploredField()[i - 1][j + 1];
                        northWest = minesField.getExploredField()[i - 1][j - 1];
                        if (northWest == '/' || northEast == '/' || north == '/' || west == '/' || east == '/') {
                            patchFreeTool(minesField, i, j);
                        }
                    } else if (j == 0) { //fst Clm
                        east = minesField.getExploredField()[i][j + 1];
                        north = minesField.getExploredField()[i - 1][j];
                        northEast = minesField.getExploredField()[i - 1][j + 1];
                        south = minesField.getExploredField()[i + 1][j];
                        southEast = minesField.getExploredField()[i + 1][j + 1];
                        if (southEast == '/' || south == '/' || northEast == '/' || north == '/' || east == '/') {
                            patchFreeTool(minesField, i, j);
                        }
                    } else if (j == 8) { //last Clm
                        north = minesField.getExploredField()[i - 1][j];
                        south = minesField.getExploredField()[i + 1][j];
                        west = minesField.getExploredField()[i][j - 1];
                        northWest = minesField.getExploredField()[i - 1][j - 1];
                        southWest = minesField.getExploredField()[i + 1][j - 1];
                        if (southWest == '/' || northWest == '/' || west == '/' || south == '/' || north == '/') {
                            patchFreeTool(minesField, i, j);
                        }
                    } else {
                        north = minesField.getExploredField()[i - 1][j];
                        south = minesField.getExploredField()[i + 1][j];
                        east = minesField.getExploredField()[i][j + 1];
                        west = minesField.getExploredField()[i][j - 1];
                        northEast = minesField.getExploredField()[i - 1][j + 1];
                        northWest = minesField.getExploredField()[i - 1][j - 1];
                        southEast = minesField.getExploredField()[i + 1][j + 1];
                        southWest = minesField.getExploredField()[i + 1][j - 1];
                        if (southWest == '/' || southEast == '/' || northWest == '/' || northEast == '/' || west == '/'
                                || east == '/' || south == '/' || north == '/') {
                            patchFreeTool(minesField, i, j);
                        }
                    }
                }
            }
        }
    }

    private static boolean isEmptyAndNoMinesAround(MinesField minesField, int xCord, int yCord) {
        int n = minesField.getBinaryField()[xCord][yCord];
        int ch = minesField.getHiddenMinesField()[xCord][yCord];
        return n == 0 && (ch == '.');
    }

    private static boolean isEmptyAndMinesAround(MinesField minesField, int xCord, int yCord) {
        int n = minesField.getBinaryField()[xCord][yCord];
        int ch = minesField.getHiddenMinesField()[xCord][yCord];
        return n == 0 && (ch >= 48 && ch <= 57);
    }

    private static boolean isMine(MinesField minesField, int xCord, int yCord) {
        int n = minesField.getBinaryField()[xCord][yCord];
        return n == 1;
    }

    private static boolean isMarked(MinesField minesField, int xCord, int yCord) {
        return minesField.getMarkedMines()[xCord][yCord] == '*';
    }

    private static int[] getCoordinates() {
        System.out.print("Set/unset mines marks or claim a cell as free: ");
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
        return new int[]{yCord - 1, xCord - 1};
    }
}