package com.example.shailendra.drinkapp;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.shailendra.drinkapp.Adapter.FavoriteAdapter;
import com.example.shailendra.drinkapp.Database.ModelDB.Favorite;
import com.example.shailendra.drinkapp.Utils.Common;
import com.example.shailendra.drinkapp.Utils.RecyclerItemTouchHelper;
import com.example.shailendra.drinkapp.Utils.RecyclerItemTouchHelperListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FavoriteListActivity extends AppCompatActivity implements RecyclerItemTouchHelperListener{

    RecyclerView recycler_fav;


    RelativeLayout rootLayout;
    CompositeDisposable compositeDisposable;
    FavoriteAdapter favoriteAdapter;
    List<Favorite> localFavorites = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        compositeDisposable = new CompositeDisposable();
        rootLayout = (RelativeLayout)findViewById(R.id.rootLayout);

        recycler_fav = (RecyclerView)findViewById(R.id.recycler_fav);
        recycler_fav.setLayoutManager(new LinearLayoutManager(this));
        recycler_fav.setHasFixedSize(true);

        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recycler_fav);

        loadFavoritesItem();
    }

    //ctrl o


    @Override
    protected void onResume() {
        super.onResume();
        loadFavoritesItem();
    }

    //ctrl o

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void loadFavoritesItem() {

        compositeDisposable.add(Common.favoriteRepository.getFavItems()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Consumer<List<Favorite>>() {
            @Override
            public void accept(List<Favorite> favorites) throws Exception {
                displayFavoriteItem(favorites);
            }
        }));
    }

    private void displayFavoriteItem(List<Favorite> favorites) {
        localFavorites = favorites;
        favoriteAdapter = new FavoriteAdapter(this , favorites);
        recycler_fav.setAdapter(favoriteAdapter);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof FavoriteAdapter.FavoriteViewHolder)
        {
            String name = localFavorites.get(viewHolder.getAdapterPosition()).name;

            final Favorite deletedItem = localFavorites.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();


            //Delete item from adapter
            favoriteAdapter.removeItem(deletedIndex);

            //Delete Item from Room Database

            Common.favoriteRepository.delete(deletedItem);


            Snackbar snackbar = Snackbar.make(rootLayout , new StringBuilder(name).append(" removed from Favorites List").toString(),
                    Snackbar.LENGTH_LONG);
            snackbar.setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favoriteAdapter.restoreItem(deletedItem , deletedIndex);
                    Common.favoriteRepository.insertFav(deletedItem);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();

        }
    }
}
