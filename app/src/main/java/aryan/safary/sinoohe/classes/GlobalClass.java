package aryan.safary.sinoohe.classes;
import android.app.Activity;
import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import java.util.Objects;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.data.JsonResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GlobalClass extends Application {
    public   static Activity PharmacySelect;
      public static Activity ProductSelect;







    private void CheckUser(){
        RetrofitClient.getInstance(getApplicationContext()).getApi().
                CheckUser(MySharedPrefrence.getInstance(getApplicationContext()).getUser()).enqueue(new Callback<JsonResponseModel>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
                        if(!response.isSuccessful() || Objects.requireNonNull(response.body()).MyMessage.equals("0")) {
                            MySharedPrefrence.getInstance(getApplicationContext()).ClearSharedPrefrence();
                            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                                if(task.isSuccessful())
                                {
                                    MySharedPrefrence.getInstance(getApplicationContext()).setToken(task.getResult());
                                }

                            });






                        }

                    }


                    @Override
                    public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
                        Log.d("field", "onFailure: "+t.getMessage());
                    }
                });
    }



    @Override
    public void onCreate() {
        super.onCreate();
       FirebaseApp.initializeApp(this);
        String key = getString(R.string.Api_Map_kEY);
      //  Mapir.init(this, key);
      //  InternetConnection();
//getUserData();
      if(MySharedPrefrence.getInstance(getApplicationContext()).getToken().isEmpty()) {
          FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
              if (task.isSuccessful()) {
                  MySharedPrefrence.getInstance(getApplicationContext()).setToken(task.getResult());
              }

          });


      }
CheckUser();


    }













}
