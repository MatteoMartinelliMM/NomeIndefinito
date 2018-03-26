package com.xtini.mimalo.soundbuttongangedition.View;


import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.xtini.mimalo.soundbuttongangedition.Control.UtilitySharedPreferences;
import com.xtini.mimalo.soundbuttongangedition.Model.AudioFile;
import com.xtini.mimalo.soundbuttongangedition.Model.TrapStar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.xtini.mimalo.soundbuttongangedition.R;

import org.jsoup.Jsoup;


/**
 * Created by Teo on 29/01/2018.
 */

public class SplashScreenActivity extends AppCompatActivity {
    public static final String TRAP_SB_DATA = "TrapSBData";

    public static final String FIRST_ACCESS = "FirstAccess";
    public static final String TONY_EFFE = "TonyEffe";
    public static final String FIRS_ACCESS = "FirsAccess";
    private String assetsPath = "///android_asset/TrapSBData/";
    private boolean firstAccess = false;
    public static ArrayList<TrapStar> trapStars;
    private AlertDialog alert;
    private AlertDialog.Builder builder;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trapStars = new ArrayList<>();
        context = this;
        ArrayList<AudioFile> trapStarsAudio = new ArrayList<>();
        if (isNetworkAvailable() && web_update())
            showUpdateDialog();
        else {
            try {
                String[] artistsFolder = getAssets().list(TRAP_SB_DATA);
                int howManyArtist = artistsFolder.length;
                if (UtilitySharedPreferences.isTheFirstAccess(this)) { //SE USR FA PRIMO ACCESSO ALL APP -> NON PERMETTO ALL'USR DI ACCEDERE ALL'ACTIVITY DEI BOTTONI
                    for (int i = 0; i < howManyArtist; i++) {
                        if (!artistsFolder[i].equalsIgnoreCase(TONY_EFFE))
                            UtilitySharedPreferences.lockOrUnlockArtist(this, artistsFolder[i], false);
                        else
                            UtilitySharedPreferences.lockOrUnlockArtist(this, artistsFolder[i], true);
                    }
                    firstAccess = true;
                }
                for (int i = 0; i < howManyArtist; i++) {
                    ArrayList<String> artistFile = new ArrayList<>(Arrays.asList(getAssets().list("TrapSBData/" + artistsFolder[i])));
                    TrapStar trapStar = new TrapStar();
                    boolean firstFile = true;
                    for (String fileName : artistFile) {
                        if (firstFile) {
                            trapStar.setTrapStarName(artistsFolder[i]);
                            firstFile = false;
                        }
                        AssetFileDescriptor assetFileDescriptor = getAssets().openFd("TrapSBData/" + artistsFolder[i] + "/" + fileName);
                        AudioFile fileAudio = new AudioFile();
                        fileAudio.setFileName(fileName);
                        fileAudio.setSound(assetFileDescriptor);
                        trapStarsAudio.add(fileAudio);

                    }
                    trapStar.setFileNames(trapStarsAudio);
                    trapStarsAudio = new ArrayList<>();
                    trapStars.add(trapStar);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            updateUi();
        }

    }

    private void showUpdateDialog() {
        builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme)
                .setMessage("E' uscita una nuova versione vuoi aggiornare ?").setPositiveButton("Seeh", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = Uri.parse("market://details?id=" + "com.facebook.katana"/*context.getPackageName()*/); //TODO: scommentare getPackageName()
                        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        try {
                            context.startActivity(myAppLinkToMarket);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(context, " Sorry, Not able to open!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("No!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SplashScreenActivity splashScreenActivity = (SplashScreenActivity) dialogInterface;
                        splashScreenActivity.finish();
                    }
                });

        alert = builder.create();
        alert.show();
    }

    private void updateUi() {
        Intent intent = new Intent(this, StarChooserActivity.class);
        intent.putExtra(FIRS_ACCESS, firstAccess);
        startActivity(intent);
        finish();
    }

    private boolean web_update() {
        try {
            String curVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            String newVersion = curVersion;
            newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + getPackageName() + "&hl=en")
                    .timeout(30000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get()
                    .select("div[itemprop=softwareVersion]")
                    .first()
                    .ownText();
            return (Float.valueOf(curVersion) < Float.valueOf(newVersion));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
