package com.itpro.mobileapp.mobilevod.util;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

/**
 * Created by anhnt on 5/14/2015.
 */
public class Common {
    public static int AR_AccountRegisterActivity=1000;

    public static void hideKeyBoard(Activity context){
        context.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }
}
