package com.example.apetytnasport.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.apetytnasport.SetupWizard.SetupWizardGenderFragment;

@Entity(tableName = "Sports")
public class Sport {

    @PrimaryKey
    public int id;

    @NonNull
    public String name;
    public double proteinPct;
    public double fatPct;
    public double carbohydratePct;
    public double minKcal;
    public double maxKcal;

    @ColumnInfo(defaultValue = "0")
    public int groupId;

    @Ignore
    private double tdee;

    public String getName() {
        return name;
    }

    public double calculateTdee(int trainings, int trainingTime, int gender, int weight, int height, int age, double shape, double intensity) {
        double tdee = 0.0;

        double bmr = (9.99 * weight) + (6.25 * height) - (4.92 * age);

        if(gender == SetupWizardGenderFragment.GENDER_FEMALE)
            bmr -= 161;
        else if(gender == SetupWizardGenderFragment.GENDER_MALE)
            bmr += 5;

        double epoc = 0.07 * bmr;
        double tea = trainings * trainingTime * getKcal(intensity) + trainings * epoc / 7;
        double neat = shape * 700 + 200;
        double tef = 0.1 * (bmr + tea + neat);
        tdee = bmr + tea + neat + tef;

        if(this.tdee != tdee)
            this.tdee = tdee;

        return this.tdee;
    }

    public double getProteinValue() {
        return Math.round((tdee * proteinPct / 4) * 100) / 100.0;
    }

    public double getFatValue() {
        return Math.round((tdee * fatPct / 9) * 100) / 100.0;
    }

    public double getCarbohydrateValue() {
        return Math.round((tdee * carbohydratePct / 4) * 100) / 100.0;
    }

    private double getKcal(double intensity) {
        if(!this.hasVariableKcal())
            return minKcal;

        return (maxKcal - minKcal) * intensity + minKcal;
    }

    public boolean hasVariableKcal() {
        return minKcal != maxKcal;
    }
}
