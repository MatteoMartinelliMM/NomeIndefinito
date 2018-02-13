package com.xtini.mimalo.soundbuttongangedition.Model;

import java.util.ArrayList;

/**
 * Created by Teo on 03/02/2018.
 */

public class TrapStar {
    private String trapStarName;
    private ArrayList<String> buttonNames;


    public String getTrapStarName() {
        return trapStarName;
    }

    public void setTrapStarName(String trapStarName) {
        this.trapStarName = trapStarName;
    }

    public ArrayList<String> getFileNames() {
        return buttonNames;
    }

    public void setFileNames(ArrayList<String> buttonNames) {
        this.buttonNames = buttonNames;
    }
}
