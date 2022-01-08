package com.example.apetytnasport.SetupWizard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.apetytnasport.R;
import com.google.android.material.slider.Slider;

public class SetupWizardTrainingsFragment extends SetupWizardFragment {

    public SetupWizardTrainingsFragment(SetupWizardActivity activity) {
        super(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.setup_wizard_training_fragment, container, false);

        Slider trainingsSlider = viewGroup.findViewById(R.id.trainings_slider);
        Slider trainingTimeSlider = viewGroup.findViewById(R.id.trainingTime_slider);
        Slider intensitySlider = viewGroup.findViewById(R.id.intensity_slider);
        EditText trainingsInput = viewGroup.findViewById(R.id.trainings_input);
        EditText trainingTimeInput = viewGroup.findViewById(R.id.trainingTime_input);
        EditText intensityInput = viewGroup.findViewById(R.id.intensity_input);
        Button nextButton = viewGroup.findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSetupWizardActivity().setTrainings((int) trainingsSlider.getValue());
                getSetupWizardActivity().setTrainingTime((int) trainingTimeSlider.getValue());
                getSetupWizardActivity().setIntensity(intensitySlider.getValue() / 10);
                getSetupWizardActivity().nextPage();
            }
        });

        return viewGroup;
    }
}
