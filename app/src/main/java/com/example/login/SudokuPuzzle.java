package com.example.login;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.Gson;

@Entity(tableName = "sudoku_puzzles")
public class SudokuPuzzle {
    @PrimaryKey(autoGenerate = true)
    private int id;


    private String board;
    private String fixedCells;
    private boolean isSolved;


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getFixedCells() {
        return fixedCells;
    }

    public void setFixedCells(String fixedCells) {
        this.fixedCells = fixedCells;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public static String toJson(int[][] board) {
        Gson gson = new Gson();
        return gson.toJson(board);
    }

    public static int[][] fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, int[][].class);
    }

    public static String toJson(boolean[][] fixedCells) {
        Gson gson = new Gson();
        return gson.toJson(fixedCells);
    }

    public static boolean[][] fromJsonFixedCells(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, boolean[][].class);
    }

}

