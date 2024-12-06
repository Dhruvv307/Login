package com.example.login;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SudokuPuzzleDao {
    @Insert
    void insertPuzzle(SudokuPuzzle puzzle);

    @Query("SELECT * FROM sudoku_puzzles WHERE id = :id")
    SudokuPuzzle getPuzzleById(int id);

    @Query("SELECT * FROM sudoku_puzzles")
    List<SudokuPuzzle> getAllPuzzles();

    @Delete
    void deletePuzzle(SudokuPuzzle puzzle);

    @Query("UPDATE sudoku_puzzles SET isSolved = :isSolved WHERE id = :id")
    void updateSolvedStatus(int id, boolean isSolved);
}
