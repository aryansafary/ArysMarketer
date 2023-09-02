package aryan.safary.sinoohe.dialogs;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.GlobalClass;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.JsonResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitRequest extends AppCompatActivity {
private MaterialButton Pharmacy , Product;
//private EditText count , offer;
private MaterialButton submit;
private TextInputEditText count , offer ;

private void SetObjectId(){
    count=findViewById(R.id.submit_request_count);
    offer=findViewById(R.id.submit_request_offer);
    Pharmacy=findViewById(R.id.newRequest_pharmacy);
    Product=findViewById(R.id.newRequest_Product);
    submit=findViewById(R.id.newRequest_SubmitBtn);

    Pharmacy.setText(getString(R.string.PharmacySelect));
    Product.setText(getString(R.string.ProductSelect));
}


    private  void  init() {
    SetObjectId();
    CheckSelectedPharmacy();


        Pharmacy.setOnClickListener(v ->{
startActivity(new Intent(SubmitRequest.this,PharmacySelectDialog.class));
        this.finish();
    });
    Product.setOnClickListener(v ->{
        startActivity(new Intent(SubmitRequest.this,ProductSelectDialog.class));
        MySharedPrefrence.getInstance(SubmitRequest.this).setPage("request");
        this.finish();
    });
    submit.setOnClickListener(v -> {
      String user = MySharedPrefrence.getInstance(SubmitRequest.this).getUser();
      String pharmacy = Pharmacy.getText()+"";
      String product  = Product.getText()+"";
      String co =  count.getText()+"";
      String of = offer.getText()+"";
        RetrofitClient.getInstance(SubmitRequest.this).getApi().setRequest(user,pharmacy,product,co,of).enqueue(new Callback<JsonResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(SubmitRequest.this,"Confirm Request",Toast.LENGTH_LONG).show();
                    finish();
                    MySharedPrefrence.getInstance(SubmitRequest.this).setPharmacyName(getString(R.string.PharmacySelect));
                    MySharedPrefrence.getInstance(SubmitRequest.this).setProductName(getString(R.string.ProductSelect));
                    if(GlobalClass.ProductSelect!=null)GlobalClass.ProductSelect.finish();
                    if(GlobalClass.PharmacySelect!=null)GlobalClass.PharmacySelect.finish();



                }else{
                    Toast.makeText(SubmitRequest.this,"Info:"+user+pharmacy+product+co+of,Toast.LENGTH_LONG).show();
                    assert response.errorBody() != null;
                    Log.d("Errors", "onResponse code : "+response.code()+"ErrorBody:    "+ response.errorBody().source());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
                Toast.makeText(SubmitRequest.this,"Field",Toast.LENGTH_LONG).show();
                Log.d("Field", "onFailure: "+t.getMessage());
            }
        });
    });

    }


    private void  CheckSelectedPharmacy() {
    try {
        if (MySharedPrefrence.getInstance(SubmitRequest.this).getPharmacyName().equals(getString(R.string.PharmacySelect)) && MySharedPrefrence.getInstance(SubmitRequest.this).getProductName().equals(getString(R.string.ProductSelect))) {
            Pharmacy.setText(getString(R.string.PharmacySelect));
            Product.setText(getString(R.string.ProductSelect));
        }
        if (MySharedPrefrence.getInstance(SubmitRequest.this).getPharmacyName().equals(getString(R.string.PharmacySelect)) && !MySharedPrefrence.getInstance(SubmitRequest.this).getProductName().equals(getString(R.string.ProductSelect))) {
            Product.setText(MySharedPrefrence.getInstance(SubmitRequest.this).getProductName());
            GlobalClass.ProductSelect.finish();

        } else {
            Pharmacy.setText(MySharedPrefrence.getInstance(SubmitRequest.this).getPharmacyName());
            GlobalClass.PharmacySelect.finish();

        }
        if (!MySharedPrefrence.getInstance(SubmitRequest.this).getPharmacyName().equals(getString(R.string.PharmacySelect)) && !MySharedPrefrence.getInstance(SubmitRequest.this).getProductName().equals(getString(R.string.ProductSelect))) {
            Pharmacy.setText(MySharedPrefrence.getInstance(SubmitRequest.this).getPharmacyName());
            Product.setText(MySharedPrefrence.getInstance(SubmitRequest.this).getProductName());
            if(GlobalClass.ProductSelect!=null)GlobalClass.ProductSelect.finish();
            if(GlobalClass.PharmacySelect!=null)GlobalClass.PharmacySelect.finish();

        }
    }catch (Throwable throwable ){throwable.printStackTrace();}




}




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_request_dialog);
        init();
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MySharedPrefrence.getInstance(SubmitRequest.this).setPharmacyName(getString(R.string.PharmacySelect));
        MySharedPrefrence.getInstance(SubmitRequest.this).setProductName(getString(R.string.ProductSelect));
    }
}






