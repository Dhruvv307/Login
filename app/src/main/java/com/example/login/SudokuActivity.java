package com.example.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.RoomDatabase;

import java.util.Arrays;
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

        Button saveButton = findViewById(R.id.btnSave);
        GridLayout gridLayout = findViewById(R.id.sudokuGrid);
        Sudoku sudoku = new Sudoku();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                EditText cell = new EditText(this);
                cell.setInputType(InputType.TYPE_CLASS_NUMBER);
                cell.setGravity(Gravity.CENTER);
                cell.setTextSize(18);
                cell.setTag(row + "," + col);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(row, 1f);
                params.columnSpec = GridLayout.spec(col, 1f);
                params.width = 0;
                params.height = 0;
                cell.setLayoutParams(params);
                gridLayout.addView(cell);
            }
        }
        saveButton.setOnClickListener(v -> {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    EditText cell = (EditText) gridLayout.findViewWithTag(row + "," + col);
                    String text = cell.getText().toString();
                    if (!text.isEmpty()) {
                        int num = Integer.parseInt(text);
                        if (!sudoku.setNumber(row, col, num)) {
                            cell.setError("Invalid number");
                        }
                    }
                }
            }

            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
            //    Sudoku sudoku = new Sudoku();
                int[][] board = sudoku.getBoard();
                boolean[][] fixedCells = sudoku.getFixedCells();

                SudokuPuzzle puzzle = new SudokuPuzzle();
                puzzle.setBoard(SudokuPuzzle.toJson(board));
                puzzle.setFixedCells(SudokuPuzzle.toJson(fixedCells));
                puzzle.setSolved(false);

                AppDatabase database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "mini-maroons-db").fallbackToDestructiveMigration().setJournalMode(RoomDatabase.JournalMode.TRUNCATE).build();
                database.sudokuPuzzleDao().insertPuzzle(puzzle);
            });
        });



        int[][] board = sudoku.getBoard();
        boolean[][] fixedCells = sudoku.getFixedCells();

      //  int[][] board = sudoku.getBoard();
     //   boolean[][] fixedCells = sudoku.getFixedCells();
        Log.d("SudokuActivity", "Board: " + Arrays.deepToString(board));
        Log.d("SudokuActivity", "Fixed Cells: " + Arrays.deepToString(fixedCells));

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                EditText cell = (EditText) gridLayout.findViewWithTag(row + "," + col);
                cell.setBackgroundResource(R.drawable.cell_border);
                int finalRow = row;
                int finalCol = col;
                cell.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(!s.toString().isEmpty()){
                            int num = Integer.parseInt(s.toString());
                            if(!sudoku.isSafe(finalRow, finalCol, num)){
                                cell.setError("Invalid number");
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                if (fixedCells[row][col]) {
                    cell.setText(String.valueOf(board[row][col])); // Pre-fill fixed numbers
                    cell.setEnabled(false); // Disable editing for fixed cells
                } else {
                    cell.setText("");
                    cell.setEnabled(true);
                }
            }
        }
    }
}
//}