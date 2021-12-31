package com.example.apetytnasport.SetupWizard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.apetytnasport.R;

public class SetupWizardGenderFragment extends SetupWizardFragment {

    public static final int GENDER_FEMALE = 0;
    public static final int GENDER_MALE = 1;

    public SetupWizardGenderFragment(SetupWizardActivity activity) {
        super(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.setup_wizard_gender_fragment, container, false);
        ImageButton femaleButton = viewGroup.findViewById(R.id.female);
        ImageButton maleButton = viewGroup.findViewById(R.id.male);

        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSetupWizardActivity().setGender(GENDER_FEMALE);
                getSetupWizardActivity().nextPage();
            }
        });

        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSetupWizardActivity().setGender(GENDER_MALE);
                getSetupWizardActivity().nextPage();
            }
        });

        return viewGroup;
    }
}
