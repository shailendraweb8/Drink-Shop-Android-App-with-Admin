package com.example.shailendra.drinkapp.Database.DataSource;

import com.example.shailendra.drinkapp.Database.ModelDB.Cart;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by shailendra on 6/13/2018.
 */

public class CartRepository implements  ICartDataSource {


  private ICartDataSource iCartDataSource;

    //constructor
    public CartRepository(ICartDataSource iCartDataSource) {
        this.iCartDataSource = iCartDataSource;
    }

    private static CartRepository instance;

  public static CartRepository getInstance(ICartDataSource iCartDataSource)
  {

    if(instance == null )
      instance = new CartRepository(iCartDataSource);
    return  instance;


  }



    @Override
    public Flowable<List<Cart>> getCartItems() {
        return iCartDataSource.getCartItems();
    }

    @Override
    public Flowable<List<Cart>> getCartItemById(int cartItemId) {
        return iCartDataSource.getCartItemById(cartItemId);
    }

    @Override
    public int countCartItems() {
        return iCartDataSource.countCartItems();
    }

    @Override
    public float sumPrice() {
     return iCartDataSource.sumPrice();
     }


    @Override
    public void emptyCart() {
              iCartDataSource.emptyCart();
    }

    @Override
    public void insertToCart(Cart... carts) {
      iCartDataSource.insertToCart(carts);
    }

    @Override
    public void updateCart(Cart... carts) {
      iCartDataSource.updateCart(carts);
    }

    @Override
    public void deleteCartItem(Cart cart) {
          iCartDataSource.deleteCartItem(cart);
    }
}
