package com.example.login;

import java.util.Random;

public class Sudoku {
    private static final int GRID_SIZE = 9;
    private int[][] board = new int[GRID_SIZE][GRID_SIZE];
    private boolean[][] fixedCells = new boolean[GRID_SIZE][GRID_SIZE];

    public Sudoku() {
        generatePuzzle();
    }

    public void generatePuzzle() {
        clearBoard();
        fillDiagonalBoxes();
        solvePuzzle(0, 0);
        removeCellsForPuzzle(40);
    }

    private void clearBoard() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                board[row][col] = 0;
                fixedCells[row][col] = false;
            }
        }
    }

    private void fillDiagonalBoxes() {
        for (int i = 0; i < GRID_SIZE; i += 3) {
            fillBox(i, i);
        }
    }

    private void fillBox(int row, int col) {
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int num;
                do {
                    num = random.nextInt(9) + 1;
                } while (!isSafe(row + i, col + j, num));
                board[row + i][col + j] = num;
            }
        }
    }

    private boolean solvePuzzle(int row, int col) {
        if (row == GRID_SIZE) {
            return true;
        }

        int nextRow;
        int nextCol;

        if (col == GRID_SIZE - 1) {
            nextRow = row + 1;
            nextCol = 0;
        } else {
            nextRow = row;
            nextCol = col + 1;
        }

        if (board[row][col] != 0) {
            return solvePuzzle(nextRow, nextCol);
        }

        for (int num = 1; num <= GRID_SIZE; num++) {
            if (isSafe(row, col, num)) {
                board[row][col] = num;
                if (solvePuzzle(nextRow, nextCol)) {
                    return true;
                }
                board[row][col] = 0;
            }
        }
        return false;
    }

    public boolean isSafe(int row, int col, int num) {
        return !isInRow(row, num) && !isInCol(col, num) && !isInBox(row - row % 3, col - col % 3, num);
    }

    private boolean isInRow(int row, int num) {
        for (int col = 0; col < GRID_SIZE; col++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isInCol(int col, int num) {
        for (int row = 0; row < GRID_SIZE; row++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isInBox(int startRow, int startCol, int num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[startRow + row][startCol + col] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    private void removeCellsForPuzzle(int cellsToRemove) {
        Random random = new Random();
        while (cellsToRemove > 0) {
            int row = random.nextInt(GRID_SIZE);
            int col = random.nextInt(GRID_SIZE);
            if (board[row][col] != 0) {
                board[row][col] = 0;
                fixedCells[row][col] = false;
                cellsToRemove--;
            }
        }
    }

    public boolean isSolved() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (board[row][col] == 0 || !isSafe(row, col, board[row][col])) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] getBoard() {
        return board;
    }

    public boolean setNumber(int row, int col, int num) {
        if (fixedCells[row][col]) {
            return false;
        }
        if (isSafe(row, col, num)) {
            board[row][col] = num;
            return true;
        }
        return false;
    }
}
