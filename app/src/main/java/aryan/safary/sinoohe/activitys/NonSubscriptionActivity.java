package aryan.safary.sinoohe.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.MySharedPrefrence;

public class NonSubscriptionActivity extends AppCompatActivity {
private void init(){
    findViewById(R.id.NoneSub_logout).setOnClickListener(v -> {
        MySharedPrefrence.getInstance(NonSubscriptionActivity.this).ClearSharedPrefrence();
        startActivity(new Intent(NonSubscriptionActivity.this,MainActivity.class));
        this.finish();

    });
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_subscription);
        init();
    }
}