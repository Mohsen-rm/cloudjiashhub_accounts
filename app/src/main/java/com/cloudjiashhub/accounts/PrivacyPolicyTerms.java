package com.cloudjiashhub.accounts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PrivacyPolicyTerms extends AppCompatActivity {

    Button btnAgree;
    TextView text_Agree_to_terms;
    CheckBox checkAgree_to_terms;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "historysubscribers";
    public static final String Preferences_CheckAgrreTerms = "Keyagree";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy_terms);

        checkAgree_to_terms = findViewById(R.id.checkBox_agree_to_terms);
        btnAgree = findViewById(R.id.agree_to_terms);

        btnAgree.setEnabled(false);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Preferences_CheckAgrreTerms)) {

        }

        checkAgree_to_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAgree_to_terms.isChecked()){
                    btnAgree.setEnabled(true);
                    btnAgree.setBackgroundColor(Color.parseColor("#FFC107"));
                }else {
                    btnAgree.setEnabled(false);
                    btnAgree.setBackgroundResource(R.drawable.button_bg_transparent);
                }
            }
        });

        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Preferences_CheckAgrreTerms,"yes");
                editor.commit();
            }
        });
    }
}