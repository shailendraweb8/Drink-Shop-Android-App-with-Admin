package com.example.shailendra.drinkapp.Database.DataSource;

import com.example.shailendra.drinkapp.Database.ModelDB.Cart;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by shailendra on 6/13/2018.
 */

public interface ICartDataSource {
    Flowable<List<Cart>> getCartItems();
    Flowable<List<Cart>> getCartItemById(int cartItemId);
    int countCartItems();
    float sumPrice();
    void emptyCart();
    void insertToCart(Cart...carts);
    void updateCart(Cart...carts);
    void deleteCartItem(Cart cart);
}
