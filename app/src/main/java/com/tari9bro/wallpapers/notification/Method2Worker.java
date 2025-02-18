package com.tari9bro.wallpapers.notification;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.tari9bro.wallpapers.R;

public class Method2Worker extends Worker {
    public Method2Worker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
        notificationHelper.showNotification("join 1k playing this game", "put yourself in shoes of a skilled dunker.", "https://play.google.com/store/apps/details?id=com.nazzark.slamdunkthefirst", R.drawable.game2);
        return Result.success();
    }
}
