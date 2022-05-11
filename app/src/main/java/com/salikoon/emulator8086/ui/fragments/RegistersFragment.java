package com.salikoon.emulator8086.ui.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.salikoon.emulator8086.R;
import com.salikoon.emulator8086.hardware.StringParameter;
import com.salikoon.emulator8086.databinding.LayoutRegistersBinding;
import com.salikoon.emulator8086.ui.EmulateActivity;

import java.util.Map;

public class RegistersFragment extends Fragment{
    private LayoutRegistersBinding binding;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {


        // Inflate the layout for this fragment
        binding = LayoutRegistersBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        for (Map.Entry<String, Short> entry : EmulateActivity.elements.entrySet()) {
            // Log.d("Zarif_0002", "onCreate: L"+currentLine+" "+entry.getKey()+" "+entry.getValue());
            setValue(entry.getKey(),entry.getValue());
        }
        return view;
    }


    // set 8-bit values
    private void setText_8bit(TextView textView,short value) {
        String sValue = String.format("%02x",(byte)value);
        textView.setText(sValue);
        textView.setTypeface(textView.getTypeface(),Typeface.BOLD);
        textView.setTextColor(getResources().getColor(R.color.keyword_0_extra));
    }

    // set 16-bit values
    private void setText_16bit(TextView high,TextView low,short value) {
        byte lValue = (byte) value; // low
        byte hValue= (byte) (value >> 8); // high
        setText_8bit(high,hValue);
        setText_8bit(low,lValue);
    }

    private void setValue(String element,short value) {
        try {
            switch (element) {
                case StringParameter.AX:
                    setText_16bit(binding.ah,binding.al,value);
                    break;
                case StringParameter.BX:
                    setText_16bit(binding.bh,binding.bl,value);
                    break;
                case StringParameter.CX:
                    setText_16bit(binding.ch,binding.cl,value);
                    break;
                case StringParameter.DX:
                    setText_16bit(binding.dh,binding.dl,value);
                    break;
                case StringParameter.AH:
                    setText_8bit(binding.ah,value);
                    break;
                case StringParameter.AL:
                    setText_8bit(binding.al,value);
                    break;
                case StringParameter.BH:
                    setText_8bit(binding.bh,value);
                    break;
                case StringParameter.BL:
                    setText_8bit(binding.bl,value);
                    break;
                case StringParameter.CH:
                    setText_8bit(binding.ch,value);
                    break;
                case StringParameter.CL:
                    setText_8bit(binding.cl,value);
                    break;
                case StringParameter.DH:
                    setText_8bit(binding.dh,value);
                    break;
                case StringParameter.DL:
                    setText_8bit(binding.dl,value);
                    break;
                default:
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
