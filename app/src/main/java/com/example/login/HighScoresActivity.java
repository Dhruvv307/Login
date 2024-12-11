package com.example.login;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HighScoresActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HighScoreAdapter adapter;
    private Spinner spinnerDifficulty;
    private AppDatabase database;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        database = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "mini-maroons-db")
                .fallbackToDestructiveMigration()
                .build();
        executorService = Executors.newSingleThreadExecutor();

        recyclerView = findViewById(R.id.rvHighScores);
        spinnerDifficulty = findViewById(R.id.spinnerDifficulty);
        Button btnBack = findViewById(R.id.btnBack);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HighScoreAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"All", "Easy", "Medium", "Hard"});
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(spinnerAdapter);

        spinnerDifficulty.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                String selectedDifficulty = parent.getItemAtPosition(position).toString();
                loadHighScores(selectedDifficulty);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        });

        btnBack.setOnClickListener(v -> finish());


        loadHighScores("All");
    }

    private void loadHighScores(String difficulty) {
        executorService.execute(() -> {
            List<HighScore> scores;
            if (difficulty.equals("All")) {
                scores = database.highScoreDao().getTopScores(20);
            } else {
                scores = database.highScoreDao().getTopScoresByDifficulty(difficulty, 20);
            }

            runOnUiThread(() -> adapter.updateScores(scores));
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}