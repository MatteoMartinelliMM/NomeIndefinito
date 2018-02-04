package com.xtini.mimalo.soundbuttongangedition.Control;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xtini.mimalo.soundbuttongangedition.R;
import com.xtini.mimalo.soundbuttongangedition.View.StarChooserActivity;
import com.xtini.mimalo.soundbuttongangedition.View.TrapStarActivity;

import java.util.List;

/**
 * Created by matteoma on 2/3/2018.
 */

public class MyAdapter extends PagerAdapter {

    List<Integer> lstImages;
    Context context;
    LayoutInflater layoutInflater;

    public MyAdapter(List<Integer> lstImages, Context context) {
        this.lstImages = lstImages;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lstImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.card_item,container,false);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        imageView.setImageResource(lstImages.get(position));
        container.addView(view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StarChooserActivity temp = (StarChooserActivity)v.getContext();
                Intent intent = new Intent(temp, TrapStarActivity.class);
                temp.startActivity(intent);
            }
        });
        return view;
    }
}
