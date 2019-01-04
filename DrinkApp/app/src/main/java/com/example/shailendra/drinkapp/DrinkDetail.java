package com.example.shailendra.drinkapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.shailendra.drinkapp.Model.Drink;
import com.example.shailendra.drinkapp.Model.DrinkImage;
import com.example.shailendra.drinkapp.Retrofit.IDrinkShopAPI;
import com.example.shailendra.drinkapp.Utils.Common;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DrinkDetail extends AppCompatActivity {

    ImageView show_img;
    TextView pg_title,pg_price;
    SliderLayout sliderLayout;

    IDrinkShopAPI mService;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    String link ="";
    String price ="";
    String titlee = "";

    Context context;
    List<Drink> drinkList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_detail);

        show_img = (ImageView)findViewById(R.id.show_img);
        pg_title = (TextView)findViewById(R.id.pg_title);
        pg_price = (TextView)findViewById(R.id.pg_price);

        sliderLayout = (SliderLayout)findViewById(R.id.image_slider);

        mService = Common.getAPI();

        link = getIntent().getStringExtra("image");
        price = getIntent().getStringExtra("price") ;
       titlee = getIntent().getStringExtra("title");


            Picasso.with(context)
                    .load(link)
                    .into(show_img);

            pg_title.setText(titlee);
            pg_price.setText(price);

            getDrinkImage(Common.currentDrink.ID);

    }

    private void getDrinkImage(String drinkId) {
        compositeDisposable.add(mService.getDrinkImage(drinkId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<DrinkImage>>() {
            @Override
            public void accept(List<DrinkImage> drinkImages) throws Exception {
                displayImage(drinkImages);
            }
        }));

    }

    //ctrl o


    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    private void displayImage(List<DrinkImage> drinkImages) {
        HashMap<String , String> imageMap = new HashMap<>();
        for (DrinkImage item:drinkImages)
            imageMap.put(item.getName(),item.getLink());

        for (String name:imageMap.keySet()) {

            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView.description(name)
                    .image(imageMap.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            sliderLayout.addSlider(textSliderView);
        }
        }


    }

