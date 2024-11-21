package com.example.foodproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodproject.MainActivity;
import com.example.foodproject.R;
import com.example.foodproject.database.DatabaseHelper;

public class WelcomeActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

        databaseHelper = new DatabaseHelper(this);

        if (databaseHelper.getCurrentUserId() != -1) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        new Handler().postDelayed(() -> {
            startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            finish();
        }, 2000);
    }

    public void register(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void login(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}