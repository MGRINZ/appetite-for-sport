package com.example.apetytnasport.Database;

import androidx.room.TypeConverter;

public class SportConverter {
    @TypeConverter
    public static int sportToId(Sport sport) {
        return sport.id;
    }
}
