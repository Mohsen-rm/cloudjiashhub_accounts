package com.cloudjiashhub.accounts.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.core.app.ActivityCompat;
import com.cloudjiashhub.accounts.ActivityLogin;
import org.json.JSONObject;

public class DataUser {
    Context context;
    private Boolean is_Login;
    private String fullname;
    private String id_user;
    private String id_main;
    private String id_sessions;
    private String token;
    private String email;

    private String type_account;
    private String username;
    private String userPhoto;
    private String expiry_date_token;
    private String expiry_date_notification_token;
    SharedPreferences.Editor editor;
//    Include include;
//    Urls urls;
    SharedPreferences preferences;
    
    public DataUser(Context context) {
        this.context = context;
//        include = new Include(context);
//        urls = new Urls();

        preferences = context.getSharedPreferences("app_account_data_login", Context.MODE_PRIVATE);
        is_Login = preferences.getBoolean("is_login",false);
//        fullname = preferences.getString("fullname","");
//        token = preferences.getString("token","");
//        expiry_date_token = preferences.getString("token_expiry_date","2000-01-01");
        id_user = preferences.getString("id_user","");
//        email = preferences.getString("email","");
//        type_account = preferences.getString("type_account","");
        username = preferences.getString("username","");
//        userPhoto = preferences.getString("userphoto","");
        editor = preferences.edit();
    }

    public DataUser() {

    }

    public String GetdataStr(String key){
        return preferences.getString(key,"");
    }

    public int GetdataInt(String key){
        return preferences.getInt(key,0);
    }

    public Boolean GetdataBool(String key){
        return preferences.getBoolean(key,false);
    }

    public void setDataStr(String key,String value){
        editor.putString(key,value);
        editor.apply();
    }

    public void setDataBool(String key,Boolean value){
        editor.putBoolean(key,value);
        editor.apply();
    }

    public Boolean SaveData(JSONObject jsonObject){
        try {
            setIs_Login(true);
            setToken(jsonObject.getString("token"));
//            setExpiry_date_token(jsonObject.getString("token_expiry_date"));
            //setId_user(jsonObject.getString("id"));
            //setUsername(jsonObject.getString("username"));
//            setEmail(jsonObject.getString("email"));
//            setFullname(jsonObject.getString("fullname"));
//            setUserPhoto(jsonObject.getString("url_photo_user"));
//            setType_account(jsonObject.getString("account_type"));
            return true;
        }catch (Exception e){
            return false;
        }

    }

    public Boolean ClearData(){
        try {
            SharedPreferences settings = context.getSharedPreferences("app_stu_data_login", Context.MODE_PRIVATE);
            settings.edit().clear().commit();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Boolean getIs_Login() {
        return is_Login;
    }

    public void setIs_Login(Boolean is_Login) {
        this.is_Login = is_Login;
        editor.putBoolean("is_login" ,is_Login);
        editor.apply();
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
        editor.putString("fullname",fullname);
        editor.apply();
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
        editor.putString("id_user",id_user);
        editor.apply();
    }

    public String getId_sessions() {
        return id_sessions;
    }

    public void setId_sessions(String id_sessions) {
        this.id_sessions = id_sessions;
        editor.putString("id_sessions",id_user);
        editor.apply();
    }

    public String getId_main() {
        return id_main;
    }

    public void setId_main(String id_main) {
        this.id_main = id_main;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        editor.putString("token",token);
        editor.apply();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        editor.putString("email",email);
        editor.apply();
    }

    public String getType_account() {
        return type_account;
    }

    public void setType_account(String type_account) {
        this.type_account = type_account;
        editor.putString("type_account",type_account);
        editor.apply();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        editor.putString("username",username);
        editor.apply();
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
        editor.putString("userphoto",userPhoto);
        editor.apply();
    }
    public String getExpiry_date_token() {
        return expiry_date_token;
    }

    public void setExpiry_date_token(String expiry_date_token) {
        this.expiry_date_token = expiry_date_token;
        editor.putString("token_expiry_date",expiry_date_token);
        editor.apply();
    }

    public String getExpiry_date_notification_token() {
        return expiry_date_notification_token;
    }

    public void setExpiry_date_notification_token(String expiry_date_notification_token) {
        this.expiry_date_notification_token = expiry_date_notification_token;
    }

    public void signout(){
        ClearData();
        Intent intent = new Intent(context, ActivityLogin.class);
        context.startActivity(intent);
        ActivityCompat.finishAffinity((Activity) context);
    }
}
