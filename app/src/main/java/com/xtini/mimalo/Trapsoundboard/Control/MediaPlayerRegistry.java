package com.xtini.mimalo.Trapsoundboard.Control;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;


import java.util.HashMap;
import java.util.Iterator;


/**
 * Created by matteoma on 3/5/2018.
 */

public class MediaPlayerRegistry {
    public static HashMap<Integer , MediaPlayer> playersList = new HashMap<>();

    public static void put(MediaPlayer player) {
        playersList.put(player.getAudioSessionId(),player);
    }

    public static void closePlayers(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.onStart();
        Iterator<Integer> list = playersList.keySet().iterator();
        try {
            while(list.hasNext()){
                Integer i = list.next();
                playersList.get(i).release();
            }
        }catch (IllegalStateException e){

        }finally {
            playersList = new HashMap<>();
            dialog.dismiss();
            dialog.cancel();
        }
    }

    public static void releaseSinglePlayer(Integer i){
        playersList.get(i).release();
    }
}