package aryan.safary.sinoohe.activitys;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
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


public class EditCenterPharmacyActivity extends AppCompatActivity {
    private TextInputEditText name,location,phone;
    private MaterialButton cancel,submit;

    private LottieAnimationView anim_end;

    private void setObjectId(){
        name=findViewById(R.id.editCenterName);
        location=findViewById(R.id.editCenterLocation);
        phone=findViewById(R.id.editCenterPhone);
        cancel=findViewById(R.id.editcenterCanclBtn);
        submit=findViewById(R.id.centerEditBtn);
        name.setText(MySharedPrefrence.getInstance(EditCenterPharmacyActivity.this).getUpdateNamePharmacy());
        location.setText(MySharedPrefrence.getInstance(EditCenterPharmacyActivity.this).getUpdateLocationPharmacy());
        phone.setText(MySharedPrefrence.getInstance(EditCenterPharmacyActivity.this).getUpdatePhonePharmacy());
anim_end=findViewById(R.id.edit_center_anim_end);

    }
    private void init(){
        setObjectId();
        cancel.setOnClickListener(v -> {
            this.finish();
            MySharedPrefrence.getInstance(EditCenterPharmacyActivity.this).setUpdateNamePharmacy("");
            MySharedPrefrence.getInstance(EditCenterPharmacyActivity.this).setUpdateLocationPharmacy("");
            MySharedPrefrence.getInstance(EditCenterPharmacyActivity.this).setUpdatePhonePharmacy("");
            MySharedPrefrence.getInstance(EditCenterPharmacyActivity.this).setUpdateIdPharmacy("");
        });
        submit.setOnClickListener(v -> UpdateCenterPharmacy());
    }


   private void UpdateCenterPharmacy(){
       RetrofitClient.getInstance(EditCenterPharmacyActivity.this).getApi().EditCenterPharmacy(
                       Objects.requireNonNull(name.getText()).toString(),
                       Objects.requireNonNull(location.getText()).toString(),
                        Objects.requireNonNull(phone.getText()).toString(),
               MySharedPrefrence.getInstance(EditCenterPharmacyActivity.this).getUpdateIdPharmacy()

               )
               .enqueue(new Callback<JsonResponseModel>() {
                   @Override
                   public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
                       if(response.isSuccessful()){
                           Toast.makeText(EditCenterPharmacyActivity.this, "مرکز مورد نظر بروزرسانی شد", Toast.LENGTH_SHORT).show();
                           MySharedPrefrence.getInstance(EditCenterPharmacyActivity.this).setUpdateNamePharmacy("");
                           MySharedPrefrence.getInstance(EditCenterPharmacyActivity.this).setUpdateLocationPharmacy("");
                           MySharedPrefrence.getInstance(EditCenterPharmacyActivity.this).setUpdatePhonePharmacy("");
                           MySharedPrefrence.getInstance(EditCenterPharmacyActivity.this).setUpdateIdPharmacy("");
                           anim_end.playAnimation();
                           anim_end.setVisibility(View.VISIBLE);
                           new CountDownTimer(3000, 10000) {
                               @Override
                               public void onTick(long millisUntilFinished) {

                               }

                               @Override
                               public void onFinish() {
anim_end.setVisibility(View.GONE);
finish();
                               }
                           }.start();
                       }else Log.d("error", "onResponse: "+response.message());
                   }

                   @Override
                   public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
                       Log.d("Field", "onFailure: "+t.getMessage());
                   }
               });



   }




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_center_pharmacy);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        NetworkChanged.InternetConnection(EditCenterPharmacyActivity.this);
    }
}