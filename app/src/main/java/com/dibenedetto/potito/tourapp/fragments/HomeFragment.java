package com.dibenedetto.potito.tourapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dibenedetto.potito.tourapp.ExploreActivity;
import com.dibenedetto.potito.tourapp.R;
import com.dibenedetto.potito.tourapp.util.ViewUtility;
import com.google.android.material.snackbar.Snackbar;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import static com.dibenedetto.potito.tourapp.util.ViewUtility.findViewById;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class HomeFragment extends Fragment {

    private View layout;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment template_MainFragment.
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.home_fragment, container, false);

        /*
        View explore = ViewUtility.findViewById(layout, R.id.card_view_explore);

        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(layout, "Replace with your own action - Explore", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle onSaveInstanceState) {
        super.onActivityCreated(onSaveInstanceState);

        final CardView card = (CardView) findViewById(layout, R.id.card_home_explore);

        card.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ((ExploreActivity)getActivity()).onHomeCardClicked(v);
            }
        });
    }



}
