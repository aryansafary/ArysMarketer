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
import aryan.safary.sinoohe.classes.ProductNameAdapter;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.NameProductitem;
import aryan.safary.sinoohe.data.ProductNameModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public  class ProductSelectDialog extends AppCompatActivity {
    private RecyclerView product_recycle;
    private List<NameProductitem>list;

    private ProductNameAdapter productNameAdapter;
    private TextInputEditText search;

    private void  SetObjectId(){
        product_recycle=findViewById(R.id.newRequest_ProductList);
        product_recycle.setLayoutManager(new LinearLayoutManager(ProductSelectDialog.this));
        search=findViewById(R.id.productDialogSearch);
    }

    private void init(){
        SetObjectId();
        getProduct();
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
        ArrayList<NameProductitem> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (NameProductitem item :list ) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getProductName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            productNameAdapter.filterList(filteredlist);
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            productNameAdapter.filterList(filteredlist);
        }
    }

    private void getProduct(){
        RetrofitClient.getInstance(ProductSelectDialog.this).getApi().getProductName(MySharedPrefrence.getInstance(ProductSelectDialog.this).getUser()).enqueue(new Callback<ProductNameModel>() {
            @Override
            public void onResponse(@NonNull Call<ProductNameModel> call, @NonNull Response<ProductNameModel> response) {
                if(response.isSuccessful()){

                    assert response.body() != null;
                    list=response.body().getProductName();
                    productNameAdapter=new ProductNameAdapter(response.body().getProductName(),ProductSelectDialog.this);
                    product_recycle.setAdapter(productNameAdapter);
                }
                else { Toast.makeText(ProductSelectDialog.this, "Error", Toast.LENGTH_LONG).show();
                    Log.d("ShowError", "onResponse: "+response.message()+response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductNameModel> call, @NonNull Throwable t) {
                Toast.makeText(ProductSelectDialog.this,"Field",Toast.LENGTH_LONG).show();
                Log.d("Field", "onFailure: "+t.getMessage());
            }
        });
    }









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_selecet_dialog);
        GlobalClass.ProductSelect=this;
        init();
    }
}
