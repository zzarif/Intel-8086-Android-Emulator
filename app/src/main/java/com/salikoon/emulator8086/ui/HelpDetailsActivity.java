package com.salikoon.emulator8086.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.salikoon.emulator8086.R;
import com.salikoon.emulator8086.ui.models.HelpModel;

public class HelpDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_help_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayout syntax = findViewById(R.id.syntax);
        LinearLayout description = findViewById(R.id.description);
        LinearLayout source = findViewById(R.id.source);
        LinearLayout destination = findViewById(R.id.destination);
        LinearLayout flags_changed = findViewById(R.id.flags_changed);
        LinearLayout examples = findViewById(R.id.examples);

        HelpModel helpModel = (HelpModel) getIntent()
                .getSerializableExtra("help.activity.get.details");

        ((CollapsingToolbarLayout)findViewById(R.id.collapsingToolbar))
                .setTitle(helpModel.getTitle());

        ((TextView) syntax.findViewById(R.id.tv_title)).setText("Syntax");
        ((TextView) syntax.findViewById(R.id.tv_desc)).setText(helpModel.getSyntax());

        ((TextView) description.findViewById(R.id.tv_title)).setText("Description");
        ((TextView) description.findViewById(R.id.tv_desc)).setText(helpModel.getDescription());
        ((TextView) description.findViewById(R.id.tv_desc)).setMaxLines(100);

        ((TextView) source.findViewById(R.id.tv_title)).setText("Source");
        TextView tvSource = source.findViewById(R.id.tv_desc);
        for (String s: helpModel.getSource()) tvSource.append(s+"\n");

        ((TextView) destination.findViewById(R.id.tv_title)).setText("Destination");
        TextView tvDest = destination.findViewById(R.id.tv_desc);
        for (String s: helpModel.getDestination()) tvDest.append(s+"\n");

        ((TextView) flags_changed.findViewById(R.id.tv_title)).setText("Flags Changed");
        TextView tvFlags = flags_changed.findViewById(R.id.tv_desc);
        for (String s: helpModel.getFlagsChanged()) tvFlags.append(s+"\n");

        ((TextView) examples.findViewById(R.id.tv_title)).setText("Examples");
        TextView tvExamples = examples.findViewById(R.id.tv_desc);
        for (String s: helpModel.getExamples()) tvExamples.append(s+"\n");
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
