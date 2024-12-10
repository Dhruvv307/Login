package com.example.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

public class SudokuActivity extends AppCompatActivity {
    private SudokuCell[][] cells = new SudokuCell[9][9];
    private Button[] numberButtons = new Button[9];
    private Button clearButton;
    private Button newGameButton;
    private Button checkButton;
    private SudokuGame game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        game = new SudokuGame();
        initializeGrid();
        initializeNumberPad();
        initializeButtons();
        startNewGame();
    }

    private void initializeGrid() {
        GridLayout gridLayout = findViewById(R.id.sudoku_grid);
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                SudokuCell cell = new SudokuCell(this);
                cell.setTextSize(20);
                cell.setPadding(8, 8, 8, 8);
                cell.setBackgroundResource(R.drawable.sudoku_cell_selected_background);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = 0;
                params.rowSpec = GridLayout.spec(row, 1f);
                params.columnSpec = GridLayout.spec(col, 1f);
                params.setMargins(2, 2, 2, 2);

                cell.setLayoutParams(params);
                cells[row][col] = cell;
                gridLayout.addView(cell);

                final int finalRow = row;
                final int finalCol = col;
                cell.setOnClickListener(v -> onCellClick(finalRow, finalCol));
            }
        }
    }

    private void initializeNumberPad() {
        GridLayout numberPad = findViewById(R.id.number_pad);
        for (int i = 0; i < 9; i++) {
            Button button = new Button(this);
            button.setText(String.valueOf(i + 1));
            button.setTextSize(18);
            button.setBackgroundResource(R.drawable.number_button_background);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(i % 3, 1f);
            params.rowSpec = GridLayout.spec(i / 3, 1f);
            params.setMargins(4, 4, 4, 4);

            button.setLayoutParams(params);
            numberButtons[i] = button;
            numberPad.addView(button);

            final int number = i + 1;
            button.setOnClickListener(v -> onNumberClick(number));
        }
    }

    private void initializeButtons() {
        clearButton = findViewById(R.id.clear_button);
        newGameButton = findViewById(R.id.new_game_button);
        checkButton = findViewById(R.id.check_button);

        clearButton.setOnClickListener(v -> clearSelectedCell());
        newGameButton.setOnClickListener(v -> startNewGame());
        checkButton.setOnClickListener(v -> checkSolution());
    }

    private void startNewGame() {
        game.generateNewPuzzle();
        updateUI();
    }

    private void updateUI() {
        int[][] puzzle = game.getPuzzle();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col].setValue(puzzle[row][col]);
                cells[row][col].setLocked(puzzle[row][col] != 0);
            }
        }
    }

    private SudokuCell selectedCell = null;

    private void onCellClick(int row, int col) {
        if (selectedCell != null) {
            selectedCell.setSelected(false);
        }
        selectedCell = cells[row][col];
        selectedCell.setSelected(true);
    }

    private void onNumberClick(int number) {
        if (selectedCell != null && !selectedCell.isLocked()) {
            selectedCell.setValue(number);
            game.setNumber(number);
        }
    }

    private void clearSelectedCell() {
        if (selectedCell != null && !selectedCell.isLocked()    ) {
            selectedCell.setValue(0);
        }
    }

    private void checkSolution() {
        if (game.isSolved()) {
            Toast.makeText(this, "Congratulations! Puzzle solved!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not quite right, keep trying!", Toast.LENGTH_SHORT).show();
        }
    }
}