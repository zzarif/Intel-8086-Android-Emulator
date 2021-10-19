package com.salikoon.emulator8086.utility;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;

public class ShowCaseHelper {
    public static void show(Activity activity, View view, String title) {
        new BubbleShowCaseBuilder(activity) //Activity instance
                .title(title) //Any title for the bubble view
                .targetView(view) //View to point out
                .show(); //Display the ShowCase
    }
}
