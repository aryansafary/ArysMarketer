package aryan.safary.sinoohe.classes;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textview.MaterialTextView;
import java.util.ArrayList;
import java.util.List;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.data.JsonResponseModel;
import aryan.safary.sinoohe.data.RequestsDeductedItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestDeductedAdapter extends RecyclerView.Adapter<RequestDeductedAdapter.ViewHolder> {

    private List<RequestsDeductedItem> list ;

    public RequestDeductedAdapter( List<RequestsDeductedItem>list) {
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_deducted_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText(list.get(position).getId());
        holder.sender.setText(list.get(position).getSender());
        holder.receiver.setText(list.get(position).getReceiver());
        holder.ProductName.setText(list.get(position).getName_product());
        holder.count.setText(list.get(position).getCount());
        holder.offer.setText(list.get(position).getOffer());
        holder.data.setText(list.get(position).getData());
    }



    @Override
    public int getItemCount() {
if(list==null) list=new ArrayList<>();

return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        private final MaterialTextView id;
        private final MaterialTextView sender;
        private final MaterialTextView receiver;
        private final MaterialTextView ProductName;
        private final MaterialTextView count;
        private final MaterialTextView offer;
        private final MaterialTextView data  ;
        private MaterialRadioButton outProduct=null;
        private boolean check =false;
        int time=10;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.RequestDeducted_id);
            sender=itemView.findViewById(R.id.RequestDeduct_sender);
            receiver=itemView.findViewById(R.id.RequestDeduct_receiver);
            ProductName=itemView.findViewById(R.id.RequestDeduct_ProductName);
            count=itemView.findViewById(R.id.RequestDeduct_count);
            offer=itemView.findViewById(R.id.RequestDeduct_offer);
            data=itemView.findViewById(R.id.RequestDeduct_data);
             CountDownTimer countDownTimer = new CountDownTimer(10000,1000) {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTick(long millisUntilFinished) {
                    time--;
                    check=true;
                    outProduct.setText(itemView.getContext().getString(R.string.cancel)+"    "+time);
                    YoYo.with(Techniques.Wobble).duration(2000).playOn(itemView.findViewById(R.id.RequestDeducted));
                }

                @Override
                public void onFinish() {
                   itemView.findViewById(R.id.RequestDeducted).setVisibility(View.GONE);
RetrofitClient.getInstance(itemView.getContext()).getApi().DeleteRequestDeducted(id.getText().toString() ,
        MySharedPrefrence.getInstance(itemView.getContext()).getUser()).enqueue(new Callback<JsonResponseModel>() {
    @Override
    public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
        if(response.isSuccessful()){
     Toast.makeText(itemView.getContext(), "code :"+response.code()+"body :"+response.body(),Toast.LENGTH_LONG).show();
        }else Log.d("Error" ,"onResponse: Error In Update"+response.code());
    }

    @Override
    public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
        Log.d("Field" ,"onFailure: Error In Update"+t.getMessage());
    }
});
                }
            };
            outProduct = itemView.findViewById(R.id.RequestDeducted_OutProductBtn);outProduct.setOnClickListener(v ->
                    {
                      if(check) {
                          check=false;
                          time=10;
                          outProduct.setChecked(false);
                          outProduct.setText(itemView.getContext().getString(R.string.outProduct));
                          countDownTimer.cancel();
                      }  else countDownTimer.start();
                    });



        }


    }

}
