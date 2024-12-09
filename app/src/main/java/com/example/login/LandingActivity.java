package com.example.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LandingActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private Button adminButton;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        sharedPreferences = getSharedPreferences("MiniMaroons", MODE_PRIVATE);

        welcomeText = findViewById(R.id.tvWelcome);
        adminButton = findViewById(R.id.btnAdmin);
        Button logoutButton = findViewById(R.id.btnLogout);
        Button sudokuButton = findViewById(R.id.btnPlaySudoku);

        String username = sharedPreferences.getString("logged_in_user", "");
        boolean isAdmin = sharedPreferences.getBoolean("is_admin", false);

        welcomeText.setText("Welcome, " + username);


        adminButton.setVisibility(isAdmin ? View.VISIBLE : View.INVISIBLE);

        adminButton.setOnClickListener(v -> {

            showMessage("Accessing admin area");
        });

        logoutButton.setOnClickListener(v -> handleLogout());


        sudokuButton.setOnClickListener(v -> {
            startActivity(new Intent(LandingActivity.this, SudokuActivity.class));
        });
    }

    private void handleLogout() {
        // Clear shared preferences
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