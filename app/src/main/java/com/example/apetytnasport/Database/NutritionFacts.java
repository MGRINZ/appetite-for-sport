package com.example.apetytnasport.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NutritionFacts {

    @PrimaryKey
    public int id;

    public double calories;
    public double carbohydrate;
    public double protein;
    public double fat;
    public double saturatedFat;
    public double polyunsaturatedFat;
    public double monounsaturatedFat;
    public double transFat;
    public double cholesterol;
    public double sodium;
    public double potassium;
    public double fiber;
    public double sugar;
    public double vitaminA;
    public double vitaminC;
    public double calcium;
    public double iron;
}
