package android.ics.com.vtms.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class SessionManager {


    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    public static final String MyPREFERENCES = "MyPrefss";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_USERNAME = "userName";
    public static final String KEY_MOBILE = "mobile";
    private static final String UserID = "user_id";
    private static final String LOGINID = "login_id";
    private static final String PAINTING = "painting";
    private static final String REGISTRATION = "registration";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(MyPREFERENCES, PRIVATE_MODE);
        editor = pref.edit();
//        editor = pref.edit();
    }


    public void serverLogin(String strName, int user_Id) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, strName);
        editor.putInt(UserID, user_Id);
        editor.commit();
    }

    public void adminLogin(String strName) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, strName);
//        editor.putInt(UserID, user_Id);
        editor.commit();
    }

    public void dieselSubsidyLogin(String strMobile) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_MOBILE, strMobile);
        editor.commit();
    }

    public void malegaonLogin() {
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    // Clearing all data from Shared Preferences
    public void logoutUser() {
        editor.clear();
        editor.commit();

    }


    public void malegaonLogin(String login_id, String login_uiserid) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(LOGINID, login_id);
        editor.putString(UserID, login_uiserid);
        editor.commit();
    }


    public String getPainting() {
        return pref.getString(PAINTING, null);
    }

    public String getKeyUsername() {
        return pref.getString(KEY_USERNAME, null);
    }


    public String getLoginid() {
        return pref.getString(LOGINID, null);
    }

    public String getUserID() {
        return pref.getString(UserID, null);
    }

    public void registration(String id) {
        editor.putBoolean(REGISTRATION, true);
        editor.putString(UserID, id);
        editor.commit();
    }


}