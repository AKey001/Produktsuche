package de.produktsuche.backend.services;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import de.produktsuche.ui.notifications.NotificationBuilder;

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

        NotificationBuilder notificationBuilder = new NotificationBuilder();
        notificationBuilder.showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));

        Log.d("NOTIFICATION FCM", remoteMessage.getNotification().getTitle());
        Log.d("NOTIFICATION FCM", remoteMessage.getNotification().getBody());

    }

}
