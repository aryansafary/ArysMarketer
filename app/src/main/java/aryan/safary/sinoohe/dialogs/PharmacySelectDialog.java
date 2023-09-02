package aryan.safary.sinoohe.dialogs;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.GlobalClass;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.PharmacyNameAdapter;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.NamePharmacyItem;
import aryan.safary.sinoohe.data.PharmacyNameModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PharmacySelectDialog extends AppCompatActivity {

    private RecyclerView pharmacy_recycle;
    private PharmacyNameAdapter pharmacyNameAdapter;
    private List<NamePharmacyItem>list;
    private TextInputEditText search;



    private void SetObjectId( ){
        pharmacy_recycle = findViewById(R.id.newRequest_pharmacyList);
        pharmacy_recycle.setLayoutManager(new LinearLayoutManager(PharmacySelectDialog.this));
        search=findViewById(R.id.pharmacyDialogSearch);
    }


    private  void init( ){
        SetObjectId();
        getPharmacy();
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

    }



    private void filter(String text)  {
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
            pharmacyNameAdapter.filterList(filteredlist);
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            pharmacyNameAdapter.filterList(filteredlist);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getPharmacy();
    }

    private void  getPharmacy(){
        RetrofitClient.getInstance(PharmacySelectDialog.this).getApi().getPharmacyName(
         MySharedPrefrence.getInstance(PharmacySelectDialog.this).getUser()
                ).enqueue(new Callback<PharmacyNameModel>() {
            @Override
            public void onResponse(@NonNull Call<PharmacyNameModel> call, @NonNull Response<PharmacyNameModel> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    list=response.body().getPharmacyName();
                    pharmacyNameAdapter=new PharmacyNameAdapter(response.body().getPharmacyName());
                    pharmacy_recycle.setAdapter(pharmacyNameAdapter);
                }
                else {
                    Toast.makeText(PharmacySelectDialog.this, "Error", Toast.LENGTH_LONG).show();
                    Log.d("ShowError", "onResponse: "+response.message()+response.code());
                }

            }

            @Override
            public void onFailure(@NonNull Call<PharmacyNameModel> call, @NonNull Throwable t) {
                Toast.makeText(PharmacySelectDialog.this,"Field",Toast.LENGTH_LONG).show();
                Log.d("Field", t.getMessage());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pharmacy_select_dialog);
        GlobalClass.PharmacySelect=this;
        init();
    }
}
