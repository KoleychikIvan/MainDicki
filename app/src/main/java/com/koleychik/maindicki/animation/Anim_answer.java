package com.koleychik.maindicki.animation;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.koleychik.maindicki.R;

import java.util.concurrent.TimeUnit;


public class Anim_answer {

    Context main_context;
    View view;
    String right_word;

    public Anim_answer(Context context, View v, String word){
        main_context = context;
        view = v;
        right_word = word;
    }

    public void AnimStart(){
        MyTask myTask = new MyTask();
        myTask.execute();
    }


    class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(main_context, R.anim.no);
            view.startAnimation(animation);
            if (right_word != null) {
                Toast toast = Toast.makeText(main_context, right_word, Toast.LENGTH_LONG);
                toast.show();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                TimeUnit.MILLISECONDS.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            view.setVisibility(View.GONE);
            Animation animation = AnimationUtils.loadAnimation(main_context, R.anim.after_no);
            view.startAnimation(animation);
        }
    }

}
