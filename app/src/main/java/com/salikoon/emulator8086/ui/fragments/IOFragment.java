package com.salikoon.emulator8086.ui.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.salikoon.emulator8086.R;
import com.salikoon.emulator8086.databinding.IoDialogBinding;
import com.salikoon.emulator8086.hardware.StringParameter;
import com.salikoon.emulator8086.ui.EmulateActivity;

import java.util.Map;

public class IOFragment extends Fragment{
    public static IoDialogBinding ioBinding;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {


        // Inflate the layout for this fragment
        ioBinding = IoDialogBinding.inflate(inflater, container, false);
        View view = ioBinding.getRoot();

        // Log.d("ddr",EmulateActivity.myOutputChar);
        ioBinding.outputChar.setText(EmulateActivity.myOutputChar);
        return view;
    }

    public static short getUserInput() {
        EmulateActivity.motherOFAllBinding.tabLayout.getTabAt(0).select();
        String inputStr = ioBinding.inputChar.getText().toString();
        if (inputStr.isEmpty())
            return 0x30;
        else
            return (short) inputStr.charAt(0);
    }
}
