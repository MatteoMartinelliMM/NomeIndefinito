package com.xtini.mimalo.soundbuttongangedition.View;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xtini.mimalo.soundbuttongangedition.Model.AudioFile;
import com.xtini.mimalo.soundbuttongangedition.Model.TrapStar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Teo on 29/01/2018.
 */

public class SplashScreenActivity extends AppCompatActivity {
    private String assetsPath = "///android_asset/TrapSBData/";
    public static ArrayList<TrapStar> trapStars;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trapStars = new ArrayList<>();
        ArrayList<AudioFile> trapStarsAudio = new ArrayList<>();
        int j = 0;

        try {

            String[] artistsFolder = getAssets().list("TrapSBData");
            int howManyArtist = artistsFolder.length;
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
                String debug = "bo";
                trapStars.add(trapStar);
            }
            String debug = "bo";
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, StarChooserActivity.class);
        startActivity(intent);

        finish();
    }
}
