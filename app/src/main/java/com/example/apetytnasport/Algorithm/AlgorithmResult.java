package com.example.apetytnasport.Algorithm;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.apetytnasport.Database.FoodItemAlgorithmData;

public class AlgorithmResult implements Parcelable {

    private FoodItemAlgorithmData foodItem;
    private double servingSize;

    public AlgorithmResult(FoodItemAlgorithmData foodItem, double servingSize) {
        this.foodItem = foodItem;
        this.servingSize = servingSize;
    }

    protected AlgorithmResult(Parcel in) {
        foodItem = in.readParcelable(FoodItemAlgorithmData.class.getClassLoader());
        servingSize = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(foodItem, flags);
        dest.writeDouble(servingSize);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AlgorithmResult> CREATOR = new Creator<AlgorithmResult>() {
        @Override
        public AlgorithmResult createFromParcel(Parcel in) {
            return new AlgorithmResult(in);
        }

        @Override
        public AlgorithmResult[] newArray(int size) {
            return new AlgorithmResult[size];
        }
    };

    public FoodItemAlgorithmData getFoodItem() {
        return foodItem;
    }

    public double getServingSize() {
        return servingSize;
    }
}
