package com.example.apetytnasport.SetupWizard;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.apetytnasport.R;

import java.util.ArrayList;

public class SelectAllergensDialog extends DialogFragment {

    private final boolean[] checkedAllergens;
    private final ArrayList<Integer> selectedAllergens;

    private final SelectAllergensDialogListener listener;

    public SelectAllergensDialog(Fragment fragment, ArrayList<Integer> selectedAllergens) {
        checkedAllergens = new boolean[fragment.getResources().getStringArray(R.array.allergen_items).length];
        listener = (SelectAllergensDialogListener)fragment;
        this.selectedAllergens = new ArrayList<>(selectedAllergens);
        for(int i : selectedAllergens)
            this.checkedAllergens[i] = true;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.select_allergens_title));
        builder.setMultiChoiceItems(R.array.allergen_items, checkedAllergens, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked)
                    selectedAllergens.add(which);
                else
                    selectedAllergens.remove((Integer)which);
            }
        });
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onAllergensSave(selectedAllergens);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { }
        });
        return builder.create();
    }

    public interface SelectAllergensDialogListener {
        void onAllergensSave(ArrayList<Integer> selectedAllergens);
    }
}
