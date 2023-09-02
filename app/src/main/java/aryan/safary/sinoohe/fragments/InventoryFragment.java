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
import aryan.safary.sinoohe.classes.InventoryAdapter;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.InventoryModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventoryFragment extends Fragment {
    private RecyclerView recyclerView ;
    private final Context context ;

    private MaterialTextView textBack;
    private LottieAnimationView animBack;
 public InventoryFragment(Context context){
     this.context=context;
 }

private void setObjectId(View view){
    recyclerView=view.findViewById(R.id.InventoryRecycler);
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    textBack=view.findViewById(R.id.InventoryProduct_text_background);
    animBack=view.findViewById(R.id.InventoryProduct_text_background2);
}
private void init(View view){
     setObjectId(view);
     getInventory();
}



private void getInventory(){
    RetrofitClient.getInstance(context).getApi().getInventory(MySharedPrefrence.getInstance(getContext()).getUser()).enqueue(new Callback<InventoryModel>() {
        @Override
        public void onResponse(@NonNull Call<InventoryModel> call, @NonNull Response<InventoryModel> response) {
            if(response.isSuccessful()){
                assert response.body() != null;
                if( response.body().getInventory()==null){
                    textBack.setVisibility(View.VISIBLE);
                    animBack.setVisibility(View.VISIBLE);
                } else recyclerView.setAdapter(new InventoryAdapter(context, response.body().getInventory()));
            }else Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(@NonNull Call<InventoryModel> call, @NonNull Throwable t) {
            Log.d("Field", "onFailure in Inventory: "+t.getMessage());
        }
    });
}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_inventory, container, false);
        init(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getInventory();
    }
}