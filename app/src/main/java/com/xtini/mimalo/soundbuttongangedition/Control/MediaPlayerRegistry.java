package com.xtini.mimalo.soundbuttongangedition.Control;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matteoma on 3/5/2018.
 */

public class MediaPlayerRegistry {
    public static List<MediaPlayer> playersList = new ArrayList<>();

    public static void put(MediaPlayer player) {
        playersList.add(player);
    }

    public static void closePlayers(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.onStart(); //Blocco la UI ficnhè non ha stoppato tutti i suoni
        for (MediaPlayer player : playersList)
            if (player != null && player.isPlaying())
                player.release();
        playersList = new ArrayList<>();
        dialog.dismiss();
        dialog.cancel();
    }
}