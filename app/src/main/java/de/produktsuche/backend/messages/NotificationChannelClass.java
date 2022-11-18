package de.produktsuche.backend.messages;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class NotificationChannelClass extends Application {
    public static final String CHANNEL_ID = "channel1";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Reservierungen", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Reservierungsbestätigungen und -ablehnungen");

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
