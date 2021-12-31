package com.example.apetytnasport.SetupWizard;

import android.content.res.TypedArray;
import android.os.Bundle;
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

        kcal.setText(getResources().getString(R.string.kcal_unit, calculateTmr(activity.getSport(),
                activity.getGender(),
                activity.getWeight(),
                activity.getHeight(),
                activity.getAge(),
                activity.getIntensity())));
    }

    private int calculateTmr(int sport, int gender, int weight, int height, int age, float intensity) {
        double bmr = 0;

        if(gender == SetupWizardGenderFragment.GENDER_FEMALE)
            bmr = 655 + 9.6 * weight + 1.8 * height - 4.7 * age;
        else if(gender == SetupWizardGenderFragment.GENDER_MALE)
            bmr = 66.4 + 13.7 * weight + 6.0 * height - 6.8 * age;

        double tmr = (bmr + sportKcal(sport) * weight) * intensityMult(intensity);

        return (int) tmr;
    }

    private float sportKcal(int sportIndex) {
        TypedArray kcal = getResources().obtainTypedArray(R.array.sport_kcal);
        float value = kcal.getFloat(sportIndex, 0);
        kcal.recycle();
        return value;
    }

    private double intensityMult(float normalizedIntensity) {
        return (normalizedIntensity / 10) + 1.40;
    }
}

