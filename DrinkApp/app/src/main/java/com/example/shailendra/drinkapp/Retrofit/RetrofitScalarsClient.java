package com.example.shailendra.drinkapp.Retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitScalarsClient {
    private static Retrofit retrofit= null;


    public static Retrofit getScalarclient (String baseUrl)

    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  // in slider partt 4 for banner
                    .build();

        }
        return retrofit;

    }
}
