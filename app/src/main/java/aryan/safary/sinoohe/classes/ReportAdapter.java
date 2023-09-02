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
import aryan.safary.sinoohe.data.ReportItem;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
private List<ReportItem>list;


public ReportAdapter(List<ReportItem> list){
    this.list=list;
}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_item,parent,false);
        return new ReportAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
holder.sender.setText(list.get(position).getSender());
holder.location.setText(list.get(position).getLocation());
holder.report.setText(list.get(position).getDescription());
holder.data.setText(list.get(position).getData());
    }

    @Override
    public int getItemCount() {
    if(list==null) list=new ArrayList<>();

return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
private final MaterialTextView sender;
        private final MaterialTextView location;
        private final MaterialTextView report;
        private final MaterialTextView data ;
      public ViewHolder(@NonNull View itemView) {
          super(itemView);
          sender=itemView.findViewById(R.id.ReportItem_sender);
          location=itemView.findViewById(R.id.ReportItem_location);
          report=itemView.findViewById(R.id.ReportItem_report);
          data=itemView.findViewById(R.id.ReportItem_data);


      }
  }


}
