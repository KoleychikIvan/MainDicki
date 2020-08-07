package com.koleychik.maindicki.additions;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koleychik.maindicki.R;
import com.koleychik.maindicki.coroutines.PutToSPrefFirstTime;

public class SheepGoing {
    private String sPrefName;
    private int words;
    private Context context;
    int num_dialog = 0;

    public SheepGoing(Context context, String sPrefName, int words) {
        this.context = context;
        this.sPrefName = sPrefName;
        this.words = words;
    }

    public void checkIsItFirstTime() {
        final SharedPreferences sPref = context.getSharedPreferences(Keys.APP_NAME_FOR_SPREF, Context.MODE_PRIVATE);
        if (!sPref.getBoolean(sPrefName, false)) {
            PutToSPrefFirstTime putToSPrefFirstTime = new PutToSPrefFirstTime(sPref);
            putToSPrefFirstTime.putBooleanToSPref(sPrefName, true);

            startSheep();
        }
    }

    public void startSheep() {
        Style style = new Style();
        final View[] mas_view_dialog = new View[3];

        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_first_time);

        ImageView sheep = dialog.findViewById(R.id.imageViewSheep);
        ImageView cloud1 = dialog.findViewById(R.id.little_cloud1);
        ImageView cloud2 = dialog.findViewById(R.id.little_cloud2);
        FrameLayout main_cloud = dialog.findViewById(R.id.main_cloud);
        TextView main_cloud_text = dialog.findViewById(R.id.main_cloud_text);
        RelativeLayout mainLD = dialog.findViewById(R.id.mainLayout_dialog);
        mas_view_dialog[0] = cloud1;
        mas_view_dialog[1] = cloud2;
        mas_view_dialog[2] = main_cloud;

        style.setSheep(sheep);

        cloud1.setVisibility(View.GONE);
        cloud2.setVisibility(View.GONE);
        main_cloud.setVisibility(View.GONE);

        main_cloud_text.setText(words);

        dialog.show();

        mainLD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Animation anim = AnimationUtils.loadAnimation(context, R.anim.bounce);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                makeAnimSee100(mas_view_dialog[0], mas_view_dialog);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        sheep.startAnimation(anim);
    }

    void makeAnimSee100(View view, final View[] mas_view_dialog) {
        num_dialog++;
        view.setVisibility(View.VISIBLE);
        Animation anim_cloud = AnimationUtils.loadAnimation(context, R.anim.see_after_100);
        anim_cloud.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }
            @Override
            public void onAnimationEnd(Animation animation) {
                if (num_dialog < 3) {
                    makeAnimSee100(mas_view_dialog[num_dialog], mas_view_dialog);
                } else {
                    num_dialog = 0;
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(anim_cloud);
    }

}
