package aryan.safary.sinoohe.classes;

import static android.app.PendingIntent.FLAG_IMMUTABLE;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;



public class Notifications {
    @SuppressLint("StaticFieldLeak")
    public static  Context context ;
   public Notifications(Context context) {
       Notifications.context =context;

   }

  public static void CreateChannel(){
       if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O)  {
          NotificationChannel notificationChannel = new NotificationChannel(Const.Notification_Channel, "My Notification Chanel", NotificationManager.IMPORTANCE_DEFAULT);
          NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
          manager.createNotificationChannel(notificationChannel);
      }

  }

  public static void ShowNotification(String title ,String message , int drawable , int color, int Priority , int LightOn , int LightOff ,
                                      boolean AutoCancel , Intent intent , long vib , String sound , int id ){
CreateChannel();
   @SuppressLint("WrongConstant") PendingIntent pendingIntent = PendingIntent.getActivity(context,1000,intent,FLAG_IMMUTABLE );
      NotificationCompat.Builder builder = new NotificationCompat.Builder(context,Const.Notification_Channel);
      builder.setContentTitle(title);
      builder.setContentText(message);
      builder.setSmallIcon(drawable);
      builder.setColor(color);
      builder.setPriority(Priority);
      builder.setLights(color,LightOn,LightOff);
      builder.setAutoCancel(AutoCancel);
      builder.setVibrate(new long[]{vib,vib,vib,vib,vib,vib,vib,vib});
      builder.setContentIntent(pendingIntent);
      builder.setSound(Uri.parse(sound), AudioManager.STREAM_RING);
      NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
      manager.notify(id,builder.build());

  }
}
