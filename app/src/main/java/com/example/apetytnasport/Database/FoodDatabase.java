package com.example.apetytnasport.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {FoodItem.class, NutritionFacts.class, Sport.class, Recommendations.class, Allergens.class}, version = 1)
@TypeConverters({SportConverter.class})
public abstract class FoodDatabase extends RoomDatabase {

    private static FoodDatabase instance;

    public static FoodDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context, FoodDatabase.class, "food.db")
                    .createFromAsset("food.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract FoodDao foodDao();
}
