package aryan.safary.sinoohe.classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.data.StoreItem;

public  class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private List<StoreItem>list;
    public TransactionAdapter(List<StoreItem>list){ this.list=list;}


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item,parent,false) ;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.sender.setText(list.get(position).getSender());
        holder.receiver.setText(list.get(position).getReceiver());
        holder.name_product.setText(list.get(position).getName_product());
        holder.count.setText(list.get(position).getCount());
        holder.offer.setText(list.get(position).getOffer());
        holder.data.setText(list.get(position).getData());
        if(list.get(position).getStatus().equals("0"))
            holder.status.setText("کسر شده");
        if(list.get(position).getStatus().equals("1")) {
            holder.sender.setVisibility(View.GONE);
            holder.receiver.setVisibility(View.GONE);
            holder.offer.setVisibility(View.GONE);
            holder.sender_tex.setVisibility(View.GONE);
            holder.receiver_tex.setVisibility(View.GONE);
            holder.offer_tex.setVisibility(View.GONE);
            holder.status.setText("افزوده شده");
        }

    }



    @Override
    public int getItemCount() {
        if(list==null)list=new ArrayList<>();

        return list.size();
    }




static class ViewHolder extends RecyclerView.ViewHolder{
private final MaterialTextView   sender , receiver ,name_product , count , offer , data , status ;
private final MaterialTextView   sender_tex , receiver_tex , offer_tex  ;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        sender_tex=itemView.findViewById(R.id.Store_sender_tex);
        receiver_tex=itemView.findViewById(R.id.StoreItem_receiver_tex);
        offer_tex=itemView.findViewById(R.id.StoreItem_offer_tex);



        sender=itemView.findViewById(R.id.StoreItem_sender);
        receiver=itemView.findViewById(R.id.StoreItem_receiver);
       name_product=itemView.findViewById(R.id.StoreItem_NameProduct);
        count=itemView.findViewById(R.id.StoreItem_count);
        offer=itemView.findViewById(R.id.StoreItem_offer);
        data=itemView.findViewById(R.id.StoreItem_data);
        status=itemView.findViewById(R.id.StoreItem_status);
    }
}


}