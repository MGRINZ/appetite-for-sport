package com.example.apetytnasport.SetupWizard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.apetytnasport.R;
import com.example.apetytnasport.Database.Sport;

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

        Sport sportDiscipline = activity.getSport();

        double kcalValue = sportDiscipline.calculateTdee(
                activity.getTrainings(),
                activity.getTrainingTime(),
                activity.getGender(),
                activity.getWeight(),
                activity.getHeight(),
                activity.getAge(),
                activity.getShape(),
                activity.getIntensity()
        );

        double proteinValue = Math.round((kcalValue * 0.125 / 4) * 100) / 100.0;
        double fatValue = Math.round((kcalValue * 0.225 / 9) * 100) / 100.0;
        double carbohydrateValue = Math.round((kcalValue * 0.65 / 4) * 100) / 100.0;

        kcal.setText(getResources().getString(R.string.kcal_unit, (int) kcalValue));
        protein.setText(getResources().getString(R.string.g_unit, sportDiscipline.getProteinValue()));
        fat.setText(getResources().getString(R.string.g_unit, sportDiscipline.getFatValue()));
        carbohydrate.setText(getResources().getString(R.string.g_unit, sportDiscipline.getCarbohydrateValue()));
    }
}

