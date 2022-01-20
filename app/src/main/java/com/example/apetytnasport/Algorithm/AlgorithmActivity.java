package com.example.apetytnasport.Algorithm;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.apetytnasport.Database.FoodDao;
import com.example.apetytnasport.Database.FoodDatabase;
import com.example.apetytnasport.Database.FoodItemAlgorithmData;
import com.example.apetytnasport.Database.Sport;
import com.example.apetytnasport.MainActivity;
import com.example.apetytnasport.NoStatusBarActivity;
import com.example.apetytnasport.R;
import com.example.apetytnasport.SetupWizard.SetupWizardActivity;

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

                ArrayList<ArrayList<AlgorithmResult>> algorithmResults = removeSubstitutes(algorithm.getResults());
                ArrayList<ArrayList<AlgorithmResult>> newAlgorithmResults;


                try {
                    while(true) {
                        newAlgorithmResults = removeBelowAverage(algorithmResults);
                        if(compareResults(algorithmResults, newAlgorithmResults))
                            break;
                        algorithmResults = newAlgorithmResults;
                    }

                    Intent intent = new Intent(AlgorithmActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("dietLists", newAlgorithmResults);
                    startActivity(intent);
                }
                catch(Exception e) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.prepare_diet_error), Toast.LENGTH_SHORT);
                    Intent intent = new Intent(getApplicationContext(), SetupWizardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }
        });
    }

    @NonNull
    private ArrayList<ArrayList<AlgorithmResult>> removeSubstitutes(ArrayList<ArrayList<AlgorithmResult>> algorithmResults) {
        ArrayList<ArrayList<AlgorithmResult>> newAlgorithmResults = new ArrayList<>();

        for(ArrayList<AlgorithmResult> productsList : algorithmResults) {

            ArrayList<FoodItemAlgorithmData> newFoodItems = new ArrayList<>();
            for (AlgorithmResult algorithmResult : productsList)
                newFoodItems.add(algorithmResult.getFoodItem());

            for(int i = 0; i < productsList.size(); i++) {
                AlgorithmResult result = productsList.get(i);
                FoodItemAlgorithmData foodItem = result.getFoodItem();

                if(foodItem.substituteGroup == null)
                    continue;

                for(int j = i + 1; j < productsList.size(); j++) {
                    AlgorithmResult comparedResult = productsList.get(j);
                    FoodItemAlgorithmData comparedFoodItem = comparedResult.getFoodItem();

                    if(foodItem.substituteGroup != comparedFoodItem.substituteGroup)
                        continue;

                    if(result.getServingSize() < comparedResult.getServingSize()) {
                        newFoodItems.remove(foodItem);
                        break;
                    }
                    else
                        newFoodItems.remove(comparedFoodItem);
                }
            }

            Algorithm algorithm = new AlgorithmExact(newFoodItems, protein, fat, carbohydrate);
            algorithm.start();
            newAlgorithmResults.addAll(algorithm.getResults());
        }

        return newAlgorithmResults;
    }

    @NonNull
    private ArrayList<ArrayList<AlgorithmResult>> removeBelowAverage(ArrayList<ArrayList<AlgorithmResult>> algorithmResults) {
        ArrayList<ArrayList<AlgorithmResult>> newAlgorithmResults = new ArrayList<>();

        for(ArrayList<AlgorithmResult> productsList : algorithmResults) {
            double averageServingSize = getAverageServingSize(productsList);
            double threshold = averageServingSize / 2;

            ArrayList<FoodItemAlgorithmData> newFoodItems = new ArrayList<>();
            for (AlgorithmResult algorithmResult : productsList) {
                if(algorithmResult.getServingSize() >= threshold)
                    newFoodItems.add(algorithmResult.getFoodItem());
            }

            Algorithm algorithm = new AlgorithmExact(newFoodItems, protein, fat, carbohydrate);
            algorithm.start();
            algorithm.printResults();

            newAlgorithmResults.addAll(algorithm.getResults());
        }

        return newAlgorithmResults;
    }

    private double getAverageServingSize(ArrayList<AlgorithmResult> productsList) {
        double sum = 0.0;
        for(AlgorithmResult r : productsList)
            sum += r.getServingSize();
        return sum / productsList.size();
    }

    /**
     * Compares two Algorithm results lists checking if they have the same size
     * @param prevAlgorithmResults One of Algorithm results list
     * @param newAlgorithmResults Another of Algorithm results list
     * @return true if results have the same size, false otherwise
     * @throws Exception when Algorithm results lists have different dimensions
     */
    private boolean compareResults(ArrayList<ArrayList<AlgorithmResult>> prevAlgorithmResults, ArrayList<ArrayList<AlgorithmResult>> newAlgorithmResults) throws Exception {
        if(prevAlgorithmResults.size() != newAlgorithmResults.size())
            throw new Exception("Algorithm results have different dimensions");

        for(int i = 0; i < prevAlgorithmResults.size(); i++) {
            if(prevAlgorithmResults.get(i).size() != newAlgorithmResults.get(i).size())
                return false;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }
}