package com.example.apetytnasport;

import com.example.apetytnasport.SetupWizard.SetupWizardGenderFragment;

public class SportDiscipline {
    private final String name;
    private final double proteinPct;
    private final double fatPct;
    private final double carbohydratePct;
    private final double minKcal;
    private final double maxKcal;

    private double tdee;

    public SportDiscipline(String name, double proteinPct, double fatPct, double carbohydratePct, double minKcal, double maxKcal) {
        this.name = name;
        this.proteinPct = proteinPct;
        this.fatPct = fatPct;
        this.carbohydratePct = carbohydratePct;
        this.minKcal = minKcal;
        this.maxKcal = maxKcal;
    }

    public SportDiscipline(String name, double minKcal, double maxKcal) {
        this(name, 0.125, 0.225, 0.65, minKcal, maxKcal);
    }

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
        if(minKcal == maxKcal)
            return minKcal;

        return (maxKcal - minKcal) * intensity + minKcal;
    }
}
