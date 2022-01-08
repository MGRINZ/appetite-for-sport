package com.example.apetytnasport.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = { "groupId", "foodId" })
public class Recommendations {

    public int groupId;
    public int foodId;

}
