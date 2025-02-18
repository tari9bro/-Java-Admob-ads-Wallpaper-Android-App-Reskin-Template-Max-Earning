package com.tari9bro.wallpapers.helpers;




import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.View;
import android.widget.Button;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.tari9bro.wallpapers.R;

import java.io.IOException;
import java.io.InputStream;

public class WallpaperSetter {
    private final Context context;
    private final WallpaperManager wallpaperManager;

    public WallpaperSetter(Context context) {
        this.context = context;
        wallpaperManager = WallpaperManager.getInstance(context);
    }

    public void setWallpaperToHomeScreen(Bitmap bitmap) {
        setWallpaper(bitmap, WallpaperManager.FLAG_SYSTEM);
    }

    public void setWallpaperToLockScreen(Bitmap bitmap) {
        setWallpaper(bitmap, WallpaperManager.FLAG_LOCK);
    }

    public void setWallpaperToBoth(Bitmap bitmap) {
        setWallpaper(bitmap, WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK);
    }

    private void setWallpaper(Bitmap bitmap, int flags) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                wallpaperManager.setBitmap(bitmap, null, true, flags);
            }else {
                wallpaperManager.setBitmap(bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showWallpaperDialog(String fileName) {
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // Create a RoundedBitmapDrawable from the Bitmap
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
            roundedBitmapDrawable.setCornerRadius(16f); // Adjust the corner radius as per your preference

            // Create and configure the custom Material Dialog
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.fragment_material_dialog);
            dialog.setCancelable(true);

            Button setToHomeBtn = dialog.findViewById(R.id.setToHomeBtn);
            Button setToLockBtn = dialog.findViewById(R.id.setToLockBtn);
            Button setToBothBtn = dialog.findViewById(R.id.setToBothBtn);



            // Set wallpaper when buttons are clicked
            setToHomeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setWallpaperToHomeScreen(bitmap);
                    dialog.dismiss();
                }
            });

            setToLockBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setWallpaperToLockScreen(bitmap);
                    dialog.dismiss();
                }
            });

            setToBothBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setWallpaperToBoth(bitmap);
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
