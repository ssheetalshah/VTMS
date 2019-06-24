package android.ics.com.vtms.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class AppPreference {

    public static final String SHARED_PREFERENCE_NAME = "SATSUNG";
    public static final String ID = "id";
    public static final String IS_LOGIN = "isLogin";
    public static final String LOGID = "logid";
    public static final String LICENSE = "license";
    public static final String SERVICE = "user_service";
    public static final String SUBSERVICE = "user_subservice";

    Context context;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editor2;


    public static void setId(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ID, value);
        editor.commit();
    }

    public static String getId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(ID, "");
    }

    //--------------------------------
    public static void setLogid(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LOGID, value);
        editor.commit();
    }

    public static String getLogid(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(LOGID, "");
    }

    //-------------------------------------
    public static void setLicense(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LICENSE, value);
        editor.commit();
    }

    public static String getLicense(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(LICENSE, "");
    }

    //------------------------------
    public static void setService(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SERVICE, value);
        editor.commit();
    }

    public static String getService(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(SERVICE, "");
    }

    //-----------------------------------
    public static void setSubservice(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SUBSERVICE, value);
        editor.commit();
    }

    public static String getSubservice(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(SUBSERVICE, "");
    }




    public void cleardatetime(){
        editor2.clear();
        editor2.commit();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(IS_LOGIN, false);
    }

}
