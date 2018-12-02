package com.mitrajani.blooddonation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Mit Rajani on 12/1/2018.
 */

public class SharedPrefManager
{
    private static final String SHARED_PREF_NAME = "Name";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_FNAME = "keyfname";
    private static final String KEY_LNAME = "keylname";
    private static final String KEY_DOB = "keydob";
    private static final String KEY_ID ="keyid";

    private static SharedPrefManager mypref;
    private static Context mycontext;

    private SharedPrefManager (Context context){
        mycontext = context;
    }

    public static synchronized  SharedPrefManager getInstance(Context context){
        if (mypref == null){
            mypref = new SharedPrefManager(context);
        }
        return mypref;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mycontext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_FNAME, user.getFname());
        editor.putString(KEY_LNAME, user.getLname());
        editor.putString(KEY_DOB, user.getDOB());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mycontext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mycontext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_FNAME, null),
                sharedPreferences.getString(KEY_LNAME, null),
                sharedPreferences.getString(KEY_DOB, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mycontext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mycontext.startActivity(new Intent(mycontext, LoginActivity.class));
    }

}
