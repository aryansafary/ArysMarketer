package aryan.safary.sinoohe.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.classes.product_filter_adapter;
import aryan.safary.sinoohe.data.ProductsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFilterActivity extends AppCompatActivity {
private RecyclerView recyclerView;
private void setObjectId(){
    recyclerView = findViewById(R.id.ProductFilterRecycler);
    recyclerView.setLayoutManager(new LinearLayoutManager(ProductFilterActivity.this));
}
private void init(){
    setObjectId();
    showBrand();
}

private void showBrand(){
    RetrofitClient.getInstance(ProductFilterActivity.this).getApi().getProducts(MySharedPrefrence.getInstance(ProductFilterActivity.this).getUser()).enqueue(new Callback<ProductsModel>() {
        @Override
        public void onResponse(@NonNull Call<ProductsModel> call, @NonNull Response<ProductsModel> response) {
            if(response.isSuccessful()) {
                assert response.body() != null;
                recyclerView.setAdapter(new product_filter_adapter(response.body().getData()));
            }
            else Log.d("Error", "onResponse: "+response.code());
        }

        @Override
        public void onFailure(@NonNull Call<ProductsModel> call, @NonNull Throwable t) {
            Log.d("Field", "onFailure: "+t.getMessage());
        }
    });
}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_filter);
        init();
    }
}