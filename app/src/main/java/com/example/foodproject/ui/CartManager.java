package com.example.foodproject.ui;

import com.example.foodproject.models.CartModel;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<CartModel> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addItem(CartModel item) {
        for (CartModel existingItem : cartItems) {
            if (existingItem.getName().equals(item.getName())) {
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                return;
            }
        }
        cartItems.add(item);
    }

    public List<CartModel> getCartItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
    }
}