package com.example.shailendraweb.drinkshopserver.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shailendraweb.drinkshopserver.Model.Cart;
import com.example.shailendraweb.drinkshopserver.R;
import com.example.shailendraweb.drinkshopserver.Utils.Common;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.List;


public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailsViewHolder> {

    Context context;
    List<Cart> itemList;


    public OrderDetailAdapter(Context context) {
        this.context = context;
        this.itemList = new Gson().fromJson(Common.currentOrder.getOrderDetail(), new TypeToken<List<Cart>>(){}.getType());
    }

    @NonNull
    @Override
    public OrderDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_layout,parent,false);
        return new OrderDetailsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsViewHolder holder, int position) {

        holder.txt_drink_amount.setText(""+itemList.get(position).getAmount());
        holder.txt_drink_name.setText(new StringBuilder(itemList.get(position).getName()));
        holder.txt_size.setText(itemList.get(position).getSize() == 0?"Size M":"Size L");
        holder.txt_sugar_ice.setText(new StringBuilder("Sugar: ").append(itemList.get(position).getSugar())
        .append(", Ice: ").append(itemList.get(position).getIce()));

        String topping_format = itemList.get(position).getToppingExtras().replaceAll("\\n",",");
        topping_format = topping_format.substring(0,topping_format.length()-1);

        holder.txt_topping.setText(topping_format);

        Picasso.with(context).load(itemList.get(position).getLink()).into(holder.img_order_item);


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class OrderDetailsViewHolder extends RecyclerView.ViewHolder {

        ImageView img_order_item;
        TextView txt_drink_name,txt_drink_amount,txt_sugar_ice,txt_size,txt_topping;


        public OrderDetailsViewHolder(View itemView) {
            super(itemView);

            img_order_item = (ImageView)itemView.findViewById(R.id.img_order_item);

            txt_drink_amount = (TextView) itemView.findViewById(R.id.txt_drink_amount);
            txt_sugar_ice = (TextView) itemView.findViewById(R.id.txt_sugar_ice);
            txt_size = (TextView) itemView.findViewById(R.id.txt_size);
            txt_topping = (TextView) itemView.findViewById(R.id.txt_topping);
            txt_drink_name = (TextView) itemView.findViewById(R.id.txt_drink_name);
        }
    }
}
