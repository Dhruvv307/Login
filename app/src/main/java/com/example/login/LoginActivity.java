package com.example.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {
    private AppDatabase database;
    private SharedPreferences sharedPreferences;
    private EditText usernameInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        database = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "mini-maroons-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        sharedPreferences = getSharedPreferences("MiniMaroons", MODE_PRIVATE);

        usernameInput = findViewById(R.id.etUsername);
        passwordInput = findViewById(R.id.etPassword);
        Button loginButton = findViewById(R.id.btnLogin);
        Button createAccountButton = findViewById(R.id.btnGoToCreateAccount);

        loginButton.setOnClickListener(v -> handleLogin());

        createAccountButton.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
        });
    }


    private void handleLogin() {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            showMessage("Please fill in all fields");
            return;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            User user = database.userDao().login(username, password);

            handler.post(() -> {
                if (user != null) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("logged_in_user", username);
                    editor.putBoolean("is_admin", user.isAdmin());
                    editor.apply();

                    startActivity(new Intent(LoginActivity.this, LandingActivity.class));
                    finish();
                } else {
                    showMessage("Invalid username or password");
                }
            });
        });
        executor.shutdown();
    }



    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}