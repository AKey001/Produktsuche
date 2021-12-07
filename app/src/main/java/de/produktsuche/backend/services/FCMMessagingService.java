package de.produktsuche.backend.services;

import static de.produktsuche.backend.services.NotificationChannelClass.CHANNEL_ID;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import de.produktsuche.MainActivity;
import de.produktsuche.R;

public class FCMMessagingService extends FirebaseMessagingService {
    static final String TOKEN_PREFERENCE_KEY = "fcm_token";

    @Override
    public void onNewToken(@NonNull String id) {
        super.onNewToken(id);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putString(TOKEN_PREFERENCE_KEY, id).apply();

        Log.d("INSTANCE_ID", id);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int notificationID = sharedPreferences.getInt("notification_id", 0);
        notificationID++;
        if (notificationID > 15) {
            notificationID = 0;
        }
        sharedPreferences.edit().putInt("notification_id", notificationID).apply();


        String title = remoteMessage.getNotification().getTitle() + "";
        String body = remoteMessage.getNotification().getBody()+ "";

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_storefront_24)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setColor(getResources().getColor(R.color.green_500, getTheme()))
                .setLights(Color.GREEN, 1000,3000)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body));


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationID, notification.build());

        Log.d("NOTIFICATION FCM", remoteMessage.getNotification().getTitle());
        Log.d("NOTIFICATION FCM", remoteMessage.getNotification().getBody());

    }

}
