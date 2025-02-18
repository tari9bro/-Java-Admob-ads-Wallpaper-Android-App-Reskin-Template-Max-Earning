package com.tari9bro.wallpapers.helpers;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Lifecycle;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.tari9bro.wallpapers.R;

import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.DialogPropertiesPendulum;
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Settings {
    private final Activity activity;
    private final Context context;
    private final PreferencesHelper prefs;
    public Settings(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        prefs = new PreferencesHelper(activity);
    }


    public  void exitTheApp() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(context);
        dialog.setTitle(R.string.exit_dialog_title);
        dialog.setIcon(R.drawable.ic_exit);
        dialog.setMessage(R.string.exit_dialog_msg);
        dialog.setCancelable(false);
        dialog.setPositiveButton(R.string.yes, (dialogInterface, i) -> activity.finish());
        dialog.setNegativeButton(R.string.no, (dialogInterface, i) -> Toast.makeText(context,activity.getString(R.string.cancel_exit), Toast.LENGTH_SHORT).show()) ;
        dialog.show();

    }
    public void noInternetDialog( Lifecycle lifecycle ){
        NoInternetDialogPendulum.Builder builder = new NoInternetDialogPendulum.Builder(
                activity, lifecycle
        );
//getLifecycle()
        DialogPropertiesPendulum properties = builder.getDialogProperties();

        properties.setConnectionCallback(new ConnectionCallback() { // Optional
            @Override
            public void hasActiveConnection(boolean hasActiveConnection) {
                // ...
            }
        });

        properties.setCancelable(false); // Optional
        properties.setNoInternetConnectionTitle(activity.getString(R.string.n1)); // Optional
        properties.setNoInternetConnectionMessage(activity.getString(R.string.n2)); // Optional
        properties.setShowInternetOnButtons(true); // Optional
        properties.setPleaseTurnOnText(activity.getString(R.string.n3)); // Optional
        properties.setWifiOnButtonText("Wifi"); // Optional
        properties.setMobileDataOnButtonText("Mobile data"); // Optional

        properties.setOnAirplaneModeTitle(activity.getString(R.string.n1)); // Optional
        properties.setOnAirplaneModeMessage(activity.getString(R.string.n4)); // Optional
        properties.setPleaseTurnOffText(activity.getString(R.string.n5)); // Optional
        properties.setAirplaneModeOffButtonText(activity.getString(R.string.n6)); // Optional
        properties.setShowAirplaneModeOffButtons(true); // Optional

        builder.build();
    }
    public void moreApps() {
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(activity.getString(R.string.developer_search))));
        } catch (android.content.ActivityNotFoundException exeption) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(activity.getString(R.string.developer_id))));
        }
    }

    public void sharTheApp() {
        String url = activity.getString(R.string.app_link);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plane");
        intent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.share_dialog_msg));
        intent.putExtra(Intent.EXTRA_TEXT, url);
        activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.share_dialog_title)));
    }


    public void showPermissionDialog() {
        new MaterialAlertDialogBuilder(context)
                .setTitle("Permission Needed")
                .setMessage("This app requires access to external storage to function properly. Do you want to grant the necessary permission?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.R)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(activity,
                                new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE},
                                1001);
                        prefs.SaveBool("Permission",true);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        prefs.SaveBool("Permission",false);
                        activity.finish();
                    }
                })
                .show();
    }
        public  void saveImage ( String assetFileName) {
            try {
                // Open the asset file
                InputStream inputStream = context.getAssets().open(assetFileName);

                // Create a directory in external storage to save the image
                File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "images");
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Create a file to save the image
                File imageFile = new File(directory, assetFileName);

                // Copy the image from assets to the file
                OutputStream outputStream = new FileOutputStream(imageFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.close();
                inputStream.close();

                // Tell the media scanner to scan the new image file
                MediaScannerConnection.scanFile(context, new String[]{imageFile.getAbsolutePath()}, null, null);

                Log.d("ImageUtils", "Image saved to gallery: " + assetFileName);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("ImageUtils", "Error saving image to gallery: " + e.getMessage());
            }
        }






    
}
