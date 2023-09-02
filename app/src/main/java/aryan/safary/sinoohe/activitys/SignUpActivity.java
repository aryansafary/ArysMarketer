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
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
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
import aryan.safary.sinoohe.data.UserItem;
import aryan.safary.sinoohe.data.UserModel;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;
import ir.hamsaa.persiandatepicker.util.PersianCalendarUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpActivity extends AppCompatActivity {
    private TextInputEditText name, username, password, confirm_password, email, phone, address, companyAddress, company;
    private MaterialTextView barthday;
    private MaterialButton login_tab, signup_btn;
    private LottieAnimationView anim_show;
    private String token = "";
    private Spinner city, cityCountry;
    private String citySelected, cityCountrySelected;
    private final CountDownTimer countDownTimer = new CountDownTimer(2000, 2000) {
        @Override
        public void onTick(long millisUntilFinished) {
            anim_show.setVisibility(View.VISIBLE);
            anim_show.playAnimation();
            setEnabled(false);
        }

        @Override
        public void onFinish() {
            setEnabled(true);
            anim_show.setVisibility(View.GONE);
            anim_show.cancelAnimation();
        }
    };

    private void setEnabled(boolean bool) {
        name.setEnabled(bool);
        username.setEnabled(bool);
        password.setEnabled(bool);
        confirm_password.setEnabled(bool);
        email.setEnabled(bool);
        phone.setEnabled(bool);
        company.setEnabled(bool);
        barthday.setEnabled(bool);
        address.setEnabled(bool);
        companyAddress.setEnabled(bool);
        signup_btn.setEnabled(bool);
        login_tab.setEnabled(bool);
    }

    private void setObjectId() {
        anim_show = findViewById(R.id.SignUp_Anim_Show);
        name = findViewById(R.id.SignUp_name);
        username = findViewById(R.id.SignUp_username);
        password = findViewById(R.id.SignUp_password);
        confirm_password = findViewById(R.id.SignUp_ConfirmPassword);
        email = findViewById(R.id.SignUp_email);
        phone = findViewById(R.id.SignUp_phone);
        address = findViewById(R.id.SignUp_address);
        companyAddress = findViewById(R.id.SignUp_CompanyAddress);
        company = findViewById(R.id.SignUp_company);
        barthday = findViewById(R.id.SignUpBarthday);
        signup_btn = findViewById(R.id.SignUp_BTN);
        login_tab = findViewById(R.id.SignUp_Login_tab);
        token = MySharedPrefrence.getInstance(SignUpActivity.this).getToken();
        city = findViewById(R.id.signUp_city);
        cityCountry = findViewById(R.id.signUp_cityCountry);

    }

    private void init() {
        setObjectId();
        setSpinnerCountry();
        findViewById(R.id.SignUpBarthdayForm).setOnClickListener(v -> {
            PersianDatePickerDialog picker = new PersianDatePickerDialog(this)
                    .setPositiveButtonString("ثبت")
//                    .setTodayButtonVisible(true)
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



        signup_btn.setOnClickListener(v -> {
            anim_show.setVisibility(View.VISIBLE);
            anim_show.setAnimationFromUrl("https://arysapp.com/lottie/loading_black.json");
            anim_show.playAnimation();
            RegisterUser();


        });
        login_tab.setOnClickListener(v -> new CountDownTimer(300, 300) {
            @Override
            public void onTick(long millisUntilFinished) {
                YoYo.with(Techniques.RollOut).duration(300).delay(100).playOn(findViewById(R.id.SignUpBackForm));
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                finish();
            }
        }.start());
    }

    private void RegisterUser() {
        if (NotEmpty())
            if (Objects.requireNonNull(phone.getText()).toString().length() != 11 || !phone.getText().toString().startsWith("09")) {
                Toast.makeText(this, "شماره تلفن وارد شده نادرست است باید با 09 شروع شود و یازده رقم باشد", Toast.LENGTH_LONG).show();
                anim_show.setVisibility(View.GONE);
            }else if (!CheckEmail()) {
                Toast.makeText(this, "ایمیل وارد شده صحیح نمیباشد", Toast.LENGTH_LONG).show();
                anim_show.setVisibility(View.GONE);
            }else if(citySelected.equals("شهرستان") || cityCountrySelected.equals("استان")) {
                Toast.makeText(this, "لطفا شهر و استان خود را مشخص کنید", Toast.LENGTH_LONG).show();
                anim_show.setVisibility(View.GONE);
            }else if(Objects.requireNonNull(password.getText()).length()<8 ||
                     Objects.requireNonNull(confirm_password.getText()).length()<8) {
                Toast.makeText(this, "رمز عبور شما نباید کمتر از 8 رقم باشد", Toast.LENGTH_LONG).show();
                anim_show.setVisibility(View.GONE);
            }
        else if (Objects.requireNonNull(username.getText()).length()<5){
                Toast.makeText(this, "نام کاربری شما نباید کمتر از 5 رقم باشد", Toast.LENGTH_LONG).show();
                anim_show.setVisibility(View.GONE);
            }

        else{
        RetrofitClient.getInstance(SignUpActivity.this).getApi().SignUp(
                        Objects.requireNonNull(name.getText()).toString(),
                        Objects.requireNonNull(username.getText()).toString(),
                        Objects.requireNonNull(password.getText()).toString(),
                        Objects.requireNonNull(email.getText()).toString(),
                        Objects.requireNonNull(phone.getText()).toString(),
                        token,
                        Objects.requireNonNull(company.getText()).toString(),
                        Objects.requireNonNull(companyAddress.getText()).toString(),
                        Objects.requireNonNull(barthday.getText()).toString(),
                        citySelected,
                        cityCountrySelected,
                        Objects.requireNonNull(address.getText()).toString()).enqueue(new Callback<JsonResponseModel>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "ثبت نام شما با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                            setData();
                        } else {
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(SignUpActivity.this, "این ایمیل قبلا ثبت شده لطفا ایمیل دیگری وارد کنید", Toast.LENGTH_LONG).show();
                                    anim_show.cancelAnimation();
                                    anim_show.setVisibility(View.GONE);
                                    break;
                                case 401:
                                    Toast.makeText(SignUpActivity.this, "این شماره تلفن قبلا ثبت شده لطفا شماره تلفن دیگری وارد کنید", Toast.LENGTH_LONG).show();
                                    anim_show.cancelAnimation();
                                    anim_show.setVisibility(View.GONE);
                                    break;
                                case 402:
                                    Toast.makeText(SignUpActivity.this, "این نام کاربری قبلا ثبت شده لطفا نام کاربری دیگری وارد کنید", Toast.LENGTH_LONG).show();
                                    anim_show.cancelAnimation();
                                    anim_show.setVisibility(View.GONE);
                                    break;
                                case 403:
                                case 404:
                                case 405:
                                    Toast.makeText(SignUpActivity.this, "این نام شرکت قبلا ثبت شده لطفا نام  دیگری وارد کنید", Toast.LENGTH_LONG).show();
                                    anim_show.cancelAnimation();
                                    anim_show.setVisibility(View.GONE);
                                    break;
                                case 500:
                                    Toast.makeText(SignUpActivity.this, "خطایی رخ داده است", Toast.LENGTH_LONG).show();
                                    assert response.errorBody() != null;
                                    Log.d("errorResponse", "onResponse: " + response.errorBody().source() + response.code());
                                    anim_show.cancelAnimation();
                                    anim_show.setVisibility(View.GONE);
                                    //anim_show.setAnimationFromUrl("https://arysapp.com/lottie/error.json");
                                    anim_show.setAnimationFromUrl("https://assets3.lottiefiles.com/packages/lf20_yw3nyrsv.json");
                                    countDownTimer.start();
                                    break;
                                case 406:
                                    Toast.makeText(SignUpActivity.this, "خطایی رخ داده است", Toast.LENGTH_LONG).show();
                                    assert response.errorBody() != null;
                                    Log.d("errorResponse", "onResponse: " + response.errorBody().source() + response.code());
                                    break;
                                default:
                                    Toast.makeText(SignUpActivity.this, "error", Toast.LENGTH_SHORT).show();
                                    assert response.errorBody() != null;
                                    Log.d("error", "onResponse: " + response.errorBody().source());
                                    break;

                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
                        Log.d("Field", "onFailure: " + t.getMessage());
                        anim_show.cancelAnimation();
                        anim_show.setVisibility(View.GONE);
                        //anim_show.setAnimationFromUrl("https://arysapp.com/lottie/connection_error.json");
                        anim_show.setAnimationFromUrl("https://assets4.lottiefiles.com/packages/lf20_XXdCLB/no_connection_05.json");
                        countDownTimer.start();
                    }
                });


            }
        else {
            Toast.makeText(SignUpActivity.this, "لطفا اطلاعات خود را تکمیل کنید", Toast.LENGTH_SHORT).show();
            anim_show.cancelAnimation();
            anim_show.setVisibility(View.GONE);
            anim_show.setAnimationFromUrl("https://arysapp.com/lottie/emapty.json");
            countDownTimer.start();
        }


    }

    private boolean NotEmpty() {
        if (!Objects.requireNonNull(name.getText()).toString().isEmpty())
            if (!Objects.requireNonNull(username.getText()).toString().isEmpty())
                if (!Objects.requireNonNull(password.getText()).toString().isEmpty())
                    if (!Objects.requireNonNull(confirm_password.getText()).toString().isEmpty())
                        if (!Objects.requireNonNull(email.getText()).toString().isEmpty())
                            if (!Objects.requireNonNull(phone.getText()).toString().isEmpty())
                                if (!Objects.requireNonNull(address.getText()).toString().isEmpty())
                                    if (!Objects.requireNonNull(companyAddress.getText()).toString().isEmpty())
                                        if (!Objects.requireNonNull(barthday.getText()).toString().isEmpty())
                                            if (!Objects.requireNonNull(company.getText()).toString().isEmpty())
                                                if (!token.isEmpty())
                                                    if (password.getText().toString().equals(confirm_password.getText().toString()))
                                                        return true;
                                                    else
                                                        Toast.makeText(SignUpActivity.this, "password not match", Toast.LENGTH_SHORT).show();
                                                else {
                                                    FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                                                        if(task.isSuccessful())
                                                        {
                                                            MySharedPrefrence.getInstance(SignUpActivity.this).setToken(task.getResult());
                                                        }

                                                    });
                                                    token=MySharedPrefrence.getInstance(SignUpActivity.this).getToken();

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


    private void setData() {
        RetrofitClient.getInstance(SignUpActivity.this).getApi().getUser(Objects.requireNonNull(username.getText()).toString()).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    UserItem userItem = response.body().getData().get(0);
                    MySharedPrefrence.getInstance(SignUpActivity.this).setUserId(userItem.getId());
                    MySharedPrefrence.getInstance(SignUpActivity.this).setName(Objects.requireNonNull(name.getText()).toString());
                    MySharedPrefrence.getInstance(SignUpActivity.this).setUser(username.getText().toString());
                    MySharedPrefrence.getInstance(SignUpActivity.this).setEmail(Objects.requireNonNull(email.getText()).toString());
                    MySharedPrefrence.getInstance(SignUpActivity.this).setPhone(Objects.requireNonNull(phone.getText()).toString());
                    MySharedPrefrence.getInstance(SignUpActivity.this).setCompany(Objects.requireNonNull(company.getText()).toString());
                    MySharedPrefrence.getInstance(SignUpActivity.this).setBarthday(Objects.requireNonNull(barthday.getText()).toString());
                    MySharedPrefrence.getInstance(SignUpActivity.this).setLocation(Objects.requireNonNull(address.getText()).toString());
                    MySharedPrefrence.getInstance(SignUpActivity.this).setCompanyAddress(Objects.requireNonNull(companyAddress.getText()).toString());
                    MySharedPrefrence.getInstance(SignUpActivity.this).setCity(citySelected);
                    MySharedPrefrence.getInstance(SignUpActivity.this).setProvince(cityCountrySelected);
                    MySharedPrefrence.getInstance(SignUpActivity.this).setSide(userItem.getSide());
                    MySharedPrefrence.getInstance(SignUpActivity.this).setToken(token);
                    MySharedPrefrence.getInstance(SignUpActivity.this).setIsLogin(true);
                    MySharedPrefrence.getInstance(SignUpActivity.this).setUserStatus("1");
                    openPage();
                    startActivity(new Intent(SignUpActivity.this, SizyPayActivity.class));
                    finish();
                } else {
                    assert response.errorBody() != null;
                    Log.d("errorGetData", "onResponse: " + response.errorBody().source() + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                Log.d("Field", "onFailure: " + t.getMessage());
            }
        });
    }

    private void openPage() {
        anim_show.cancelAnimation();
        anim_show.setVisibility(View.GONE);
        // anim_show.setAnimationFromUrl("https://arysapp.com/lottie/wellcome_true.json");
        anim_show.setAnimationFromUrl("https://assets8.lottiefiles.com/private_files/lf30_z1VFRK.json");
        countDownTimer.start();
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
        cityCountry.setAdapter(adapter);

        cityCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityCountrySelected = cityCountry.getSelectedItem().toString();
                setCity(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(SignUpActivity.this, "لطفا استان و شهرستان خود را مشخص کنید", Toast.LENGTH_LONG).show();
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
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                citySelected = city.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(SignUpActivity.this, "لطفا استان و شهرستان خود را مشخص کنید", Toast.LENGTH_LONG).show();
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        NetworkChanged.InternetConnection(SignUpActivity.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//                     intent.addCategory(Intent.CATEGORY_HOME);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
        finishAffinity(); // پنجره‌های باز شده در برنامه بسته می‌شود
        System.exit(0); // برنامه بسته می‌شود
        MySharedPrefrence.getInstance(SignUpActivity.this).ClearSharedPrefrence();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                MySharedPrefrence.getInstance(SignUpActivity.this).setToken(task.getResult());
            }

        });
    }
}