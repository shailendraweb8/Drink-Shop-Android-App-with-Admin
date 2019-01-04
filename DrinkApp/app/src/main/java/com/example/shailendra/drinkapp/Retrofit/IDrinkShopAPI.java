package com.example.shailendra.drinkapp.Retrofit;


import android.database.Observable;

import com.example.shailendra.drinkapp.Model.Banner;
import com.example.shailendra.drinkapp.Model.Category;
import com.example.shailendra.drinkapp.Model.CheckUserResponse;
import com.example.shailendra.drinkapp.Model.Drink;
import com.example.shailendra.drinkapp.Model.DrinkImage;
import com.example.shailendra.drinkapp.Model.Order;
import com.example.shailendra.drinkapp.Model.Store;
import com.example.shailendra.drinkapp.Model.User;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by shailendra on 5/19/2018.
 */
public interface IDrinkShopAPI {
    @FormUrlEncoded
    @POST("checkuser.php")
    Call<CheckUserResponse> checkUserExists(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("register.php")
    Call<User> registerNewUser(@Field("phone") String phone ,
                               @Field("name") String name,
                               @Field("address") String address,
                               @Field("birthdate") String birthdate);



    @FormUrlEncoded
    @POST("getuser.php")
    Call<User> getUserInformation(@Field("phone") String phone);

    //for banners
    @GET("getbanner.php")
    io.reactivex.Observable<List<Banner>> getBanners();


    //for category or menu
    @GET("getmenu.php")
    io.reactivex.Observable<List<Category>> getMenu();

    //for drink load
    @FormUrlEncoded
    @POST("getdrink.php")
    io.reactivex.Observable<List<Drink>> getDrink(@Field("menuid") String menuID );


    //for drink Image load by mee
    @FormUrlEncoded
    @POST("getDrinkImage.php")
    io.reactivex.Observable<List<DrinkImage>> getDrinkImage(@Field("drinkid") String drinkID );

   //upload Avatar
    @Multipart
    @POST("upload.php")
    Call<String> uploadFile(@Part MultipartBody.Part phone , @Part MultipartBody.Part file);

   //get all drinks
    @GET("getalldrinks.php")
    io.reactivex.Observable<List<Drink>> getAllDrinks();

    //Submit order
    @FormUrlEncoded
    @POST("submitorder.php")
    Call<String> submitOrder(@Field("price") float orderPrice,
                             @Field("orderDetail") String orderDetail,
                             @Field("comment") String comment,
                             @Field("address") String address,
                             @Field("phone") String phone);

    //Payment Gateway
    @FormUrlEncoded
    @POST("braintree/checkout.php")
    Call<String> payment(@Field("nonce") String nonce,
                             @Field("amount") String amount);

    //GET order By Status
    @FormUrlEncoded
    @POST("getorder.php")
    io.reactivex.Observable<List<Order>> getOrder(@Field("userPhone") String userPhone ,
                                                  @Field("status") String status);

    //FCM Token
    @FormUrlEncoded
    @POST("updatetoken.php")
    Call<String> updateToken(@Field("phone") String phone,
                             @Field("token") String token ,
                             @Field("isServerToken") String isServerToken);

    //Order Cancel
    @FormUrlEncoded
    @POST("cancelorder.php")
    Call<String> cancelOrder(@Field("orderId") String orderId,
                             @Field("userPhone") String userPhone);

    //Near By Stores
    @FormUrlEncoded
    @POST("getnearbystore.php")
    io.reactivex.Observable<List<Store>> getNearbyStore(@Field("lat") String lat,
                                                        @Field("lng") String lng);
}
