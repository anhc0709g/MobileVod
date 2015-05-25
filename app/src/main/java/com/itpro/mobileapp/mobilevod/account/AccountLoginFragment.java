package com.itpro.mobileapp.mobilevod.account;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.FacebookSdk;
import com.itpro.mobileapp.mobilevod.R;
import com.itpro.mobileapp.mobilevod.util.Common;

import java.security.MessageDigest;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountLoginFragment extends Fragment {
    public static String TAG="AccountLoginFragment";
    public AccountLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo( "com.itpro.mobileapp.mobilevod",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash2:", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }

          //  FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        View rootView=inflater.inflate(R.layout.account_login_fragment, container, false);
        Button btLogin=(Button)rootView.findViewById(R.id.btLogin);
        Button btRegister=(Button)rootView.findViewById(R.id.btRegister);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivityForResult( (new Intent(getActivity(),AccountRegisterActivity.class)), Common.AR_AccountRegisterActivity);
            }
        });

        return rootView;

    }


}
