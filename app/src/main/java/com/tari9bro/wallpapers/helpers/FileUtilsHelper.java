package com.tari9bro.wallpapers.helpers;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtilsHelper {

    private static final String TAG = "FileUtilsHelper";

    // Function to get a list of image filenames from the Assets folder
    public static List<String> getFilesFromAssets(Context context) {
        List<String> imageFilenames = new ArrayList<>();
        AssetManager assetManager = context.getAssets();
        try {
            // List all files in the specified folder path
            String[] files = assetManager.list("");
            for (String file : files) {
                if (file.endsWith(".jpg") || file.endsWith(".png") || file.endsWith(".jpeg")) {
                    imageFilenames.add(file);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading images from Assets folder: " + e.getMessage());
        }
        return imageFilenames;
    }

    // Function to load a Bitmap image from the Assets folder using filename
    public static Bitmap loadBitmapFromAssets(Context context, String filename) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open(filename);
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            Log.e(TAG, "Error loading bitmap from Assets folder: " + e.getMessage());
        }
        return null;
    }


}

