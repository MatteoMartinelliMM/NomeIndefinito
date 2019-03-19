package com.xtini.mimalo.Trapsoundboard.View;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.xtini.mimalo.Trapsoundboard.BuildConfig;
import com.xtini.mimalo.Trapsoundboard.Control.RestCall;
import com.xtini.mimalo.Trapsoundboard.Control.UtilitySharedPreferences;
import com.xtini.mimalo.Trapsoundboard.Model.AudioFile;
import com.xtini.mimalo.Trapsoundboard.Model.TrapStar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.xtini.mimalo.Trapsoundboard.R;


/**
 * Created by Teo on 29/01/2018.
 */

public class SplashScreenActivity extends AppCompatActivity implements RestCall.OnResultReceived {
    public static final String TRAP_SB_DATA = "TrapSBData";

    public static final String TONY_EFFE = "TonyEffe";
    public static final String FIRS_ACCESS = "FirsAccess";
    public static ArrayList<TrapStar> trapStars;
    private AlertDialog alert;
    private boolean firstAccess = false;
    private AlertDialog.Builder builder;
    private Context context;
    private boolean flag = false;
    private ArrayList<AudioFile> trapStarsAudio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trapStars = new ArrayList<>();
        context = this;
        trapStarsAudio = new ArrayList<>();
        if (!isNetworkAvailable())
            showNoConnectionDialog();
        else {
            web_update();
        }

    }

    private void getDataRoutine() {

        try {
            String[] artistsFolder = getAssets().list(TRAP_SB_DATA);
            int howManyArtist = artistsFolder.length;
            int index = 0;
            for (String artistName : artistsFolder) {
                UtilitySharedPreferences.artistFolderPosition(this, artistName + "_folder", index);
                index++;
            }
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

    @Override
    protected void onResume() {
        super.onResume();
        if (flag) {
            if (isNetworkAvailable()) {
                web_update();
            } else {
                showNoConnectionDialog();
            }
        }
    }

    private void showUpdateDialog() {
        builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme)
                .setMessage("E' uscita una nuova versione vuoi aggiornare ?").setPositiveButton("Seeh", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = Uri.parse("market://details?id=" + getPackageName());
                        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        try {
                            context.startActivity(myAppLinkToMarket);
                        } catch (ActivityNotFoundException e) {
                            ViewUtilities.createCustomToast(context, findViewById(android.R.id.content), " Impossibile aprire il contenuto!").show();
                        }
                    }
                }).setNegativeButton("No!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });

        alert = builder.create();
        alert.show();
    }

    private void showNoConnectionDialog() {

        builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme)
                .setMessage("Hai bisogno della connessione Internet per utilizzare l'app, vuoi attivarla bufetto?").setPositiveButton("Dati", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        flag = true;
                        startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                    }
                }).setNegativeButton("No!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).setNeutralButton("Wi-Fi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        flag = true;
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                });

        alert = builder.create();
        alert.show();
    }

    private void updateUi() {
        Intent intent = new Intent(this, StarChooserActivity.class);
        startActivity(intent);
        finish();
    }


    private void web_update() {
        RestCall.get("Versione.json", this);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onSuccess(String response) {
        String bo = response;
        bo.contains("df");
        if (response.equalsIgnoreCase(BuildConfig.VERSION_NAME)) {
            getDataRoutine();
        } else {
            showUpdateDialog();
        }
    }

    @Override
    public void onFailure(int statusCode) {
        ViewUtilities.createCustomToast(this, findViewById(android.R.id.content), "Problema di connessione, riprova più tardi!").show();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(Toast.LENGTH_SHORT);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
