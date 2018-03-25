package com.xtini.mimalo.soundbuttongangedition.View;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xtini.mimalo.soundbuttongangedition.R;

import static com.xtini.mimalo.soundbuttongangedition.View.StarChooserActivity.N_ALTRA_VOLTA;

/**
 * Created by matteoma on 3/13/2018.
 */

public class ViewUtilities {
    static final int MY_TOAST_DELAY = 1000;

    public static Toast createCustomToast(Context context, View v,String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)
                v.findViewById(R.id.like_popup_layout));

        TextView text = layout.findViewById(R.id.like_popup_tv);
        text.setText(msg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        layout.setAlpha(0.85f);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        return toast;
    }
}
