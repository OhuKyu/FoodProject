package com.example.foodproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodproject.R;
import com.example.foodproject.adapters.FeaturedAdapter;
import com.example.foodproject.adapters.FeaturedVerAdapter;
import com.example.foodproject.models.FeaturedModel;
import com.example.foodproject.models.FeaturedVerModel;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    //Hor RecyclerView
    private RecyclerView recyclerView;
    private List<FeaturedModel> featuredModelsList;
    private FeaturedAdapter featuredAdapter;

    //Ver RecyclerView
    private RecyclerView recyclerView2;
    private List<FeaturedVerModel> featuredVerModelsList;
    private FeaturedVerAdapter featuredVerAdapter;

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        // Initialize RecyclerViews
        recyclerView = view.findViewById(R.id.featured_hor_rec);
        recyclerView2 = view.findViewById(R.id.featured_ver_rec);

        // Set layout managers
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // Initialize lists
        featuredModelsList = new ArrayList<>();
        featuredVerModelsList = new ArrayList<>();

        // Add data
        featuredModelsList.add(new FeaturedModel(R.drawable.fav1, "Món ăn 1", "Description"));
        featuredModelsList.add(new FeaturedModel(R.drawable.fav2, "Món ăn 2", "Description"));
        featuredModelsList.add(new FeaturedModel(R.drawable.fav3, "Món ăn 3", "Description"));

        featuredVerModelsList.add(new FeaturedVerModel(R.drawable.cf1, "Coffee 1", "Description", "5.0", "10:00 - 23:00"));
        featuredVerModelsList.add(new FeaturedVerModel(R.drawable.cf2, "Coffee 2", "Description", "5.0", "10:00 - 23:00"));
        featuredVerModelsList.add(new FeaturedVerModel(R.drawable.cf3, "Coffee 3", "Description", "5.0", "10:00 - 23:00"));
        featuredVerModelsList.add(new FeaturedVerModel(R.drawable.cf4, "Coffee 4", "Description", "5.0", "10:00 - 23:00"));

        // Initialize and set adapters
        featuredAdapter = new FeaturedAdapter(featuredModelsList);
        featuredVerAdapter = new FeaturedVerAdapter(featuredVerModelsList);

        recyclerView.setAdapter(featuredAdapter);
        recyclerView2.setAdapter(featuredVerAdapter);

        return view;
    }
}