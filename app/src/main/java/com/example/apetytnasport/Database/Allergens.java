package com.example.apetytnasport.Database;

import androidx.room.Entity;

@Entity(primaryKeys = { "foodId", "allergenId" })
public class Allergens {

    public int foodId;
    public int allergenId;
}
