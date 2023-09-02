package aryan.safary.sinoohe.classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.List;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.data.ProductsItem;

public class product_filter_adapter extends RecyclerView.Adapter<product_filter_adapter.VieHolder> {
private final List<ProductsItem>list;

    public product_filter_adapter(List<ProductsItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public VieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_filter_item,parent,false);
       return new  product_filter_adapter.VieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VieHolder holder, int position) {
holder.checkBox.setText(list.get(position).getBrand());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class VieHolder extends RecyclerView.ViewHolder{
private final MaterialCheckBox checkBox;

        public VieHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.ProductFilter_brand);
        }
    }
}
