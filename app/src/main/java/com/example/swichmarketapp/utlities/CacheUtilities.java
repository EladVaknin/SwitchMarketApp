package com.example.swichmarketapp.utlities;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;

public class CacheUtilities {

    public static final String USER_NAME_KEY = "userName";
    public static final String PHONE_NUMBER_KEY = "phoneNumber";
    public static final String IMAGE_PROFILE_KEY = "imageProfile";
    public static final String RATING_KEY = "ratingKey";
    public static final String USER_FILE = "user";


    public static void cacheUserName(Activity activity, String userName) {
        SharedPreferences.Editor editor = activity.getSharedPreferences(USER_FILE, MODE_PRIVATE).edit();
        editor.putString(USER_NAME_KEY, userName);
        editor.apply();
    }

    public static void cachePhoneNumber(Activity activity, String phoneNumber) {
        SharedPreferences.Editor editor = activity.getSharedPreferences(USER_FILE, MODE_PRIVATE).edit();
        editor.putString(PHONE_NUMBER_KEY, phoneNumber);
        editor.apply();
    }

    public static String getUserName(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences(USER_FILE, MODE_PRIVATE);
        return prefs.getString(USER_NAME_KEY, "");

    }

    public static String getPhoneNumber(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences(USER_FILE, MODE_PRIVATE);
        return prefs.getString(PHONE_NUMBER_KEY, "");

    }

    public static void cacheImageProfile(Activity activity, String imageProfile) {
        SharedPreferences.Editor editor = activity.getSharedPreferences(USER_FILE, MODE_PRIVATE).edit();
        editor.putString(IMAGE_PROFILE_KEY, imageProfile);
        editor.apply();
    }

    public static void clearAll(Activity activity) {
        SharedPreferences.Editor editor = activity.getSharedPreferences(USER_FILE, MODE_PRIVATE).edit();
        editor.clear().apply();
    }

    public static String getImageProfile(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences(USER_FILE, MODE_PRIVATE);
        return prefs.getString(IMAGE_PROFILE_KEY, "");
    }

    public static void cacheRating(Activity activity, float rating) {
        SharedPreferences.Editor editor = activity.getSharedPreferences(USER_FILE, MODE_PRIVATE).edit();
        editor.putFloat(RATING_KEY, rating);
        editor.apply();
    }

    public static float getRating(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences(USER_FILE, MODE_PRIVATE);
        return prefs.getFloat(RATING_KEY, 0f);
    }


}
