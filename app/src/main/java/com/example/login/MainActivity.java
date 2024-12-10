package com.example.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MiniMaroons", MODE_PRIVATE);

        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "mini-maroons-db").fallbackToDestructiveMigration().build();


        if (sharedPreferences.contains("logged_in_user")) {
            startActivity(new Intent(this, LandingActivity.class));
            finish();
            return;
        }

        initializePredefinedUsers();

        Button loginButton = findViewById(R.id.btnLogin);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button createAccountButton = findViewById(R.id.btnCreateAccount);

        loginButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

        createAccountButton.setOnClickListener(v -> {
            showCreateAccountDialog();
        });
    }

    private void initializePredefinedUsers() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            UserDao userDao = database.userDao();
            if (userDao.getAllUsers().isEmpty()) {
                userDao.insert(new User("testuser1", "testuser1", false));
                userDao.insert(new User("admin2", "admin2", true));
            }
        });
        executor.shutdown();
    }
        private void showCreateAccountDialog() {
            startActivity(new Intent(MainActivity.this, CreateAccountActivity.class));
        }
}
