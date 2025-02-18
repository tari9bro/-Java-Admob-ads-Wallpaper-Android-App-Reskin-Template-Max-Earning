package com.tari9bro.wallpapers.notification;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class NotificationHelper {

    public static final String CHANNEL_ID = "MyNotificationChannel";
    public static final CharSequence CHANNEL_NAME = "My Notification Channel";
    private  final int NOTIFICATION_ID = 1;
    // 6 hours interval
    private final Context context;

    public NotificationHelper(Context context) {
        this.context = context;
    }

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            // Set additional properties for the notification channel (optional)
            channel.setDescription("Notification Channel for wallpapers ");
            channel.enableVibration(true);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public  void showNotification( String title, String message, String url, int icon) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(message);
        notificationBuilder.setSmallIcon(icon );

        notificationBuilder.setAutoCancel(true);
// Create a style for the expanded view with an image
        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle()
                .bigPicture(BitmapFactory.decodeResource(context.getResources(),icon ))
                .bigLargeIcon(BitmapFactory.decodeResource(context.getResources(),icon ));
        notificationBuilder.setStyle(style);
        // Create an intent to open the URL
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        notificationBuilder.setContentIntent(pendingIntent);

        // Set notification sound
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(soundUri);

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }
    public void startPeriodicTasks() {
        // Define the common interval for all methods (3 hours in milliseconds)
        long commonInterval = 3 * 60 * 60 * 1000;

        // Create periodic work requests for each method
        PeriodicWorkRequest periodicWorkRequest1 = new PeriodicWorkRequest.Builder(
                Method1Worker.class, commonInterval, TimeUnit.MILLISECONDS)
                .setInitialDelay(0, TimeUnit.MILLISECONDS) // Start immediately
                .build();

        PeriodicWorkRequest periodicWorkRequest2 = new PeriodicWorkRequest.Builder(
                Method2Worker.class, commonInterval, TimeUnit.MILLISECONDS)
                .setInitialDelay(1 * 60 * 60 * 1000, TimeUnit.MILLISECONDS) // Start after 1 hour
                .build();

        PeriodicWorkRequest periodicWorkRequest3 = new PeriodicWorkRequest.Builder(
                Method3Worker.class, commonInterval, TimeUnit.MILLISECONDS)
                .setInitialDelay(2 * 60 * 60 * 1000, TimeUnit.MILLISECONDS) // Start after 2 hours
                .build();

        // Enqueue the work requests
        WorkManager.getInstance(context).enqueue(periodicWorkRequest1);
        WorkManager.getInstance(context).enqueue(periodicWorkRequest2);
        WorkManager.getInstance(context).enqueue(periodicWorkRequest3);
    }

}
