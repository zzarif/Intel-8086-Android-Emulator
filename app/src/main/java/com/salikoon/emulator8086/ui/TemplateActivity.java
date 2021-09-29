package com.salikoon.emulator8086.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.salikoon.emulator8086.R;
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

public class TemplateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_help);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<HelpModel> templateList = getTemplateList();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TemplateAdapter adapter = new TemplateAdapter(this,templateList);
        recyclerView.setAdapter(adapter);

        adapter.setClickListener(new TemplateAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                HelpModel helpModel = templateList.get(position);
                Intent intent = new Intent(TemplateActivity.this, EditorActivity.class);
                intent.putExtra(IntentKey.USER_CODE.getKey(),helpModel.getDescription());
                startActivity(intent);
            }
        });

    }

    private ArrayList<HelpModel> getTemplateList() {
        InputStream inputStream = getResources().openRawResource(R.raw.templates);
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
            JSONArray jsonArray = jsonObject.getJSONArray("Templates");
            for (int i=0; i<jsonArray.length(); ++i) {
                JSONObject singleInstruction = jsonArray.getJSONObject(i);
                HelpModel helpModel = new HelpModel();
                helpModel.setTitle(singleInstruction.getString("Title"));
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
