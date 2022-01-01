package com.example.apetytnasport.Database;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FoodDao {
    @Query("SELECT * FROM FoodItem")
    public List<FoodItem> getFoodItems();

    @Query("SELECT * FROM NutritionFacts WHERE id = :id")
    public NutritionFacts getNutritionFacts(int id);
}
