package com.example.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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
   //     database = Room.databaseBuilder(getApplicationContext(),
   //             AppDatabase.class, "mini-maroons-db").build();

        database = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "mini-maroons-db")
                .allowMainThreadQueries() // Optional: For debugging only, remove in production
                .fallbackToDestructiveMigration()
                .build();

        // Check if user is already logged in
        if (sharedPreferences.contains("logged_in_user")) {
            startActivity(new Intent(this, LandingPage.class));
            finish();
            return;
        }


        initializePredefinedUsers();

        Button loginButton = findViewById(R.id.btnLogin);
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

    }
}

