package com.example.foodproject.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodproject.R;
import com.example.foodproject.adapters.HomeHorAdapter;
import com.example.foodproject.adapters.HomeVerAdapter;
import com.example.foodproject.adapters.UpdateVerticalRec;
import com.example.foodproject.databinding.FragmentHomeBinding;
import com.example.foodproject.models.HomeHorModel;
import com.example.foodproject.models.HomeVerModel;
import com.example.foodproject.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements UpdateVerticalRec {

    RecyclerView homeHorizontalRec, homeVerticalRec;
    ArrayList<HomeHorModel> homeHorModelList;
    HomeHorAdapter homeHorAdapter;

    ArrayList<HomeVerModel> homeVerModelList;
    HomeVerAdapter homeVerAdapter;

    private DatabaseHelper databaseHelper;
    private BroadcastReceiver productChangeReceiver;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        homeHorizontalRec = root.findViewById(R.id.home_hor_rec);
        homeVerticalRec = root.findViewById(R.id.home_ver_rec);

        setupHorizontalRecyclerView();

        homeVerModelList = new ArrayList<>();
        homeVerModelList = databaseHelper.getProductsByCategory("Pizza");

        homeVerAdapter = new HomeVerAdapter(getActivity(), homeVerModelList);
        homeVerticalRec.setAdapter(homeVerAdapter);
        homeVerticalRec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return root;
    }

    @Override
    public void callBack(int position, ArrayList<HomeVerModel> list) {
        String category = homeHorModelList.get(position).getName();
        homeVerModelList = databaseHelper.getProductsByCategory(category);
        homeVerAdapter = new HomeVerAdapter(getContext(), homeVerModelList);
        homeVerAdapter.notifyDataSetChanged();
        homeVerticalRec.setAdapter(homeVerAdapter);
    }

    private void setupHorizontalRecyclerView() {
        homeHorModelList = new ArrayList<>();
        homeHorModelList.add(new HomeHorModel("Pizza", R.drawable.pizza));
        homeHorModelList.add(new HomeHorModel("Hamburger", R.drawable.hamburger));
        homeHorModelList.add(new HomeHorModel("Fries", R.drawable.fried_potatoes));
        homeHorModelList.add(new HomeHorModel("Ice cream", R.drawable.ice_cream));
        homeHorModelList.add(new HomeHorModel("Sandwich", R.drawable.sandwich));

        homeHorAdapter = new HomeHorAdapter(this, getActivity(), homeHorModelList);
        homeHorizontalRec.setAdapter(homeHorAdapter);
        homeHorizontalRec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        homeHorizontalRec.setHasFixedSize(true);
        homeHorizontalRec.setNestedScrollingEnabled(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        productChangeReceiver = new BroadcastReceiver() {
            @Override 
            public void onReceive(Context context, Intent intent) {
                if ("PRODUCT_CHANGED".equals(intent.getAction())) {
                    refreshData();
                }
            }
        };
        
        requireActivity().registerReceiver(productChangeReceiver, 
            new IntentFilter("PRODUCT_CHANGED"));
    }
    
    private void refreshData() {
        homeVerModelList = databaseHelper.getProductsByCategory(
            homeHorModelList.get(homeHorizontalRec.getChildAdapterPosition(homeHorizontalRec.getChildAt(0))).getName());
        homeVerAdapter = new HomeVerAdapter(getContext(), homeVerModelList);
        homeVerticalRec.setAdapter(homeVerAdapter);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (productChangeReceiver != null) {
            requireActivity().unregisterReceiver(productChangeReceiver);
        }
    }
}