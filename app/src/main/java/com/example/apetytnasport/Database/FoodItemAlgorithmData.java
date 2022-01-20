package com.example.apetytnasport.Database;

import android.os.Parcel;
import android.os.Parcelable;

public class FoodItemAlgorithmData implements Parcelable {

    public String name;
    public double servingSizeValue;
    public String servingSizeUnit;
    public Double carbohydrate;
    public Double protein;
    public Double fat;
    public Integer substituteGroup;

    public FoodItemAlgorithmData() {

    }


    protected FoodItemAlgorithmData(Parcel in) {
        name = in.readString();
        servingSizeValue = in.readDouble();
        servingSizeUnit = in.readString();
        if (in.readByte() == 0) {
            carbohydrate = null;
        } else {
            carbohydrate = in.readDouble();
        }
        if (in.readByte() == 0) {
            protein = null;
        } else {
            protein = in.readDouble();
        }
        if (in.readByte() == 0) {
            fat = null;
        } else {
            fat = in.readDouble();
        }
        if (in.readByte() == 0) {
            substituteGroup = null;
        } else {
            substituteGroup = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(servingSizeValue);
        dest.writeString(servingSizeUnit);
        if (carbohydrate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(carbohydrate);
        }
        if (protein == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(protein);
        }
        if (fat == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(fat);
        }
        if (substituteGroup == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(substituteGroup);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FoodItemAlgorithmData> CREATOR = new Creator<FoodItemAlgorithmData>() {
        @Override
        public FoodItemAlgorithmData createFromParcel(Parcel in) {
            return new FoodItemAlgorithmData(in);
        }

        @Override
        public FoodItemAlgorithmData[] newArray(int size) {
            return new FoodItemAlgorithmData[size];
        }
    };
}
