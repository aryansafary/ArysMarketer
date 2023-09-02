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
import aryan.safary.sinoohe.data.CalculatorItem;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.ViewHolder> {
private List<CalculatorItem>list;

public CalenderAdapter (List<CalculatorItem>list)
{
    this.list=list;
}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calender_item,parent,false);
           return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
holder.day.setText(list.get(position).getDay());
holder.region.setText(list.get(position).getRegion());
holder.description.setText(list.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
    if(list==null) list=new ArrayList<>();

    return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
private final MaterialTextView day;
        private final MaterialTextView region;
        private final MaterialTextView description ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.calender_saturday_day);
            region=itemView.findViewById(R.id.calender_saturday_region);
            description=itemView.findViewById(R.id.calender_saturday_description);
        }
    }
}
