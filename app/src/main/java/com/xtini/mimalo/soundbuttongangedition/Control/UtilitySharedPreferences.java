package com.xtini.mimalo.soundbuttongangedition.Control;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by matteoma on 3/22/2018.
 */

public class UtilitySharedPreferences {

    public static final String FIRST_ACCESS = "FirstAccess";
    public static final String CLICKED_ARTIST = "ClickedArtist";

    //salvo nelle shared nome appena faccio click
    public static void saveClickedArtistName(Context context , String trapStarName){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CLICKED_ARTIST,trapStarName);
        editor.commit();
    }

    public static String getClickedArtistName(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(CLICKED_ARTIST,"");
    }

    public static boolean artistIsUnlocked(Context context, String trapStarname) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(trapStarname, false);
    }

    public static void lockOrUnlockArtist(Context context, String trapStarName, boolean flag) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(trapStarName, flag);
        editor.commit();
    }

    public static boolean isTheFirstAccess(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.contains(FIRST_ACCESS))
            return false;
        else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(FIRST_ACCESS, true);
            editor.commit();
            return true;
        }
    }

    public static void clearAll(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}
