package com.example.apetytnasport.SetupWizard;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class SetupWizardFragment extends Fragment {

    private final SetupWizardActivity activity;

    public SetupWizardFragment(SetupWizardActivity activity) {
        this.activity = activity;
    }

    protected SetupWizardActivity getSetupWizardActivity() {
        return activity;
    }

}
