package com.example.foodproject.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodproject.R;
import com.example.foodproject.database.DatabaseHelper;
import com.example.foodproject.models.CartModel;
import com.example.foodproject.ui.CartManager;
import com.example.foodproject.models.HomeVerModel;
import com.example.foodproject.fragments.CartFragment;
import com.example.foodproject.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class HomeVerAdapter extends RecyclerView.Adapter<HomeVerAdapter.ViewHolder> {

    Context context;
    ArrayList<HomeVerModel> list;
    private DatabaseHelper databaseHelper;

    public HomeVerAdapter(Context context, ArrayList<HomeVerModel> list) {
        this.context = context;
        this.list = list;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public HomeVerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_vertical_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeVerAdapter.ViewHolder holder, int position) {
        HomeVerModel item = list.get(position);
        try {
            holder.imageView.setImageResource(item.getImage());
        } catch (Exception e) {
            Log.e("HomeVerAdapter", "Error loading image: " + e.getMessage());
            holder.imageView.setImageResource(R.drawable.coffe);
        }
        
        holder.name.setText(item.getName());
        holder.time.setText(item.getTime());
        holder.rating.setText(item.getRating());
        holder.price.setText(item.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog(position);
            }
        });
    }

    private void showBottomSheetDialog(int position) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View bottomSheetView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_dialog, null);
        
        ImageView imageView = bottomSheetView.findViewById(R.id.bottom_sheet_image);
        TextView nameText = bottomSheetView.findViewById(R.id.bottom_sheet_name);
        TextView descriptionText = bottomSheetView.findViewById(R.id.bottom_sheet_description);
        TextView ratingText = bottomSheetView.findViewById(R.id.bottom_sheet_rating);
        TextView timeText = bottomSheetView.findViewById(R.id.bottom_sheet_time);
        TextView priceText = bottomSheetView.findViewById(R.id.bottom_sheet_price);
        Button addToCartButton = bottomSheetView.findViewById(R.id.add_to_cart_button);

        HomeVerModel item = list.get(position);
        
        imageView.setImageResource(item.getImage());
        nameText.setText(item.getName());
        descriptionText.setText("Description");
        ratingText.setText(item.getRating());
        timeText.setText(item.getTime());
        priceText.setText(item.getPrice());

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartModel cartItem = new CartModel(
                    item.getImage(),
                    item.getName(),
                    item.getPrice(),
                    1
                );
                CartManager.getInstance().addItem(cartItem);
                Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, time, rating, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ver_img);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            rating = itemView.findViewById(R.id.rating);
            price = itemView.findViewById(R.id.price);
        }
    }
}
