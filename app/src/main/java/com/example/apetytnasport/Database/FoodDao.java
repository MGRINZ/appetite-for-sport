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

    @Query("SELECT r.groupId, fi.name, fi.servingSizeValue, fi.servingSizeUnit, fi.substituteGroup, nf.carbohydrate, nf.protein, nf.fat FROM FoodItem AS fi " +
            "JOIN NutritionFacts AS nf ON nf.id = fi.id " +
            "LEFT JOIN Recommendations AS r ON fi.id = r.foodId " +
            "LEFT JOIN Sports AS s ON s.groupId = r.groupId " +
            "WHERE s.id = :sport " +
//            "WHERE (s.id = :sport OR r.groupId IS NULL) " +
            "    AND fi.id NOT IN (" +
            "        SELECT foodId FROM Allergens" +
            "        WHERE allergenId IN (:allergens)" +
            "    )" +
            "ORDER BY fi.name")
    public List<FoodItemAlgorithmData> getRecommendations(Sport sport, int[] allergens);

    @Query("SELECT r.groupId, fi.name, fi.servingSizeValue, fi.servingSizeUnit, fi.substituteGroup, nf.carbohydrate, nf.protein, nf.fat FROM FoodItem AS fi " +
            "JOIN NutritionFacts AS nf ON nf.id = fi.id " +
            "LEFT JOIN Recommendations AS r ON fi.id = r.foodId " +
            "LEFT JOIN Sports AS s ON s.groupId = r.groupId " +
//            "WHERE s.id = :sport " +
            "WHERE (s.id = :sport OR r.groupId IS NULL) " +
            "    AND fi.id NOT IN (" +
            "        SELECT foodId FROM Allergens" +
            "        WHERE allergenId IN (:allergens)" +
            "    )" +
            "ORDER BY fi.name")
    public List<FoodItemAlgorithmData> getMoreThanRecommendations(Sport sport, int[] allergens);
}
