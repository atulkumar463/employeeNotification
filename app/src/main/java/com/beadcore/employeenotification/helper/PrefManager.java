package com.beadcore.employeenotification.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public  class PrefManager {




    private static final String SHARED_PREFERENCES_NAME = "employee_app_Attendenece_details";

    public  static String LOGIN_STATUS= "login_status";
    public  static String EMP_ID= "emp001";
    public  static String LOGIN_TOKEN= "login_token";
    public  static String FCM_TOKEN= "fcm_token";
    public  static String NAME= "name";
    public  static String COMPANY_NAME= "name";
    public  static String IMAGE= "image";







    public static void  clearPref(Context context)
    {
        SharedPreferences prefs =context .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.apply();


    }



    public static void  clearKeyPref(Context context,String key)
    {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.remove(key);
        edit.apply();


    }

    public static String  readStringPref(Context context,String key)
    {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        return prefs.getString(key,"");



    }

    public static void  writeStringPref(Context context,String key, String data)
    {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(key,data);
        edit.apply();

    }







    public static boolean  readBooleanPref(Context context,String key)
    {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        return prefs.getBoolean(key,false);



    }

    public static void  writeBooleanPref(Context context,String key, boolean data)
    {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean(key,data);
        edit.apply();

    }

}
