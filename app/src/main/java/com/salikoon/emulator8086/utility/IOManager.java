package com.salikoon.emulator8086.utility;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.salikoon.emulator8086.R;

public class IOManager extends Application {
    public static Application instance;
    private static short val = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static short getUserInput(){
        View view = LayoutInflater.from(instance)
                .inflate(R.layout.io_dialog,null);
        view.setClipToOutline(true);
        EditText etChar = view.findViewById(R.id.et_file_name);
        TextView btnSave = view.findViewById(R.id.btn_save);
        LinearLayout err = view.findViewById(R.id.ll_error_message);
        AlertDialog dialog = new AlertDialog.Builder(instance)
                .setView(view)
                .create();
        btnSave.setOnClickListener(v -> {
            String str = etChar.getText().toString();
            if (!str.isEmpty()) {
                val = (short) str.charAt(0);
                dialog.dismiss();
            }
            else  err.setVisibility(View.VISIBLE);
        });
        return val;
    }

    public static void putCharOutput(short sVal){
        View view = LayoutInflater.from(instance)
                .inflate(R.layout.io_dialog,null);
        view.setClipToOutline(true);
        EditText etChar = view.findViewById(R.id.et_file_name);
        TextView btnSave = view.findViewById(R.id.btn_save);
        AlertDialog dialog = new AlertDialog.Builder(instance)
                .setView(view)
                .create();
        etChar.setText(sVal);
        etChar.setEnabled(false);
        btnSave.setOnClickListener(v -> dialog.dismiss());
    }
}
