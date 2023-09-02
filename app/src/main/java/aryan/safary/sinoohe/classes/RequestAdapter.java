package aryan.safary.sinoohe.classes;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textview.MaterialTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.data.RequestItem;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
private List<RequestItem>list;


    public RequestAdapter(  List<RequestItem>list) {
        this.list = list;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item,parent, false);
        return new RequestAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.sender.setText(list.get(position).getSender());
        holder.receiver.setText(list.get(position).getReceiver());
        holder.location.setText(list.get(position).getLocation());
        holder.ProductName.setText(list.get(position).getProductName());
        holder.count.setText(list.get(position).getCount());
        holder.offer.setText(list.get(position).getOffer());
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        holder.ProductPrice.setText(decimalFormat.format(Integer.parseInt(list.get(position).getProductPrice())) +"   تومان");
        holder.TotalPrice.setText(decimalFormat.format( Integer.parseInt(list.get(position).getTotalPrice())) +"   تومان" );
        holder.data.setText(list.get(position).getData());
        switch (list.get(position).getStatus()) {
            case "0":
                holder.status.setText("درحال بررسی");
                    break;
            case "1":
                    holder.status.setText("درحال ارسال");
                    break;
            case "2":
                    holder.status.setText("تحویل داده شد");
                    break;
        }
    }

    @Override
    public int getItemCount() {
        if(list==null)list=new ArrayList<>();
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        private final  MaterialTextView sender , receiver ,location , ProductName , count , offer , ProductPrice , TotalPrice , data , status ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sender=itemView.findViewById(R.id.RequestItem_sender);
            receiver=itemView.findViewById(R.id.RequestItem_receiver);
            location=itemView.findViewById(R.id.RequestItem_location);
            ProductName=itemView.findViewById(R.id.RequestItem_NameProduct);
            count=itemView.findViewById(R.id.RequestItem_count);
            offer=itemView.findViewById(R.id.RequestItem_offer);
            ProductPrice=itemView.findViewById(R.id.RequestItem_ProductPrice);
            TotalPrice=itemView.findViewById(R.id.RequestItem_TotalPrice);
            data=itemView.findViewById(R.id.RequestItem_data);
            status=itemView.findViewById(R.id.RequestItem_status);
            if(status.getText().toString().equals("0"))
            status.setText("درحال بررسی");
            else if(status.getText().toString().equals("1"))
                status.setText("درحال ارسال");
            else if(status.getText().toString().equals("2"))
                status.setText("تحویل داده شد");

        }
    }
}
