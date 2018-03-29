package com.xtini.mimalo.Trapsoundboard.Control;

import com.xtini.mimalo.Trapsoundboard.Model.BlankSpaces;

/**
 * Created by matteoma on 3/16/2018.
 */

public class FormatButtonLabel {

    public static String format(String buttonLabel) {
        String formattedButtonlabel = "";
        int lenghtButtonLabel = buttonLabel.length();
        int gapToComplete = 17 - lenghtButtonLabel;
        int numbOfSpace;
        numbOfSpace = gapToComplete / 2;
        String leftSpace,rightSpace;
        if (gapToComplete % 2 == 0) {
            leftSpace = BlankSpaces.BLANK_SPACE_MAP.get(numbOfSpace);
            formattedButtonlabel = leftSpace + buttonLabel + leftSpace;
        } else {
            leftSpace = BlankSpaces.BLANK_SPACE_MAP.get(numbOfSpace);
            rightSpace = BlankSpaces.BLANK_SPACE_MAP.get(numbOfSpace);
            formattedButtonlabel = leftSpace + buttonLabel + rightSpace;
        }
        return formattedButtonlabel;
    }
}
