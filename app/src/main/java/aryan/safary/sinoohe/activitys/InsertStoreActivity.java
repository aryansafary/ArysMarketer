package aryan.safary.sinoohe.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.GlobalClass;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.NetworkChanged;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.JsonResponseModel;
import aryan.safary.sinoohe.dialogs.ProductSelectDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InsertStoreActivity extends AppCompatActivity {
private MaterialButton product , insertStore;
private TextInputEditText count;
private LottieAnimationView animationView;
private void setObjectId(){
    count=findViewById(R.id.InsertStore_count);
    insertStore=findViewById(R.id.InsertStore_btn);
    product=findViewById(R.id.InsertStore_product);
    product.setText(getString(R.string.ProductSelect));
    animationView=findViewById(R.id.InsertStoreAnim);

}
private void init(){
    setObjectId();
    CheckSelectedProduct();
    product.setOnClickListener(v -> {
        startActivity(new Intent(InsertStoreActivity.this, ProductSelectDialog.class));
        MySharedPrefrence.getInstance(InsertStoreActivity.this).setPage("store");
        this.finish();
    });

    insertStore.setOnClickListener(v -> InsertStore());
}

    private void  CheckSelectedProduct() {

            if (MySharedPrefrence.getInstance(InsertStoreActivity.this).getProductName().equals(getString(R.string.ProductSelect))) {

                product.setText(getString(R.string.ProductSelect));
            }
            else {
                product.setText(MySharedPrefrence.getInstance(InsertStoreActivity.this).getProductName());
                if(GlobalClass.ProductSelect!=null)GlobalClass.ProductSelect.finish();

            }

    }



   private void InsertStore(){
    if(!Objects.requireNonNull(count.getText()).toString().isEmpty() &&
            !product.getText().toString().equals(getString(R.string.ProductSelect)))
    {
        RetrofitClient.getInstance(InsertStoreActivity.this).getApi().InsertStore(product.getText().toString(),
                        count.getText().toString(),
                        MySharedPrefrence.getInstance(InsertStoreActivity.this).getUser())
                .enqueue(new Callback<JsonResponseModel>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
                        if(response.isSuccessful())
                        {
                            animationView.playAnimation();
                            animationView.setVisibility(View.VISIBLE);
                            new CountDownTimer(2500, 2500) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    MySharedPrefrence.getInstance(InsertStoreActivity.this).setProductName(getString(R.string.ProductSelect));
                                    finish();
                                    Toast.makeText(InsertStoreActivity.this,"محصول به انبار اضافه شد",Toast.LENGTH_LONG).show();
                                }
                            }.start();

                        }else {
                            assert response.errorBody() != null;
                            Log.d("Error", "onResponse: "+response.code()+ response.errorBody().source());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
                        Log.d("Field", "onFailure: "+t.getMessage());
                    }
                });




    }
   }


    @Override
    protected void onResume() {
        super.onResume();
        NetworkChanged.InternetConnection(InsertStoreActivity.this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_store);
        init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MySharedPrefrence.getInstance(InsertStoreActivity.this).setProductName(getString(R.string.ProductSelect));
    }
}