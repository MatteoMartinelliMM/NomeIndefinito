package com.xtini.mimalo.Trapsoundboard.Control;


import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.xtini.mimalo.Trapsoundboard.Model.AudioFile;
import com.xtini.mimalo.Trapsoundboard.R;
import com.xtini.mimalo.Trapsoundboard.View.ViewUtilities;

import static com.xtini.mimalo.Trapsoundboard.View.SplashScreenActivity.TRAP_SB_DATA;


/**
 * Created by Teo on 03/02/2018.
 */

public class ButtonResViewAdapter extends RecyclerView.Adapter<ButtonResViewAdapter.ViewHolder> {

    public static final String QUALCOSA_È_ANDATO_STORTO_RIPROVA_PIÙ_TARDI = "Qualcosa è andato storto, riprova più tardi!";
    private ArrayList<AudioFile> audioList;
    private Context context;
    private AudioManager audioManager;
    private String trapStarName;
    private Toast toastVolume, toastError;
    public static final String ALZA_IL_VOLUME_BUFU = " Alza il volume BUFU! ";

    public ButtonResViewAdapter(ArrayList<AudioFile> buttonNames, Context context, View v, String trapStarName) {
        this.audioList = buttonNames;
        this.context = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        this.trapStarName = trapStarName;
        toastVolume = ViewUtilities.createCustomToast(context, v, ALZA_IL_VOLUME_BUFU);
        toastError = ViewUtilities.createCustomToast(context, v, QUALCOSA_È_ANDATO_STORTO_RIPROVA_PIÙ_TARDI);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView buttonElement;
        public TextView buttonLabel;
        public ImageButton codeineButton;
        public ImageView share;

        public ViewHolder(View v) {
            super(v);
            buttonLabel = v.findViewById(R.id.buttonLabel);
            codeineButton = v.findViewById(R.id.soundAction);
            share = v.findViewById(R.id.share);
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


        holder.buttonLabel.setText(" " + currentButtonName + " ");

        holder.codeineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                holder.buttonLabel.startAnimation(AnimationUtils.loadAnimation(context, R.anim.button_clicked));
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
                    toastVolume.show();
            }
        });
        holder.codeineButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.button_long_clicked));
                shareTheFile(position);
                return true;
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.button_clicked));
                shareTheFile(position);
            }
        });
    }

    private void shareTheFile(int position) {
        try {
            String fileName = audioList.get(position).getFileName();
            File file = null;
            if (trapStarName.contains("6ix"))
                trapStarName = "6ix9ine";
            int artistFolderPosition = UtilitySharedPreferences.getArtistFolderPosition(context, trapStarName + "_folder");
            if (artistFolderPosition != -1) {
                file = getAudioFile(fileName, artistFolderPosition);
                if (file != null) {
                    shareAudio(file);
                } else {
                    showErrorToast();
                }
            } else {
                showErrorToast();
            }
        } catch (Exception e) {
            showErrorToast();
        }
    }

    @Override
    public int getItemCount() {
        return audioList.size();
    }

    private File getAudioFile(String fileName, int artistPosition) throws Exception {
        InputStream fileFromAssets = getFileFromAssets(fileName, artistPosition);
        return createFileFromInputStream(fileFromAssets, fileName);
    }

    private InputStream getFileFromAssets(String fileName, int artistFolderPosition) throws Exception {
        AssetManager assetManager = context.getResources().getAssets();
        String[] list = assetManager.list(TRAP_SB_DATA);
        return assetManager.open(TRAP_SB_DATA + "/" + list[artistFolderPosition] + "/" + fileName);
    }

    private void shareAudio(File file) {
        Uri path = FileProvider.getUriForFile(context, "com.xtini.mimalo.Trapsoundboard", file);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Trapsoundboard");
        shareIntent.putExtra(Intent.EXTRA_STREAM, path);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setType("audio/*");
        context.startActivity(Intent.createChooser(shareIntent, "Condividi bufu!"));
    }

    private void showErrorToast() {
        toastError.show();
    }

    private boolean volumeIsOn() {
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) != 0;
    }

    private File createFileFromInputStream(InputStream inputStream, String fileName) throws Exception {
        File file = new File(context.getExternalCacheDir() + "/" + fileName);
        OutputStream outputStream = new FileOutputStream(file);
        byte buffer[] = new byte[inputStream.available()];
        int length = 0;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.close();
        inputStream.close();
        return file;
    }
}
