package com.example.foodproject.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodproject.R;
import com.example.foodproject.adapters.ProductManagementAdapter;
import com.example.foodproject.database.DatabaseHelper;
import com.example.foodproject.models.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProductManagementActivity extends AppCompatActivity implements ProductChangeListener {
    private RecyclerView recyclerView;
    private ProductManagementAdapter adapter;
    private DatabaseHelper databaseHelper;
    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_management);

        databaseHelper = new DatabaseHelper(this);
        products = databaseHelper.getAllProducts();

        recyclerView = findViewById(R.id.products_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductManagementAdapter(this, products);
        adapter.setProductChangeListener(this);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab_add_product);
        fab.setOnClickListener(v -> showAddProductDialog());
    }

    @Override
    public void onProductChanged() {
        products = databaseHelper.getAllProducts();
        adapter = new ProductManagementAdapter(this, products);
        adapter.setProductChangeListener(this);
        recyclerView.setAdapter(adapter);
        
        // Send broadcast to update HomeFragment
        Intent intent = new Intent("PRODUCT_CHANGED");
        sendBroadcast(intent);
    }

    private void showAddProductDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_product, null);
        
        EditText nameEdit = view.findViewById(R.id.product_name);
        EditText descEdit = view.findViewById(R.id.product_description);
        EditText priceEdit = view.findViewById(R.id.product_price);
        Spinner categorySpinner = view.findViewById(R.id.product_category);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_item,
            new String[]{"Breakfast", "Lunch", "Dinner", "Sweets", "Coffee"});
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);

        builder.setView(view)
               .setTitle("Thêm sản phẩm mới")
               .setPositiveButton("Thêm", (dialog, which) -> {
                    String name = nameEdit.getText().toString();
                    String description = descEdit.getText().toString();
                    double price = Double.parseDouble(priceEdit.getText().toString());
                    String category = categorySpinner.getSelectedItem().toString();

                    // Add product to database
                    long newId = databaseHelper.addProduct(name, description, price, 
                        R.drawable.coffe, category, 5.0);
                    
                    // Add new product to list
                    Product newProduct = new Product();
                    newProduct.setId(newId);
                    newProduct.setName(name);
                    newProduct.setDescription(description);
                    newProduct.setPrice(price);
                    newProduct.setCategory(category);
                    newProduct.setImage(R.drawable.coffe);
                    newProduct.setRating(5.0);
                    
                    products.add(newProduct);
                    adapter.notifyDataSetChanged();
                    onProductChanged();
               })
               .setNegativeButton("Hủy", null)
               .show();
    }
}