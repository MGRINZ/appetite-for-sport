package com.example.apetytnasport;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apetytnasport.Algorithm.AlgorithmResult;
import com.example.apetytnasport.Database.FoodItemAlgorithmData;
import com.example.apetytnasport.SetupWizard.SetupWizardActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ArrayList<AlgorithmResult>> dietLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        Intent intent = getIntent();

        dietLists = (ArrayList<ArrayList<AlgorithmResult>>) intent.getSerializableExtra("dietLists");

        RecyclerView dietView = findViewById(R.id.diet_view);
        dietView.setAdapter(new DietListAdapter());
        dietView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.new_diet) {
            Intent intent = new Intent(getApplicationContext(), SetupWizardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class DietListAdapter extends RecyclerView.Adapter<DietListAdapter.DietListViewHolder> {
        @NonNull
        @Override
        public DietListAdapter.DietListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diet_list_item, parent, false);
            return new DietListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DietListViewHolder holder, int position) {
            AlgorithmResult result = dietLists.get(0).get(position);
            FoodItemAlgorithmData foodItem = result.getFoodItem();

            double servingSize = result.getServingSize();
            double protein = foodItem.protein / foodItem.servingSizeValue * result.getServingSize();
            double fat = foodItem.fat / foodItem.servingSizeValue * result.getServingSize();
            double carbohydrate = foodItem.carbohydrate / foodItem.servingSizeValue * result.getServingSize();

            holder.getName().setText(foodItem.name);
            holder.getServingSize().setText(String.format(Locale.getDefault(), "%.2f %s", servingSize, foodItem.servingSizeUnit));
            holder.getProtein().setText(getResources().getString(R.string.g_unit, protein));
            holder.getFat().setText(getResources().getString(R.string.g_unit, fat));
            holder.getCarbohydrate().setText(getResources().getString(R.string.g_unit, carbohydrate));
        }

        @Override
        public int getItemCount() {
            return dietLists.get(0).size();
        }

        private class DietListViewHolder extends RecyclerView.ViewHolder {

            private final TextView name;
            private final TextView servingSize;
            private final TextView protein;
            private final TextView fat;
            private final TextView carbohydrate;

            public DietListViewHolder(View view) {
                super(view);
                name = view.findViewById(R.id.name);
                servingSize = view.findViewById(R.id.serving);
                protein = view.findViewById(R.id.protein);
                fat = view.findViewById(R.id.fat);
                carbohydrate = view.findViewById(R.id.carbohydrate);
            }

            public TextView getName() {
                return name;
            }

            public TextView getServingSize() {
                return servingSize;
            }

            public TextView getProtein() {
                return protein;
            }

            public TextView getFat() {
                return fat;
            }

            public TextView getCarbohydrate() {
                return carbohydrate;
            }
        }
    }
}