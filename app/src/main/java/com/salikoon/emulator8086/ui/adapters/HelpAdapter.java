package com.salikoon.emulator8086.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.salikoon.emulator8086.R;
import com.salikoon.emulator8086.ui.models.HelpModel;

import java.util.ArrayList;

public class HelpAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<HelpModel> objects;

    public HelpAdapter(@NonNull Context context, @NonNull ArrayList<HelpModel> objects) {
        super(context, 0, objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_help, parent, false);
        }
        HelpModel object = objects.get(position);
        TextView tvTitle = convertView.findViewById(R.id.tv_title);
        TextView tvSyntax = convertView.findViewById(R.id.tv_syntax);
        TextView tvDescription = convertView.findViewById(R.id.tv_desc);
        tvTitle.setText(object.getTitle());
        tvSyntax.setText(object.getSyntax());
        tvDescription.setText(object.getDescription());
        return convertView;
    }
}
