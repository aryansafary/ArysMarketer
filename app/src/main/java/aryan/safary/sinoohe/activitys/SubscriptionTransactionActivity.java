package aryan.safary.sinoohe.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.google.android.material.tabs.TabLayout;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.NetworkChanged;
import aryan.safary.sinoohe.fragments.TransactionListFragment;
import aryan.safary.sinoohe.fragments.SubscriptionListFragment;

public class SubscriptionTransactionActivity extends AppCompatActivity {
private TabLayout tabLayout;
private void setObjectId(){
    tabLayout=findViewById(R.id.SubscriptionTransaction_form);
    getSupportFragmentManager().beginTransaction().
            add(R.id.SubscriptionTransactionContainer , new SubscriptionListFragment()).
           commit();
}
    private void init(){
    setObjectId();
    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()){
                case 0 :
                   getSupportFragmentManager().beginTransaction().
                           replace(R.id.SubscriptionTransactionContainer , new SubscriptionListFragment()).
                          commit();
                    break;
                case 1 :
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.SubscriptionTransactionContainer , new TransactionListFragment()).
                          commit();
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
    protected void onResume() {
        super.onResume();
        NetworkChanged.InternetConnection(SubscriptionTransactionActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_transaction);
        init();
    }
}