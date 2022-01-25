package com.example.apetytnasport.Algorithm;

import android.content.Context;

import com.example.apetytnasport.Database.FoodItem;
import com.example.apetytnasport.Database.FoodItemAlgorithmData;
import com.example.apetytnasport.Database.Sport;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

public abstract class Algorithm {
    protected ArrayList<double[]> allXes = new ArrayList<>();
    protected double[][] globalF;
    protected int xDim;

    protected double protein;
    protected double fat;
    protected double carbohydrate;
    private final List<FoodItemAlgorithmData> foodItems;

    public Algorithm(List<FoodItemAlgorithmData> foodItems, double protein, double fat, double carbohydrate) {
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.foodItems = foodItems;

        loadData(foodItems);
    }

    protected static boolean isXSaved(ArrayList<double[]> allXes, double[] nextX) {
        for (double[] prevX : allXes) {
            boolean same = true;

            for(int i = 0; i < nextX.length; i++) {
                if(Math.round(nextX[i] * 1000) / 1000.0 != Math.round(prevX[i] * 1000) / 1000.0) {
                    same = false;
                    break;
                }
            }

            if(same)
                return true;
        }
        return false;
    }

    protected double fVal(double[] a, double[] x) {
        double y = 0;
        for(int i = 0; i < x.length; i++)
            y += a[i] * x[i];
        y += a[a.length - 1];
        return y;
    }

    protected abstract double[] optimize(double[] a);
    protected abstract void loadData(List<FoodItemAlgorithmData> foodItems);
    public abstract ArrayList<Double> getError();

    public void start() {
        double[][] f = globalF;

        double[][] x = new double[f.length][f[0].length - 1];
        for(int i = 0; i < f.length; i++)
        {
            x[i] = optimize(f[i]);
            if(!isXSaved(allXes, x[i]))
                allXes.add(x[i]);
        }
    }

    public void printResults() {
        for(double[] ds : allXes) {
            // for(int i = 0; i < ds.length; i++)
            // if(ds[i] != 0)
            //     System.out.println("x" + i + ": " + ds[i]);

            double[][] f = globalF;

            System.out.println(fVal(f[0], ds) + "\t" + (f[0][f[0].length - 1] - fVal(f[0], ds)) / f[0][f[0].length - 1] * 100 + "%");
            System.out.println(fVal(f[1], ds) + "\t" + (f[1][f[1].length - 1] - fVal(f[1], ds)) / f[1][f[1].length - 1] * 100 + "%");
            System.out.println(fVal(f[2], ds) + "\t" + (f[2][f[2].length - 1] - fVal(f[2], ds)) / f[2][f[2].length - 1] * 100 + "%");
            System.out.println();
        }
    }

    public ArrayList<ArrayList<AlgorithmResult>> getResults() {
        ArrayList<ArrayList<AlgorithmResult>> results = new ArrayList<>();

        for (double[] x : allXes) {
            ArrayList<AlgorithmResult> r = new ArrayList<AlgorithmResult>();
            for(int i = 0; i < x.length; i++)
                r.add(new AlgorithmResult(foodItems.get(i), x[i]));
            results.add(r);
        }
        return results;
    }
}

