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
import com.example.apetytnasport.SportDiscipline;

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

        SportDiscipline sportDiscipline = new SportDiscipline("", 0.62, 1.23);

        double kcalValue = sportDiscipline.calculateTdee(
                1,
                60,
                activity.getGender(),
                activity.getWeight(),
                activity.getHeight(),
                activity.getAge(),
                activity.getShape(),
                1.0
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

