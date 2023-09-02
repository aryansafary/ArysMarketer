package aryan.safary.sinoohe.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textview.MaterialTextView;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.activitys.TransactionFilterActivity;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.classes.TransactionAdapter;
import aryan.safary.sinoohe.data.StoreModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionFragment extends Fragment {
   private RecyclerView recyclerView ;

   private MaterialTextView textBack;
   private LottieAnimationView animBack;
public TransactionFragment(){}
    private void setObjectId(View view){
    recyclerView=view.findViewById(R.id.store_recycler);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    textBack=view.findViewById(R.id.Transaction_text_background);
    animBack=view.findViewById(R.id.Transaction_text_background2);
    }
    private void init(View view){
        setObjectId(view);
        getStore();
        view.findViewById(R.id.transaction_filter).setOnClickListener(v -> startActivity(new Intent(getContext(), TransactionFilterActivity.class)));

    }



    private void getStore(){
        RetrofitClient.getInstance(getContext()).getApi().getStore(MySharedPrefrence.getInstance(getContext()).getTransActionFilter(),
                MySharedPrefrence.getInstance(getContext()).getUser()).enqueue(new Callback<StoreModel>() {
            @Override
            public void onResponse(@NonNull Call<StoreModel> call, @NonNull Response<StoreModel> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getData()==null || response.body().getData().isEmpty()){
                        textBack.setVisibility(View.VISIBLE);
                        animBack.setVisibility(View.VISIBLE);
                    }
                        else{
                        assert response.body() != null;
                        recyclerView.setAdapter(new TransactionAdapter(response.body().getData()));
                    }
                }

                else Log.d("Error", "onResponseCode: "+response.code());
            }

            @Override
            public void onFailure(@NonNull Call<StoreModel> call, @NonNull Throwable t) {
                Log.d("Field", "onFailure: "+t.getMessage());
            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        init(view);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        getStore();
    }
}