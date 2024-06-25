package aryan.safary.sinoohe.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;
import java.util.Objects;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.NetworkChanged;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.JsonResponseModel;
import aryan.safary.sinoohe.data.UserItem;
import aryan.safary.sinoohe.data.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MainActivity extends AppCompatActivity {

    private String token ="";
    private MaterialButton LoginBtn , signup_tb ;
    private TextInputEditText username, password;
    private LottieAnimationView Loading ;
    private final CountDownTimer timerAnim= new CountDownTimer(2000 , 2000) {
        @Override
        public void onTick(long millisUntilFinished) {
            Loading.playAnimation();
            Loading.setVisibility(View.VISIBLE);
            username.setEnabled(false);
            password.setEnabled(false);
            username.setError("Wrong Input!");


        }

        @Override
        public void onFinish() {
Loading.cancelAnimation();
Loading.setVisibility(View.GONE);
            username.setEnabled(true);
            password.setEnabled(true);
        }
    };





    private void SetObjectId() {
        LoginBtn = findViewById(R.id.LoginBtn);
        signup_tb= findViewById(R.id.Main_signup_tab);
        username = findViewById(R.id.LoginUsername);
        password = findViewById(R.id.LoginPassword);
        Loading = findViewById(R.id.Main_loading);
        //token=MySharedPrefrence.getInstance(MainActivity.this).getToken();
        token="token";
    }

    private void init() {
        CheckerLogin();
        SetObjectId();
        LoginBtn.setOnClickListener(view -> {
            Loading.playAnimation();
           login();
        });
        signup_tb.setOnClickListener(v -> new CountDownTimer(300,300) {
            @Override
            public void onTick(long millisUntilFinished) {
                YoYo.with(Techniques.RollIn).duration(300).delay(100).playOn(findViewById(R.id.LoginBackForm));
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
                finish();
            }
        }.start());
    }


    private void getData(String username){
        RetrofitClient.getInstance(MainActivity.this).getApi().getUser(username).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
               if (response.isSuccessful()){
                   assert response.body() != null;
                UserItem userItem = response.body().getData().get(0);
               MySharedPrefrence.getInstance(MainActivity.this).setUserId(userItem.getId());
               MySharedPrefrence.getInstance(MainActivity.this).setName(userItem.getName());
               MySharedPrefrence.getInstance(MainActivity.this).setSide(userItem.getSide());
               MySharedPrefrence.getInstance(MainActivity.this).setPhone(userItem.getPhone());
               MySharedPrefrence.getInstance(MainActivity.this).setEmail(userItem.getEmail());
               MySharedPrefrence.getInstance(MainActivity.this).setImage(userItem.getImage());
               MySharedPrefrence.getInstance(MainActivity.this).setBarthday(userItem.getBarthday());
               MySharedPrefrence.getInstance(MainActivity.this).setLocation(userItem.getLocation());
               MySharedPrefrence.getInstance(MainActivity.this).setDta(userItem.getData());
               MySharedPrefrence.getInstance(MainActivity.this).setCompany(userItem.getCompany());
               MySharedPrefrence.getInstance(MainActivity.this).setProvince(userItem.getProvince());
               MySharedPrefrence.getInstance(MainActivity.this).setCompanyAddress(userItem.getCompanyAddress());
               MySharedPrefrence.getInstance(MainActivity.this).setCity(userItem.getCity());
               MySharedPrefrence.getInstance(MainActivity.this).setUserStatus("1");
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                MainActivity.this.finish();
               }else Log.d("Error", "onResponse: "+response.code());
            }

            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                Log.d("Field", "onFailure: "+t.getMessage());
            }
        });
    }
private boolean IsEmpty(){
        boolean check=false;
    String u = Objects.requireNonNull(username.getText()).toString();
    String p = Objects.requireNonNull(password.getText()).toString();
    if(!u.isEmpty())
      if(!p.isEmpty())
          if(!token.isEmpty())
              check=true;
    else{
              FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                  if(task.isSuccessful())
                  {
                      MySharedPrefrence.getInstance(MainActivity.this).setToken(task.getResult());
                  }

              });
              token=MySharedPrefrence.getInstance(MainActivity.this).getToken();

          }


    return check;

}
    private void login() {
        if (IsEmpty()) {
            String u = Objects.requireNonNull(username.getText()).toString();
            String p = Objects.requireNonNull(password.getText()).toString();
           RetrofitClient.getInstance(MainActivity.this).
                    getApi().loginUser(u, p, token).enqueue(new Callback<JsonResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
                    if (response.isSuccessful()) {
                        switch (response.code()) {
                            case 200:
                                //Toast.makeText(MainActivity.this,u+"____________________"+p+"________________"+"token"+token,Toast.LENGTH_LONG).show();
                                getData(u);
                                Loading.playAnimation();
                                MySharedPrefrence.getInstance(MainActivity.this).setUser(username.getText().toString());
                                MySharedPrefrence.getInstance(MainActivity.this).setIsLogin(true);
                                break;
                            case 406:
                                Toast.makeText(MainActivity.this, "همچین کاربری یافت نشد.", Toast.LENGTH_LONG).show();
                                break;
                            case 500:
                                Toast.makeText(MainActivity.this, "خطا!", Toast.LENGTH_LONG).show();
                                break;
                            case 400:
                                Toast.makeText(MainActivity.this, "در وارد کردن اطلاعات دقت فرمایید", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(MainActivity.this, "خطایی وجود دارد", Toast.LENGTH_LONG).show();
                                break;

                        }


                    } else {

                        assert response.errorBody() != null;
                        Log.d("Error", "onResponse: " +response.code()+response.message());
                        Loading.setAnimationFromUrl("https://assets1.lottiefiles.com/packages/lf20_pqpmxbxp.json");
                        timerAnim.start();


                    }
                }


                @Override
                public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
                    Log.d("error", t.getMessage());

                }
            });

        } else {
            Loading.setAnimationFromUrl("https://assets2.lottiefiles.com/packages/lf20_eJkC1J.json");
            timerAnim.start();

        }

    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        NetworkChanged.InternetConnection(MainActivity.this);
    }

    private void CheckerLogin() {
        String us;
        us = MySharedPrefrence.getInstance(MainActivity.this).getUser();
        if (MySharedPrefrence.getInstance(MainActivity.this).getIsLogin() && us != null) {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                      this.finish();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//                    intent.addCategory(Intent.CATEGORY_HOME);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
        finishAffinity(); // پنجره‌های باز شده در برنامه بسته می‌شود
        System.exit(0); // برنامه بسته می‌شود
                    MySharedPrefrence.getInstance(MainActivity.this).ClearSharedPrefrence();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if(task.isSuccessful()&&MySharedPrefrence.getInstance(MainActivity.this).getToken().isEmpty())
            {
                MySharedPrefrence.getInstance(MainActivity.this).setToken(task.getResult());
            }

        });
    }
}





