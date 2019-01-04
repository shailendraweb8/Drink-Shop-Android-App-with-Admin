package com.example.shailendra.drinkapp.Utils;

import com.example.shailendra.drinkapp.Database.DataSource.CartRepository;
import com.example.shailendra.drinkapp.Database.DataSource.FavoriteRepository;
import com.example.shailendra.drinkapp.Database.Local.EDMTRoomDatabase;
import com.example.shailendra.drinkapp.Database.ModelDB.Cart;
import com.example.shailendra.drinkapp.Model.Category;
import com.example.shailendra.drinkapp.Model.Drink;
import com.example.shailendra.drinkapp.Model.Order;
import com.example.shailendra.drinkapp.Model.User;
import com.example.shailendra.drinkapp.Retrofit.IDrinkShopAPI;
import com.example.shailendra.drinkapp.Retrofit.RetrofitClient;
import com.example.shailendra.drinkapp.Retrofit.RetrofitScalarsClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shailendra on 5/19/2018.
 */
public class Common {

    public static final String BASE_URL = "http://shikhaelectricals.com/DrinkShop/";
    public static final String API_TOKEN_URL = "http://shikhaelectricals.com/DrinkShop/braintree/main.php";  //for payment gateway

    public static User currentUser = null;

    public static Category currentCategory = null ;  //create for store menu/category position or data for new actiivity
    public static Drink currentDrink = null ;   //create for store drink position or id ............by mee
    public static Order currentOrder = null ; //store order




    public static List<Drink> toppingList = new ArrayList<>();  // For Topping add in When item added in cart
    public  static final String TOPPING_MENU_ID = "7";
    public static double toppingPrice = 0.0;
    public static  List<String> toppingAdded = new ArrayList<>();

    //Hold fied for cart
    public static int sizeOfCup = -1;  // -1 : no choose error
    public  static int sugar = -1;
    public static int ice = -1 ;

    //Database
    public static EDMTRoomDatabase edmtRoomDatabase;
    public static CartRepository cartRepository;
    public  static FavoriteRepository favoriteRepository;

    public static IDrinkShopAPI getAPI()
    {
        return RetrofitClient.getclient(BASE_URL).create(IDrinkShopAPI.class);

    }

    public static IDrinkShopAPI getScalarsAPI()
    {
        return RetrofitScalarsClient.getScalarclient(BASE_URL).create(IDrinkShopAPI.class);

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
