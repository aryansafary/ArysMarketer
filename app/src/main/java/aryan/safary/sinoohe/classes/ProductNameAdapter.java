package aryan.safary.sinoohe.classes;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.activitys.InsertStoreActivity;
import aryan.safary.sinoohe.data.NameProductitem;
import aryan.safary.sinoohe.dialogs.SubmitRequest;
public class ProductNameAdapter extends RecyclerView.Adapter<ProductNameAdapter.ViewHolder>{
    private List<NameProductitem> list;
    private final Context context;
    public ProductNameAdapter( List<NameProductitem>list,Context context){
        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public ProductNameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_name_item,parent, false);
        return new ProductNameAdapter.ViewHolder(view);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void filterList(ArrayList<NameProductitem> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        list = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull ProductNameAdapter.ViewHolder holder, int position) {
        NameProductitem NamePharmacyItem = list.get(position);
        holder.tex.setText(NamePharmacyItem.getProductName());
        holder.count.setText(NamePharmacyItem.getCount());
        Picasso.get().load(Uri.parse(context.getString(R.string.ImageUrl,list.get(position).getImage()))).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends  RecyclerView.ViewHolder{
        private final MaterialTextView tex , count ;
        private final SimpleDraweeView image;
        public ViewHolder(@NonNull View view) {
            super(view);
            tex =view.findViewById(R.id.ProductNI_tex);
            count=view.findViewById(R.id.ProductNI_count);
            image=view.findViewById(R.id.productNI_image);
view.setOnClickListener(v -> {
   MySharedPrefrence.getInstance(view.getContext()).setProductName(tex.getText().toString());
   if(MySharedPrefrence.getInstance(view.getContext()).getPage().equals("request"))
   view.getContext().startActivity(new Intent(view.getContext(), SubmitRequest.class));
   else if(MySharedPrefrence.getInstance(view.getContext()).getPage().equals("store"))
       view.getContext().startActivity(new Intent(view.getContext(), InsertStoreActivity.class));
});

        }
    }


}
