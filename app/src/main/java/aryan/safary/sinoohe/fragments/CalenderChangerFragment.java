package aryan.safary.sinoohe.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.JsonResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public  class CalenderChangerFragment extends Fragment {
private final String name ;
private MaterialButton submit ;
private LottieAnimationView CalenderAnim;
    private TextInputEditText sat_region;
    private TextInputEditText sun_region;
    private TextInputEditText mon_region;
    private TextInputEditText tus_region;
    private TextInputEditText wed_region;
    private TextInputEditText thu_region;
    private TextInputEditText sat_des;
    private TextInputEditText sun_des;
    private TextInputEditText mon_des;
    private TextInputEditText tus_des;
    private TextInputEditText wed_des;
    private TextInputEditText thu_des;
private void setObjectId(View view  ){
    sat_region=view.findViewById(R.id.CC_saturday_region);
    sun_region=view.findViewById(R.id.CC_sunday_region);
    mon_region=view.findViewById(R.id.CC_monday_region);
    tus_region=view.findViewById(R.id.CC_tuesday_region);
    wed_region=view.findViewById(R.id.CC_wednesday_region);
    thu_region=view.findViewById(R.id.CC_thursday_region);
    sat_des=view.findViewById(R.id.CC_saturday_description);
    sun_des=view.findViewById(R.id.CC_sunday_description);
    mon_des=view.findViewById(R.id.CC_monday_description);
    tus_des=view.findViewById(R.id.CC_tuesday_description);
    wed_des=view.findViewById(R.id.CC_wednesday_description);
    thu_des=view.findViewById(R.id.CC_thursday_description);
    submit=view.findViewById(R.id.CC_submit);
    CalenderAnim=view.findViewById(R.id.CalenderLottie);
}
private void init( View view){
    setObjectId(view);
    setSideOption();
    Toast.makeText(getContext(),name,Toast.LENGTH_LONG).show();
    submit.setOnClickListener(v -> {
        if(checkNotEmpty())
        SetCalculator();
        else Toast.makeText(getContext(), "لطفا تمامی فیلد ها را کامل پر کرده", Toast.LENGTH_LONG).show();
    });


}
private boolean checkNotEmpty(){
    return !Objects.requireNonNull(sat_region.getText()).toString().isEmpty() && !Objects.requireNonNull(sat_des.getText()).toString().isEmpty() &&
            !Objects.requireNonNull(sun_region.getText()).toString().isEmpty() && !Objects.requireNonNull(sun_des.getText()).toString().isEmpty() &&
            !Objects.requireNonNull(mon_region.getText()).toString().isEmpty() && !Objects.requireNonNull(mon_des.getText()).toString().isEmpty() &&
            !Objects.requireNonNull(tus_region.getText()).toString().isEmpty() && !Objects.requireNonNull(tus_des.getText()).toString().isEmpty() &&
            !Objects.requireNonNull(wed_region.getText()).toString().isEmpty() && !Objects.requireNonNull(wed_des.getText()).toString().isEmpty() &&
            !Objects.requireNonNull(thu_region.getText()).toString().isEmpty() && !Objects.requireNonNull(thu_des.getText()).toString().isEmpty();
}
private void SetCalculator(){
    RetrofitClient.getInstance(getContext()).getApi().setCalculator(
            name,
            Objects.requireNonNull(sat_region.getText()).toString(),
            Objects.requireNonNull(sat_des.getText()).toString()
            , Objects.requireNonNull(sun_region.getText()).toString()
            , Objects.requireNonNull(sun_des.getText()).toString()
            , Objects.requireNonNull(mon_region.getText()).toString()
            , Objects.requireNonNull(mon_des.getText()).toString()
            , Objects.requireNonNull(tus_region.getText()).toString()
            , Objects.requireNonNull(tus_des.getText()).toString()
            , Objects.requireNonNull(wed_region.getText()).toString()
            , Objects.requireNonNull(wed_des.getText()).toString()
            , Objects.requireNonNull(thu_region.getText()).toString()
            , Objects.requireNonNull(thu_des.getText()).toString()
    ).enqueue(new Callback<JsonResponseModel>() {
        @Override
        public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
            if(response.isSuccessful()) {

                new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        CalenderAnim.playAnimation();
                        CalenderAnim.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFinish() {
                        requireActivity().finish();
                    }
                }.start();

            }
            else {
                assert response.errorBody() != null;
                Log.d("error", "onResponse: "+ response.errorBody().source() +response.code());
            }
        }

        @Override
        public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
            Log.d("Field", "onFailure: "+t.getMessage());
        }
    });
}
public CalenderChangerFragment(String name ){
    this.name=name;}
private void setSideOption(){
    if(!MySharedPrefrence.getInstance(getContext()).getSide().equals("مدیر"))
     submit.setVisibility(View.GONE);
}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_calender_changer, container, false);
       init(view);
       return view;
    }
}