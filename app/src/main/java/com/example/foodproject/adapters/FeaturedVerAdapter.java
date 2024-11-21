package com.example.foodproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodproject.R;
import com.example.foodproject.models.FeaturedVerModel;

import java.util.List;

public class FeaturedVerAdapter extends RecyclerView.Adapter<FeaturedVerAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(FeaturedVerModel item);
        void onFavoriteClick(FeaturedVerModel item, int position);
    }

    private Context context;
    private List<FeaturedVerModel> list;
    private OnItemClickListener listener;

    public FeaturedVerAdapter(List<FeaturedVerModel> list) {
        this.list = list;
    }

    public FeaturedVerAdapter(Context context, List<FeaturedVerModel> list, OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public FeaturedVerAdapter(Context context, List<FeaturedVerModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_ver_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder == null || list == null || position >= list.size()) {
            return;
        }

        FeaturedVerModel model = list.get(position);
        if (model == null) {
            return;
        }

        if (holder.imageView != null) {
            holder.imageView.setImageResource(model.getImage());
        }
        if (holder.name != null) {
            holder.name.setText(model.getName());
        }
        if (holder.description != null) {
            holder.description.setText(model.getDescription());
        }
        if (holder.rating != null) {
            holder.rating.setText(model.getRating());
        }
        if (holder.timing != null) {
            holder.timing.setText(model.getTime());
        }

        if (listener != null) {
            holder.itemView.setOnClickListener(v -> listener.onItemClick(model));
            if (holder.favoriteBtn != null) {
                holder.favoriteBtn.setOnClickListener(v -> listener.onFavoriteClick(model, position));
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, description, rating, timing;
        ImageButton favoriteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ver_img);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.detailed_des);
            rating = itemView.findViewById(R.id.rating);
            timing = itemView.findViewById(R.id.time);
            favoriteBtn = itemView.findViewById(R.id.favorite_btn);
        }
    }
}
