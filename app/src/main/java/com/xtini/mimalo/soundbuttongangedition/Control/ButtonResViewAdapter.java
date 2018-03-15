package com.xtini.mimalo.soundbuttongangedition.Control;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import com.xtini.mimalo.soundbuttongangedition.Model.AudioFile;
import com.xtini.mimalo.soundbuttongangedition.R;
import com.xtini.mimalo.soundbuttongangedition.View.TrapStarActivity;
import com.xtini.mimalo.soundbuttongangedition.View.ViewUtilities;



/**
 * Created by Teo on 03/02/2018.
 */

public class ButtonResViewAdapter extends RecyclerView.Adapter<ButtonResViewAdapter.ViewHolder> {

    private ArrayList<AudioFile> audioList;
    private Context context;
    private AudioManager audioManager;
    private Toast toast;
    public static final String ALZA_IL_VOLUME_BUFU = " Alza il volume BUFU! ";

    public ButtonResViewAdapter(ArrayList<AudioFile> buttonNames, Context context, View v) {
        this.audioList = buttonNames;
        this.context = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        toast = ViewUtilities.createCustomToast(context, v, ALZA_IL_VOLUME_BUFU);

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView buttonElement;
        public TextView buttonLabel;
        public ImageButton codeineButton;

        public ViewHolder(View v) {
            super(v);
            buttonElement = itemView.findViewById(R.id.buttonElement);
            buttonLabel = v.findViewById(R.id.buttonLabel);
            codeineButton = v.findViewById(R.id.soundAction);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if (parent != null)
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_button_list, null);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String currentButtonName = audioList.get(position).getFileName();
        currentButtonName = currentButtonName.substring(0, currentButtonName.length() - 4);
        currentButtonName = currentButtonName.replace("_", " ");
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "futura-heavy-oblique.ttf");
        holder.buttonLabel.setTypeface(typeface);

        if (currentButtonName.contains(" ") && currentButtonName.length() > 16)
            holder.buttonLabel.setText(currentButtonName);
        else
            holder.buttonLabel.setText("  " + currentButtonName + "  ");

        holder.codeineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                holder.buttonLabel.startAnimation(AnimationUtils.loadAnimation(context, R.anim.button_clicked));
                TrapStarActivity.howManyClick++;
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.button_clicked));
                AssetFileDescriptor assetFileDescriptor = audioList.get(position).getSound();
                final MediaPlayer mp = new MediaPlayer();
                MediaPlayerRegistry.put(mp);
                if (volumeIsOn()) {
                    try {
                        mp.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                        mp.prepare();
                        mp.start();
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                MediaPlayerRegistry.releaseSinglePlayer(mediaPlayer.getAudioSessionId());
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else
                    toast.show();
            }

        });

    }

    private boolean volumeIsOn() {
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) != 0;
    }

    @Override
    public int getItemCount() {
        return audioList.size();
    }


}
