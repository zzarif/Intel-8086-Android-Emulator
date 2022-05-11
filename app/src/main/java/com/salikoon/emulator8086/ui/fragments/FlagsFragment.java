package com.salikoon.emulator8086.ui.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.salikoon.emulator8086.R;
import com.salikoon.emulator8086.databinding.LayoutFlagsBinding;
import com.salikoon.emulator8086.hardware.StringParameter;
import com.salikoon.emulator8086.ui.EmulateActivity;

import java.util.Map;

public class FlagsFragment extends Fragment{
    private LayoutFlagsBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        // Inflate the layout for this fragment
        binding = LayoutFlagsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        for (Map.Entry<String, Short> entry : EmulateActivity.elements.entrySet()) {
            // Log.d("Zarif_0002", "onCreate: L"+currentLine+" "+entry.getKey()+" "+entry.getValue());
            setValue(entry.getKey(),entry.getValue());
        }
        return view;
    }

    // set 1-bit values e.g. flags
    private void setText_1bit(TextView textView,short value) {
        textView.setText(Short.toString(value));
        textView.setTypeface(textView.getTypeface(),Typeface.BOLD);
        textView.setTextColor(getResources().getColor(R.color.keyword_0_extra));
    }

    private void setValue(String element,short value) {
        try {
            switch (element) {
                case StringParameter.OverflowFlag:
                    setText_1bit(binding.of,value);
                    break;
                case StringParameter.DirectionFlag:
                    setText_1bit(binding.df,value);
                    break;
                case StringParameter.InterruptFlag:
                    setText_1bit(binding.eyef,value);
                    break;
                case StringParameter.TrapFlag:
                    setText_1bit(binding.tf,value);
                    break;
                case StringParameter.SignFlag:
                    setText_1bit(binding.sf,value);
                    break;
                case StringParameter.ZeroFlag:
                    setText_1bit(binding.zf,value);
                    break;
                case StringParameter.AuxiliaryFlag:
                    setText_1bit(binding.af,value);
                    break;
                case StringParameter.ParityFlag:
                    setText_1bit(binding.pf,value);
                    break;
                case StringParameter.CarryFlag:
                    setText_1bit(binding.cf,value);
                    break;
                default:
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
