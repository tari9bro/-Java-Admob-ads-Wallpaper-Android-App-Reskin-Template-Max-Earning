package com.tari9bro.wallpapers.notification;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.tari9bro.wallpapers.R;

public class Method1Worker extends Worker {

    public Method1Worker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Method 1 to be executed here
        NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());

        notificationHelper.showNotification( "Ball Rolling Game ", "you can't win this game", "https://play.google.com/store/apps/details?id=com.nezzak.mygame", R.drawable.game1);
        return Result.success();
    }
}

// Create similar Worker classes for other methods (Method2Worker, Method3Worker, etc.)
