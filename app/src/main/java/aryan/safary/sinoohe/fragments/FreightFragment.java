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

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textview.MaterialTextView;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.FreightAdapter;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.FreightModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreightFragment extends Fragment {
    private RecyclerView recyclerView;
    private final Context context ;
    private MaterialTextView textBack;
    private LottieAnimationView animBack;

private void setObjectId(View view){
recyclerView=view.findViewById(R.id.freight_recycler);
recyclerView.setLayoutManager(new LinearLayoutManager(context));
textBack=view.findViewById(R.id.freight_text_background);
animBack=view.findViewById(R.id.freight_text_background2);
}
private void init(View view){
    setObjectId(view);
}

    public FreightFragment(Context context) {
        // Required empty public constructor
        this.context=context;
    }

    private void getFreight(){
        RetrofitClient.getInstance(context).getApi().getFright(MySharedPrefrence.getInstance(getContext()).getUser()).enqueue(new Callback<FreightModel>() {
            @Override
            public void onResponse(@NonNull Call<FreightModel> call, @NonNull Response<FreightModel> response) {
                if(response.isSuccessful()) {
                    if(response.body().getData()==null){
                            textBack.setVisibility(View.VISIBLE);
                            animBack.setVisibility(View.VISIBLE);
                        }else {
                            assert response.body() != null;
                            recyclerView.setAdapter(new FreightAdapter(response.body().getData()));
                        }
                } else {
                    assert response.errorBody() != null;
                    Log.e("Error", "onResponse: "+String.valueOf(response.errorBody().source())+response.code() );
                }
            }

            @Override
            public void onFailure(@NonNull Call<FreightModel> call, @NonNull Throwable t) {
                Log.d("Field", "onFailure: "+t.getMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_freight, container, false);
        init(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getFreight();
    }
}