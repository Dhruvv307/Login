package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.widget.CheckBox;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateAccountActivity extends AppCompatActivity {
    private AppDatabase database;
    private EditText usernameInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private CheckBox adminCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Initialize database
        database = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "mini-maroons-db")
                .fallbackToDestructiveMigration()
                .build();

        // Initialize UI elements
        usernameInput = findViewById(R.id.etNewUsername);
        passwordInput = findViewById(R.id.etNewPassword);
        confirmPasswordInput = findViewById(R.id.etConfirmPassword);
        Button createAccountButton = findViewById(R.id.btnCreateAccount);
        Button backToLoginButton = findViewById(R.id.btnBackToLogin);
        adminCheckBox = findViewById(R.id.checkBox);

        adminCheckBox.setChecked(false);

        createAccountButton.setOnClickListener(v -> handleCreateAccount());
        backToLoginButton.setOnClickListener(v -> navigateToLogin());
    }

    private void handleCreateAccount() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();
        boolean isAdmin = adminCheckBox.isChecked();

        // Validation
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showMessage("Please fill in all fields");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showMessage("Passwords do not match");
            return;
        }

        if (password.length() < 6) {
            showMessage("Password must be at least 6 characters long");
            return;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {

                User existingUser = database.userDao().getUserByUsername(username);

                if (existingUser != null) {
                    handler.post(() -> showMessage("Username already exists"));
                } else {

                    User newUser = new User(username, password, isAdmin);
                    database.userDao().insert(newUser);

                    handler.post(() -> {
                        showMessage("Account created successfully");
                        navigateToLogin();
                    });
                }
            } catch (Exception e) {
                handler.post(() -> showMessage("Error creating account: " + e.getMessage()));
            }
            executor.shutdown();
        });
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}