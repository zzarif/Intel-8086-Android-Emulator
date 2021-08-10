package com.salikoon.emulator8086.ui;

import android.content.Intent;
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
import com.salikoon.emulator8086.utility.ErrorUtils;
import com.salikoon.emulator8086.utility.FileManager;
import com.salikoon.emulator8086.utility.IntentKey;
import com.salikoon.emulator8086.utility.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

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
            if (FileManager.isStoragePermissionGranted(this)) {
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
                String path = ((RecentFile)adapterView.getItemAtPosition(i)).getFilePath();
                String contents = FileManager.getText(path);

                // pass code to editor
                if (contents!=null) {
                    Intent intent = new Intent(
                            MainActivity.this,EditorActivity.class);
                    intent.putExtra(IntentKey.USER_CODE.getKey(),contents);
                    String editorTitle = path.substring(path.lastIndexOf(File.separator)+1);
                    intent.putExtra(IntentKey.EDITOR_TITLE.getKey(),editorTitle);
                    intent.putExtra(IntentKey.FILE_PATH.getKey(),path);
                    startActivity(intent);
                }
                else ErrorUtils.genericError(MainActivity.this);
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
            ArrayList<RecentFile> objects = preferenceManager.getRecentFiles();
            if (objects!=null) {
                Collections.reverse(objects);
                recentFiles.addAll(objects);
                adapter.notifyDataSetChanged();
            }
            else ErrorUtils.genericError(this);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK){
            pickiT.getPath(data.getData(),Build.VERSION.SDK_INT);
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
        String fileName = path.substring(path.lastIndexOf(File.separator) + 1);
        String currTime = new SimpleDateFormat(
                "dd MMM yyyy hh:mm:ss a").format(new Date());
        RecentFile recentFile = new RecentFile();
        recentFile.setFileName(fileName);
        recentFile.setFilePath(path);
        recentFile.setTime(currTime);
        preferenceManager.addRecentFile(recentFile);
        String contents = FileManager.getText(path);

        // pass code to editor
        if (contents!=null) {
            Intent intent = new Intent(this,EditorActivity.class);
            intent.putExtra(IntentKey.USER_CODE.getKey(),contents);
            intent.putExtra(IntentKey.EDITOR_TITLE.getKey(),fileName);
            intent.putExtra(IntentKey.FILE_PATH.getKey(),path);
            startActivity(intent);
        }
        else ErrorUtils.genericError(this);
    }
}