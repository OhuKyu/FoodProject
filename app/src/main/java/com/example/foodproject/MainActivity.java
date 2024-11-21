package com.example.foodproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodproject.activities.ProductManagementActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.foodproject.databinding.ActivityMainBinding;
import com.example.foodproject.database.DatabaseHelper;
import com.example.foodproject.activities.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        databaseHelper = new DatabaseHelper(this);
        
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        
        // Thêm xử lý nút đăng xuất
        View headerView = navigationView.getHeaderView(0);
        Button logoutBtn = binding.navView.findViewById(R.id.logout_button);
        logoutBtn.setOnClickListener(v -> {
            databaseHelper.saveCurrentUserId(-1);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_daily_meal, R.id.nav_fauvorite, R.id.nav_my_cart)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.add(0, R.id.action_reset_db, 0, "Reset Database")
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_product_management) {
            // Kiểm tra quyền admin trước khi cho phép truy cập
            if (databaseHelper.isAdmin(databaseHelper.getCurrentUserId())) {
                startActivity(new Intent(this, ProductManagementActivity.class));
            } else {
                Toast.makeText(this, "Bạn không có quyền truy cập chức năng này", 
                    Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        else if (id == R.id.action_reset_db) {
            new AlertDialog.Builder(this)
                .setTitle("Xác nhận reset")
                .setMessage("Bạn có chắc muốn xóa tất cả dữ liệu và tạo lại database?")
                .setPositiveButton("Đồng ý", (dialog, which) -> {
                    databaseHelper.resetDatabase();
                    Toast.makeText(this, "Đã reset database", Toast.LENGTH_SHORT).show();
                    recreate();
                })
                .setNegativeButton("Hủy", null)
                .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}