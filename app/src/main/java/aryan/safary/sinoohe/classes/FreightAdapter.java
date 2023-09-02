package aryan.safary.sinoohe.classes;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textview.MaterialTextView;
import java.util.ArrayList;
import java.util.List;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.data.FreightItem;
import aryan.safary.sinoohe.data.JsonResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreightAdapter extends RecyclerView.Adapter<FreightAdapter.ViewHolder> {
    private List<FreightItem>list;
    public FreightAdapter(List<FreightItem> list){
        this.list=list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.freight_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText(list.get(position).getId());
        holder.receiver.setText(list.get(position).getReceiver());
        holder.location.setText(list.get(position).getLocation());
        holder.name_product.setText(list.get(position).getName_product());
        holder.count.setText(list.get(position).getCount());
        holder.offer.setText(list.get(position).getOffer());
        holder.data.setText(list.get(position).getData());
    }

    @Override
    public int getItemCount() {
        if(list==null) list=new ArrayList<>();

        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final MaterialTextView id;
        private final MaterialTextView receiver;
        private final MaterialTextView location;
        private final MaterialTextView name_product;
        private final MaterialTextView count;
        private final MaterialTextView offer;
        private final MaterialTextView data;
        private final MaterialRadioButton status;
        private int time = 10;
        private boolean check = false;
        private final CountDownTimer countDownTimer ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.freight_id);
            receiver=itemView.findViewById(R.id.freightItem_receiver);
            location=itemView.findViewById(R.id.freightItem_location);
            name_product=itemView.findViewById(R.id.freightItem_NameProduct);
            count=itemView.findViewById(R.id.freightItem_count);
            offer=itemView.findViewById(R.id.freightItem_offer);
            data=itemView.findViewById(R.id.freightItem_data);
            status=itemView.findViewById(R.id.freightItem_status_tex);
            status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(check){
                        check=false;
                        time=10;
                        status.setChecked(false);
                        status.setText(itemView.getContext().getString(R.string.sended_product_farsi));
                        countDownTimer.cancel();
                    }else countDownTimer.start();
                }
            });
            countDownTimer=new CountDownTimer(10000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    time--;
                    check=true;
                    status.setText(itemView.getContext().getString(R.string.cancel)+"    "+time);
                    YoYo.with(Techniques.Wobble).duration(3000).playOn(itemView.findViewById(R.id.freight));
                }

                @Override
                public void onFinish() {

RetrofitClient.getInstance(itemView.getContext())
        .getApi().updateFright(id.getText().toString()) .enqueue(new Callback<JsonResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
                if(response.isSuccessful())
                itemView.findViewById(R.id.freight).setVisibility(View.GONE);

                else Log.d("Error", "onResponse: "+response.code());
            }

            @Override
            public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
                Log.d("Field", "onFailure: "+t.getMessage());
            }
        });               }
            };



        }
    }
}
