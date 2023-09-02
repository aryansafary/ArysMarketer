package aryan.safary.sinoohe.activitys;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.messaging.FirebaseMessaging;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.NetworkChanged;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.CityModel;
import aryan.safary.sinoohe.data.JsonResponseModel;
import aryan.safary.sinoohe.data.ProvinceModel;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;
import ir.hamsaa.persiandatepicker.util.PersianCalendarUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity { private TextInputEditText name, username, email, phone, address, companyAddress, company , side;
 private MaterialTextView barthday   ;
private MaterialButton changePass,Save;
 private Spinner province , city ;
 private String P="" , C="" , token ;
 private MaterialToolbar toolbar;
 private LottieAnimationView Anim;
 private final CountDownTimer  countDownTimer = new CountDownTimer(1000,2000) {
     @Override
     public void onTick(long millisUntilFinished) {
         Anim.setVisibility(View.VISIBLE);
         Anim.playAnimation();
         setEnabled(false);
     }

     @Override
     public void onFinish() {
         Anim.cancelAnimation();
         Anim.setVisibility(View.GONE);
     }
 };
    private void setEnabled(boolean bool) {
        name.setEnabled(bool);
        username.setEnabled(bool);
        email.setEnabled(bool);
        phone.setEnabled(bool);
        barthday.setEnabled(bool);
        address.setEnabled(bool);
        companyAddress.setEnabled(bool);
        Save.setEnabled(bool);
        changePass.setEnabled(false);
    }







private void UpdateData(){
    MySharedPrefrence.getInstance(EditProfileActivity.this).setName(Objects.requireNonNull(name.getText()).toString());
    MySharedPrefrence.getInstance(EditProfileActivity.this).setUser(Objects.requireNonNull(username.getText()).toString());
    MySharedPrefrence.getInstance(EditProfileActivity.this).setEmail(Objects.requireNonNull(email.getText()).toString());
    MySharedPrefrence.getInstance(EditProfileActivity.this).setPhone(Objects.requireNonNull(phone.getText()).toString());
    MySharedPrefrence.getInstance(EditProfileActivity.this).setProvince(P);
    MySharedPrefrence.getInstance(EditProfileActivity.this).setCity(C);
    MySharedPrefrence.getInstance(EditProfileActivity.this).setLocation(Objects.requireNonNull(address.getText()).toString());
    MySharedPrefrence.getInstance(EditProfileActivity.this).setBarthday(Objects.requireNonNull(barthday.getText()).toString());
}


    @SuppressLint("SetTextI18n")
    private void setObjectId() {
        toolbar=findViewById(R.id.EditProfileToolbar);
        Anim=findViewById(R.id.EditProfileAnim);
        name = findViewById(R.id.EditProfileName);
        username = findViewById(R.id.EditProfileUsername);
        email = findViewById(R.id.EditProfileEmail);
        phone = findViewById(R.id.EditProfilePhone);
        address = findViewById(R.id.EditProfileAddress);
        companyAddress = findViewById(R.id.EditProfileCompanyAddress);
        company = findViewById(R.id.EditProfileCompany);
        barthday = findViewById(R.id.EditProfileBarthday);
        side=findViewById(R.id.EditProfileSide);
        changePass=findViewById(R.id.EditProfileChangePassword);
        Save=findViewById(R.id.EditProfileSave);
        province=findViewById(R.id.EditProfileProvince);
        city=findViewById(R.id.EditProfileCity);
        token= MySharedPrefrence.getInstance(this).getToken();
        name.setText(MySharedPrefrence.getInstance(this).getName());
        username.setText(MySharedPrefrence.getInstance(this).getUser());
        email.setText(MySharedPrefrence.getInstance(this).getEmail());
        phone.setText(MySharedPrefrence.getInstance(this).getPhone());
        address.setText(MySharedPrefrence.getInstance(this).getLocation());
        companyAddress.setText(MySharedPrefrence.getInstance(this).getCompanyAddress());
        company.setText(MySharedPrefrence.getInstance(this).getCompany());
        side.setText(MySharedPrefrence.getInstance(this).getSide());
        side.setEnabled(false);
        companyAddress.setEnabled(false);
        company.setEnabled(false);
        barthday.setText(MySharedPrefrence.getInstance(this).getBarthday());
        P=MySharedPrefrence.getInstance(this).getProvince();
        C=MySharedPrefrence.getInstance(this).getCity();
        if(barthday.getText().toString().isEmpty()) barthday.setText(getString(R.string.Barthday_farsi));


        }
//        if(cityText.getText().toString().isEmpty() || provinceText.getText().toString().isEmpty()){
//            findViewById(R.id.EditProfileLocText).setVisibility(View.GONE);
//            cityText.setVisibility(View.GONE);
//            provinceText.setVisibility(View.GONE);
//            findViewById(R.id.EditProfileSpinner).setVisibility(View.VISIBLE);
//            city.setVisibility(View.VISIBLE);
//            provinceText.setVisibility(View.VISIBLE);
//        }else {
//            if(findViewById(R.id.EditProfileLocText).getVisibility()==View.GONE){
//                findViewById(R.id.EditProfileSpinner).setVisibility(View.GONE);
//                city.setVisibility(View.GONE);
//                province.setVisibility(View.GONE);
//                findViewById(R.id.EditProfileLocText).setVisibility(View.VISIBLE);
//                cityText.setVisibility(View.VISIBLE);
//                provinceText.setVisibility(View.VISIBLE);
//                provinceText.setText("استان :"+P);
//                cityText.setText("شهرستان:"+C);
            //}

        //}




    private void init() {
       setObjectId();
       setSpinnerCountry();
       setSupportActionBar(toolbar);
       changePass.setOnClickListener(v-> {
           startActivity(new Intent(EditProfileActivity.this,ChangePasswordActivity.class));
           this.finish();
    });
       findViewById(R.id.EditProfileBarthdayForm).setOnClickListener(v -> {
           PersianDatePickerDialog picker = new PersianDatePickerDialog(this)
                   .setPositiveButtonString("ثبت")
//                   .setTodayButtonVisible(true)
                   .setMinYear(1300)
                  .setPickerBackgroundColor(getResources().getColor(R.color.picker))
                  .setBackgroundColor(getResources().getColor(R.color.picker))
                   .setActionTextColor(Color.BLUE)
                   .setActionTextSize(25)
                   .setAllButtonsTextSize(20)
                   .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                   .setMaxMonth(PersianDatePickerDialog.THIS_MONTH)
                   .setMaxDay(PersianDatePickerDialog.THIS_DAY)
                   .setInitDate(1370, 1, 1)
                  // .setActionTextColor(Color.GRAY)
                   .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                   .setShowInBottomSheet(true)
                   .setListener(new PersianPickerListener() {
                       @SuppressLint("SetTextI18n")
                       @Override
                       public void onDateSelected(@NotNull PersianPickerDate persianPickerDate) {
                           Log.d("error1", "onDateSelected: " + persianPickerDate.getTimestamp());//675930448000
                           Log.d("error2", "onDateSelected: " + persianPickerDate.getGregorianDate());//Mon Jun 03 10:57:28 GMT+04:30 1991
                           Log.d("error3", "onDateSelected: " + persianPickerDate.getPersianLongDate());// دوشنبه  13  خرداد  1370
                           Log.d("error4", "onDateSelected: " + persianPickerDate.getPersianMonthName());//خرداد
                           Log.d("error5", "onDateSelected: " + PersianCalendarUtils.isPersianLeapYear(persianPickerDate.getPersianYear()));//true
                           barthday.setText(persianPickerDate.getPersianYear() + "/" + persianPickerDate.getPersianMonth() + "/" + persianPickerDate.getPersianDay());
                       }

                       @Override
                       public void onDismissed() {

                       }
                   });

           picker.show();




       });





       Save.setOnClickListener(v-> {
        if(NotEmpty())
            if (Objects.requireNonNull(phone.getText()).toString().length() != 11 || !phone.getText().toString().startsWith("09"))
                Toast.makeText(this, "شماره تلفن وارد شده نادرست است باید با 09 شروع شود و یازده رقم باشد", Toast.LENGTH_LONG).show();
            else if (!CheckEmail())
                Toast.makeText(this, "ایمیل وارد شده صحیح نمیباشد", Toast.LENGTH_LONG).show();
            else if(C.equals("شهرستان") || P.equals("استان"))
                Toast.makeText(this, "لطفا شهر و استان خود را مشخص کنید", Toast.LENGTH_LONG).show();
             else if (Objects.requireNonNull(username.getText()).length()<5)
                Toast.makeText(this, "نام کاربری شما نباید کمتر از 5 رقم باشد", Toast.LENGTH_LONG).show();
          else  EditProfile();
        else{
            Toast.makeText(EditProfileActivity.this, "لطفا اطلاعات خود را کامل وارد کنید" , Toast.LENGTH_LONG).show();
            Anim.setAnimation(R.raw.emapty);
            countDownTimer.start();
        }

           });

    }








private void EditProfile(){
    RetrofitClient.getInstance(EditProfileActivity.this).getApi().EditProfile(
            MySharedPrefrence.getInstance(EditProfileActivity.this).getUserId(),
            Objects.requireNonNull(name.getText()).toString(),
            Objects.requireNonNull(username.getText()).toString(),
            C,P, Objects.requireNonNull(address.getText()).toString(),
            Objects.requireNonNull(phone.getText()).toString(),
            Objects.requireNonNull(email.getText()).toString(),
            Objects.requireNonNull(barthday.getText()).toString()
    ).enqueue(new Callback<JsonResponseModel>() {
        @Override
        public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
           if(response.isSuccessful()) {
               Toast.makeText(EditProfileActivity.this,"ویرایش اطلاعات با موفقیت انجام شد",Toast.LENGTH_LONG).show();
               Anim.setAnimation(R.raw.update);
               UpdateData();
           new CountDownTimer(1000,2000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Anim.playAnimation();
                    Anim.setVisibility(View.VISIBLE);
                    setEnabled(false);
                }

                @Override
                public void onFinish() {
                    Anim.cancelAnimation();
                    Anim.setVisibility(View.GONE);
                    setEnabled(true);
                    startActivity(new Intent(EditProfileActivity.this,HomeActivity.class));
                    finish();
                }
            }.start();
           }else {
               Log.d("Error", "onResponse: " + response.message());
               Toast.makeText(EditProfileActivity.this,"یک خطاوجود دارد ",Toast.LENGTH_LONG).show();
           }
        }

        @Override
        public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
            Log.d("Field", "onFailure: "+t.getMessage());
        }
    });
}


    private boolean NotEmpty() {
        if (!Objects.requireNonNull(name.getText()).toString().isEmpty())
            if (!Objects.requireNonNull(username.getText()).toString().isEmpty())
                if (!Objects.requireNonNull(side.getText()).toString().isEmpty())
                    if (!Objects.requireNonNull(email.getText()).toString().isEmpty())
                        if (!Objects.requireNonNull(phone.getText()).toString().isEmpty())
                            if (!Objects.requireNonNull(address.getText()).toString().isEmpty())
                                    if (!Objects.requireNonNull(barthday.getText()).toString().isEmpty())
                                        if (!Objects.requireNonNull(company.getText()).toString().isEmpty())
                                            if (!token.isEmpty())
                                                        return true;
                                                else {
                                                    FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                                                        if (task.isSuccessful()) {
                                                            // MySharedPrefrence.getInstance(MainActivity.this).setToken(task.getResult());
                                                            token = task.getResult();
                                                            // login();
                                                        } else {
                                                            Log.d("token", "NotEmpty: " + task.getResult());
                                                            NotEmpty();
                                                        }

                                                    });


                                                }

        return false;
    }

    private boolean CheckEmail() {
        boolean check = false;
        String e = Objects.requireNonNull(email.getText()).toString();
        int indexAd = e.indexOf("@");
        String start="00";
        String end="00";
        if(indexAd==-1) Toast.makeText(this, "ایمیل وارد شده یک متن است و نادرست است", Toast.LENGTH_LONG).show();
        else{
            start=e.substring(0,indexAd).toLowerCase();
            end=e.substring(indexAd).toLowerCase();
        }
        if (start.startsWith(getString(R.string.number)))
            Toast.makeText(this, "هیچ ایمیلی با عدد شروع نمیشود", Toast.LENGTH_LONG).show();
        else if     (end.equals("@gmail.com") ||
                end.equals("@yahoo.com") ||
                end.equals("@hotmail.com") ||
                end.equals("@email.com"))
            check = true;


        return check;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        NetworkChanged.InternetConnection(EditProfileActivity.this);
    }

    //Set Spinner For Location
 private void setSpinnerCountry() {
     String[] Provinces = new String[getProvinceArray().size()];
     for (int i = 0; i < getProvinceArray().size(); i++) {
         Provinces[i] = getProvinceArray().get(i).getName();
     }
     // on below line we are initializing adapter for our spinner
     ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Provinces);
     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     // on below line we are setting adapter for spinner.
     province.setAdapter(adapter);
for(int i=0;i<Provinces.length;i++){
    if(P.equals(Provinces[i])) province.setSelection(i);
}
     province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             P=province.getSelectedItem().toString();
             setCity(position);
         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {
             Toast.makeText(EditProfileActivity.this,"لطفا استان و شهرستان خود را مشخص کنید",Toast.LENGTH_LONG).show();
         }


     });

 }
    String[] setCitySpinner(int position) {
        List<CityModel> list = new ArrayList<>(getCityArray(position));
        String[] city = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            city[i] = list.get(i).getName();
        }


        return city;
    }
    private void setCity(int pos) {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, setCitySpinner(pos));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // on below line we are setting adapter for spinner.
        city.setAdapter(adapter);
        for(int i=0;i<setCitySpinner(province.getSelectedItemPosition()).length;i++){
            if(C.equals(setCitySpinner(province.getSelectedItemPosition())[i]   ))city.setSelection(i);
        }
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                C=city.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(EditProfileActivity.this,"لطفا استان و شهرستان خود را مشخص کنید",Toast.LENGTH_LONG).show();
            }
        });
    }
    private String LoadJson(String FileName) {
        String res = "";
        try {
            InputStream inputStream = getAssets().open(FileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            res = new String(buffer, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    private ArrayList<CityModel> getCityArray(int position) {
        String FileName = "city.json";
        ArrayList<CityModel> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(LoadJson(FileName));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                CityModel cityModel = new CityModel();
                if (jsonObject.getInt("province_id") == position) {
                    cityModel.setId(jsonObject.getInt("id"));
                    cityModel.setName(jsonObject.getString("name"));
                    cityModel.setSlug(jsonObject.getString("slug"));
                    cityModel.setProvince_id(jsonObject.getInt("province_id"));

                    list.add(cityModel);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    private ArrayList<ProvinceModel> getProvinceArray() {
        String FileName = "provinces.json";
        ArrayList<ProvinceModel> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(LoadJson(FileName));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ProvinceModel provinceModel = new ProvinceModel();
                provinceModel.setId(jsonObject.getInt("id"));
                provinceModel.setName(jsonObject.getString("name"));
                provinceModel.setSlug(jsonObject.getString("slug"));
                list.add(provinceModel);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditProfileActivity.this,HomeActivity.class));
    }
}