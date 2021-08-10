package com.salikoon.emulator8086.utility;

import android.content.Context;
import android.widget.Toast;

public class ErrorUtils {

    public static void genericError(Context context) {
        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
    }
}
