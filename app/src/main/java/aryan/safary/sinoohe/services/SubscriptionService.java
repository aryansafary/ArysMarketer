package aryan.safary.sinoohe.services;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.activitys.CheckConnectionActivity;
import aryan.safary.sinoohe.activitys.MainActivity;
import aryan.safary.sinoohe.activitys.SizyPayActivity;
import aryan.safary.sinoohe.activitys.NonSubscriptionActivity;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.JsonResponseModel;
import aryan.safary.sinoohe.data.SubscriptionModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionService extends Service {
    public SubscriptionService() {

    }
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




    private void showPrice() {
        if (MySharedPrefrence.getInstance(getApplicationContext()).getSide().equals("مدیر")) {
                Intent intent = new Intent(this, SizyPayActivity.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


        } else {
            Intent intent = new Intent(this, NonSubscriptionActivity.class);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
    private void getSubscription() {
        RetrofitClient.getInstance(getApplicationContext()).getApi().
                getSubscription(MySharedPrefrence.getInstance(getApplicationContext()).getUser(),
                        MySharedPrefrence.getInstance(getApplicationContext()).getCompany(),
                        MySharedPrefrence.getInstance(getApplicationContext()).getSide()
                )
                .enqueue(new Callback<SubscriptionModel>() {
                    @Override
                    public void onResponse(@NonNull Call<SubscriptionModel> call, @NonNull Response<SubscriptionModel> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            if(response.body().getData() != null) {
                                if (!response.body().getData().isEmpty()) {
                                    if (!response.body().getData().get(0).getActive().equals("true"))
                                        showPrice();
                                   //else you have daily

                                } else {
                                    Toast.makeText(getApplicationContext(), "اشتراک به اتمام رسیده", Toast.LENGTH_SHORT).show();
                                    showPrice();
                                }
                            } else {
                                showPrice();

                            }


                    }
                        else {
                            assert response.errorBody() != null;
                            Log.d("Error", "onResponse: " + response.errorBody().source() + response.code());
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<SubscriptionModel> call, @NonNull Throwable t) {
                        Log.d("field", "onFailure: " + t.getMessage());
                    }
                });
    }

    @Override
    public void onCreate() {

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getSubscription();
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "ممنون که از Arys Marketer  استفاده میکنی", Toast.LENGTH_SHORT).show();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}