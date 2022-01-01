package com.example.apetytnasport.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class FoodItem {

    @PrimaryKey
    public int id;

    public String name;
    public String image;
    public double servingSizeValue;
    public String servingSizeUnit;
    public int nutritionFactsId;

    @Ignore
    private NutritionFacts nutritionFacts;

    public NutritionFacts getNutritionFacts(Context context) {
        if(nutritionFacts == null) {
            FoodDao dao = FoodDatabase.getInstance(context).foodDao();
            nutritionFacts = dao.getNutritionFacts(nutritionFactsId);
        }
        return nutritionFacts;
    }
}
