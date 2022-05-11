package com.salikoon.emulator8086.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.salikoon.emulator8086.R;
import com.salikoon.emulator8086.ui.adapters.HelpAdapter;
import com.salikoon.emulator8086.ui.adapters.TemplateAdapter;
import com.salikoon.emulator8086.ui.models.HelpModel;
import com.salikoon.emulator8086.utility.IntentKey;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_help);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<HelpModel> helpModels = getInstructionsList();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        HelpAdapter adapter = new HelpAdapter(this, helpModels);
        recyclerView.setAdapter(adapter);

        adapter.setClickListener(new HelpAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                HelpModel helpModel = helpModels.get(position);
                Intent intent = new Intent(HelpActivity.this, HelpDetailsActivity.class);
                intent.putExtra("help.activity.get.details",helpModel);
                startActivity(intent);
            }
        });

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

                ArrayList<String> sourceList = new ArrayList<>();
                JSONArray source = singleInstruction.getJSONArray("Source");
                for (int j=0; j< source.length(); ++j) {
                    sourceList.add(source.getString(j));
                }
                helpModel.setSource(sourceList);

                ArrayList<String> destList = new ArrayList<>();
                JSONArray dest = singleInstruction.getJSONArray("Destination");
                for (int j=0; j< dest.length(); ++j) {
                    destList.add(dest.getString(j));
                }
                helpModel.setDestination(destList);

                ArrayList<String> flagsChangedList = new ArrayList<>();
                JSONArray flagsChanged = singleInstruction.getJSONArray("FlagsChanged");
                for (int j=0; j< flagsChanged.length(); ++j) {
                    flagsChangedList.add(flagsChanged.getString(j));
                }
                helpModel.setFlagsChanged(flagsChangedList);

                ArrayList<String> exampleList = new ArrayList<>();
                JSONArray examples = singleInstruction.getJSONArray("Examples");
                for (int j=0; j< examples.length(); ++j) {
                    JSONObject singleExample = examples.getJSONObject(j);
                    exampleList.add(singleExample.getString("Type")+" => "+singleExample.getString("Specimen"));
                }
                helpModel.setExamples(exampleList);

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
