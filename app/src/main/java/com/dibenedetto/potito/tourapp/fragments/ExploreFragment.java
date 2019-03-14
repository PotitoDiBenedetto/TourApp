package com.dibenedetto.potito.tourapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dibenedetto.potito.tourapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
//import androidx.appcompat.app.AppCompatActivity;
import com.dibenedetto.potito.tourapp.util.ViewUtility;

public class ExploreFragment extends Fragment {

    private ExploreViewModel mViewModel;

    public static ExploreFragment newInstance() {
        return new ExploreFragment();
    }

    public ExploreFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //this mActivity = () context;

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.explore_fragment, container, false);

        FloatingActionButton fab = (FloatingActionButton) ViewUtility.findViewById(layout, R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ExploreViewModel.class);
        // TODO: Use the ViewModel
    }

    /**
     * Interface that the Activity should implement to be notified of an item selection
     */
    public interface OnCardItemSelectedListener {

        /**
         * Invoked when we select an item
         *
         * @param item The selected busStop
         */
        //void onCardItemSelected(BusStop busStop);

    }

}
