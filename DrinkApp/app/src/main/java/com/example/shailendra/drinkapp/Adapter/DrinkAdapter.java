package com.example.shailendra.drinkapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.shailendra.drinkapp.Database.ModelDB.Cart;
import com.example.shailendra.drinkapp.Database.ModelDB.Favorite;
import com.example.shailendra.drinkapp.DrinkActivity;
import com.example.shailendra.drinkapp.DrinkDetail;
import com.example.shailendra.drinkapp.Interface.IItemClickListener;
import com.example.shailendra.drinkapp.Model.Drink;
import com.example.shailendra.drinkapp.R;
import com.example.shailendra.drinkapp.Utils.Common;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shailendra on 5/24/2018.
 */


//click on DrinkViewHolder & click on red bulb & click on imlements methods
public class DrinkAdapter extends RecyclerView.Adapter<DrinkViewHolder>  {

    Context context;
    List<Drink> drinkList;

    // generate & click on constructor


    public DrinkAdapter(Context context, List<Drink> drinkList) {
        this.context = context;
        this.drinkList = drinkList;
    }



    @Override
    public DrinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(context).inflate(R.layout.drink_item_layout,null);

        return new DrinkViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(final DrinkViewHolder holder, final int position) {

        holder.txt_price.setText(new StringBuilder("₹").append(drinkList.get(position).Price).toString());
        holder.txt_drink_name.setText(drinkList.get(position).Name);

        holder.btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddToCartDialog(position);
            }
        });

        Picasso.with(context)
                .load(drinkList.get(position).Link)
                .into(holder.img_product);

          holder.setItemClickListener(new IItemClickListener() {
              @Override
              public void onClick(View v) {
                  //item clicked
                  Common.currentDrink = drinkList.get(position);
                 Intent drinkDetails = new Intent(context, DrinkDetail.class);
                 drinkDetails.putExtra("DrinkID",drinkList.get(position).ID);
                 drinkDetails.putExtra("image",drinkList.get(position).Link);
                 drinkDetails.putExtra("price",drinkList.get(position).Price);
                 drinkDetails.putExtra("title",drinkList.get(position).Name);
                 context.startActivity(drinkDetails);
              }
          });

          //Favorite System

        if (Common.favoriteRepository.isFavorite(Integer.parseInt(drinkList.get(position).ID)) == 1)
        {
            holder.btn_favorites.setImageResource(R.drawable.ic_favorite_white_24dp);
        }
        else
        {
            holder.btn_favorites.setImageResource(R.drawable.ic_favorite_border_white_24dp);
        }

       holder.btn_favorites.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (Common.favoriteRepository.isFavorite(Integer.parseInt(drinkList.get(position).ID)) != 1)
               {
                   addOrRemoveFavorite(drinkList.get(position),true);
                   holder.btn_favorites.setImageResource(R.drawable.ic_favorite_white_24dp);
               }
               else
               {
                   addOrRemoveFavorite(drinkList.get(position),false);
                   holder.btn_favorites.setImageResource(R.drawable.ic_favorite_border_white_24dp);
               }
           }
       });
    }

    private void addOrRemoveFavorite(Drink drink, boolean isAdd) {
        Favorite favorite = new Favorite();
        favorite.id = drink.ID;
        favorite.link = drink.Link;
        favorite.name = drink.Name;
        favorite.price = drink.Price;
        favorite.menuId = drink.MenuID;

        if (isAdd)
        {
            Common.favoriteRepository.insertFav(favorite);
        }
        else
        {
            Common.favoriteRepository.delete(favorite);
        }
    }


    private void showAddToCartDialog(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.add_to_cart_layout,null);

        //view for cart
        ImageView img_product_dialog = (ImageView)itemView.findViewById(R.id.img_cart_product);
        final ElegantNumberButton txt_count = (ElegantNumberButton)itemView.findViewById(R.id.txt_count);
        TextView txt_product_dialog = (TextView)itemView.findViewById(R.id.txt_cart_product_name);

        EditText edt_comment = (EditText)itemView.findViewById(R.id.edt_comment);

        RadioButton rdi_sizeM = (RadioButton)itemView.findViewById(R.id.rdi_sizeM);
        RadioButton rdi_sizeL = (RadioButton)itemView.findViewById(R.id.rdi_sizeL);

        rdi_sizeM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    Common.sizeOfCup = 0;
                }
            }
        });

        rdi_sizeL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    Common.sizeOfCup = 1;
                }
            }
        });

        RadioButton rdi_sugar100 = (RadioButton)itemView.findViewById(R.id.rdi_sugar_100);
        RadioButton rdi_sugar70 = (RadioButton)itemView.findViewById(R.id.rdi_sugar_70);
        RadioButton rdi_sugar50 = (RadioButton)itemView.findViewById(R.id.rdi_sugar_50);
        RadioButton rdi_sugar30 = (RadioButton)itemView.findViewById(R.id.rdi_sugar_30);
        RadioButton rdi_sugar_free = (RadioButton)itemView.findViewById(R.id.rdi_sugar_free);

        rdi_sugar30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    Common.sugar = 30;
                }
            }
        });

        rdi_sugar50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    Common.sugar = 50;
                }
            }
        });


        rdi_sugar70.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    Common.sugar = 70;
                }
            }
        });

        rdi_sugar100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    Common.sugar = 100;
                }
            }
        });


        rdi_sugar_free.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    Common.sugar = 0;
                }
            }
        });



        RadioButton rdi_ice100 = (RadioButton)itemView.findViewById(R.id.rdi_ice_100);
        RadioButton rdi_ice70 = (RadioButton)itemView.findViewById(R.id.rdi_ice_70);
        RadioButton rdi_ice50 = (RadioButton)itemView.findViewById(R.id.rdi_ice_50);
        RadioButton rdi_ice30 = (RadioButton)itemView.findViewById(R.id.rdi_ice_30);
        RadioButton rdi_ice_free = (RadioButton)itemView.findViewById(R.id.rdi_ice_free);

        rdi_ice30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    Common.ice = 30;
                }
            }
        });

        rdi_ice50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    Common.ice = 50;
                }
            }
        });

        rdi_ice70.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    Common.ice = 70;
                }
            }
        });

        rdi_ice100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    Common.ice = 100;
                }
            }
        });

        rdi_ice_free.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    Common.ice = 0;
                }
            }
        });



//Add topping List in ADD to Cart
        RecyclerView recycler_topping = (RecyclerView)itemView.findViewById(R.id.recycler_topping);
        recycler_topping.setLayoutManager(new LinearLayoutManager(context));
        recycler_topping.setHasFixedSize(true);

        MultiChoiceAdapter adapter = new MultiChoiceAdapter(context , Common.toppingList);
        recycler_topping.setAdapter(adapter);

        //set data
        Picasso.with(context)
                .load(drinkList.get(position).Link)
                .into(img_product_dialog);

        txt_product_dialog.setText(drinkList.get(position).Name);


        builder.setView(itemView);
        builder.setNegativeButton("ADD TO CART ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                if (Common.sizeOfCup == -1) {

                    Toast.makeText(context, "Please Choose Size Of Cup", Toast.LENGTH_SHORT).show();
                    return;

                }
              if (Common.sugar == -1)
                {

                    Toast.makeText(context, "Please Choose Sugar", Toast.LENGTH_SHORT).show();
                    return ;
                }

                 if (Common.ice == -1)
                {

                    Toast.makeText(context, "Please Choose Ice", Toast.LENGTH_SHORT).show();
                    return ;
                }



                showConfirmDialog(position , txt_count.getNumber());
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    private void showConfirmDialog(final int position, final String number) {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.confirm_add_to_cart_layout,null);


        //View on Confirm Dialog

        ImageView img_product_dialog = (ImageView)itemView.findViewById(R.id.img_product);
         final TextView txt_product_dialog = (TextView)itemView.findViewById(R.id.txt_cart_product_name);
         final TextView txt_product_price = (TextView)itemView.findViewById(R.id.txt_cart_product_price);
         TextView txt_sugar = (TextView)itemView.findViewById(R.id.txt_sugar);
         TextView txt_ice = (TextView)itemView.findViewById(R.id.txt_ice);
         final TextView txt_topping_extra = (TextView)itemView.findViewById(R.id.txt_topping_extra);

        //set data

        Picasso.with(context).load(drinkList.get(position).Link).into(img_product_dialog);

        txt_product_dialog.setText(new StringBuilder(drinkList.get(position).Name).append(" x")
         .append(Common.sizeOfCup == 0 ? "Size M":"Size L")
        .append(number).toString());

        txt_ice.setText(new StringBuilder("Ice: ").append(Common.ice).append("%").toString());

        txt_sugar.setText(new StringBuilder("Sugar: ").append(Common.sugar).append("%").toString());


        double price = (Double.parseDouble(drinkList.get(position).Price)* Double.parseDouble(number)) + Common.toppingPrice;

        if(Common.sizeOfCup == 1) //Size L
        {
            price+=(3.0*Double.parseDouble(number));
        }

    txt_product_price.setText(new StringBuilder("₹").append(price));

        StringBuilder topping_final_comment = new StringBuilder("");
        for(String line:Common.toppingAdded)
              topping_final_comment.append(line).append("\n");


        txt_topping_extra.setText(topping_final_comment);


        final double finalPrice = price;

        txt_product_price.setText(new StringBuilder("₹").append(finalPrice));


        builder1.setNegativeButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {



                dialogInterface.dismiss();

                //Add To Database
                //create new cart item

                try {
                    Cart cartitem = new Cart();
                    cartitem.name = drinkList.get(position).Name;
                    cartitem.amount = Integer.parseInt(number);
                    cartitem.ice = Common.ice;
                    cartitem.sugar = Common.sugar;
                    cartitem.price = finalPrice;
                    cartitem.size = Common.sizeOfCup;
                    cartitem.toppingExtras = txt_topping_extra.getText().toString();
                    cartitem.link = drinkList.get(position).Link;

                    //Add to DB
                    Common.cartRepository.insertToCart(cartitem);

                    Log.e("SHAILENDRA", new Gson().toJson(cartitem));

                    Toast.makeText(context, "Save item to cart success", Toast.LENGTH_SHORT).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }





                 //destroy stores data in Common.java
                Common.sizeOfCup = -1;
                Common.toppingAdded.clear();
                Common.sugar = -1 ;
                Common.ice = -1;
            }
        });

        builder1.setView(itemView);
        builder1.show();
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }
}
