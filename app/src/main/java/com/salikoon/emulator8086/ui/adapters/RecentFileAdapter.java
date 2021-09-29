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
import com.salikoon.emulator8086.ui.models.RecentFile;

import java.util.ArrayList;

public class RecentFileAdapter extends ArrayAdapter {
    private ArrayList<RecentFile> objects;
    private Context context;

    public RecentFileAdapter(@NonNull Context context, @NonNull ArrayList<RecentFile> objects) {
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
        RecentFile recentFile = objects.get(position);
        TextView tvFileName = convertView.findViewById(R.id.tv_file_name);
        TextView tvFilePath = convertView.findViewById(R.id.tv_file_path);
        tvFileName.setText(recentFile.getFileName());
        tvFilePath.setText(recentFile.getFilePath());

        return convertView;
    }
}
