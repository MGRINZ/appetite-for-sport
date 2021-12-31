package com.example.apetytnasport.SetupWizard;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.apetytnasport.R;

public class SetupWizardActivity extends AppCompatActivity {

    private final int NUM_PAGES = 4;
    private ViewPager2 viewPager;
    private int gender;
    private int sport;
    private int weight;
    private int height;
    private int age;
    private float intensity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_wizard);

        // Transparent status bar
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);

        viewPager = findViewById(R.id.setup_wizard_pager);
        viewPager.setAdapter(new SetupWizardAdapter(this));
        viewPager.setUserInputEnabled(false);
    }

    @Override
    public void onBackPressed() {
        if(viewPager.getCurrentItem() == 0)
            super.onBackPressed();
        else
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
    }

    public void nextPage() {
        if(viewPager.getCurrentItem() == NUM_PAGES - 1)
            return;
        else
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getGender() {
        return gender;
    }

    public void setSport(int sportIndex) {
        this.sport = sportIndex;
    }

    public int getSport() {
        return sport;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    private class SetupWizardAdapter extends FragmentStateAdapter {

        public SetupWizardAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch(position) {
                case 0:
                    return new SetupWizardSportsFragment(SetupWizardActivity.this);
                case 1:
                    return new SetupWizardGenderFragment(SetupWizardActivity.this);
                case 2:
                    return new SetupWizardShapeFragment(SetupWizardActivity.this);
                case 3:
                    return new SetupWizardSummaryFragment(SetupWizardActivity.this);
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}