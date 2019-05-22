package com.example.mattjoslenproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyReceiver extends BroadcastReceiver {

    static int notificationID;
    String channel_id = "student app";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Notification", Toast.LENGTH_LONG).show();
        createNotificationChannel(context, channel_id);

        String title = intent.getStringExtra("title");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground_student)
                .setContentTitle(title)
                .setContentText("")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationID++, builder.build());
    }

    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Student App Notification Channel";
            String description = "This is the Student App Notification Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
