package com.xtini.mimalo.Trapsoundboard.Control;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.xtini.mimalo.Trapsoundboard.View.SplashScreenActivity;

/**
 * Created by matteoma on 3/22/2018.
 */

public class UtilitySharedPreferences {

    public static final String FIRST_ACCESS = "FirstAccess";
    public static final String CLICKED_ARTIST = "ClickedArtist";
    public static final String CLICK_ARTIST_COUNT = "ClickArtistCount";

    //salvo nelle shared nome appena faccio click
    public static void saveClickedArtistName(Context context, String trapStarName) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CLICKED_ARTIST, trapStarName);
        editor.commit();
    }

    public static String getClickedArtistName(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(CLICKED_ARTIST, "");
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

    public static void clearAll(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public static int getClickArtistCount(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.contains(CLICK_ARTIST_COUNT))
            return preferences.getInt(CLICK_ARTIST_COUNT, 0);
        else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(CLICK_ARTIST_COUNT, 0);
            return 0;
        }
    }

    public static void incrementArtistCount(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(CLICK_ARTIST_COUNT, getClickArtistCount(context) + 1);
        editor.commit();

    }

    public static void clearNrOfClick(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(CLICK_ARTIST_COUNT, 0);
        editor.commit();

    }

    public static void artistFolderPosition(Context context, String artistName, int index) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(artistName, index);
        editor.commit();
    }

    public static int getArtistFolderPosition(Context context, String artistName) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(artistName, -1);
    }
}
