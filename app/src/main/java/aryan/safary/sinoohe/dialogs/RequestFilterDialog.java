package aryan.safary.sinoohe.dialogs;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.checkbox.MaterialCheckBox;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.MySharedPrefrence;


public class RequestFilterDialog  extends AppCompatActivity {
    private MaterialCheckBox checkBox1 , checkBox2 , checkBox3 , checkBox4;
   private   void CheckBoxChecker(){

 switch (MySharedPrefrence.getInstance(RequestFilterDialog.this).getRequestFilter()){
     case 2:
         checkBox1.setChecked(false);
         checkBox2.setChecked(true);
         checkBox3.setChecked(false);
         checkBox4.setChecked(false);
         break;
     case 3:
         checkBox1.setChecked(false);
         checkBox2.setChecked(false);
         checkBox3.setChecked(true);
         checkBox4.setChecked(false);
         break;
     case 4:
         checkBox1.setChecked(false);
         checkBox2.setChecked(false);
         checkBox3.setChecked(false);
         checkBox4.setChecked(true);
         break;
     case 1:
     default:
         checkBox1.setChecked(true);
         checkBox2.setChecked(false);
         checkBox3.setChecked(false);
         checkBox4.setChecked(false);
         break;
 }
checkBox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
 if(isChecked)   {
     checkBox2.setChecked(false);
     checkBox3.setChecked(false);
     checkBox4.setChecked(false);
     MySharedPrefrence.getInstance(RequestFilterDialog.this).setRequestFilter(1);
     this.finish();
 }
});
checkBox2.setOnCheckedChangeListener((buttonView, isChecked) -> {
    if(isChecked){
        checkBox1.setChecked(false);
        checkBox3.setChecked(false);
        checkBox4.setChecked(false);
        MySharedPrefrence.getInstance(RequestFilterDialog.this).setRequestFilter(2);
        this.finish();
    }
});
checkBox3.setOnCheckedChangeListener((buttonView, isChecked) -> {
    if(isChecked){
        checkBox2.setChecked(false);
        checkBox1.setChecked(false);
        checkBox4.setChecked(false);
        MySharedPrefrence.getInstance(RequestFilterDialog.this).setRequestFilter(3);
        this.finish();

    }
});
checkBox4.setOnCheckedChangeListener((buttonView, isChecked) -> {
 if(isChecked){
     checkBox1.setChecked(false);
     checkBox2.setChecked(false);
     checkBox3.setChecked(false);
     MySharedPrefrence.getInstance(RequestFilterDialog.this).setRequestFilter(4);
     this.finish();
 }
});
   }




    private void setObjectId(){
        checkBox1=findViewById(R.id.request_filter_check_1);
        checkBox2=findViewById(R.id.request_filter_check_2);
        checkBox3=findViewById(R.id.request_filter_check_3);
        checkBox4=findViewById(R.id.request_filter_check_4);



    }

    private void init(){
        setObjectId();
    CheckBoxChecker();

   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_filter_dialog);
        init();

    }
}
