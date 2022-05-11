package com.salikoon.emulator8086.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.salikoon.emulator8086.R;
import com.salikoon.emulator8086.databinding.LayoutPointersBinding;
import com.salikoon.emulator8086.ui.EmulateActivity;

import java.util.Map;

public class PointersFragments extends Fragment{
    private LayoutPointersBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        // Inflate the layout for this fragment
        binding = LayoutPointersBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        for (Map.Entry<String, Short> entry : EmulateActivity.elements.entrySet()) {
            // Log.d("Zarif_0002", "onCreate: L"+currentLine+" "+entry.getKey()+" "+entry.getValue());
            setValue(entry.getKey(),entry.getValue());
        }
        return view;
    }


    private void setValue(String element,short value) {
        try {
            switch (element) {
                default:
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
