package com.example.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LandingActivity extends AppCompatActivity {
        private SharedPreferences sharedPreferences;
        private Button adminButton;
        private TextView welcomeText;
        private Button playButton;
        private Button loadButton;
        private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        sharedPreferences = getSharedPreferences("MiniMaroons", MODE_PRIVATE);

            welcomeText = findViewById(R.id.tvWelcome);
            adminButton = findViewById(R.id.btnAdmin);
            logoutButton = findViewById(R.id.btnLogout);
            loadButton = findViewById(R.id.btnLoad);
            playButton = findViewById(R.id.btnPlay);

        String username = sharedPreferences.getString("logged_in_user", "");
        boolean isAdmin = sharedPreferences.getBoolean("is_admin", false);

        welcomeText.setText("Welcome, " + username);

            adminButton.setVisibility(isAdmin ? View.VISIBLE : View.INVISIBLE);

            adminButton.setOnClickListener(v -> {
                showMessage("Accessing admin area");
            });

            playButton.setOnClickListener(v -> {
                startActivity(new Intent(LandingActivity.this, SudokuActivity.class));
                finish();
            });

            logoutButton.setOnClickListener(v -> handleLogout());

            loadButton.setOnClickListener(v -> {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {

                    AppDatabase database = Room.databaseBuilder(getApplicationContext(),
                            AppDatabase.class, "mini-maroons-db").fallbackToDestructiveMigration().setJournalMode(RoomDatabase.JournalMode.TRUNCATE).build();
                    //SudokuPuzzle savedPuzzle = database.sudokuPuzzleDao().getPuzzleById(1);
                    List<SudokuPuzzle> puzzles = database.sudokuPuzzleDao().getAllPuzzles();

                    runOnUiThread(() -> {
                        if (puzzles.isEmpty()) {
                            Toast.makeText(this, "No saved puzzles found.", Toast.LENGTH_SHORT).show();
                        } else {
                            RecyclerView recyclerView = findViewById(R.id.rvSudokuPuzzles);
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setLayoutManager(new LinearLayoutManager(this));
                            recyclerView.setAdapter(new SudokuPuzzleAdapter(this, puzzles));
                        }
                    });

                });
            });
        }

        private void handleLogout() {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            startActivity(new Intent(LandingActivity.this, MainActivity.class));
            finish();
        }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

