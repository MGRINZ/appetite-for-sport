package com.example.apetytnasport.SetupWizard;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
        Slider intensitySlider = viewGroup.findViewById(R.id.intensity_slider);
        EditText weightInput = viewGroup.findViewById(R.id.weight_input);
        EditText heightInput = viewGroup.findViewById(R.id.height_input);
        EditText ageInput = viewGroup.findViewById(R.id.age_input);
        EditText intensityInput = viewGroup.findViewById(R.id.intensity_input);
        Button nextButton = viewGroup.findViewById(R.id.next_button);

        weightSlider.addOnChangeListener(new OnShapeSliderChangeListener(weightInput));
        heightSlider.addOnChangeListener(new OnShapeSliderChangeListener(heightInput));
        ageSlider.addOnChangeListener(new OnShapeSliderChangeListener(ageInput));
        intensitySlider.addOnChangeListener(new OnShapeSliderChangeListener(intensityInput, true));

        weightInput.setText(String.valueOf((int) weightSlider.getValueFrom()));
        heightInput.setText(String.valueOf((int) heightSlider.getValueFrom()));
        ageInput.setText(String.valueOf((int) ageSlider.getValueFrom()));
        intensityInput.setText(String.valueOf((int) intensitySlider.getValueFrom()));

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
                getSetupWizardActivity().setIntensity(intensitySlider.getValue());
                getSetupWizardActivity().nextPage();
            }
        });

        return viewGroup;
    }

    private class OnShapeSliderChangeListener implements Slider.OnChangeListener {
        private final EditText editInput;
        private final boolean useFloat;

        public OnShapeSliderChangeListener(EditText editInput) {
            this(editInput, false);
        }

        public OnShapeSliderChangeListener(EditText editInput, boolean useFloat) {
            this.editInput = editInput;
            this.useFloat = useFloat;
        }

        @Override
        public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
            String valueString;
            if(useFloat)
                valueString = String.valueOf(value);
            else
                valueString = String.valueOf((int) value);
            editInput.setText(valueString);
        }
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
