package com.example.foodproject.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodproject.R;
import com.example.foodproject.database.DatabaseHelper;
import com.example.foodproject.models.Product;
import com.example.foodproject.activities.ProductChangeListener;

import java.util.List;

public class ProductManagementAdapter extends RecyclerView.Adapter<ProductManagementAdapter.ViewHolder> {
    private List<Product> products;
    private Context context;
    private DatabaseHelper databaseHelper;
    private ProductChangeListener listener;

    public ProductManagementAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
        this.databaseHelper = new DatabaseHelper(context);
    }

    public void setProductChangeListener(ProductChangeListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        
        holder.imageView.setImageResource(product.getImage());
        holder.nameText.setText(product.getName());
        holder.priceText.setText(String.format("$%.2f", product.getPrice()));
        holder.categoryText.setText(product.getCategory());

        holder.editButton.setOnClickListener(v -> showEditDialog(product));
        holder.deleteButton.setOnClickListener(v -> showDeleteConfirmation(product));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    private void showEditDialog(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_product, null);
        
        EditText nameEdit = view.findViewById(R.id.product_name);
        EditText descEdit = view.findViewById(R.id.product_description);
        EditText priceEdit = view.findViewById(R.id.product_price);
        Spinner categorySpinner = view.findViewById(R.id.product_category);

        // Set giá trị hiện tại
        nameEdit.setText(product.getName());
        descEdit.setText(product.getDescription());
        priceEdit.setText(String.valueOf(product.getPrice()));

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(context,
            android.R.layout.simple_spinner_item,
            new String[]{"Breakfast", "Lunch", "Dinner", "Sweets", "Coffee"});
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);
        
        int position = spinnerAdapter.getPosition(product.getCategory());
        categorySpinner.setSelection(position);

        builder.setView(view)
               .setTitle("Sửa sản phẩm")
               .setPositiveButton("Lưu", (dialog, which) -> {
                    String name = nameEdit.getText().toString();
                    String description = descEdit.getText().toString();
                    double price = Double.parseDouble(priceEdit.getText().toString());
                    String category = categorySpinner.getSelectedItem().toString();

                    // Cập nhật sản phẩm trong database
                    databaseHelper.updateProduct(product.getId(), name, description, 
                        price, category, product.getRating());
                    
                    // Cập nhật object trong list
                    product.setName(name);
                    product.setDescription(description);
                    product.setPrice(price);
                    product.setCategory(category);
                    
                    if (listener != null) {
                        listener.onProductChanged();
                    }
               })
               .setNegativeButton("Hủy", null)
               .show();
    }

    private void showDeleteConfirmation(Product product) {
        new AlertDialog.Builder(context)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc muốn xóa sản phẩm này?")
            .setPositiveButton("Xóa", (dialog, which) -> {
                databaseHelper.deleteProduct(product.getId());
                products.remove(product);
                if (listener != null) {
                    listener.onProductChanged();
                }
                notifyDataSetChanged();
            })
            .setNegativeButton("Hủy", null)
            .show();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameText, priceText, categoryText;
        ImageButton editButton, deleteButton;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.product_image);
            nameText = itemView.findViewById(R.id.product_name);
            priceText = itemView.findViewById(R.id.product_price);
            categoryText = itemView.findViewById(R.id.product_category);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}