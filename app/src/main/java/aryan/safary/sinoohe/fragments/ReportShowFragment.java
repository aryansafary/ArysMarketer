package aryan.safary.sinoohe.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textview.MaterialTextView;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.ReportAdapter;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.ReportModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReportShowFragment extends Fragment {
private RecyclerView recyclerView ;
private MaterialTextView textBack;
private LottieAnimationView animBack;
private LottieAnimationView lottieAnimationView ;
    private void setObjectId(View view ){
        lottieAnimationView=view.findViewById(R.id.reportAnim);
        recyclerView = view.findViewById(R.id.report_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        textBack=view.findViewById(R.id.Report_text_background);
        animBack=view.findViewById(R.id.Report_text_background2);

    }
    private void init( View view){
        setObjectId(view);
    }
   private void getReports(){
    String Name;
        if(!MySharedPrefrence.getInstance(getContext()).getSide().equals("مدیر")) {
             Name = MySharedPrefrence.getInstance(getContext()).getName();
        }else {
            Name = MySharedPrefrence.getInstance(getContext()).getSelectedName();

        }
    Toast.makeText(getContext(),Name,Toast.LENGTH_LONG).show();
   RetrofitClient.getInstance(getContext()).getApi().getReport(Name)
           .enqueue(new Callback<ReportModel>() {
       @Override
       public void onResponse(@NonNull Call<ReportModel> call, @NonNull Response<ReportModel> response) {
           if (response.isSuccessful()) {
               if(response.body().getData().isEmpty())
               {
                   textBack.setVisibility(View.VISIBLE);
                   animBack.setVisibility(View.VISIBLE);
               }else recyclerView.setAdapter(new ReportAdapter(response.body().getData()));
           }
           else {
               assert response.errorBody() != null;
               Log.d("Error", "onResponse:"+response.code()+ response.errorBody().source());
           }
       }

       @Override
       public void onFailure(@NonNull Call<ReportModel> call, @NonNull Throwable t) {
           Log.d("Field", "onFailure: "+t.getMessage());
       }
   });
    lottieAnimationView.setVisibility(View.GONE);
}
 public ReportShowFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report_show, container, false);
        init(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getReports();
    }
}