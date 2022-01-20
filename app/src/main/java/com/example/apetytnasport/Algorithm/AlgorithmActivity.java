package com.example.apetytnasport.Algorithm;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;

import com.example.apetytnasport.Database.FoodDao;
import com.example.apetytnasport.Database.FoodDatabase;
import com.example.apetytnasport.Database.FoodItemAlgorithmData;
import com.example.apetytnasport.Database.Sport;
import com.example.apetytnasport.MainActivity;
import com.example.apetytnasport.NoStatusBarActivity;
import com.example.apetytnasport.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AlgorithmActivity extends NoStatusBarActivity {

    private ExecutorService executor;
    private double protein;
    private double fat;
    private double carbohydrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm);

        Intent intent = getIntent();
        int sportId = intent.getIntExtra("sportId", 0);
        protein = intent.getDoubleExtra("protein", 0);
        fat = intent.getDoubleExtra("fat", 0);
        carbohydrate = intent.getDoubleExtra("carbohydrate", 0);

        FoodDao dao = FoodDatabase.getInstance(this).foodDao();
        Sport sport = dao.getSport(sportId);
        List<FoodItemAlgorithmData> foodItems = dao.getRecommendations(sport);

        executor = Executors.newSingleThreadExecutor();
        executor.submit(new Runnable() {
            @Override
            public void run() {
                Algorithm algorithm = new AlgorithmExact(foodItems, protein, fat, carbohydrate);
                algorithm.start();

                if(Thread.interrupted())
                    return;

                ArrayList<ArrayList<AlgorithmResult>> algorithmResults = removeSubstitutes(algorithm.getResults(), foodItems);

                Intent intent = new Intent(AlgorithmActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("dietLists", algorithmResults);
                startActivity(intent);
            }
        });
    }

    @NonNull
    private ArrayList<ArrayList<AlgorithmResult>> removeSubstitutes(ArrayList<ArrayList<AlgorithmResult>> algorithmResults, List<FoodItemAlgorithmData> foodItems) {
        ArrayList<ArrayList<AlgorithmResult>> newAlgorithmResults = new ArrayList<>();

        for(ArrayList<AlgorithmResult> productsList : algorithmResults) {
            ArrayList<FoodItemAlgorithmData> newFoodItems = new ArrayList<>(foodItems);
            for(int i = 0; i < productsList.size(); i++) {
                FoodItemAlgorithmData foodItem = productsList.get(i).getFoodItem();
                for(int j = i + 1; j < productsList.size(); j++) {
                    FoodItemAlgorithmData comparedFoodItem = productsList.get(j).getFoodItem();

                    if(foodItem.substituteGroup == null)
                        continue;

                    if(!newFoodItems.contains(comparedFoodItem))
                        continue;

                    if(foodItem.substituteGroup == comparedFoodItem.substituteGroup) {
                        if(foodItem.servingSizeValue < comparedFoodItem.servingSizeValue) {
                            newFoodItems.remove(foodItem);
                            break;
                        }
                        else
                            newFoodItems.remove(comparedFoodItem);
                    }
                }
            }

            Algorithm algorithm = new AlgorithmExact(newFoodItems, protein, fat, carbohydrate);
            algorithm.start();
            newAlgorithmResults.addAll(algorithm.getResults());
        }

        return newAlgorithmResults;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }
}