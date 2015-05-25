package com.itpro.mobileapp.mobilevod.homePage;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itpro.mobileapp.mobilevod.BaseFragment;
import com.itpro.mobileapp.mobilevod.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePage extends BaseFragment {


    public HomePage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("anhnt","HomePage-onCreateView");
        return inflater.inflate(R.layout.home_page_fragment, container, false);

    }



}
