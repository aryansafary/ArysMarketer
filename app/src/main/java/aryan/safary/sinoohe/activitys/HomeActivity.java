package aryan.safary.sinoohe.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import aryan.safary.sinoohe.classes.NetworkChanged;
import aryan.safary.sinoohe.services.SubscriptionService;
import aryan.safary.sinoohe.classes.CustomBottomNavigationView;
import aryan.safary.sinoohe.dialogs.ShowOptionDialog;
import aryan.safary.sinoohe.fragments.StoreFragment;
import aryan.safary.sinoohe.fragments.FreightFragment;
import aryan.safary.sinoohe.fragments.RequestFragment;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.fragments.ProductsFragment;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.fragments.ReportFragment;


public class HomeActivity extends AppCompatActivity {

    private CustomBottomNavigationView curvedBottomNavigationView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialTextView name;
    //______________________________________________________

    private void SetObjectId() {
        MaterialToolbar toolbar;
        curvedBottomNavigationView = findViewById(R.id.Modir_BottomNav);
        curvedBottomNavigationView.inflateMenu(R.menu.buttom_navigation_modier_menu);
        navigationView = findViewById(R.id.modir_NavigationView);
        drawerLayout = findViewById(R.id.modir_DrawableLayout);
        toolbar = findViewById(R.id.toolbar);
        View header = navigationView.getHeaderView(0);
        name = header.findViewById(R.id.HomeNavigationName);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("آریس مارکت");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);


    }

    @SuppressLint("NonConstantResourceId")
    private void init() {
        SetObjectId();
        curvedBottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.modir_Products:
                    Toast.makeText(HomeActivity.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.Modier_Container, new ProductsFragment()).
                            commit();
                    return true;
                case R.id.modir_Report:
                    Toast.makeText(HomeActivity.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                    if (MySharedPrefrence.getInstance(HomeActivity.this).getSide().equals("مدیر"))
                        getSupportFragmentManager().beginTransaction().replace(R.id.Modier_Container, new ReportFragment(false)).commit();
                    else startActivity(new Intent(HomeActivity.this, MainReportActivity.class));
                    return true;
                case R.id.modir_Store:
                    Toast.makeText(HomeActivity.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.Modier_Container, new StoreFragment(HomeActivity.this)).commit();
                    return true;
                case R.id.modir_Freight:
                    Toast.makeText(HomeActivity.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.Modier_Container, new FreightFragment(HomeActivity.this)).commit();
                    return true;
                case R.id.modir_Requests:
                    Toast.makeText(HomeActivity.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.Modier_Container, new RequestFragment()).commit();
                    return true;


            }
            return false;
        });
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.NewUser:
                    Toast.makeText(HomeActivity.this, "New User", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(HomeActivity.this, NewUserActivity.class));
                    break;
                case R.id.centerList:
                    Toast.makeText(HomeActivity.this, "لیست مراکز", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(HomeActivity.this, Center_Pharmacy_List_Activity.class));
                    break;
                case R.id.UserList:
                    Toast.makeText(HomeActivity.this, "لیست کارمندان", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(HomeActivity.this, UserListActivity.class));
                    break;
                case R.id.set_data_user:
                    Toast.makeText(HomeActivity.this, "Set Data Calender", Toast.LENGTH_LONG).show();
                    if (MySharedPrefrence.getInstance(HomeActivity.this).getSide().equals("مدیر"))
                        getSupportFragmentManager().beginTransaction().replace(R.id.Modier_Container, new ReportFragment(true)).commit();
                    else
                        startActivity(new Intent(HomeActivity.this, SetClenderActivity.class));
                    break;

                case R.id.nav_edit:
                    Toast.makeText(HomeActivity.this, "بخش ویرایش اطلاعات", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(HomeActivity.this,EditProfileActivity.class));
                    finish();

                    break;
                case R.id.nav_setting:
                    Toast.makeText(HomeActivity.this, "Setting", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(HomeActivity.this, MapActivity.class));
                    break;
                case R.id.nav_payment:
                    Toast.makeText(HomeActivity.this, "payment", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(HomeActivity.this, SubscriptionTransactionActivity.class));
                    break;
                case R.id.nav_logout:
                    MySharedPrefrence.getInstance(HomeActivity.this).ClearSharedPrefrence();
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                        if(task.isSuccessful())
                        {
                            MySharedPrefrence.getInstance(HomeActivity.this).setToken(task.getResult());
                        }

                    });


                    startActivity(intent);
                    this.finish();
                    break;
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
        SetSideOption();
        findViewById(R.id.Modir_Btn).setOnClickListener(v -> getSupportFragmentManager().beginTransaction().
                add(R.id.Home_Container_Center, new ShowOptionDialog()).
                addToBackStack(null).commit());


    }


    private void SetSideOption() {
        String nameUser = MySharedPrefrence.getInstance(HomeActivity.this).getName();
        String address = MySharedPrefrence.getInstance(HomeActivity.this).getLocation();
        String phone = MySharedPrefrence.getInstance(HomeActivity.this).getPhone();
        String email = MySharedPrefrence.getInstance(HomeActivity.this).getEmail();
        String barthday = MySharedPrefrence.getInstance(HomeActivity.this).getBarthday();
        if (nameUser.isEmpty() || address.isEmpty() || phone.isEmpty() || email.isEmpty() || barthday.isEmpty()) {
            startActivity(new Intent(HomeActivity.this, EditProfileActivity.class));
            curvedBottomNavigationView.setVisibility(View.GONE);
            navigationView.setVisibility(View.GONE);
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.Modier_Container, new ProductsFragment()).commit();
            if (!MySharedPrefrence.getInstance(HomeActivity.this).getName().isEmpty())
                name.setText(MySharedPrefrence.getInstance(HomeActivity.this).getName());
            curvedBottomNavigationView.setVisibility(View.VISIBLE);
            navigationView.setVisibility(View.VISIBLE);
        }
        switch (MySharedPrefrence.getInstance(HomeActivity.this).getSide()) {
            case "مدیر":
                curvedBottomNavigationView.getMenu().findItem(R.id.modir_Freight).setVisible(false);
                break;
            case "باربری":
                navigationView.getMenu().findItem(R.id.UserList).setVisible(false);
                navigationView.getMenu().findItem(R.id.centerList).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_payment).setVisible(false);
                navigationView.getMenu().findItem(R.id.NewUser).setVisible(false);
                curvedBottomNavigationView.getMenu().findItem(R.id.modir_Store).setVisible(false);
                curvedBottomNavigationView.getMenu().findItem(R.id.modir_Requests).setVisible(false);
                break;
            case "انباردار":
                navigationView.getMenu().findItem(R.id.UserList).setVisible(false);
                navigationView.getMenu().findItem(R.id.centerList).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_payment).setVisible(false);
                navigationView.getMenu().findItem(R.id.NewUser).setVisible(false);
                curvedBottomNavigationView.getMenu().findItem(R.id.modir_Freight).setVisible(false);
                curvedBottomNavigationView.getMenu().findItem(R.id.modir_Requests).setVisible(false);
                break;
            case "بازاریاب":
                navigationView.getMenu().findItem(R.id.UserList).setVisible(false);
                navigationView.getMenu().findItem(R.id.centerList).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_payment).setVisible(false);
                navigationView.getMenu().findItem(R.id.NewUser).setVisible(false);
                curvedBottomNavigationView.getMenu().findItem(R.id.modir_Freight).setVisible(false);
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }


    @Override
    protected void onResume() {
        super.onResume();

        NetworkChanged.InternetConnection(HomeActivity.this);


        startService(new Intent(HomeActivity.this, SubscriptionService.class));
        if (!MySharedPrefrence.getInstance(HomeActivity.this).getName().isEmpty())
            name.setText(MySharedPrefrence.getInstance(HomeActivity.this).getName());





    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }


}