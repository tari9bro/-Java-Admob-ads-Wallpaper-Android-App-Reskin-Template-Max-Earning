package com.tari9bro.wallpapers.notification;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.tari9bro.wallpapers.R;

public class Method3Worker extends Worker {
    public Method3Worker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());

        notificationHelper.showNotification("you can't be hero on this game", "high-octane world of crime-fighting and intense gunplay", "https://play.google.com/store/apps/details?id=com.nezzak.bluebeetle", R.drawable.game3);
        return Result.success();
    }
}
