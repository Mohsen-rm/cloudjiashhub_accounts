package com.cloudjiashhub.accounts.app;

import android.content.Context;
import android.content.SharedPreferences;

public class CheckAgrreTerms {

    public Context context;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "historysubscribers";
    public static final String Preferences_CheckAgrreTerms = "Keyagree";

    public CheckAgrreTerms(Context context) {
        this.context = context;
    }

    public boolean checkAgree() {
        sharedpreferences = context.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Preferences_CheckAgrreTerms)) {
            if (sharedpreferences.getString(Preferences_CheckAgrreTerms, "").equals("yes")){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
}
