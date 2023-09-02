package aryan.safary.sinoohe.classes;


import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import static aryan.safary.sinoohe.classes.Notifications.context;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;


import aryan.safary.sinoohe.activitys.CheckConnectionActivity;

public class NetworkChanged {


    public static void InternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            Toast.makeText(context, "اتصال شما به اینترنت برقرار نیست", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, CheckConnectionActivity.class);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public static boolean CheckAirPlan() {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    }
    public static boolean Connection(){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return  activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}