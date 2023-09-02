package aryan.safary.sinoohe.services;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.activitys.CheckConnectionActivity;

public class LocationService extends Service {
    private MediaPlayer soundPlayer;

    private void InternetConnection(){
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected) {
            Toast.makeText(getApplicationContext(), "اتصال شما به اینترنت برقرار نیست", Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(this, CheckConnectionActivity.class);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }




    @Override
    public void onCreate() {

        Toast.makeText(this, "کار شما از الان شروع شد", Toast.LENGTH_SHORT).show();
        soundPlayer = MediaPlayer.create(this, R.raw.started_work);
        soundPlayer.setLooping(false);

    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "ثبت اطلاعات فعال شد", Toast.LENGTH_SHORT).show();

        String CHANNEL_ID = "channelId";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String offerChannelName = "Service Channel";
            String offerChannelDescription= "Music Channel";
            int offerChannelImportance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notifChannel = new NotificationChannel(CHANNEL_ID, offerChannelName, offerChannelImportance);
            notifChannel.setDescription(offerChannelDescription);
            NotificationManager notifManager = getSystemService(NotificationManager.class);
            notifManager.createNotificationChannel(notifChannel);

        }
        NotificationCompat.Builder sNotifBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("شما درحال حاضر سرکار هستید").
                setSound(Uri.parse("https://arysapp.com/Server/sound/started_work.mp3"))
                .setContentText("یک روز پر رونق و عالی را برایتان آرزومندم");

        Notification servNotification = sNotifBuilder.build();

        startForeground(1, servNotification);

        soundPlayer.start();
        return START_STICKY;

    }

    @Override
    public void onDestroy() {

        Toast.makeText(this, "سرویس متوقف شد", Toast.LENGTH_SHORT).show();
        soundPlayer = MediaPlayer.create(this,R.raw.endedwork );
        soundPlayer.setLooping(false);
        soundPlayer.start();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}