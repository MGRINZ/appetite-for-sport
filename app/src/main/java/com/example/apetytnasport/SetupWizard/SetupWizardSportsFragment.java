package com.example.apetytnasport.SetupWizard;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.apetytnasport.Database.FoodDao;
import com.example.apetytnasport.Database.FoodDatabase;
import com.example.apetytnasport.Database.FoodItem;
import com.example.apetytnasport.Database.Sport;
import com.example.apetytnasport.R;

import java.util.ArrayList;
import java.util.List;

public class SetupWizardSportsFragment extends SetupWizardFragment {

    public SetupWizardSportsFragment(SetupWizardActivity activity) {
        super(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.setup_wizard_sports_fragment, container, false);

        RecyclerView recyclerView = viewGroup.findViewById(R.id.sports_grid);
        recyclerView.setAdapter(new SportsAdapter());
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 3));

        return viewGroup;
    }

    private class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.SportsViewHolder> {

        private final List<Sport> sports;
        private final List<Drawable> sportDrawables = new ArrayList<Drawable>();

        public SportsAdapter() {
            FoodDao dao = FoodDatabase.getInstance(getContext()).foodDao();
            sports = dao.getSports();
//            TypedArray sportDrawablesTypedArray = getResources().obtainTypedArray(R.array.sport_drawables);
//            for(int i = 0; i < sportDrawablesTypedArray.length(); i++)
//                sportDrawables.add(sportDrawablesTypedArray.getDrawable(i));
//            sportDrawablesTypedArray.recycle();
        }

        @NonNull
        @Override
        public SportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sports_grid_item, parent, false);
            return new SportsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SportsViewHolder holder, int position) {
            ImageButton button = holder.getButton();
            TextView caption = holder.getCaption();

//            button.setImageDrawable(sportDrawables.get(position));
            caption.setText(sports.get(position).name);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSetupWizardActivity().setSport(sports.get(holder.getAdapterPosition()));
                    getSetupWizardActivity().nextPage();
                }
            });
        }

        @Override
        public int getItemCount() {
            return sports.size();
        }

        public class SportsViewHolder extends RecyclerView.ViewHolder {
            private final ImageButton button;
            private final TextView caption;

            public SportsViewHolder(@NonNull View itemView) {
                super(itemView);
                button = itemView.findViewById(R.id.button);
                caption = itemView.findViewById(R.id.caption);
            }

            public ImageButton getButton() {
                return button;
            }

            public TextView getCaption() {
                return caption;
            }
        }
    }
}
