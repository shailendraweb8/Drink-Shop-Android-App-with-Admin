package com.example.shailendra.drinkapp.Utils;

//for swipe to delete

import android.support.v7.widget.RecyclerView;

public interface RecyclerItemTouchHelperListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder , int direction , int position);
}
