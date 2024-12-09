package com.example.login;

import java.util.HashSet;
import java.util.Random;

public class SudokuGame {
    private int[][] puzzle = new int[9][9];
    private int[][] solution = new int[9][9];
    private Random random = new Random();

    public void generateNewPuzzle() {
        clearPuzzle();
        generateSolution(0, 0);
        createPuzzleFromSolution();
    }

    private void clearPuzzle() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                puzzle[i][j] = 0;
                solution[i][j] = 0;
            }
        }
    }

    private boolean generateSolution(int row, int col) {
        if (col >= 9) {
            row++;
            col = 0;
        }

        if (row >= 9) {
            return true;
        }

        for (int num = 1; num <= 9; num++) {
            solution[row][col] = num;
            if (isValidBoard(solution)) {
                if (generateSolution(row, col + 1)) {
                    return true;
                }
            }
            solution[row][col] = 0;
        }
        return false;
    }

    private void createPuzzleFromSolution() {
        for (int i = 0; i < 9; i++) {
            System.arraycopy(solution[i], 0, puzzle[i], 0, 9);
        }


        int cellsToRemove = 40;
        while (cellsToRemove > 0) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);
            if (puzzle[row][col] != 0) {
                puzzle[row][col] = 0;
                cellsToRemove--;
            }
        }
    }

    private boolean isValidBoard(int[][] board) {
        for (int eachSubArray = 0; eachSubArray < board.length; eachSubArray++) {
            HashSet<Integer> horizontalSet = new HashSet<>();
            HashSet<Integer> verticalSet = new HashSet<>();
            HashSet<Integer> boxSet = new HashSet<>();

            for (int eachChar = 0; eachChar < board[0].length; eachChar++) {

                if (board[eachSubArray][eachChar] != 0) {
                    if (!horizontalSet.add(board[eachSubArray][eachChar])) {
                        return false;
                    }
                }


                if (board[eachChar][eachSubArray] != 0) {
                    if (!verticalSet.add(board[eachChar][eachSubArray])) {
                        return false;
                    }
                }


                int rowIndex = 3 * (eachSubArray / 3);
                int colIndex = 3 * (eachSubArray % 3);
                int boxValue = board[rowIndex + eachChar / 3][colIndex + eachChar % 3];
                if (boxValue != 0) {
                    if (!boxSet.add(boxValue)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isValid(int[][] board, int row, int col, int num) {

        board[row][col] = num;
        boolean valid = isValidBoard(board);

        if (!valid) {
            board[row][col] = 0;
        }
        return valid;
    }

    public boolean isSolved() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (puzzle[i][j] != solution[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] getPuzzle() {
        return puzzle;
    }

    public void setNumber(int row, int col, int number) {
        if (row >= 0 && row < 9 && col >= 0 && col < 9) {
            puzzle[row][col] = number;
        }
    }

    public void setNumber(int number) {

    }
}