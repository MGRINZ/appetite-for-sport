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

    @Query("SELECT * FROM Sports ORDER BY id")
    public List<Sport> getSports();

    @Query("SELECT * FROM Sports WHERE id = :id")
    public Sport getSport(int id);

    @Query("SELECT fi.name, fi.servingSizeValue, nf.carbohydrate, nf.protein, nf.fat FROM FoodItem AS fi " +
            "JOIN NutritionFacts AS nf ON nf.id = fi.id " +
            "JOIN Recommendations AS r ON fi.id = r.foodId " +
            "JOIN Sports AS s ON s.groupId = r.groupId " +
            "WHERE s.id = :sport " +
            "ORDER BY fi.name")
    public List<FoodItemAlgorithmData> getRecommendations(Sport sport);
}
