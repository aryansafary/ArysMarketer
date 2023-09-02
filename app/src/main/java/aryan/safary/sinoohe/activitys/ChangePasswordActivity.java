package aryan.safary.sinoohe.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.JsonResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
private TextInputEditText this_pass , new_pass , confirm_pass;
private MaterialButton submit;
private void setObjectId(){
    this_pass=findViewById(R.id.change_password_this);
    new_pass=findViewById(R.id.change_password_new);
    confirm_pass=findViewById(R.id.change_password_confirm);
    submit=findViewById(R.id.change_password_submit);


}
private void init(){
    setObjectId();
    submit.setOnClickListener(v -> {
if(IsEmpty()){
    if(Objects.requireNonNull(new_pass.getText()).toString().length()<9 || Objects.requireNonNull(confirm_pass.getText()).toString().length()<9 ){
        Toast.makeText(ChangePasswordActivity.this, "رمز عبور شما باید حاوی حداقل 8 حرف  باشد", Toast.LENGTH_SHORT).show();
    }else{
    RetrofitClient.getInstance(ChangePasswordActivity.this).getApi()
            .CheckPassword(MySharedPrefrence.getInstance(ChangePasswordActivity.this).getUser(),
                    Objects.requireNonNull(this_pass.getText()).toString(),
                    Objects.requireNonNull(new_pass.getText()).toString()).enqueue(new Callback<JsonResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
                 if(response.isSuccessful()){
                     MySharedPrefrence.getInstance(ChangePasswordActivity.this).ClearSharedPrefrence();
                    startActivity(new Intent(ChangePasswordActivity.this,MainActivity.class));
                     ChangePasswordActivity.this.finish();
                     FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                         if(task.isSuccessful())
                         {
                             MySharedPrefrence.getInstance(ChangePasswordActivity.this).setToken(task.getResult());
                         }

                     });
                 }else {
                     Toast.makeText(ChangePasswordActivity.this, "رمز عبور فعلی را درست وارد نکرده اید", Toast.LENGTH_SHORT).show();
                 }
                }

                @Override
                public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
                    Log.d("field", "onFailure: "+t.getMessage());
                    Toast.makeText(ChangePasswordActivity.this, "Field", Toast.LENGTH_SHORT).show();
                }
            });}
}else{
    Toast.makeText(ChangePasswordActivity.this, "لطفا اطلاعات  رو به درستی وارد کنید", Toast.LENGTH_SHORT).show();
}
    });
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
    }
    private boolean IsEmpty(){
    boolean check=false;
    if(!Objects.requireNonNull(this_pass.getText()).toString().isEmpty())
        if (!Objects.requireNonNull(new_pass.getText()).toString().isEmpty())
            if (!Objects.requireNonNull(confirm_pass.getText()).toString().isEmpty())
                if (Objects.requireNonNull(new_pass.getText()).toString().equals(confirm_pass.getText().toString()))
                    check = true;

return check;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ChangePasswordActivity.this,HomeActivity.class));
    }
}