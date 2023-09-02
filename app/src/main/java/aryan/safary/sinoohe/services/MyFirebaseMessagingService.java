package aryan.safary.sinoohe.services;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.activitys.SpeleshScreenActivity;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.Notifications;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String message = data.get("body");
        long time = remoteMessage.getSentTime();
        Log.e("title", "onMessageReceived: " + title + "" + message);
        Log.d("firebase_message", "onMessageReceived: " + remoteMessage.getData().get("title") + remoteMessage.getData().get("body"));
        String data_time = new SimpleDateFormat("yyyy/MM,dd __ HH:mm:ss", Locale.ENGLISH).format(new Date(time));
        Notifications notifications = new Notifications(this);
        notifications.ShowNotification(title, message, R.mipmap.ic_icon, R.color.green_light, 2,
                1000, 1000, false, new Intent(this, SpeleshScreenActivity.class), 8000,
                "https://arysapp.com/Server/sound/notification.mp3", 5);





    }







    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
          MySharedPrefrence.getInstance(this).setToken(token);
    }



}




