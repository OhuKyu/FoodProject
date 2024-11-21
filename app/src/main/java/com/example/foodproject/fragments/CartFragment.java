package com.example.foodproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodproject.R;
import com.example.foodproject.adapters.CartAdapter;
import com.example.foodproject.database.DatabaseHelper;
import com.example.foodproject.models.CartModel;
import com.example.foodproject.ui.CartManager;

import java.util.List;

public class CartFragment extends Fragment {
    
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private TextView totalPriceText;
    private Button checkoutBtn;
    private List<CartModel> cartItems;
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        databaseHelper = new DatabaseHelper(getContext());
        
        recyclerView = view.findViewById(R.id.cart_recycler);
        totalPriceText = view.findViewById(R.id.total_price);
        checkoutBtn = view.findViewById(R.id.checkout_btn);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartItems = CartManager.getInstance().getCartItems();
        cartAdapter = new CartAdapter(cartItems);
        recyclerView.setAdapter(cartAdapter);

        updateTotalPrice();

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartItems.isEmpty()) {
                    Toast.makeText(getContext(), "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (databaseHelper.getCurrentUserId() == -1) {
                    Toast.makeText(getContext(), "Vui lòng đăng nhập để thanh toán!", 
                                 Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    databaseHelper.saveOrder(cartItems);
                    Toast.makeText(getContext(), "Đặt hàng thành công!", 
                                 Toast.LENGTH_SHORT).show();
                    CartManager.getInstance().clearCart();
                    cartAdapter.notifyDataSetChanged();
                    updateTotalPrice();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Có lỗi xảy ra: " + e.getMessage(), 
                                 Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void updateTotalPrice() {
        double total = 0;
        for (CartModel item : cartItems) {
            String priceStr = item.getPrice().replace("$", "")
                                       .replace("Tối thiểu - ", "");
            try {
                double price = Double.parseDouble(priceStr);
                total += price * item.getQuantity();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        totalPriceText.setText(String.format("Tổng tiền: $%.2f", total));
    }

    @Override
    public void onResume() {
        super.onResume();
        cartAdapter.notifyDataSetChanged();
        updateTotalPrice();
    }
}