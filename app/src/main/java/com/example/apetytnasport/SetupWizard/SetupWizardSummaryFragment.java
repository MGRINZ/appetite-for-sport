package com.example.apetytnasport.SetupWizard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.apetytnasport.Algorithm.AlgorithmActivity;
import com.example.apetytnasport.R;
import com.example.apetytnasport.Database.Sport;

public class SetupWizardSummaryFragment extends SetupWizardFragment {

    private TextView kcalView;
    private TextView proteinView;
    private TextView fatView;
    private TextView carbohydrateView;

    private double kcalVal;
    private double proteinVal;
    private double fatVal;
    private double carbohydrateVal;

    private SetupWizardActivity activity;

    public SetupWizardSummaryFragment(SetupWizardActivity activity) {
        super(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.setup_wizard_summary_fragment, container, false);

        this.kcalView = viewGroup.findViewById(R.id.kcal);
        this.proteinView = viewGroup.findViewById(R.id.protein);
        this.fatView = viewGroup.findViewById(R.id.fat);
        this.carbohydrateView = viewGroup.findViewById(R.id.carbohydrate);

        Button prepareDietButton = viewGroup.findViewById(R.id.prepare_diet);
        prepareDietButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, AlgorithmActivity.class);
                intent.putExtra("sportId", activity.getSport().id);
                intent.putExtra("protein", proteinVal);
                intent.putExtra("fat", fatVal);
                intent.putExtra("carbohydrate", carbohydrateVal);

                activity.startActivity(intent);
            }
        });

        this.activity = getSetupWizardActivity();

        return viewGroup;
    }

    @Override
    public void onResume() {
        super.onResume();

        Sport sportDiscipline = activity.getSport();

        this.kcalVal = sportDiscipline.calculateTdee(
                activity.getTrainings(),
                activity.getTrainingTime(),
                activity.getGender(),
                activity.getWeight(),
                activity.getHeight(),
                activity.getAge(),
                activity.getShape(),
                activity.getIntensity()
        );

        this.proteinVal = sportDiscipline.getProteinValue();
        this.fatVal = sportDiscipline.getFatValue();
        this.carbohydrateVal = sportDiscipline.getCarbohydrateValue();

        this.kcalView.setText(getResources().getString(R.string.kcal_unit, (int) this.kcalVal));
        this.proteinView.setText(getResources().getString(R.string.g_unit, this.proteinVal));
        this.fatView.setText(getResources().getString(R.string.g_unit, this.fatVal));
        this.carbohydrateView.setText(getResources().getString(R.string.g_unit, this.carbohydrateVal));
    }
}

