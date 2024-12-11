package com.example.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdminStuff extends AppCompatActivity {
    private Button clearData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_stuff);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        clearData = findViewById(R.id.btnClearData);

        clearData.setOnClickListener(v -> {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(()->{
                AppDatabase database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "mini-maroons-db").fallbackToDestructiveMigration().build();

                database.userDao().clearAllUsers();
                database.sudokuPuzzleDao().clearAllPuzzles();

                runOnUiThread(() -> {
                    Toast.makeText(AdminStuff.this, "All data cleared!", Toast.LENGTH_SHORT).show();
                });
            });
        });
    }
}