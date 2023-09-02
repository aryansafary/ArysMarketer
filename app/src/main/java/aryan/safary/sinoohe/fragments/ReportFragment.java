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
import aryan.safary.sinoohe.classes.NameAdapter;

import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.NameModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportFragment extends Fragment {

    private final boolean checkPage;
    public ReportFragment(boolean checkPage) {
        this.checkPage=checkPage;
    }
private RecyclerView recyclerViewUser;
    private NameAdapter nameAdapter;

private void setObjectId(View view){
    recyclerViewUser=view.findViewById(R.id.Report_Recycler);
    recyclerViewUser.setLayoutManager(new LinearLayoutManager(getContext()));

}
private void init(View view){
    setObjectId(view);
    getUserList();
}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
init(view);
        return view;
    }





 private void getUserList( ){
     RetrofitClient.getInstance(getContext()).getApi().getName(MySharedPrefrence.getInstance(getContext()).getUser()).enqueue(new Callback<NameModel>() {
         @Override
         public void onResponse(@NonNull Call<NameModel> call, @NonNull Response<NameModel> response) {
             if(response.isSuccessful()){


               //  assert response.body() != null;
                 assert response.body() != null;
                 nameAdapter = new NameAdapter( response.body().getName(),checkPage);
                 recyclerViewUser.setAdapter(nameAdapter);
             }
         }

         @Override
         public void onFailure(@NonNull Call<NameModel> call, @NonNull Throwable t) {
Log.i("ErrorShowUser",t.getMessage());
         }
     });
 }

    @Override
    public void onResume() {
        super.onResume();
        getUserList();
    }
}