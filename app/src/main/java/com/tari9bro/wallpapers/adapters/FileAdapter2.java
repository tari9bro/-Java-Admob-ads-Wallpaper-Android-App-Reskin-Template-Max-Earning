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

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tari9bro.wallpapers.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FileAdapter2 extends RecyclerView.Adapter<FileAdapter2.FileViewHolder> {
    private final Context context;
    private final Activity activity;
    private final List<String> fileList;











    public String getFileNameStr() {
        return fileNameStr;
    }

    public void setFileNameStr(String fileNameStr) {
        this.fileNameStr = fileNameStr;
    }

    private String fileNameStr;

    String fileName;





    FragmentManager fragmentManager;

    public FileAdapter2(Context context, List<String> fileList, Activity activity,FragmentManager fragmentManager) {
        this.context = context;
        this.fileList = fileList;
          this.fragmentManager = fragmentManager;
        this.activity = activity;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_file2, parent, false);
        return new FileViewHolder(itemView);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, @SuppressLint("RecyclerView") int position) {



        fileName = fileList.get(position);







        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);


            // Create a Bitmap with the desired dimensions
            Bitmap originalBitmap = BitmapFactory.decodeStream(inputStream);
            if (originalBitmap != null) {
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap,500, 500, false);

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











        setFileNameStr(fileName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setFileNameStr(fileName);

            }
        });
        }



    @Override
    public int getItemCount() {
        return fileList.size();
    }

    static class FileViewHolder extends RecyclerView.ViewHolder {
        ImageView fileImageView;





        FileViewHolder(@NonNull View itemView) {
            super(itemView);
            fileImageView = itemView.findViewById(R.id.fileImageView);


        }
    }









}
