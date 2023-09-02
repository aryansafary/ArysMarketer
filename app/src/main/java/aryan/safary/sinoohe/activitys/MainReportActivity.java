package aryan.safary.sinoohe.activitys;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.tabs.TabLayout;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.NetworkChanged;
import aryan.safary.sinoohe.fragments.ReportShowFragment;
import aryan.safary.sinoohe.fragments.DataLocationFragment;


public class MainReportActivity extends AppCompatActivity {
    TabLayout tabLayout;
    private void setObjectId( ) {
        tabLayout = findViewById(R.id.ReportTab);
getSupportFragmentManager().beginTransaction().add(R.id.ReportContainer,new DataLocationFragment()).commit();

    }

    private void init( ) {
        setObjectId();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                       getSupportFragmentManager().beginTransaction().replace(R.id.ReportContainer, new DataLocationFragment()).commit();
                        Toast.makeText(MainReportActivity.this, "Map", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                       getSupportFragmentManager().beginTransaction().replace(R.id.ReportContainer, new ReportShowFragment()).commit();
                        Toast.makeText(MainReportActivity.this, "report", Toast.LENGTH_LONG).show();
                        break;


                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_report);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        NetworkChanged.InternetConnection(MainReportActivity.this);
    }
}