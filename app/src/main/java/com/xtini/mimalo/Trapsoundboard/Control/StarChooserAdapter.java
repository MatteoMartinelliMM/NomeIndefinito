package com.xtini.mimalo.Trapsoundboard.Control;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtini.mimalo.Trapsoundboard.Model.AudioFile;
import com.xtini.mimalo.Trapsoundboard.Model.TrapStar;
import com.xtini.mimalo.Trapsoundboard.R;
import com.xtini.mimalo.Trapsoundboard.View.StarChooserActivity;
import com.xtini.mimalo.Trapsoundboard.View.TrapStarActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by matteoma on 2/3/2018.
 */

public class StarChooserAdapter extends PagerAdapter {

    public static final String TRAP_STAR = "TrapStar";
    public static final String LOCKED = "_locked";
    public static ArrayList<AudioFile> audioFiles;
    List<TrapStar> trapStars;
    Context context;
    LayoutInflater layoutInflater;
    private ImageView artistPicture;
    private TextView artistName;
    private StarChooserActivity starChooserActivity;
    private ShowExplainDialog showExplainDialog;


    public StarChooserAdapter(List<TrapStar> trapStars, Context context, StarChooserActivity starChooserActivity, ShowExplainDialog showExplainDialog) {
        this.trapStars = trapStars;
        this.starChooserActivity = starChooserActivity;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.showExplainDialog = showExplainDialog;
    }

    @Override
    public int getCount() {
        return trapStars.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = layoutInflater.inflate(R.layout.trapstar_element, container, false);
        artistPicture = view.findViewById(R.id.imageView);
        artistName = view.findViewById(R.id.textView);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "futura-heavy-oblique.ttf");
        artistName.setTypeface(typeface);
        boolean artistIsUnlocked = UtilitySharedPreferences.artistIsUnlocked(container.getContext(), trapStars.get(position).getTrapStarName());
        container.addView(view);


        if (artistIsUnlocked) {
            unlockedArtistActions(position);
        } else {
            lockedArtistActions(position);
        }
        return view;
    }

    private void lockedArtistActions(final int position) {
        artistName.setText(" " + "??????"+ " ");
        int id = context.getResources().getIdentifier(trapStars.get(position).getTrapStarName().toLowerCase() + LOCKED, "drawable", context.getPackageName());
        artistPicture.setImageResource(id);
        artistName.setBackgroundResource(R.color.disabledTextView);
        artistName.setTextColor(context.getResources().getColor(R.color.disabledTextColor));

        artistPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Se l'artista non  è sbloccato mostro il dialog e salvo chi ho cliccato
                if (!UtilitySharedPreferences.artistIsUnlocked(view.getContext(), trapStars.get(position).getTrapStarName())) {
                    showExplainDialog.showExplainDialog();
                    UtilitySharedPreferences.saveClickedArtistName(view.getContext(), trapStars.get(position).getTrapStarName());
                }
            }
        });
    }

    private void unlockedArtistActions(final int position) {
        artistName.setText(" " + trapStars.get(position).getTrapStarName() + " ");
        int id = context.getResources().getIdentifier(trapStars.get(position).getTrapStarName().toLowerCase(), "drawable", context.getPackageName());
        artistPicture.setImageResource(id);
        artistPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioFiles = trapStars.get(position).getFileNames();
                StarChooserActivity temp = (StarChooserActivity) v.getContext();
                Intent intent = new Intent(temp, TrapStarActivity.class);
                intent.putExtra(TRAP_STAR, artistName.getText());
                temp.startActivity(intent);
            }
        });
    }

}
