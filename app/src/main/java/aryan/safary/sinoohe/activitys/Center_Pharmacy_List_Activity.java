package aryan.safary.sinoohe.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.CenterListPharmacyAdapter;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.NetworkChanged;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.CityModel;
import aryan.safary.sinoohe.data.NamePharmacyItem;
import aryan.safary.sinoohe.data.PharmacyNameModel;
import aryan.safary.sinoohe.data.ProvinceModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Center_Pharmacy_List_Activity extends AppCompatActivity {
private Spinner province , city , activityCenter;
private MaterialTextView textBack;
private LottieAnimationView animBack;
private TextInputEditText search;
//private SearchView searchView;
private ImageView new_center ;
private RecyclerView recyclerView;
private String P="" , C="" , A="";

private CenterListPharmacyAdapter adapter;

private List<NamePharmacyItem>list;

    private void setObjectId(){
        province=findViewById(R.id.centerListProvince);
        city=findViewById(R.id.centerListCity);
        activityCenter=findViewById(R.id.centerListActivity);
        search=findViewById(R.id.centerListSearch);
        //searchView=findViewById(R.id.centerListSearchForm);
        new_center=findViewById(R.id.centerListInsert);
        recyclerView=findViewById(R.id.centerListRecycler);
        MaterialToolbar toolbar = findViewById(R.id.centerListToolbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(Center_Pharmacy_List_Activity.this));
        setSupportActionBar(toolbar);
        textBack=findViewById(R.id.CenterList_text_background);
        animBack=findViewById(R.id.CenterList_text_background2);
    }

    private void init(){
    setObjectId();
    getCenterList();
    new_center.setOnClickListener(v ->{
        startActivity(new Intent(Center_Pharmacy_List_Activity.this,AddNewPharmacyActivity.class));
        this.finish();
    });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
        setSpinnerCountry();
    }



    private void getCenterList(){
        RetrofitClient.getInstance(Center_Pharmacy_List_Activity.this).getApi().getPharmacyName(
                MySharedPrefrence.getInstance(Center_Pharmacy_List_Activity.this).getUser()).
                enqueue(new Callback<PharmacyNameModel>() {
            @Override
            public void onResponse(@NonNull Call<PharmacyNameModel> call, @NonNull Response<PharmacyNameModel> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getPharmacyName()==null || response.body().getPharmacyName().isEmpty()){
                        textBack.setVisibility(View.VISIBLE);
                        animBack.setVisibility(View.VISIBLE);
                    }
                    else {
                        assert response.body() != null;
                        list = response.body().getPharmacyName();
                        adapter = new CenterListPharmacyAdapter(Center_Pharmacy_List_Activity.this, response.body().getPharmacyName());
                        recyclerView.setAdapter(adapter);
                    }
                } else Log.d("Error", "onResponse: "+response.message());
            }

            @Override
            public void onFailure(@NonNull Call<PharmacyNameModel> call, @NonNull Throwable t) {
                Log.d("Field", "onFailure: "+t.getMessage());
            }
        });
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<NamePharmacyItem> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (NamePharmacyItem item :list ) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            adapter.filterList(filteredlist);
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getCenterList();
        NetworkChanged.InternetConnection(Center_Pharmacy_List_Activity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_pharmacy_list);
        init();
    }









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

        province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                P=province.getSelectedItem().toString();
                setCity(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Center_Pharmacy_List_Activity.this,"لطفا استان و شهرستان خود را مشخص کنید",Toast.LENGTH_LONG).show();
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.pharmacyActivity_arrays, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        activityCenter.setAdapter(adapter);
        activityCenter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                A=adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Center_Pharmacy_List_Activity.this, "سمت خود را مشخص کنید", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void setCity(int pos) {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, setCitySpinner(pos));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // on below line we are setting adapter for spinner.
        city.setAdapter(adapter);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                C=city.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Center_Pharmacy_List_Activity.this,"لطفا استان و شهرستان خود را مشخص کنید",Toast.LENGTH_LONG).show();
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