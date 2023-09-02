package aryan.safary.sinoohe.classes;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.data.InventoryItem;


public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {
    private final Context context;
    private List<InventoryItem> list;
public InventoryAdapter(Context context , List<InventoryItem>list){
    this.context = context;
    this.list=list;
}



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Fresco.initialize(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
holder.name.setText(list.get(position).getName());
holder.count.setText(list.get(position).getCount());
holder.image.setImageURI(Uri.parse( context.getString(R.string.ImageUrl,list.get(position).getImage()))          );
    }


    @Override
    public int getItemCount() {
    if(list==null) list=new ArrayList<>()   ;

return list.size();
    }

    static class ViewHolder extends  RecyclerView.ViewHolder{
        private final MaterialTextView name;
        private final MaterialTextView count;
        private final SimpleDraweeView image ;
        public ViewHolder(@NonNull View view) {
            super(view);
            name= itemView.findViewById(R.id.InventoryItem_name);
            count= itemView.findViewById(R.id.InventoryItem_count);
            image= itemView.findViewById(R.id.Inventory_image);

        }
    }
}

