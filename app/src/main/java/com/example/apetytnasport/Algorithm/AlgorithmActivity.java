package com.example.apetytnasport.Algorithm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.apetytnasport.Database.FoodDao;
import com.example.apetytnasport.Database.FoodDatabase;
import com.example.apetytnasport.Database.FoodItemAlgorithmData;
import com.example.apetytnasport.Database.Sport;
import com.example.apetytnasport.MainActivity;
import com.example.apetytnasport.NoStatusBarActivity;
import com.example.apetytnasport.R;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AlgorithmActivity extends NoStatusBarActivity {

    private ExecutorService executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm);

        Intent intent = getIntent();
        int sportId = intent.getIntExtra("sportId", 0);
        double protein = intent.getDoubleExtra("protein", 0);
        double fat = intent.getDoubleExtra("fat", 0);
        double carbohydrate = intent.getDoubleExtra("carbohydrate", 0);

        FoodDao dao = FoodDatabase.getInstance(this).foodDao();
        Sport sport = dao.getSport(sportId);
        List<FoodItemAlgorithmData> foodItems = dao.getRecommendations(sport);

        executor = Executors.newSingleThreadExecutor();
        executor.submit(new Runnable() {
            @Override
            public void run() {
                Algorithm algorithm = new AlgorithmExact(foodItems, protein, fat, carbohydrate);
                algorithm.start();
                algorithm.printResults();

                if(Thread.interrupted())
                    return;

                Intent intent = new Intent(AlgorithmActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }
}