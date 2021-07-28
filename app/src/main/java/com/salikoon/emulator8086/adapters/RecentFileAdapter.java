package com.salikoon.emulator8086.adapters;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.salikoon.emulator8086.R;

import java.util.ArrayList;

public class RecentFileAdapter extends ArrayAdapter {
    private ArrayList<Pair<String,String>> objects;
    private Context context;

    public RecentFileAdapter(@NonNull Context context, @NonNull ArrayList<Pair<String,String>> objects) {
        super(context, 0, objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_recent_activity, parent, false);
        }

        Pair<String,String> object = objects.get(position);

        TextView tvTitle = convertView.findViewById(R.id.tv_title);
        TextView tvBody = convertView.findViewById(R.id.tv_body);

        tvTitle.setText(object.first);
        tvBody.setText(object.second);

        return convertView;

    }
}
