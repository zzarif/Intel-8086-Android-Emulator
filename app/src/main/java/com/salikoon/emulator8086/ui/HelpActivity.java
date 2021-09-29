package com.salikoon.emulator8086.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.salikoon.emulator8086.R;
import com.salikoon.emulator8086.ui.adapters.HelpAdapter;
import com.salikoon.emulator8086.ui.models.HelpModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_help);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        HelpAdapter adapter = new HelpAdapter(this, getInstructionsList());
        recyclerView.setAdapter(adapter);

    }

    private ArrayList<HelpModel> getInstructionsList() {
        InputStream inputStream = getResources().openRawResource(R.raw.help_manual);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ArrayList<HelpModel> helpModels = new ArrayList<>();
        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(byteArrayOutputStream.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("InstructionList");
            for (int i=0; i<jsonArray.length(); ++i) {
                JSONObject singleInstruction = jsonArray.getJSONObject(i);
                HelpModel helpModel = new HelpModel();
                helpModel.setTitle(singleInstruction.getString("Instruction"));
                helpModel.setSyntax(singleInstruction.getString("Syntax"));
                helpModel.setDescription(singleInstruction.getString("Description"));
                helpModels.add(helpModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return helpModels;
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
