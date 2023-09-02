package aryan.safary.sinoohe.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.NetworkChanged;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.JsonResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewUserActivity extends AppCompatActivity {
private Spinner side_list;
private MaterialButton submit_button;
private TextInputEditText username , password ;
private String side ;
private LottieAnimationView lottieAnimationView;
    private void setSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.app_arrays, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        side_list.setAdapter(adapter);
        side_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               side=adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(NewUserActivity.this, "سمت خود را مشخص کنید", Toast.LENGTH_LONG).show();
            }
        });
    }

private void setObjectId(){
    side_list=findViewById(R.id.NewUser_side);

    //Set Spinner Side list -->
//    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(NewUserActivity.this,
//            R.array.app_arrays, android.R.layout.simple_spinner_item);
//// Specify the layout to use when the list of choices appears
//    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
//    side_list.setAdapter(adapter);

    //_______________________________
    lottieAnimationView=findViewById(R.id.NewUser_loading);
    submit_button=findViewById(R.id.NewUser_submit_btn);
    username=findViewById(R.id.newUser_username);
    password=findViewById(R.id.newUser_password);
}private void init(){
    setObjectId();
    setSpinner();
    submit_button.setOnClickListener(v -> RegisterUser());

    }
    private void RegisterUser(){
    String user = Objects.requireNonNull(username.getText()).toString(),
           pass= Objects.requireNonNull(password.getText()).toString();
    if(user.isEmpty() && pass.isEmpty()  &&  side.isEmpty() || side.equals("سمت کاربر را مشخص کنید"))
        Toast.makeText(NewUserActivity.this,"لطفا اظلاعات رو کامل وارد کنید!",Toast.LENGTH_LONG).show();
    else if(user.length()<5 ) Toast.makeText(NewUserActivity.this,"نام کاربری نباید کمتر از 5 حرف باشد",Toast.LENGTH_LONG).show();
    else if(pass.length()<8 ) Toast.makeText(NewUserActivity.this,"رمز عبور نباید کمتر از 8 حرف باشد",Toast.LENGTH_LONG).show();
    else{
        RetrofitClient.getInstance(NewUserActivity.this).getApi().NewUser(side,user,pass, MySharedPrefrence.getInstance(NewUserActivity.this).getUser()).enqueue(new Callback<JsonResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
                if (response.isSuccessful())
                 {
                     Toast.makeText(NewUserActivity.this,"کاربر با موفقیت استخدام شد",Toast.LENGTH_LONG).show();
                     lottieAnimationView.setVisibility(View.VISIBLE);
                     lottieAnimationView.playAnimation();
                     new CountDownTimer(2000, 1000) {
                         @Override
                         public void onTick(long millisUntilFinished) {

                         }

                         @Override
                         public void onFinish() {
                             finish();
                             lottieAnimationView.cancelAnimation();
                             lottieAnimationView.setVisibility(View.GONE);
                         }
                     }.start();


                }else {
                    assert response.errorBody() != null;
                    Log.d("Error", "onResponse: "+response.code()+ response.errorBody().source());
                }
                }


            @Override
            public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
                Log.d("Field", "OnField: "+t.getMessage());
            }
        });
    }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        NetworkChanged.InternetConnection(NewUserActivity.this);
    }
}