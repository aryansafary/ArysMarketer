package aryan.safary.sinoohe.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.NetworkChanged;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.classes.SizPayAdapter;
import aryan.safary.sinoohe.data.PaySubscriptionModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SizyPayActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
private  void  setObjectId(){
recyclerView=findViewById(R.id.subscription_recycler);
recyclerView.setLayoutManager(new LinearLayoutManager(SizyPayActivity.this));

}
private void  init(){
    setObjectId();
}
private void getPay(){
    RetrofitClient.getInstance(SizyPayActivity.this).getApi().getPayment().enqueue(new Callback<PaySubscriptionModel>() {
        @Override
        public void onResponse(@NonNull Call<PaySubscriptionModel> call, @NonNull Response<PaySubscriptionModel> response) {
            if(response.isSuccessful()) {
                assert response.body() != null;
                recyclerView.setAdapter(new SizPayAdapter(response.body().getData()));
            }    else {
                assert response.errorBody() != null;
                Log.d("Error", "onResponse: "+response.code()+response.errorBody().source());
            }
        }

        @Override
        public void onFailure(@NonNull Call<PaySubscriptionModel> call, @NonNull Throwable t) {
            Log.d("Field", "onFailure: "+t.getMessage());
        }
    });
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sizy_pay);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPay();
        NetworkChanged.InternetConnection(SizyPayActivity.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "لطفا اشتراک تهیه نمایید ", Toast.LENGTH_LONG).show();
        startActivity(new Intent(SizyPayActivity.this,SizyPayActivity.class));

    }
}