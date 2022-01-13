package com.example.apetytnasport.SetupWizard;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.apetytnasport.R;
import com.google.android.material.slider.Slider;

public class SetupWizardShapeFragment extends SetupWizardFragment {

    public SetupWizardShapeFragment(SetupWizardActivity activity) {
        super(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.setup_wizard_shape_fragment, container, false);

        Slider weightSlider = viewGroup.findViewById(R.id.weight_slider);
        Slider heightSlider = viewGroup.findViewById(R.id.height_slider);
        Slider ageSlider = viewGroup.findViewById(R.id.age_slider);
        Slider shapeSlider = viewGroup.findViewById(R.id.shape_slider);
        EditText weightInput = viewGroup.findViewById(R.id.weight_input);
        EditText heightInput = viewGroup.findViewById(R.id.height_input);
        EditText ageInput = viewGroup.findViewById(R.id.age_input);
        Button nextButton = viewGroup.findViewById(R.id.next_button);

        weightSlider.addOnChangeListener(new OnSliderChangeListener(weightInput));
        heightSlider.addOnChangeListener(new OnSliderChangeListener(heightInput));
        ageSlider.addOnChangeListener(new OnSliderChangeListener(ageInput));

        weightInput.setText(String.valueOf((int) weightSlider.getValue()));
        heightInput.setText(String.valueOf((int) heightSlider.getValue()));
        ageInput.setText(String.valueOf((int) ageSlider.getValueFrom()));

        weightInput.addTextChangedListener(new ShapeTextWatcher(weightSlider));
        //
        // TODO: fix user input and add the rest of the watchers
        //

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSetupWizardActivity().setWeight((int) weightSlider.getValue());
                getSetupWizardActivity().setHeight((int) heightSlider.getValue());
                getSetupWizardActivity().setAge((int) ageSlider.getValue());
                getSetupWizardActivity().setShape(shapeSlider.getValue());
                getSetupWizardActivity().nextPage();
            }
        });

        return viewGroup;
    }

    private class ShapeTextWatcher implements TextWatcher {
        private final Slider slider;

        // TODO: Add useFloat attribute

        public ShapeTextWatcher(Slider slider) {
            this.slider = slider;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int value;
            if(s.length() <= 0)
                value = (int) slider.getValueFrom();
            else
                value = Integer.parseInt(s.toString());
            if(value < slider.getValueFrom())
                value = (int) slider.getValueFrom();
            else if(value > slider.getValueTo())
                value = (int) slider.getValueTo();

            slider.setValue(value);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
