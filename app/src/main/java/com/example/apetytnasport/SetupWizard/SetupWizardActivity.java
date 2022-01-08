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

import com.example.apetytnasport.Database.Sport;
import com.example.apetytnasport.NoStatusBarActivity;
import com.example.apetytnasport.R;

public class SetupWizardActivity extends NoStatusBarActivity {

    private final int NUM_PAGES = 5;
    private ViewPager2 viewPager;
    private int gender;
    private Sport sport;
    private int trainings;
    private int trainingTime;
    private int weight;
    private int height;
    private int age;
    private float intensity;
    private float shape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_wizard);

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

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public Sport getSport() {
        return sport;
    }

    public void setTrainings(int trainings) {
        this.trainings = trainings;
    }

    public int getTrainings() {
        return trainings;
    }

    public void setTrainingTime(int trainingTime) {
        this.trainingTime = trainingTime;
    }

    public int getTrainingTime() {
        return trainingTime;
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

    public float getShape() {
        return shape;
    }

    public void setShape(float shape) {
        this.shape = shape;
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
                    return new SetupWizardTrainingsFragment(SetupWizardActivity.this);
                case 4:
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