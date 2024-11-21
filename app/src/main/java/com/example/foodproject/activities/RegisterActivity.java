package com.example.foodproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodproject.MainActivity;
import com.example.foodproject.R;
import com.example.foodproject.database.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText, usernameEditText, passwordEditText;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        databaseHelper = new DatabaseHelper(this);
        
        emailEditText = findViewById(R.id.editText);
        usernameEditText = findViewById(R.id.editText2);
        passwordEditText = findViewById(R.id.editText3);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void login(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void mainActivity(View view) {
        String email = emailEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (databaseHelper.checkEmail(email)) {
            Toast.makeText(this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        if (databaseHelper.checkUsername(username)) {
            Toast.makeText(this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        if (databaseHelper.addUser(email, username, password)) {
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}