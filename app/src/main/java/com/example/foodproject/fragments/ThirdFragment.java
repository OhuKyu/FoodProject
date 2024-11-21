package com.example.foodproject.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.foodproject.R;
import com.example.foodproject.adapters.FeaturedVerAdapter;
import com.example.foodproject.models.FeaturedVerModel;
import java.util.ArrayList;
import java.util.List;

public class ThirdFragment extends Fragment {

    private RecyclerView newItemsRecycler;
    private RecyclerView upcomingRecycler;
    private FeaturedVerAdapter newItemsAdapter;
    private FeaturedVerAdapter upcomingAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        newItemsRecycler = view.findViewById(R.id.new_items_recycler);
        upcomingRecycler = view.findViewById(R.id.upcoming_recycler);

        // Set up new items with Grid Layout
        newItemsRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        List<FeaturedVerModel> newItemsList = new ArrayList<>();
        newItemsList.add(new FeaturedVerModel(R.drawable.new1, "Mì Ý Đặc Biệt", "Mì Ý sốt bò bằm", "4.9", "11:00 - 21:00"));
        newItemsList.add(new FeaturedVerModel(R.drawable.new2, "Sushi Set", "Set sushi cao cấp", "4.8", "10:00 - 22:00"));
        newItemsList.add(new FeaturedVerModel(R.drawable.new3, "Gà Rán Phô Mai", "Gà rán sốt phô mai", "4.7", "10:00 - 22:00"));
        newItemsList.add(new FeaturedVerModel(R.drawable.new4, "Salad Trộn", "Salad trộn dầu giấm", "4.6", "08:00 - 20:00"));
        newItemsAdapter = new FeaturedVerAdapter(newItemsList);
        newItemsRecycler.setAdapter(newItemsAdapter);

        // Set up upcoming items with horizontal scroll
        upcomingRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        List<FeaturedVerModel> upcomingList = new ArrayList<>();
        upcomingList.add(new FeaturedVerModel(R.drawable.upcoming1, "Bánh Tráng Trộn", "Bánh tráng trộn đặc biệt", "Sắp ra mắt", ""));
        upcomingList.add(new FeaturedVerModel(R.drawable.upcoming2, "Trà Sữa Trân Châu", "Trà sữa nhà làm", "Sắp ra mắt", ""));
        upcomingList.add(new FeaturedVerModel(R.drawable.upcoming3, "Cơm Chiên Hải Sản", "Cơm chiên hải sản đặc biệt", "Sắp ra mắt", ""));
        upcomingAdapter = new FeaturedVerAdapter(upcomingList);
        upcomingRecycler.setAdapter(upcomingAdapter);

        return view;
    }
}