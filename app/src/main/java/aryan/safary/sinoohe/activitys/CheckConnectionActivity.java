package aryan.safary.sinoohe.activitys;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import aryan.safary.sinoohe.R;


public class CheckConnectionActivity extends AppCompatActivity {
private MaterialTextView text;
private MaterialButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_connection);
        text=findViewById(R.id.CheckInternetText);
        button=findViewById(R.id.CheckInternetButton);
        YoYo.with(Techniques.BounceInDown).duration(5000).playOn(text);
        YoYo.with(Techniques.RotateIn).playOn(button);
        button.setOnClickListener(v -> {
YoYo.with(Techniques.Wobble).duration(5000).playOn(v);
YoYo.with(Techniques.Bounce).duration(5000).playOn(text);
            ConnectivityManager cm = (ConnectivityManager) CheckConnectionActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (!isConnected) {
                Snackbar.make(v, "اتصال شما به اینترنت برقرار نیست", BaseTransientBottomBar.LENGTH_LONG).show();
            }else this.finish();

        });
    }
}