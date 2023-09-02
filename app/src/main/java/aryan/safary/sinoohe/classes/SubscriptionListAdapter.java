package aryan.safary.sinoohe.classes;

import android.annotation.SuppressLint;
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
import aryan.safary.sinoohe.data.SubscriptionItem;

public class SubscriptionListAdapter extends RecyclerView.Adapter<SubscriptionListAdapter.ViewHolder> {
    private List<SubscriptionItem>list ;
    private final Context context;
    public SubscriptionListAdapter(List<SubscriptionItem> list, Context context){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_subscription_list,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
holder.username.setText(list.get(position).getUsername());
holder.day_start.setText(list.get(position).getDay_start());
holder.day_end.setText(list.get(position).getDay_end());
holder.hours.setText(list.get(position).getHours()+":"+list.get(position).getMinutes()+":"+list.get(position).getSeconds());
if(list.get(position).getStatus().equals("1"))
holder.status.setText("به اتمام رسیده");
else holder.status.setText("فعال هست");
    }

    @Override
    public int getItemCount() {
        if(list==null)
         list=new ArrayList<>();

        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final MaterialTextView username;
        private final MaterialTextView day_start;
        private final MaterialTextView day_end;
        private final MaterialTextView hours;
        private final MaterialTextView status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.itemSubscriptionListName);
            day_start=itemView.findViewById(R.id.itemSubscriptionListDayStart);
            day_end=itemView.findViewById(R.id.itemSubscriptionListDayEnd);
            hours=itemView.findViewById(R.id.itemSubscriptionListTime);
            status=itemView.findViewById(R.id.itemSubscriptionListStatus);
        }
    }
}
