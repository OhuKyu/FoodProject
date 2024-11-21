package com.example.foodproject.ui.dailymeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodproject.R;
import com.example.foodproject.adapters.DailyMealAdapter;
import com.example.foodproject.models.DailyMealModel;

import java.util.ArrayList;
import java.util.List;

public class DailyMealFragment extends Fragment {

    RecyclerView recyclerView;
    List<DailyMealModel> dailyMealModels;
    DailyMealAdapter dailyMealAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.daily_meal_fragment, container, false);

        recyclerView = root.findViewById(R.id.daily_meal_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dailyMealModels = new ArrayList<>();

        dailyMealModels.add(new DailyMealModel(R.drawable.breakfast, "Bữa sáng", "20%", "breakfast", "Description Description"));
        dailyMealModels.add(new DailyMealModel(R.drawable.lunch, "Bữa trưa", "15%", "lunch", "Description Description"));
        dailyMealModels.add(new DailyMealModel(R.drawable.dinner, "Bữa tối", "23%", "dinner", "Description Description"));
        dailyMealModels.add(new DailyMealModel(R.drawable.sweets, "Đồ ngọt", "22%", "sweets", "Description Description"));
        dailyMealModels.add(new DailyMealModel(R.drawable.coffe, "Cafe", "24%", "coffee", "Description Description"));

        dailyMealAdapter = new DailyMealAdapter(getContext(), dailyMealModels);
        recyclerView.setAdapter(dailyMealAdapter);
        dailyMealAdapter.notifyDataSetChanged();

        return root;
    }
}