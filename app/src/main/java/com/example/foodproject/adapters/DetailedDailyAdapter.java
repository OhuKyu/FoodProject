package com.example.foodproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodproject.R;
import com.example.foodproject.models.CartModel;
import com.example.foodproject.models.DetailedDailyModel;
import com.example.foodproject.ui.CartManager;

import java.util.List;

public class DetailedDailyAdapter extends RecyclerView.Adapter<DetailedDailyAdapter.ViewHolder> {

    List<DetailedDailyModel> list;

    public DetailedDailyAdapter(List<DetailedDailyModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.detailed_daily_meal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position).getImage());
        holder.name.setText(list.get(position).getName());
        holder.price.setText(list.get(position).getPrice());
        holder.description.setText(list.get(position).getDescription());
        holder.time.setText(list.get(position).getTime());
        holder.rating.setText(list.get(position).getRating());

        Button addToCartBtn = holder.itemView.findViewById(R.id.add_to_cart_btn);
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailedDailyModel item = list.get(position);
                CartModel cartItem = new CartModel(
                    item.getImage(),
                    item.getName(),
                    item.getPrice(),
                    1
                );
                CartManager.getInstance().addItem(cartItem);
                Toast.makeText(v.getContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name, price, description, rating, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.detailed_img);
            name = itemView.findViewById(R.id.detailed_name);
            price = itemView.findViewById(R.id.detailed_price);
            description = itemView.findViewById(R.id.detailed_des);
            rating = itemView.findViewById(R.id.detailed_rating);
            time = itemView.findViewById(R.id.detailed_time);
        }
    }
}
