package com.cloudjiashhub.accounts.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;

import com.cloudjiashhub.accounts.R;

public class MsgNoNet {
    private Activity activity;

    public MsgNoNet(Activity activity) {
        this.activity = activity;
    }

    public void NoNetconnect(){
        AlertDialog dialogDetails = null;
        LayoutInflater inflater = LayoutInflater.from(activity);
        View dialogview = inflater.inflate(R.layout.msgnoconnect, null);
        final Button btn_go_login = (Button) dialogview.findViewById(R.id.msg_go_login);
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(activity);
        dialogbuilder.setView(dialogview);
        dialogDetails = dialogbuilder.create();
        final AlertDialog alertDialog = (AlertDialog) dialogDetails;
        btn_go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                intent.setComponent(cn);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity( intent);
            }
        });
        alertDialog.show();
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}
