package com.xtini.mimalo.soundbuttongangedition.View;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.xtini.mimalo.soundbuttongangedition.Control.UtilitySharedPreferences;
import com.xtini.mimalo.soundbuttongangedition.Model.AudioFile;
import com.xtini.mimalo.soundbuttongangedition.Model.TrapStar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trapStars = new ArrayList<>();
        ArrayList<AudioFile> trapStarsAudio = new ArrayList<>();

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

    private void updateUi() {
        Intent intent = new Intent(this, StarChooserActivity.class);
        intent.putExtra(FIRS_ACCESS,firstAccess);
        startActivity(intent);
        finish();
    }

}
