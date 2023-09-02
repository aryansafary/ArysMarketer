package aryan.safary.sinoohe.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.classes.SubscriptionListAdapter;
import aryan.safary.sinoohe.data.SubscriptionModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubscriptionListFragment extends Fragment {

    public SubscriptionListFragment() {
        // Required empty public constructor
    }
    private RecyclerView recyclerView;
    private View view;
    private void setObjectId(){
        recyclerView=view.findViewById(R.id.SubscriptionListRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void init(){
        setObjectId();
        getSubscription();
    }

private void getSubscription(){
    RetrofitClient.getInstance(getContext()).getApi().
            getSubscriptionList(MySharedPrefrence.getInstance(getContext()).getCompany())
            .enqueue(new Callback<SubscriptionModel>() {
                @Override
                public void onResponse(@NonNull Call<SubscriptionModel> call, @NonNull Response<SubscriptionModel> response) {
                  if(response.isSuccessful()) {
                      assert response.body() != null;
                      recyclerView.setAdapter(new SubscriptionListAdapter(response.body().getData(),getContext()));
                  }else {
                      assert response.errorBody() != null;
                      Log.d("error", "onResponse: "+response.errorBody().source());
                  }
                }

                @Override
                public void onFailure(@NonNull Call<SubscriptionModel> call, @NonNull Throwable t) {
                    Log.d("Field", "onFailure: "+t.getMessage());
                }
            });
}


    @Override
    public void onResume() {
        super.onResume();
        getSubscription();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subscription_list, container, false);
        this.view=view;
        init();
        return view;
    }
}