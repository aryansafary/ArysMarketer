package aryan.safary.sinoohe.classes;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import aryan.safary.sinoohe.activitys.EditProductActivity;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.data.JsonResponseModel;
import aryan.safary.sinoohe.data.ProductsItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsAdaptor extends RecyclerView.Adapter<ProductsAdaptor.ViewHolder> {
    private final Context context;
    private List<ProductsItem> list;

    public ProductsAdaptor(Context context, List<ProductsItem> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Fresco.initialize(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterList(ArrayList<ProductsItem> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        list = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }









    @SuppressLint({"SetTextI18n", "NonConstantResourceId", "SuspiciousIndentation"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.brand.setText(list.get(position).getBrand());
        holder.id.setText(list.get(position).getId());
        holder.description.setText(list.get(position).getDescription());
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        holder.price.setText(decimalFormat.format(Integer.parseInt(list.get(position).getPrice())) + "   تومان");
        // Picasso.get().load(       Uri.parse(context.getString(R.string.ImageUrl,list.get(position).getImage()))        ).resize(300,300).into(holder.pic);
        holder.pic.setImageURI(Uri.parse(context.getString(R.string.ImageUrl, list.get(position).getImage())));
        if(MySharedPrefrence.getInstance(context).getSide().equals("مدیر")) holder.ItemClick.setOnClickListener(v -> {
            //creating a popup menu
            PopupMenu popup = new PopupMenu(v.getContext(), v);
            //inflating menu from xml resource
            popup.inflate(R.menu.product_menu);
            popup.setForceShowIcon(true);
            //adding click listener
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.productEdit:
                      MySharedPrefrence.getInstance(context).setProductId(holder.id.getText().toString() );
                      MySharedPrefrence.getInstance(context).setUpdateNameProduct(holder.name.getText().toString());
                      MySharedPrefrence.getInstance(context).setUpdateBrandProduct(holder.brand.getText().toString());
                      MySharedPrefrence.getInstance(context).setUpdateDescriptionProduct(holder.description.getText().toString());
                      MySharedPrefrence.getInstance(context).setUpdatePriceProduct(list.get(position).getPrice());
                       context.startActivity(new Intent(context, EditProductActivity.class));
                        break;
                    case R.id.productDelete:
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("حذف محصول");
                        builder.setMessage("آیا مطمن هستید برای حذف  محصول "+holder.name.getText()+"   از لیست محصولات؟");
                        builder.setNegativeButton("نه حذف نشود",(dialog, which) -> {});
                        builder.setPositiveButton("بعله حذف شود",(dialog, which) -> RetrofitClient.getInstance(v.getContext())
                                .getApi().DeleteProduct(
                                        MySharedPrefrence.getInstance(v.getContext()).getUser()
                                        , MySharedPrefrence.getInstance(v.getContext()).getCompany(),
                                        holder.name.getText().toString(),
                                        holder.id.getText().toString())
                                .enqueue(new Callback<JsonResponseModel>() {
                                    @Override
                                    public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
                                        if (response.isSuccessful()) {
                                            Snackbar.make(v, "محصول با موفقیت حذف گردید", BaseTransientBottomBar.LENGTH_SHORT).show();
                                        holder.ItemClick.setVisibility(View.GONE);

                                        } else {
                                            assert response.errorBody() != null;
                                            Log.d("Error", "onResponse: " + response.errorBody().source());
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
                                        Log.d("Field", "onFailure: " + t.getMessage());
                                    }}));
                        builder.setIcon(R.drawable.ic_error);
                        builder.show();
                        break;

                }
                return false;
            });
            //displaying the popup
            popup.show();


        });
    }

    @Override
    public int getItemCount() {
        if (list == null)
            list = new ArrayList<>();

        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView name;
        private final MaterialTextView brand;
        private final MaterialTextView description;
        private final MaterialTextView price;
        private final MaterialTextView id;
        private final SimpleDraweeView pic;
        private final ConstraintLayout ItemClick;

        public ViewHolder(@NonNull View view) {
            super(view);
            name = itemView.findViewById(R.id.PI_name);
            brand = itemView.findViewById(R.id.PI_brand);
            description = itemView.findViewById(R.id.PI_description);
            price = itemView.findViewById(R.id.PI_price);
            id = itemView.findViewById(R.id.productItem_Id);
            pic = itemView.findViewById(R.id.PI_image);
            ItemClick = itemView.findViewById(R.id.ItemProductClick);


        }
    }
}
