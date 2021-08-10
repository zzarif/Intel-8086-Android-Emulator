package com.salikoon.emulator8086.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.salikoon.emulator8086.ui.EditorActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 * manages read/write
 * from/to file
 * @author zibranzarif
 */
public class FileManager {

    /**
     * checks permission to
     * modify external storage
     * @return true: if granted
     *         false: otherwise
     */
    public static boolean isStoragePermissionGranted(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else return true;
    }

    /**
     * reads text from filepath
     * forwards content to editor
     * @param path: absolute file path
     */
    public static String getText(String path) {
        BufferedReader reader = null;
        try {
            File file = new File(path);
            FileInputStream in = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null)
                builder.append(line).append("\n");
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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

    /**
     * creates new file in
     * /storage/emulated/0/Documents
     * @param fileName: name of the file
     *                (e.g. demo.txt)
     * @param body: contents of file
     * @return success?true:false
     */
    public static boolean createFile(String fileName,String body) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DOCUMENTS),fileName);
            FileWriter writer = new FileWriter(file);
            writer.append(body);
            writer.flush();
            writer.close();
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * overwrites an existing file
     * @param path: file location
     * @param body: new contents
     * @return success?true:false
     */
    public static boolean overwriteFile(String path,String body) {
        File file = new File(path);
        try {
            if (file.exists()) file.delete();
            FileWriter writer = new FileWriter(file);
            writer.append(body);
            writer.flush();
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
