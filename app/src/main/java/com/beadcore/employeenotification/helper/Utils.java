package com.beadcore.employeenotification.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;

    public static SharedPreferences getSharedPreferencesInstance(Context mContext){
        if (sharedPreferences==null){
            sharedPreferences = mContext.getSharedPreferences("employee_notification",Context.MODE_PRIVATE);
            return sharedPreferences;
        }
        else {
            return sharedPreferences;
        }
    }

    public static SharedPreferences.Editor getEditorInstance(Context mContext){
        if (sharedPreferences==null){
            sharedPreferences = mContext.getSharedPreferences("employee_notification",Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            return editor;
        }
        else {
            editor = sharedPreferences.edit();
            return editor;
        }
    }
}
