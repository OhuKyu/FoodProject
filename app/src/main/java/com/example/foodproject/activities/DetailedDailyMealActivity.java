package com.example.foodproject.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodproject.R;
import com.example.foodproject.adapters.DetailedDailyAdapter;
import com.example.foodproject.models.DetailedDailyModel;

import java.util.ArrayList;
import java.util.List;

public class DetailedDailyMealActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<DetailedDailyModel> detailedDailyModelList;
    DetailedDailyAdapter dailyAdapter;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_daily_meal);

        String type = getIntent().getStringExtra("type");

        recyclerView = findViewById(R.id.detailed_rec);
        imageView = findViewById(R.id.detailed_img);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailedDailyModelList = new ArrayList<>();
        dailyAdapter = new DetailedDailyAdapter(detailedDailyModelList);
        recyclerView.setAdapter(dailyAdapter);

        if (type != null && type.equalsIgnoreCase("breakfast")) {
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.fav1, "Bữa sáng", "4.4", "Description", "45$", "10:00 - 12:00"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.fav2, "Bữa sáng", "4.2", "Description", "45$", "10:00 - 12:00"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.fav3, "Bữa sáng", "4.5", "Description", "45$", "10:00 - 12:00"));
            dailyAdapter.notifyDataSetChanged();
        }
        if (type != null && type.equalsIgnoreCase("lunch")) {
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.l1, "Bữa trưa", "4.4", "Description", "45$", "10:00 - 12:00"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.l2, "Bữa trưa", "4.2", "Description", "45$", "10:00 - 12:00"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.l3, "Bữa trưa", "4.5", "Description", "45$", "10:00 - 12:00"));
            dailyAdapter.notifyDataSetChanged();
        }
        if (type != null && type.equalsIgnoreCase("dinner")) {
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.d1, "Bữa tối", "4.4", "Description", "45$", "10:00 - 12:00"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.d2, "Bữa tối", "4.2", "Description", "45$", "10:00 - 12:00"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.d3, "Bữa tối", "4.5", "Description", "45$", "10:00 - 12:00"));
            dailyAdapter.notifyDataSetChanged();
        }
        if (type != null && type.equalsIgnoreCase("sweets")) {

            imageView.setImageResource(R.drawable.sweets);
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.s1, "Đồ ngọt", "4.4", "Description", "45$", "10:00 - 12:00"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.s2, "Đồ ngọt", "4.2", "Description", "45$", "10:00 - 12:00"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.s3, "Đồ ngọt", "4.5", "Description", "45$", "10:00 - 12:00"));
            dailyAdapter.notifyDataSetChanged();
        }
        if (type != null && type.equalsIgnoreCase("coffee")) {

            imageView.setImageResource(R.drawable.sweets);
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.cf1, "Cafe", "4.4", "Description", "45$", "10:00 - 12:00"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.cf2, "Cafe", "4.2", "Description", "45$", "10:00 - 12:00"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.cf3, "Cafe", "4.5", "Description", "45$", "10:00 - 12:00"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.cf4, "Cafe", "4.5", "Description", "45$", "10:00 - 12:00"));
            dailyAdapter.notifyDataSetChanged();
        }
    }
}