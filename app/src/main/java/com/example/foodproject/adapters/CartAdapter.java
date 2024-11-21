package com.example.foodproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodproject.R;
import com.example.foodproject.models.CartModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    List<CartModel> cartModels;

    public CartAdapter(List<CartModel> cartModels) {
        this.cartModels = cartModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartModel item = cartModels.get(position);
        holder.imageView.setImageResource(item.getImage());
        holder.name.setText(item.getName());
        holder.price.setText(item.getPrice());
        holder.quantity.setText(String.valueOf(item.getQuantity()));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                cartModels.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartModels.size());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, price, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cart_item_image);
            name = itemView.findViewById(R.id.cart_item_name);
            price = itemView.findViewById(R.id.cart_item_price);
            quantity = itemView.findViewById(R.id.cart_item_quantity);
        }
    }
}
