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

import com.andreabaccega.widget.FormEditText;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.itpro.mobileapp.mobilevod.R;
import com.itpro.mobileapp.mobilevod.util.Common;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountLoginFragment extends Fragment {
    public static String TAG="AccountLoginFragment";
    private CallbackManager callbackManager;
    private FormEditText txtEmail;
    private FormEditText txtPassword;
    private LoginButton fb_login_button;
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
                Log.i(" anhnt KeyHash2:", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }

           FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
            Log.i("anhnt", "AccessToken.getCurrentAccessToken()=" + AccessToken.getCurrentAccessToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        View rootView=inflater.inflate(R.layout.account_login_fragment, container, false);
        fb_login_button=(LoginButton)rootView.findViewById(R.id.fb_login_button);


        initFB();




        Button btLogin=(Button)rootView.findViewById(R.id.btLogin);
        Button btRegister=(Button)rootView.findViewById(R.id.btRegister);

        txtEmail=(FormEditText)rootView.findViewById(R.id.txtEmail);
        txtPassword=(FormEditText)rootView.findViewById(R.id.txtPassword);


        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivityForResult( (new Intent(getActivity(),AccountRegisterActivity.class)), Common.AR_AccountRegisterActivity);
            }
        });
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        return rootView;

    }
    private void initFB(){
        fb_login_button.setReadPermissions("user_friends");
        fb_login_button.setReadPermissions("email");
        fb_login_button.setFragment(this);




        callbackManager = CallbackManager.Factory.create();

        fb_login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
/*                Log.i("anhnt", "LoginResult.getCurrentAccessToken()=" + loginResult.getAccessToken());
                Log.i("anhnt", "AccessToken.getCurrentAccessToken()=" + AccessToken.getCurrentAccessToken().getToken());

                Profile p = Profile.getCurrentProfile();
                Log.i("anhnt", "Profile=" + p);


               final  ProfileTracker mProfileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                        Log.v("anhnt", profile2.getFirstName());
                        Profile p = Profile.getCurrentProfile();
                        Log.i("anhnt", "Profile=" + p);
                    }
                };
                mProfileTracker.startTracking();*/


            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }


        });






    }


    public void facebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    public void onSuccess(LoginResult loginResult) {
                        Log.i(TAG, "Login OK");
                        Log.i(TAG, "accessToken:" + loginResult.getAccessToken().getToken());
                        AccessToken accessToken = loginResult.getAccessToken();

                    }

                    @Override
                    public void onCancel() {
                        Log.i(TAG, "Login Cancel");
                    }

                    @Override
                    public void onError(FacebookException e) {
                        Log.i(TAG, "Login Error");
                    }
                });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    public void login() {
        boolean passCheck=false;
        if(!txtPassword.testValidity()){
            txtPassword.requestFocus();
        }else {
            passCheck = true;
        }
        if(!txtEmail.testValidity()){
              txtEmail.requestFocus();
        }else {
            passCheck = true;
        }

    }

}
