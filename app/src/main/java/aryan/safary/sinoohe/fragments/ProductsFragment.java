package aryan.safary.sinoohe.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import java.util.ArrayList;
import java.util.List;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.activitys.ProductFilterActivity;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.ProductsAdaptor;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.ProductsItem;
import aryan.safary.sinoohe.data.ProductsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsFragment extends Fragment {

    public ProductsFragment() {
    }
private MaterialTextView showBackgroundText;
    private LottieAnimationView BackGroundAnim;
    private RecyclerView recyclerView;
    private ProductsAdaptor productsAdaptor;
    private List<ProductsItem>list;
    private TextInputEditText search;
    private LottieAnimationView waiting1;
    private ConstraintLayout constraintLayout;
    //  private final List<ProductsItem>products=new ArrayList<>();


    //Write Me--------->
    private void getProducts() {

        RetrofitClient.getInstance(getContext()).getApi().getProducts(MySharedPrefrence.getInstance(getContext()).getUser()).enqueue(new Callback<ProductsModel>() {
            @Override
            public void onResponse(@NonNull Call<ProductsModel> call, @NonNull Response<ProductsModel> response) {
                if (response.isSuccessful()) {
                    waiting1.setVisibility(View.GONE);
                    assert response.body() != null;
                    if (response.body().getData()==null) {
                        showBackgroundText.setVisibility(View.VISIBLE);
                        BackGroundAnim.setVisibility(View.VISIBLE);
                    }
                    else
                        showBackgroundText.setVisibility(View.GONE);
                     list=response.body().getData();
                    productsAdaptor = new ProductsAdaptor(getContext(), response.body().getData());
                    recyclerView.setAdapter(productsAdaptor);


                } else {
                    Log.d("Error", "onResponse: " + response.code() + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductsModel> call, @NonNull Throwable t) {
                Log.d("Field", "onFailure: Can't show Product " + t.getMessage());


            }
        });

    }

    private void SetObjectId(View view) {
        showBackgroundText=view.findViewById(R.id.product_text_background);
        recyclerView = view.findViewById(R.id.Products_Recycler);
        constraintLayout = view.findViewById(R.id.product_filter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        waiting1 = view.findViewById(R.id.waiting1);
        waiting1.playAnimation();
        BackGroundAnim=view.findViewById(R.id.product_text_background2);
        search=view.findViewById(R.id.proSearch);

    }


    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<ProductsItem> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (ProductsItem item :list ) {
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
            productsAdaptor.filterList(filteredlist);
            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            productsAdaptor.filterList(filteredlist);
        }
    }


    private void init(View view) {
        SetObjectId(view);
        setSideOption();
        getProducts();
        constraintLayout.setOnClickListener(v -> startActivity(new Intent(getActivity(), ProductFilterActivity.class)));
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
//Write Me-------------->
//TopLearn_RetrofitLearning----------->
//// private void SiteTopLearnRetrofit(View view){
//     MyApi request_server = ApiClient.getApiClient(getContext().getString(R.string.BaseUrl)).create(MyApi.class);
//     request_server.getProduct().enqueue(new Callback<List<aryan.safary.sinoohe.TopLearn.Retrofit.products>>() {
//         @Override
//         public void onResponse(@NonNull Call<List<aryan.safary.sinoohe.TopLearn.Retrofit.products>> call, @NonNull Response<List<aryan.safary.sinoohe.TopLearn.Retrofit.products>> response) {
//             if( response.isSuccessful()  ){
//             //    products=response.body();
//                 adapter=new ProductAdapter(getContext(),products);
//                 recyclerView.setAdapter(adapter);
//
//             }
//       }

    //         @Override
//         public void onFailure(@NonNull Call<List<aryan.safary.sinoohe.TopLearn.Retrofit.products>> call, @NonNull Throwable t) {
//
//             Log.e("Error In Connection :",t.getMessage());
//         }
//     });
// }
    private void setSideOption() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        init(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getProducts();
    }
}