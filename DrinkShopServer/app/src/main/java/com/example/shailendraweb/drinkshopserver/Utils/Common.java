package com.example.shailendraweb.drinkshopserver.Utils;

import com.example.shailendraweb.drinkshopserver.Model.Category;
import com.example.shailendraweb.drinkshopserver.Model.Drink;
import com.example.shailendraweb.drinkshopserver.Model.Order;
import com.example.shailendraweb.drinkshopserver.Retrofit.IDrinkShopAPI;
import com.example.shailendraweb.drinkshopserver.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

public class Common {

    public static final String BASE_URL = "http://shikhaelectricals.com/DrinkShop/";

    public static Category currentCategory ;
    public static Drink currentDrink;
    public static Order currentOrder;

    public static List<Category> menuList = new ArrayList<>();   //for spinner menu

    public static IDrinkShopAPI getAPI()
    {
        return RetrofitClient.getClient(BASE_URL).create(IDrinkShopAPI.class);
    }


    public static String convertCodeToStatus(int orderStatus) {
        //for Order Status when get into ShowOrder

        switch (orderStatus)
        {
            case 0:
                return "Placed";

            case 1:
                return "Processing";

            case 2:
                return "Shipping";

            case 3:
                return "Shipped";

            case -1:
                return "Cancelled";

            default:
                return "Order Error";
        }
    }
}
