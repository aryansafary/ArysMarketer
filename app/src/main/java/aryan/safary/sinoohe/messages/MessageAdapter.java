package aryan.safary.sinoohe.messages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import aryan.safary.sinoohe.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<MessageModel> list;
    private final String username;
    private final Context context;

    public MessageAdapter(Context context,List<MessageModel> list){
        this.list=list;
        username = context.getSharedPreferences("myapp",0).getString("username","");
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row, parent, false));
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n", "RtlHardcoded"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MessageModel model = list.get(position);

        if(model.getSender().equalsIgnoreCase(username)){
            holder.card.setGravity(Gravity.RIGHT);
            holder.text.setText(model.getMessage());
            holder.text.setBackground(context.getResources().getDrawable(R.drawable.sharp_add_black_36));
        }else{
            holder.text.setText(model.getSender()+":\n"+model.getMessage());

        }


    }

    @Override
    public int getItemCount() {
        if (list == null)list=new ArrayList<>();
        return list.size();

    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView text;
        private final LinearLayout card;

        ViewHolder(View v){
            super(v);
            text = v.findViewById(R.id.row_text);
            card = v.findViewById(R.id.row_card);

        }

    }
}
