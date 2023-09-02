package aryan.safary.sinoohe.activitys;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddNewPharmacyActivity extends AppCompatActivity {
    private TextInputEditText name , location , phone;

    private LottieAnimationView lottieAnimationView;
    private Spinner province , city , activity ;
private String province_text , city_text , activity_text,name_text ,location_text ,phone_text;

private void setObjectId(){
   name =findViewById(R.id.newCenterName);
 location =findViewById(R.id.newCenterLocation);
 phone =findViewById(R.id.newCenterPhone);
    province=findViewById(R.id.centerProvince);
    city=findViewById(R.id.centerCity);
    activity=findViewById(R.id.centerActivity);
    lottieAnimationView=findViewById(R.id.newPharmacyLottie);
     setSpinnerCountry();
}
private void init(){
    setObjectId();
    findViewById(R.id.centerAddBtn).setOnClickListener(v -> {
        name_text= Objects.requireNonNull(name.getText()).toString();
        location_text= Objects.requireNonNull(location.getText()).toString();
        phone_text= Objects.requireNonNull(phone.getText()).toString();
        if(name_text.isEmpty() && location_text.isEmpty() && phone_text.isEmpty() &&
           province_text.isEmpty() && city_text.isEmpty() && activity_text.isEmpty())
        {
            Toast.makeText(AddNewPharmacyActivity.this, " لطفا تمامی کادرها رو پر کنید و هیچ ستونی را خالی نگزارید", Toast.LENGTH_SHORT).show();
        }else if(city_text.equals("شهرستان") || province_text.equals("استان") ) Toast.makeText(AddNewPharmacyActivity.this, " لطفا استان و شهرستان مرکز را مشخص کنید", Toast.LENGTH_SHORT).show();
         else if(activity_text.equals("")) Toast.makeText(AddNewPharmacyActivity.this, "لطفا حوزه فعالیت مرکز رو مشخص کنید", Toast.LENGTH_SHORT).show();
        else InsertToCenter();
    });
    findViewById(R.id.centerCanclBtn).setOnClickListener(v -> {
        onBackPressed();
    startActivity(new Intent(AddNewPharmacyActivity.this,Center_Pharmacy_List_Activity.class));

    });
}

private void InsertToCenter(){
    RetrofitClient.getInstance(AddNewPharmacyActivity.this).getApi().InsertPharmacy(name_text,
            MySharedPrefrence.getInstance(AddNewPharmacyActivity.this).getUser(),
            city_text,province_text,location_text,phone_text,activity_text,
            MySharedPrefrence.getInstance(AddNewPharmacyActivity.this).getCompany()).enqueue(new Callback<JsonResponseModel>() {
        @Override
        public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
            if (response.isSuccessful()){
                new CountDownTimer(2500, 2500) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        lottieAnimationView.playAnimation();
                        lottieAnimationView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFinish() {
                        Toast.makeText(AddNewPharmacyActivity.this, "مرکز "+name_text+"با موفقیت به مراکز افزوده شد", Toast.LENGTH_LONG).show();
                        AddNewPharmacyActivity.this.finish();
                        startActivity(new Intent(AddNewPharmacyActivity.this, Center_Pharmacy_List_Activity.class));
                    }
                }.start();


            }
            else  if (response.code() == 406) {
                    Toast.makeText(AddNewPharmacyActivity.this, "خالی ارسال شد و إخیره نگردید", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("error", "onResponse: " + response.message());
                    Toast.makeText(AddNewPharmacyActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }


        @Override
        public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
            Log.d("Field", "onFailure: "+t.getMessage());
            Toast.makeText(AddNewPharmacyActivity.this, "Field", Toast.LENGTH_SHORT).show();
        }
    });
}


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_pharmacy);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        NetworkChanged.InternetConnection(AddNewPharmacyActivity.this);
    }

    private void setSpinnerCountry() {
        String[] Provinces = new String[getProvinceArray().size()];
        for (int i = 0; i < getProvinceArray().size(); i++) {
            Provinces[i] = getProvinceArray().get(i).getName();
        }
        // on below line we are initializing adapter for our spinner
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(AddNewPharmacyActivity.this, android.R.layout.simple_spinner_item, Provinces);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // on below line we are setting adapter for spinner.
        province.setAdapter(adapter);

        province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                province_text=province.getSelectedItem().toString();
                setCity(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddNewPharmacyActivity.this,"لطفا استان و شهرستان خود را مشخص کنید",Toast.LENGTH_LONG).show();
            }


        });

        setActivitySpinner();

    }
    private String[] setCitySpinner(int position) {
        List<CityModel> list = new ArrayList<>(getCityArray(position));
        String[] city = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            city[i] = list.get(i).getName();
        }


        return city;
    }
    private void setActivitySpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(AddNewPharmacyActivity.this,
                R.array.pharmacyActivity_arrays, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        activity.setAdapter(adapter);
        activity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                activity_text=adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddNewPharmacyActivity.this, "سمت خود را مشخص کنید", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void setCity(int pos) {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(AddNewPharmacyActivity.this, android.R.layout.simple_spinner_item, setCitySpinner(pos));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // on below line we are setting adapter for spinner.
        city.setAdapter(adapter);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city_text=city.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddNewPharmacyActivity.this,"لطفا استان و شهرستان مرکز را مشخص کنید",Toast.LENGTH_LONG).show();
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





















}