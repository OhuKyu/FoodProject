package com.example.foodproject.ui.favourite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodproject.R;
import com.example.foodproject.adapters.FeaturedVerAdapter;
import com.example.foodproject.database.DatabaseHelper;
import com.example.foodproject.fragments.FragmentAdapter;
import com.example.foodproject.models.FeaturedVerModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private FragmentAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                         ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favourite, container, false);

        tabLayout = root.findViewById(R.id.tab_layout);
        viewPager2 = root.findViewById(R.id.view_pager2);

        FragmentManager fragmentManager = getChildFragmentManager();
        adapter = new FragmentAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Món ăn yêu thích"));
        tabLayout.addTab(tabLayout.newTab().setText("Dự kiến ra mắt"));
        tabLayout.addTab(tabLayout.newTab().setText("Nổi bật"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        return root;
    }
}