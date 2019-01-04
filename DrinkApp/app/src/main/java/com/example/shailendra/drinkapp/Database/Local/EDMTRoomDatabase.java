package com.example.shailendra.drinkapp.Database.Local;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.shailendra.drinkapp.Database.ModelDB.Cart;
import com.example.shailendra.drinkapp.Database.ModelDB.Favorite;

/**
 * Created by shailendra on 6/13/2018.
 */

@Database(entities = {Cart.class , Favorite.class} , version = 1 )
public abstract class EDMTRoomDatabase extends RoomDatabase {

    public abstract CartDAO cartDAO();
    public abstract FavoriteDAO favoriteDAO();


    private static EDMTRoomDatabase instance;

    public static EDMTRoomDatabase getInstance(Context context)
    {
        if(instance==null)
            instance = Room.databaseBuilder(context , EDMTRoomDatabase.class,"EDMT_DrinkshopDB")
                    .allowMainThreadQueries()
                    .build();

        return  instance;


    }

}
