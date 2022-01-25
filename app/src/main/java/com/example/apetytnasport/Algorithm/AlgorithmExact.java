package com.example.apetytnasport.Algorithm;

import android.content.Context;

import com.example.apetytnasport.Database.FoodDao;
import com.example.apetytnasport.Database.FoodDatabase;
import com.example.apetytnasport.Database.FoodItem;
import com.example.apetytnasport.Database.FoodItemAlgorithmData;
import com.example.apetytnasport.Database.Sport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.xypron.jcobyla.Calcfc;
import de.xypron.jcobyla.Cobyla;

public class AlgorithmExact extends Algorithm {

    public AlgorithmExact(List<FoodItemAlgorithmData> foodItems, double protein, double fat, double carbohydrate) {
        super(foodItems, protein, fat, carbohydrate);
    }

    @Override
    protected void loadData(List<FoodItemAlgorithmData> foodItems) {
        xDim = foodItems.size();

        double[] proteinF = new double[xDim + 1];
        double[] fatF = new double[xDim + 1];
        double[] carbohydrateF = new double[xDim + 1];

        int foodIndex = 0;
        for (FoodItemAlgorithmData foodItem: foodItems) {
            double servingSize = foodItem.servingSizeValue;
            double protein = foodItem.protein;
            double fat = foodItem.fat;
            double carbohydrate = foodItem.carbohydrate;

            proteinF[foodIndex] = - protein / servingSize;
            fatF[foodIndex] = - fat / servingSize;
            carbohydrateF[foodIndex] = - carbohydrate / servingSize;

            foodIndex++;
        }

        proteinF[foodIndex] = this.protein;
        fatF[foodIndex] = this.fat;
        carbohydrateF[foodIndex] = this.carbohydrate;

        globalF = new double[][] { new double[(xDim + 1) * 3] };

        System.arraycopy(proteinF, 0, globalF[0], 0, xDim + 1);
        System.arraycopy(fatF, 0, globalF[0], xDim + 1, xDim + 1);
        System.arraycopy(carbohydrateF, 0, globalF[0], (xDim + 1) * 2, xDim + 1);
    }

    @Override
    protected double[] optimize(double[] a) {
        double rhobeg = 0.5;
        double rhoend = 1.0e-6;
        int iprint = 1;
        int maxfun = 3500;
//        int maxfun = 500;

        Calcfc calcfc = new Calcfc() {
            @Override
            public double compute(int n, int m, double[] x, double[] con) {
                double val = 0;
                double curVal;

                double[] partA = new double[xDim + 1];

                System.arraycopy(a, 0, partA, 0, xDim + 1);

                curVal = fVal(partA, x);
                con[0] = curVal;
                val += curVal;

                System.arraycopy(a, xDim + 1, partA, 0, xDim + 1);

                curVal = fVal(partA, x);
                con[1] = curVal;
                val += curVal;

                System.arraycopy(a, (xDim + 1) * 2, partA, 0, xDim + 1);

                curVal = fVal(partA, x);
                con[2] = curVal;
                val += curVal;

                System.arraycopy(x, 0, con, 3, m - 3);
                return val;
            }
        };
        double[] x = new double[a.length / 3 - 1];
        Arrays.fill(x, 1e-6);
        Cobyla.findMinimum(calcfc, x.length, x.length + 3, x, rhobeg, rhoend, iprint, maxfun);
        return x;
    }

    @Override
    public void printResults() {
        for(double[] ds : allXes) {
            double[] f0 = new double[xDim + 1];
            double[] f1 = new double[xDim + 1];
            double[] f2 = new double[xDim + 1];

            for(int i = 0; i < xDim + 1; i++) {
                f0[i] = globalF[0][i];
                f1[i] = globalF[0][i + xDim + 1];
                f2[i] = globalF[0][i + (xDim + 1) * 2];
            }

            double proteinResult = protein - fVal(f0, ds);
            double fatResult = fat - fVal(f1, ds);
            double carbohydrateResult = carbohydrate - fVal(f2, ds);

            double proteinResultPct = (proteinResult) / protein * 100;
            double fatResultPct = (fatResult) / fat * 100;
            double carbohydrateResultPct = (carbohydrateResult) / carbohydrate * 100;

            System.out.println(proteinResult + " g\t" + proteinResultPct + "%");
            System.out.println(fatResult + " g\t" + fatResultPct + "%");
            System.out.println(carbohydrateResult + " g\t" + carbohydrateResultPct + "%");
            System.out.println();
        }
    }

    @Override
    public ArrayList<Double> getError() {
        ArrayList<Double> errors = new ArrayList<>();

        for(double[] ds : allXes) {
            double[] f0 = new double[xDim + 1];
            double[] f1 = new double[xDim + 1];
            double[] f2 = new double[xDim + 1];

            for(int i = 0; i < xDim + 1; i++) {
                f0[i] = globalF[0][i];
                f1[i] = globalF[0][i + xDim + 1];
                f2[i] = globalF[0][i + (xDim + 1) * 2];
            }

            errors.add(fVal(f0, ds) + fVal(f1, ds) + fVal(f2, ds));
        }

        return errors;
    }
}

