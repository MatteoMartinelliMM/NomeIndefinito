package com.xtini.mimalo.soundbuttongangedition.Control;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtini.mimalo.soundbuttongangedition.Model.AudioFile;
import com.xtini.mimalo.soundbuttongangedition.Model.TrapStar;
import com.xtini.mimalo.soundbuttongangedition.R;
import com.xtini.mimalo.soundbuttongangedition.View.StarChooserActivity;
import com.xtini.mimalo.soundbuttongangedition.View.TrapStarActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matteoma on 2/3/2018.
 */

public class StarChooserAdapter extends PagerAdapter {

    public static final String TRAP_STAR = "TrapStar";
    public static ArrayList<AudioFile> audioFiles;
    List<Integer> lstImages;
    List<String> lstNomi;
    List<TrapStar> trapStars;
    Context context;
    LayoutInflater layoutInflater;
    private String bo;

    public StarChooserAdapter(List<TrapStar> trapStars, Context context) {
        this.trapStars = trapStars;

        this.context = context;
        layoutInflater = LayoutInflater.from(context);
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
        View view = layoutInflater.inflate(R.layout.card_item, container, false);
        final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        final TextView textView = (TextView) view.findViewById(R.id.textView);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "futura-heavy-oblique.ttf");
        textView.setTypeface(typeface);
        int id = context.getResources().getIdentifier(trapStars.get(position).getTrapStarName().toLowerCase(), "drawable", context.getPackageName());
        imageView.setImageResource(id);
        container.addView(view);
        textView.setText(" " + trapStars.get(position).getTrapStarName() + " ");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioFiles = trapStars.get(position).getFileNames();
                StarChooserActivity temp = (StarChooserActivity) v.getContext();
                Intent intent = new Intent(temp, TrapStarActivity.class);
                intent.putExtra(TRAP_STAR, textView.getText());
                temp.startActivity(intent);
            }
        });
        return view;
    }
}
