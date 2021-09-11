package com.salikoon.emulator8086.utility;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.salikoon.emulator8086.R;

public class MyAnimator {
    public static void squeezeAnimate(Context context, View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        AnimatorSet reducer = (AnimatorSet) AnimatorInflater.loadAnimator(context,R.animator.squeeze_in);
                        reducer.setTarget(view);
                        reducer.start();
                        break;
                    case MotionEvent.ACTION_UP:
                        AnimatorSet regainer = (AnimatorSet) AnimatorInflater.loadAnimator(context,R.animator.squeeze_out);
                        regainer.setTarget(view);
                        regainer.start();
                        break;
                }
                return false;
            }
        });
    }
}
