package com.xtini.mimalo.soundbuttongangedition.Control;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.xtini.mimalo.soundbuttongangedition.R;



/**
 * Created by Teo on 03/02/2018.
 */

public class ButtonResViewAdapter extends RecyclerView.Adapter<ButtonResViewAdapter.ViewHolder> {
    private ArrayList<String> buttonNames;
    private Context context;

    public ButtonResViewAdapter(ArrayList<String> buttonNames, Context context) {
        this.buttonNames = buttonNames;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView buttonElement;
        public ImageView soundAction;
        public TextView buttonLabel;
        public ViewHolder(View v) {
            super(v);
            buttonElement = itemView.findViewById(R.id.buttonElement);
            soundAction = v.findViewById(R.id.soundAction);
            buttonLabel = v.findViewById(R.id.buttonLabel);
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        String currentButtonName = buttonNames.get(position);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "futura-heavy-oblique.ttf");
        holder.buttonLabel.setTypeface(typeface);
        holder.buttonLabel.setText(" "+currentButtonName+"  ");

    }

    @Override
    public int getItemCount() {
        return buttonNames.size();
    }



}
