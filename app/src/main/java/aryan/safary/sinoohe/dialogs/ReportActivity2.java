package aryan.safary.sinoohe.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.JsonResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivity2 extends AppCompatActivity {
    private TextInputEditText description;
    private MaterialButton submit ;
private void setObjectId( ){
description=findViewById(R.id.newReport_description);
submit=findViewById(R.id.ReportSubmit);

}
private void init( ){
    setObjectId();
    submit.setOnClickListener(v -> sendData());
}


    private void sendData(){
    if(!Objects.requireNonNull(description.getText()).toString().isEmpty())
        RetrofitClient.getInstance(ReportActivity2.this).getApi().
                setReport(MySharedPrefrence.getInstance(ReportActivity2.this).getUser(),
                Objects.requireNonNull(description.getText()).toString()).enqueue(new Callback<JsonResponseModel>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
                        if(response.isSuccessful()){
                            finish();
                            Toast.makeText(ReportActivity2.this,"تایید شد ", Toast.LENGTH_LONG).show();
                        }else {
                            assert response.errorBody() != null;
                            Log.d("Error", "onResponse: "+response.code()+String.valueOf(response.errorBody().source()));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
                        Log.d("Field", "onFailure: "+t.getMessage());
                    }
                });
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_2);
        init();
    }
}
