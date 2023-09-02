package aryan.safary.sinoohe.activitys;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.NetworkChanged;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.JsonResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProductActivity extends AppCompatActivity {
    private EditText name,brand,description,price;
    private MaterialButton cancel,submit;
    private void setObjectId(){
        name=findViewById(R.id.editProduct_name);
        brand=findViewById(R.id.editProduct_brand);
        description=findViewById(R.id.editProduct_description);
        price=findViewById(R.id.editProduct_price);
        cancel=findViewById(R.id.editProduct_cancel_btn);
        submit=findViewById(R.id.editProduct_edit_btn);
        name.setText(MySharedPrefrence.getInstance(EditProductActivity.this).getUpdateNameProduct());
        brand.setText(MySharedPrefrence.getInstance(EditProductActivity.this).getUpdateBrandProduct());
        description.setText(MySharedPrefrence.getInstance(EditProductActivity.this).getUpdateDescriptionProduct());
        price.setText(MySharedPrefrence.getInstance(EditProductActivity.this).getUpdatePriceProduct());

    }
    private void init(){
        setObjectId();
        cancel.setOnClickListener(v -> {
            this.finish();
        MySharedPrefrence.getInstance(EditProductActivity.this).setProductId("");
        });
        submit.setOnClickListener(v -> UpdateProduct());
    }
private void UpdateProduct(){
    RetrofitClient.getInstance(EditProductActivity.this).getApi().EditProduct(
            name.getText().toString(),
            brand.getText().toString(),
            description.getText().toString(),
            MySharedPrefrence.getInstance(EditProductActivity.this).getProductId(),
            price.getText().toString()).enqueue(new Callback<JsonResponseModel>() {
        @Override
        public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
          if(response.isSuccessful()) {
              Toast.makeText(EditProductActivity.this,"محصول بروزرسانی شد",Toast.LENGTH_SHORT).show();
              MySharedPrefrence.getInstance(EditProductActivity.this).setProductId("");
              finish();
          } else{
              Log.d("Error", "onResponse: "+ response.message());
          }
        }

        @Override
        public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
            Log.d("Field", "onFailure: "+ Objects.requireNonNull(t.getMessage()));
        }
    });
    MySharedPrefrence.getInstance(EditProductActivity.this).setProductId("");
}
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        init();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        NetworkChanged.InternetConnection(EditProductActivity.this);
    }
}