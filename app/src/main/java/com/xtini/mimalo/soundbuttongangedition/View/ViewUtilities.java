package com.xtini.mimalo.soundbuttongangedition.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xtini.mimalo.soundbuttongangedition.R;

/**
 * Created by matteoma on 3/13/2018.
 */

public class ViewUtilities {

    public static Toast createCustomToast(Context context, View v,String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)
                v.findViewById(R.id.like_popup_layout));

        TextView text = layout.findViewById(R.id.like_popup_tv);
        text.setText(msg);
        layout.setAlpha(0.6f);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        return toast;
    }
}
