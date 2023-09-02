package aryan.safary.sinoohe.classes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textview.MaterialTextView;
import java.util.ArrayList;
import java.util.List;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.data.NamePharmacyItem;
import aryan.safary.sinoohe.dialogs.SubmitRequest;

public class PharmacyNameAdapter extends RecyclerView.Adapter<PharmacyNameAdapter.ViewHolder>{

    private List<NamePharmacyItem> list;
    public PharmacyNameAdapter( List<NamePharmacyItem>list){
        this.list=list;
    }
    @NonNull
    @Override
    public PharmacyNameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pharmacy_name_item,parent, false);
        return new PharmacyNameAdapter.ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterList(ArrayList<NamePharmacyItem> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        list = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull PharmacyNameAdapter.ViewHolder holder, int position) {
        NamePharmacyItem namePharmacyitem = list.get(position);
        holder.tex.setText(namePharmacyitem.getName());
        holder.loc.setText(namePharmacyitem.getLoc());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends  RecyclerView.ViewHolder{
        private final MaterialTextView tex , loc;
        public ViewHolder(@NonNull View view) {
            super(view);
            tex =view.findViewById(R.id.PNI_tex);
            loc=view.findViewById(R.id.PNI_loc);
           view.setOnClickListener(v -> {
               MySharedPrefrence.getInstance(view.getContext()).setPharmacyName(tex.getText().toString());
              view.getContext().startActivity(new Intent(view.getContext(), SubmitRequest.class));
           });
        }
    }




}
