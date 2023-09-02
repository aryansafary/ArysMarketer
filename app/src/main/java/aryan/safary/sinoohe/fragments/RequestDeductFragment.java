package aryan.safary.sinoohe.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textview.MaterialTextView;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.RequestDeductedAdapter;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.RequestDeductedModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestDeductFragment extends Fragment {
 private final Context context;
 private MaterialTextView textBack;
 private LottieAnimationView animBack;
 private RecyclerView recyclerView;
public RequestDeductFragment(Context context){this.context=context;}
private void setObjectId(View view){
    recyclerView=view.findViewById(R.id.RequestDeductedRecycler);
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    textBack=view.findViewById(R.id.RequestDelete_text_background);
    animBack=view.findViewById(R.id.RequestDelete_text_background2);
}
 private void init(View view){
    setObjectId(view);
    getRequestDeducted();
 }

 private void getRequestDeducted(){
     RetrofitClient.getInstance(context).getApi().getDeducted(MySharedPrefrence.getInstance(getContext()).getUser()).enqueue(new Callback<RequestDeductedModel>() {
         @Override
         public void onResponse(@NonNull Call<RequestDeductedModel> call, @NonNull Response<RequestDeductedModel> response) {
             if(response.isSuccessful())
             if( response.body().getRequestDeducted()==null){
                     textBack.setVisibility(View.VISIBLE);
                     animBack.setVisibility(View.VISIBLE);
                 }else {
                 assert response.body() != null;
                 recyclerView.setAdapter(new RequestDeductedAdapter(response.body().getRequestDeducted()));
             }else Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
         }

         @Override
         public void onFailure(@NonNull Call<RequestDeductedModel> call, @NonNull Throwable t) {
             Log.d("Field", "onFailure  On RequestDeducted: "+t.getMessage());
         }
     });
 }


    @Override
    public void onResume() {
        super.onResume();
        getRequestDeducted();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_request_deduct, container, false);
        init(view);
        return view;
    }
}