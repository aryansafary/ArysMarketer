package aryan.safary.sinoohe.classes;

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
import aryan.safary.sinoohe.activitys.ShowMapActivity;
import aryan.safary.sinoohe.data.DataItem;
public class DataShowAdapter extends RecyclerView.Adapter<DataShowAdapter.ViewHolder> {
private List<DataItem>list;
public DataShowAdapter(List<DataItem> list){
    this.list=list;

}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showdata,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
  holder.text.setText(list.get(position).getData());
    }

    @Override
    public int getItemCount() {
    if(list==null) list=new ArrayList<>();

 return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
    private final MaterialTextView text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.item_show_data_text);
            itemView.findViewById(R.id.item_show_data).setOnClickListener(v ->{
                itemView.getContext().startActivity(new Intent(itemView.getContext(), ShowMapActivity.class));
                MySharedPrefrence.getInstance(itemView.getContext()).setSelectedData(text.getText().toString());
            });
        }
    }
}
