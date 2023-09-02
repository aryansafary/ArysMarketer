package aryan.safary.sinoohe.activitys;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.ViewPagerAdapter;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.classes.SizPayAdapter;
import aryan.safary.sinoohe.data.PaySubscriptionModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PayCallBackActivity extends AppCompatActivity {


//    private void getPay(){
//        RetrofitClient.getInstance(PayCallBackActivity.this).getApi().getPayment().enqueue(new Callback<PaySubscriptionModel>() {
//            @Override
//            public void onResponse(@NonNull Call<PaySubscriptionModel> call, @NonNull Response<PaySubscriptionModel> response) {
//                if(response.isSuccessful()) {
//                    assert response.body() != null;
//                    viewPager.setAdapter(new SizPayAdapter(response.body().getData()));
//                }    else {
//                    assert response.errorBody() != null;
//                    Log.d("Error", "onResponse: "+response.code()+response.errorBody().source());
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<PaySubscriptionModel> call, @NonNull Throwable t) {
//                Log.d("Field", "onFailure: "+t.getMessage());
//            }
//        });
//    }
private void  setObjectId( ){



}

    private void  init( ) {
        setObjectId();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pay_call_back);
        init();
    }
}