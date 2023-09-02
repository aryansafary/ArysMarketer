package aryan.safary.sinoohe.activitys;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.checkbox.MaterialCheckBox;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.MySharedPrefrence;


public class TransactionFilterActivity extends AppCompatActivity {
    private   MaterialCheckBox checkBox1,checkBox2,checkBox3;
    private void setObjectId(){
        checkBox1=findViewById(R.id.TransactionFilterCheckBox1);
        checkBox2=findViewById(R.id.TransactionFilterCheckBox2);
        checkBox3=findViewById(R.id.TransactionFilterCheckBox3);

    }
    private void init(){
        setObjectId();
        CheckBoxChecker();
    }



    private   void CheckBoxChecker(){

        switch (MySharedPrefrence.getInstance(TransactionFilterActivity.this).getTransActionFilter()){
            case 2:
                checkBox1.setChecked(false);
                checkBox2.setChecked(true);
                checkBox3.setChecked(false);
                break;
            case 3:
                checkBox1.setChecked(false);
                checkBox2.setChecked(false);
                checkBox3.setChecked(true);
                break;
            case 1:
            default:
                checkBox1.setChecked(true);
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                break;
        }
        checkBox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)   {
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                MySharedPrefrence.getInstance(TransactionFilterActivity.this).setTransActionFilter(1);
                this.finish();
            }
        });
        checkBox2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                checkBox1.setChecked(false);
                checkBox3.setChecked(false);
                MySharedPrefrence.getInstance(TransactionFilterActivity.this).setTransActionFilter(2);
                this.finish();
            }
        });
        checkBox3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                checkBox2.setChecked(false);
                checkBox1.setChecked(false);
                MySharedPrefrence.getInstance(TransactionFilterActivity.this).setTransActionFilter(3);
                this.finish();

            }
        });

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_filter);
        init();
    }
}