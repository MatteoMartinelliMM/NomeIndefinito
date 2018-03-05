package com.xtini.mimalo.soundbuttongangedition.Model;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.InputStream;

/**
 * Created by matteoma on 3/4/2018.
 */

public class AudioFile {
    AssetFileDescriptor sound;
    String fileName;

    public AudioFile() {
    }

    public AssetFileDescriptor getSound() {
        return sound;
    }

    public void setSound(AssetFileDescriptor sound) {
        this.sound = sound;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

