package aryan.safary.sinoohe.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.RequestAdapter;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.RequestModel;
import aryan.safary.sinoohe.dialogs.RequestFilterDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RequestFragment extends Fragment {
private RecyclerView recyclerViewRequest;
private MaterialTextView textBack;
private LottieAnimationView animBack;
private ConstraintLayout Filter;
private View view;
private LottieAnimationView buyAnim;
private void SetObjectId(){
    recyclerViewRequest=view.findViewById(R.id.request_recyclerview);
    recyclerViewRequest.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerViewRequest.setLayoutManager(new LinearLayoutManager(getContext()));
    Filter=view.findViewById(R.id.request_filter);
buyAnim=view.findViewById(R.id.requestAnim);
textBack=view.findViewById(R.id.Request_text_background);
animBack=view.findViewById(R.id.Request_text_background2);

}
private void init(){
    SetObjectId();
    ShowRequests();
    Filter.setOnClickListener(v -> startActivity(new Intent(getContext(),RequestFilterDialog.class)));

}

    @Override
    public void onResume() {
        super.onResume();
        ShowRequests();


    }



    public RequestFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View  view = inflater.inflate(R.layout.fragment_request, container, false);
     this.view=view;
     init();
        return view;
    }





    private void  ShowRequests(){
        RetrofitClient.getInstance(getContext()).getApi().getRequest(MySharedPrefrence.getInstance(getContext()).getRequestFilter(),
                MySharedPrefrence.getInstance(getContext()).getUser()).
                enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(@NonNull Call<RequestModel> call, @NonNull Response<RequestModel> response) {
                if(response.isSuccessful()) {
                    buyAnim.setVisibility(View.GONE);
                    if (response.body().getData() == null || response.body().getData().isEmpty()) {
                        textBack.setVisibility(View.VISIBLE);
                        animBack.setVisibility(View.VISIBLE);

                    } else {
                        assert response.body() != null;
                        // requestAdapter = new RequestAdapter(response.body().getData());
                        recyclerViewRequest.setAdapter(new RequestAdapter(response.body().getData()));

                    }
                }else Log.d("Error", "onResponse: "+response.code()+ response.errorBody());
            }

            @Override
            public void onFailure(@NonNull Call<RequestModel> call, @NonNull Throwable t) {

            }
        });




    }


}