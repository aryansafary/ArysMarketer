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
import aryan.safary.sinoohe.classes.TransactionListAdapter;
import aryan.safary.sinoohe.data.trans_id_model;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TransactionListFragment extends Fragment {

    public TransactionListFragment() {
        // Required empty public constructor
    }
    private RecyclerView recyclerView;
    private View view ;
    private void setObjectId(){
        recyclerView=view.findViewById(R.id.TransactionListRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void init(){
        setObjectId();
        getTransactionList();
    }



    private void getTransactionList(){
        RetrofitClient.getInstance(getContext()).getApi().
                getTransactionList(MySharedPrefrence.getInstance(getContext()).getUser())
                .enqueue(new Callback<trans_id_model>() {
                    @Override
                    public void onResponse(@NonNull Call<trans_id_model> call, @NonNull Response<trans_id_model> response) {
                        if(response.isSuccessful()){
                            if(response.body().getData()==null){}
                            else recyclerView.setAdapter(new TransactionListAdapter(response.body().getData(),getContext()));
                        }else {
                            assert response.errorBody() != null;
                            Log.d("error", "onResponse: "+response.errorBody().source());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<trans_id_model> call, @NonNull Throwable t) {
                        Log.d("field", "onFailure: "+t.getMessage());
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        getTransactionList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction_list, container, false);
        this.view=view;
        init();
        return view;
    }
}