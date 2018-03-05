package com.xtini.mimalo.soundbuttongangedition.Model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Created by Teo on 03/02/2018.
 */

public class TrapStar {
    private String trapStarName;
    private ArrayList<AudioFile> files;


    public String getTrapStarName() {
        return trapStarName;
    }

    public void setTrapStarName(String trapStarName) {
        this.trapStarName = trapStarName;
    }



    public ArrayList<AudioFile> getFileNames() {
        return files;
    }

    public void setFileNames(ArrayList<AudioFile> files) {
        this.files = files;
    }
}
