package aryan.safary.sinoohe.activitys;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textview.MaterialTextView;
import aryan.safary.sinoohe.R;



public class SpeleshScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelesh_screen);
        MaterialTextView nameApp = findViewById(R.id.spleshNameApp);
       // nameApp.setText(getResources().getString(R.string.app_name));
        Animation move_anim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fed_in);
       move_anim.setDuration(4000);
       YoYo.with(Techniques.BounceIn).duration(4000).playOn(findViewById(R.id.spleshNameApp));
       new CountDownTimer (5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent =new Intent(SpeleshScreenActivity.this,MainActivity.class);
 startActivity(intent);
 SpeleshScreenActivity.this.finish();

            }
        }.start();


    }


















}