package com.example.shailendraweb.drinkshopserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.shailendraweb.drinkshopserver.Adapter.OrderDetailAdapter;
import com.example.shailendraweb.drinkshopserver.Utils.Common;

public class ViewOrderDetail extends AppCompatActivity {

    TextView txt_order_id , txt_order_price , txt_order_comment , txt_order_address;
    Spinner spinner_order_status;
    RecyclerView recycler_order_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_detail);

        txt_order_id = (TextView)findViewById(R.id.txt_order_id);
        txt_order_price = (TextView)findViewById(R.id.txt_order_price);
        txt_order_comment = (TextView)findViewById(R.id.txt_order_comment);
        txt_order_address = (TextView)findViewById(R.id.txt_order_address);

        spinner_order_status = (Spinner)findViewById(R.id.spinner_order_status);


        recycler_order_detail = (RecyclerView)findViewById(R.id.recycler_order_detail);
        recycler_order_detail.setLayoutManager(new LinearLayoutManager(this));
        recycler_order_detail.setAdapter(new OrderDetailAdapter(this));
        recycler_order_detail.setHasFixedSize(true);

        //set data

        txt_order_id.setText(new StringBuilder("#")
                .append(Common.currentOrder.getOrderId()));
        txt_order_price.setText(new StringBuilder("₹").append(Common.currentOrder.getOrderPrice()));
        txt_order_address.setText(Common.currentOrder.getOrderAddress());
        txt_order_comment.setText(Common.currentOrder.getOrderComment());

    }
}
