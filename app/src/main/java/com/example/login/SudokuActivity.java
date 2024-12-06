package com.example.login;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SudokuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sudoku);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    Button saveButton = findViewById(R.id.btnSave);
    saveButton.setOnClickListener(v -> {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Sudoku sudoku = ;
            int[][] board = sudoku.getBoard();
            boolean[][] fixedCells = sudoku.getFixedCells();

            SudokuPuzzle puzzle = new SudokuPuzzle();
            puzzle.setBoard(SudokuPuzzle.toJson(board));
            puzzle.setFixedCells(SudokuPuzzle.toJson(fixedCells));
            puzzle.setSolved(false);

            database.sudokuPuzzleDao().insertPuzzle(puzzle);
        });
    });
}