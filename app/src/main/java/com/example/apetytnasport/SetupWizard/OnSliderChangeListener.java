package com.example.apetytnasport.SetupWizard;

import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.material.slider.Slider;

class OnSliderChangeListener implements Slider.OnChangeListener {
    private final EditText editInput;
    private final boolean useFloat;

    public OnSliderChangeListener(EditText editInput) {
        this(editInput, false);
    }

    public OnSliderChangeListener(EditText editInput, boolean useFloat) {
        this.editInput = editInput;
        this.useFloat = useFloat;
    }

    @Override
    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
        String valueString;
        if (useFloat)
            valueString = String.valueOf(value);
        else
            valueString = String.valueOf((int) value);
        editInput.setText(valueString);
    }
}
