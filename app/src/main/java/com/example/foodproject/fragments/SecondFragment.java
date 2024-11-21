package com.example.foodproject.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
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

public class SecondFragment extends Fragment {

    private RecyclerView trendingRecycler;
    private RecyclerView topRatedRecycler;
    private FeaturedVerAdapter trendingAdapter;
    private FeaturedVerAdapter topRatedAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        trendingRecycler = view.findViewById(R.id.trending_recycler);
        topRatedRecycler = view.findViewById(R.id.top_rated_recycler);

        // Set up trending items
        trendingRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        List<FeaturedVerModel> trendingList = new ArrayList<>();
        trendingList.add(new FeaturedVerModel(R.drawable.pizza1, "Pizza Hải Sản", "Pizza hải sản tươi ngon", "4.8", "10:00 - 22:00"));
        trendingList.add(new FeaturedVerModel(R.drawable.burger1, "Burger Bò", "Burger bò đặc biệt", "4.7", "09:00 - 23:00"));
        trendingList.add(new FeaturedVerModel(R.drawable.fries1, "Khoai tây chiên", "Khoai tây chiên giòn", "4.6", "11:00 - 22:00"));
        trendingAdapter = new FeaturedVerAdapter(trendingList);
        trendingRecycler.setAdapter(trendingAdapter);

        // Set up top rated items
        topRatedRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        List<FeaturedVerModel> topRatedList = new ArrayList<>();
        topRatedList.add(new FeaturedVerModel(R.drawable.sandwich1, "Bánh mì thịt", "Bánh mì thịt đặc biệt", "4.9", "06:00 - 22:00"));
        topRatedList.add(new FeaturedVerModel(R.drawable.icecream1, "Kem Ý", "Kem Ý các loại", "4.8", "10:00 - 22:30"));
        topRatedList.add(new FeaturedVerModel(R.drawable.cf1, "Cà phê", "Cà phê nguyên chất", "4.7", "07:00 - 23:00"));
        topRatedAdapter = new FeaturedVerAdapter(topRatedList);
        topRatedRecycler.setAdapter(topRatedAdapter);

        return view;
    }
}