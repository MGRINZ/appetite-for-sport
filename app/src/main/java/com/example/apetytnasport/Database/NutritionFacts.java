package com.example.apetytnasport.Database;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NutritionFacts {

    @PrimaryKey
    public int id;

    public Double calories;
    public Double carbohydrate;
    public Double protein;
    public Double fat;
    public Double saturatedFat;
    public Double polyunsaturatedFat;
    public Double monounsaturatedFat;
    public Double transFat;
    public Double cholesterol;
    public Double sodium;
    public Double potassium;
    public Double fiber;
    public Double sugar;
    public Double vitaminA;
    public Double vitaminC;
    public Double calcium;
    public Double iron;
}
