package aryan.safary.sinoohe.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textview.MaterialTextView;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.CalenderAdapter;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.NetworkChanged;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.CalculatorModel;
import aryan.safary.sinoohe.fragments.CalenderChangerFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SetClenderActivity extends AppCompatActivity {
    private String name;
    private MaterialTextView textBack;
    private LottieAnimationView animBack;
    private ConstraintLayout change;
    private RecyclerView recyclerView;

private void setObjectId(){
    if(!MySharedPrefrence.getInstance(SetClenderActivity.this).getSelectedName().isEmpty())
    name= MySharedPrefrence.getInstance(SetClenderActivity.this).getSelectedName();
    else name=MySharedPrefrence.getInstance(SetClenderActivity.this).getName();
    change=findViewById(R.id.calender_change);
    recyclerView=findViewById(R.id.calender_recycler);
    recyclerView.setLayoutManager(new LinearLayoutManager(SetClenderActivity.this));
    textBack=findViewById(R.id.Calender_text_background);
    animBack=findViewById(R.id.Calender_text_background2);
}
private void init(){
    setObjectId();
    setSideOption();
   change.setOnClickListener(v -> {
       Toast.makeText(SetClenderActivity.this,"change",Toast.LENGTH_LONG).show();
       getSupportFragmentManager().beginTransaction().add(R.id.calender_Container,new CalenderChangerFragment(name)).commit();
   });
   getCalculator();


}
private void getCalculator(){
    RetrofitClient.getInstance(SetClenderActivity.this).getApi().getCalculator(name).enqueue(new Callback<CalculatorModel>() {
        @Override
        public void onResponse(@NonNull Call<CalculatorModel> call, @NonNull Response<CalculatorModel> response) {
            if(response.isSuccessful())
                if(response.body().getData()==null || response.body().getData().isEmpty()){
                    textBack.setVisibility(View.VISIBLE);
                    animBack.setVisibility(View.VISIBLE);
                }
                else {
                    assert response.body() != null;
                    recyclerView.setAdapter(new CalenderAdapter(response.body().getData()));
                }else {
                assert response.errorBody() != null;
                Log.d("error", "onResponse: "+ response.errorBody().source() +response.code());
            }
        }

        @Override
        public void onFailure(@NonNull Call<CalculatorModel> call, @NonNull Throwable t) {
            Log.d("Field", "onFailure: "+t.getMessage());
        }
    });

}
private void setSideOption(){
    if(!MySharedPrefrence.getInstance(SetClenderActivity.this).getSide().equals("مدیر"))
     change.setVisibility(View.GONE);
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_clender);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCalculator();
        NetworkChanged.InternetConnection(SetClenderActivity.this);

    }
}