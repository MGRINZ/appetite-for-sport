package com.example.apetytnasport.SetupWizard;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.apetytnasport.R;

public class SetupWizardSummaryFragment extends SetupWizardFragment {

    private ViewGroup viewGroup;

    public SetupWizardSummaryFragment(SetupWizardActivity activity) {
        super(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.setup_wizard_summary_fragment, container, false);
        return viewGroup;
    }

    @Override
    public void onResume() {
        super.onResume();

        TextView kcal = viewGroup.findViewById(R.id.kcal);
        TextView protein = viewGroup.findViewById(R.id.protein);
        TextView fat = viewGroup.findViewById(R.id.fat);
        TextView carbohydrate = viewGroup.findViewById(R.id.carbohydrate);

        SetupWizardActivity activity = getSetupWizardActivity();

        double kcalValue = calculateTdee(
                activity.getSport(),
                1, //activity.getTrainings(),
                60, //activity.getTrainingTime(),
                activity.getGender(),
                activity.getWeight(),
                activity.getHeight(),
                activity.getAge(),
                activity.getShape()
        );

        double proteinValue = Math.round((kcalValue * 0.125 / 4) * 100) / 100.0;
        double fatValue = Math.round((kcalValue * 0.225 / 9) * 100) / 100.0;
        double carbohydrateValue = Math.round((kcalValue * 0.65 / 4) * 100) / 100.0;

        kcal.setText(getResources().getString(R.string.kcal_unit, (int) kcalValue));
        protein.setText(getResources().getString(R.string.g_unit, proteinValue));
        fat.setText(getResources().getString(R.string.g_unit, fatValue));
        carbohydrate.setText(getResources().getString(R.string.g_unit, carbohydrateValue));
    }

    private double calculateTdee(int sport, int trainings, int trainingTime, int gender, int weight, int height, int age, double shape) {
        double tdee = 0;

        double bmr = (9.99 * weight) + (6.25 * height) - (4.92 * age);

        if(gender == SetupWizardGenderFragment.GENDER_FEMALE)
            bmr -= 161;
        else if(gender == SetupWizardGenderFragment.GENDER_MALE)
            bmr += 5;

        double epoc = 0.07 * bmr;
        double tea = trainings * trainingTime * sportKcal(sport) + trainings * epoc / 7;
        double neat = shape * 700 + 200;
        double tef = 0.1 * (bmr + tea + neat);
        tdee = bmr + tea + neat + tef;
        return tdee;
    }

    private float sportKcal(int sportIndex) {
        // TypedArray kcal = getResources().obtainTypedArray(R.array.sport_kcal);
        // float value = kcal.getFloat(sportIndex, 0);
        // kcal.recycle();
        float value = (float)1.23;
        return value;
    }
}

