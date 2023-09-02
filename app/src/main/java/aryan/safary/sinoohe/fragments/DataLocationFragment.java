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
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.DataShowAdapter;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.DataItem;
import aryan.safary.sinoohe.data.DataModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DataLocationFragment extends Fragment {
    private RecyclerView recyclerView ;
    private MaterialTextView textBack;
    private LottieAnimationView animBack;
private void setObjectId(View view){
    recyclerView=view.findViewById(R.id.DataLocation_recycler);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    textBack=view.findViewById(R.id.data_location_text);
    animBack=view.findViewById(R.id.data_location_anim);
}
 private void init(View view){
    setObjectId(view);
    ShowData();

 }
    public DataLocationFragment() {
        // Required empty public constructor
    }

    private void ShowData(){
        String name;
        if(!MySharedPrefrence.getInstance(getContext()).getSelectedName().isEmpty())
       name  = MySharedPrefrence.getInstance(getContext()).getSelectedName();
        else name=MySharedPrefrence.getInstance(getContext()).getName();
        RetrofitClient.getInstance(getContext()).getApi().getDataLocation(name).enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(@NonNull Call<DataModel> call, @NonNull Response<DataModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getData().isEmpty() || response.body().getData()==null)
                    {
                        textBack.setVisibility(View.VISIBLE);
                        animBack.setVisibility(View.VISIBLE);
                    }
                    else Toast.makeText(getContext(),"یک تاریخ  رو انخاب کنید و فعالیت آن روز را مشاهده کنید",Toast.LENGTH_LONG).show();
                    assert response.body() != null;
                    List<DataItem> list = new ArrayList<>(response.body().getData());
                 recyclerView.setAdapter(new DataShowAdapter(list));
                }else Log.d("Error", "onResponse: "+response.code()+ response.message());
            }

            @Override
            public void onFailure(@NonNull Call<DataModel> call, @NonNull Throwable t) {
                Log.d("Field", "onFailure: "+t.getMessage());
            }
        });




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_data_location, container, false);
init(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ShowData();
    }
}