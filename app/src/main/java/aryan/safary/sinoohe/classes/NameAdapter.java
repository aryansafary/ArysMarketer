package aryan.safary.sinoohe.classes;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textview.MaterialTextView;
import java.util.List;

import aryan.safary.sinoohe.activitys.MainReportActivity;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.activitys.SetClenderActivity;
import aryan.safary.sinoohe.data.NameItem;

public class NameAdapter extends RecyclerView.Adapter<NameAdapter.ViewHolder>{
    private final List<NameItem> list;
    private final boolean checkPage ;
    public NameAdapter( List<NameItem>list , boolean checkPage ){
        this.list=list;
        this.checkPage=checkPage;
    }


    @NonNull
    @Override
    public NameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_report_item,parent, false);
        return new NameAdapter.ViewHolder(view , checkPage);
    }

    @Override
    public void onBindViewHolder(@NonNull NameAdapter.ViewHolder holder, int position) {
        NameItem nameItem = list.get(position);
        holder.username.setText(nameItem.getName());
        //holder.profile.setImageURI(Uri.parse(list.get(position).get );
        if(holder.username.getText().toString().isEmpty() || holder.username.getText().toString().equals(""))
         holder.username.setText("کاربر اطلاعات خود را ثبت نکرده");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }














    static class ViewHolder extends  RecyclerView.ViewHolder{
        private final MaterialTextView username;
        private final ImageView profile ;

        public ViewHolder(@NonNull View view , boolean checkPage) {
            super(view);
            username=view.findViewById(R.id.report_user_username);
            profile=view.findViewById(R.id.report_user_profile);
            view.findViewById(R.id.user_const).setOnClickListener(v -> {
                if(checkPage)
                    view.getContext().startActivity(new Intent(view.getContext(), SetClenderActivity.class));
                else view.getContext().startActivity(new Intent(view.getContext(), MainReportActivity.class));

                MySharedPrefrence.getInstance(view.getContext()).setSelectedName(username.getText().toString());
            });
        }
    }

}
