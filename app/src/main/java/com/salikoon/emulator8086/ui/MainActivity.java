package com.salikoon.emulator8086.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;
import com.salikoon.emulator8086.R;
import com.salikoon.emulator8086.adapters.RecentFileAdapter;
import com.salikoon.emulator8086.ui.models.RecentFile;
import com.salikoon.emulator8086.utility.IntentKey;
import com.salikoon.emulator8086.utility.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Base activity
 * @author zibranzarif
 */
public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,PickiTCallbacks {

    private final int PICK_FILE_REQUEST_CODE = 41;
    DrawerLayout drawer;
    PreferenceManager preferenceManager;
    PickiT pickiT;

    ListView listView;
    RecentFileAdapter adapter;
    ArrayList<RecentFile> recentFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // onClick handler: start editor
        findViewById(R.id.ll_create_new).setOnClickListener(
                v -> startActivity(new Intent(MainActivity.this, EditorActivity.class))
        );

        // onClick handler: check permission, pick file
        findViewById(R.id.ll_open_file).setOnClickListener(v -> {
            if (isStoragePermissionGranted()) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("text/plain");
                chooseFile = Intent.createChooser(
                        chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICK_FILE_REQUEST_CODE);
            }
        });

        pickiT = new PickiT(this,this,this);
        preferenceManager = new PreferenceManager(this);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        listView = findViewById(R.id.list_view);
        recentFiles = new ArrayList<>();
        adapter = new RecentFileAdapter(this,recentFiles);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String filePath = ((RecentFile)adapterView.getItemAtPosition(i)).getFilePath();
                parseContent(filePath);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_about:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        recentFiles.clear();
        try {
            String filePath = preferenceManager.getFileAbsolutePath();
            String filename=filePath.substring(filePath.lastIndexOf("/")+1);
            recentFiles.add(new RecentFile(filename,filePath,""));
            adapter.notifyDataSetChanged();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * checks permission to
     * modify external storage
     * @return if granted: true
     *         else: false
     */
    private boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK){
            pickiT.getPath(data.getData(),Build.VERSION.SDK_INT);
        }
    }

    /**
     * reads text from filepath
     * forwards content to editor
     * @param path: absolute file path
     */
    private void parseContent(String path) {
        BufferedReader reader = null;
        try {
            File file = new File(path);
            FileInputStream in = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null)
                builder.append(line).append("\n");

            // pass code to editor
            Intent intent = new Intent(this,EditorActivity.class);
            intent.putExtra(IntentKey.USER_CODE.getKey(),builder.toString());
            String editorTitle = path.substring(path.lastIndexOf("/")+1);
            intent.putExtra(IntentKey.EDITOR_TITLE.getKey(),editorTitle);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void PickiTonUriReturned(){}

    @Override
    public void PickiTonStartListener(){}

    @Override
    public void PickiTonProgressUpdate(int progress){}

    @Override
    public void PickiTonCompleteListener(String path, boolean wasDriveFile, boolean wasUnknownProvider,
                                         boolean wasSuccessful, String Reason) {
        preferenceManager.addFileAbsolutePath(path);
        parseContent(path);
    }
}