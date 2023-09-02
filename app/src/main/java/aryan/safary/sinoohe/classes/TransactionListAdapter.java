package aryan.safary.sinoohe.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.data.trans_id_item;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.ViewHolder> {
    private List<trans_id_item> list ;
    private final Context context;
    public TransactionListAdapter(List<trans_id_item> list, Context context){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
holder.username.setText(list.get(position).getUsername());
holder.amount.setText(list.get(position).getAmount());
holder.card.setText(list.get(position).getCard());
holder.code.setText(list.get(position).getShaparak_Ref_Id());
holder.data.setText(list.get(position).getData());
switch (list.get(position).getStatus()){
    case "100":
        holder.status.setText("پرداخت موفق بود");
        break;
    case "101":
        holder.status.setText("منتظر ارسال تراکنش و ادامه پرداخت");
        break;
    case "102":
        holder.status.setText("پرداخت رد شده توسط کاربر یا بانک");
        break;
    case "103":
        holder.status.setText("پرداخت در حال انتظار جواب بانک");
        break;
    case "104":
        holder.status.setText("پرداخت لغو شده است");
        break;
    default:
        holder.status.setText("پرداخت با مشکل مواجه شده");
        break;
}


    }

    @Override
    public int getItemCount() {
        if(list==null) list=new ArrayList<>();

        return list.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        private final MaterialTextView username;
        private final MaterialTextView amount;
        private final MaterialTextView card;
        private final MaterialTextView code;
        private final MaterialTextView data;
        private final MaterialTextView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.itemTransactionListName);
            amount=itemView.findViewById(R.id.itemTransactionListAmount);
            card=itemView.findViewById(R.id.itemTransactionListCard);
            code=itemView.findViewById(R.id.itemTransactionListCode);
            data=itemView.findViewById(R.id.itemTransactionListData);
            status=itemView.findViewById(R.id.itemTransactionListStatus);
        }
    }
}
