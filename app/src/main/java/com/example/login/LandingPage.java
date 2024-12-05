package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landing_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Intent intent = getIntent();
        User currentUser = (User) intent.getSerializableExtra("currentUser");
        //User currentUser = (User) getIntent().getSerializableExtra("currentUser");

        TextView usernameTextView = findViewById(R.id.usernameTextView);
        Button adminButton = findViewById(R.id.adminButton);


        if(currentUser != null){
            boolean isAdmin = currentUser.isAdmin();
            usernameTextView.setText("Welcome, " + currentUser.getUsername() + "!");

            if (isAdmin) {
                adminButton.setVisibility(View.VISIBLE);
            }
        } else {
            adminButton.setVisibility(View.INVISIBLE);
        }
    }
}