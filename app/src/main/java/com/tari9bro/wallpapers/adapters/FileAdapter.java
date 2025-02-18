package com.tari9bro.wallpapers.adapters;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tari9bro.wallpapers.R;
import com.tari9bro.wallpapers.activity.MainActivity;
import com.tari9bro.wallpapers.ads.Ads;
import com.tari9bro.wallpapers.fragments.ImageFragment;
import com.tari9bro.wallpapers.helpers.PreferencesHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {
    private final Context context;
    private final Activity activity;
    private final List<String> fileList;
    private final int placeholderWidth; // Desired placeholder width
    private final int placeholderHeight; // Desired placeholder height
    private PreferencesHelper pref;

    public FileAdapter(Context context, List<String> fileList, int placeholderWidth, int placeholderHeight,Activity activity) {
        this.context = context;
        this.fileList = fileList;
        this.placeholderWidth = placeholderWidth;
        this.placeholderHeight = placeholderHeight;
        this.activity = activity;
    }
    public static boolean containsDigitThree(int number) {
        String numberString = String.valueOf(number);
        return numberString.contains("3");
    }
    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_file, parent, false);
        return new FileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String fileName = fileList.get(position);
        pref = new PreferencesHelper(activity);
        if (!containsDigitThree(position) ) {
            holder.badge.setVisibility(View.GONE);
        } else {
            holder.badge.setVisibility(View.VISIBLE);
        }
        // Load image from assets folder using AssetManager
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);


            // Create a Bitmap with the desired dimensions
            Bitmap originalBitmap = BitmapFactory.decodeStream(inputStream);
            if (originalBitmap != null) {
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, placeholderWidth, placeholderHeight, false);

                // Create a RoundedBitmapDrawable from the Bitmap and set it as the placeholder
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), scaledBitmap);
                roundedBitmapDrawable.setCornerRadius(16f); // Adjust the corner radius as per your preference
                holder.fileImageView.setImageDrawable(roundedBitmapDrawable);

                // Remember to recycle the originalBitmap to release memory
                originalBitmap.recycle();
            } else {
                // If decoding fails, set a default image
                holder.fileImageView.setImageResource(R.drawable.default_image);
            }
        } catch (IOException e) {
            e.printStackTrace();
            holder.fileImageView.setImageResource(R.drawable.default_image);
        }





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref.SaveInt("position",position);

                handleClick();
                if (holder.badge.getVisibility() == View.VISIBLE) {
                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    ImageFragment imageFragment = ImageFragment.newInstanceWithBadge(fileName, R.drawable.star);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, imageFragment)
                            .addToBackStack(null)
                            .commit();
                } else {
                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    ImageFragment imageFragment = ImageFragment.newInstance(fileName);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, imageFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    static class FileViewHolder extends RecyclerView.ViewHolder {
        ImageView fileImageView;
        TextView badge;

        FileViewHolder(@NonNull View itemView) {
            super(itemView);
            fileImageView = itemView.findViewById(R.id.fileImageView);
            badge = itemView.findViewById(R.id.badge);
        }
    }
    private void handleClick() {
        PreferencesHelper pref = new PreferencesHelper(activity);
      Ads  ads = new Ads(activity,context);


        // Load the click count from SharedPreferences
        int clickCount = pref.LoadInt("clickCount");
        clickCount++;
        pref.SaveInt("clickCount", clickCount);
        // Check if the click count is a multiple of 7
        if (clickCount % 7 == 0) {
            // Show the interstitial ad when the click count reaches a multiple of 7
            ads.playInterstitialAd();

        }


    }

}
