package com.salikoon.emulator8086.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.salikoon.emulator8086.R;
import com.salikoon.emulator8086.utility.ErrorUtils;
import com.salikoon.emulator8086.utility.FileManager;
import com.salikoon.emulator8086.utility.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    TextView tvPoint;
    ImageView ivPlus,ivMinus;
    PreferenceManager preferenceManager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_settings_options, menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferenceManager = new PreferenceManager(this);

        tvPoint = findViewById(R.id.tv_point);
        ivPlus = findViewById(R.id.iv_plus);
        ivMinus = findViewById(R.id.iv_minus);

        tvPoint.setText(preferenceManager.getEditorFontSize());

        ivPlus.setOnClickListener(view -> {
            int current = Integer.parseInt(tvPoint.getText().toString());
            if (current<25) {
                tvPoint.setText(String.valueOf(current+1));
            }
            else Toast.makeText(this,"You have reached maximum size",Toast.LENGTH_SHORT).show();
        });
        ivMinus.setOnClickListener(view -> {
            int current = Integer.parseInt(tvPoint.getText().toString());
            if (current>10) {
                tvPoint.setText(String.valueOf(current-1));
            }
            else Toast.makeText(this,"You have reached minimum size",Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save:
                preferenceManager.setEditorFontSize(tvPoint.getText().toString());
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
