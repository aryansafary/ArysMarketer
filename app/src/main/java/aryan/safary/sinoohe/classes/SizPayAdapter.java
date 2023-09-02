package aryan.safary.sinoohe.classes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.data.JsonResponseModel;
import aryan.safary.sinoohe.data.PaySubscriptionItem;
import aryan.safary.sinoohe.data.trans_id_item;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SizPayAdapter extends RecyclerView.Adapter<SizPayAdapter.ViewHolder> {
    private List<PaySubscriptionItem> list;

    public SizPayAdapter(List<PaySubscriptionItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pay, parent, false);

        return new SizPayAdapter.ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.amounts.setText(list.get(position).getPrice());
        holder.price.setText(new DecimalFormat("#,###").format(Integer.parseInt(list.get(position).getPrice())) + " تومان ");
        String m="";
        switch (list.get(position).getMonth()) {
            case "1" :
             m="یک ماهه"   ;
                break;
            case "2" :
                m="دو ماهه"   ;
                break;
            case "3" :
                m="سه ماهه"   ;
                break;
            case "4" :
                m="چهار ماهه"   ;
                break;
            case "5" :
                m="پنج ماهه"   ;
                break;
            case "6" :
                m="شیش ماهه"   ;
                break;
            case "7" :
                m="هفت ماهه"   ;
                break;
            case "8" :
                m="هشت ماهه"   ;
                break;
            case "9" :
                m="نه ماهه"   ;
                break;
            case "10" :
                m=" ده ماه"   ;
                break;
            case "11" :
                m=" یازده ماه"   ;
                break;
            case "12" :
                m=" یک ساله"   ;
                break;
        }
        holder.subscription.setText(m);
        holder.id.setText(list.get(position).getId());
    }

    @Override
    public int getItemCount() {
        if (list == null)
            list = new ArrayList<>();

        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView price;
        private final MaterialTextView subscription;
        private final MaterialTextView id;
        private final MaterialTextView amounts;

        private void SaveToken(String username, String amount, String order_id, int code, String trans_id) {
            RetrofitClient.getInstance(itemView.getContext()).
                    getApi().InsertTransactionPayment(username,Integer.parseInt(amount), order_id, code, trans_id)
                    .enqueue(new Callback<JsonResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
                    if (response.isSuccessful()) {
                        itemView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(itemView.getContext().getString(R.string.ATM_Uri) + trans_id)));

                    } else {
                        assert response.errorBody() != null;
                        Log.d("Error", "onResponse: " + response.errorBody().source() + response.code());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
                    Log.d("Field", "onFailure: " + t.getMessage());
                }
            });
        }

        private void getTransID(String api_key, String order_id, int amount, String CallBackUri, String username) {
            RetrofitClient.getInstance(itemView.getContext()).getApi().getNexPayToken(api_key, order_id, amount, CallBackUri).
                    enqueue(new Callback<trans_id_item>() {
                        @Override
                        public void onResponse(@NonNull Call<trans_id_item> call, @NonNull Response<trans_id_item> response) {
                            if (response.isSuccessful()) {
                                assert response.body() != null;
                                SaveToken(username, response.body().getAmount(), order_id, response.body().getCode(), response.body().getTrans_id());
                            } else {
                                assert response.errorBody() != null;
                                Log.d("error", "onResponse: " + response.errorBody().source() + response.message() + response.code());
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<trans_id_item> call, @NonNull Throwable t) {
                            Log.d("field", "onFailure: " + t.getMessage());
                        }
                    });
        }





        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.item_pay_id);
            price = itemView.findViewById(R.id.item_pay_price);
            subscription = itemView.findViewById(R.id.item_pay_subscription);
            amounts = itemView.findViewById(R.id.item_pay_amount);
            MaterialButton submit = itemView.findViewById(R.id.item_pay_submit);
            String username = MySharedPrefrence.getInstance(itemView.getContext()).getUser();
            String api_key = itemView.getContext().getString(R.string.NexPay_api_key);
            final String[] order_id = new String[1];
            RetrofitClient.getInstance(itemView.getContext()).getApi().getOrderId(username).enqueue(new Callback<JsonResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        order_id[0] = response.body().MyMessage;
                    } else {
                        assert response.errorBody() != null;
                        Log.d("error", "onResponse: " + response.errorBody().source());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
                    Log.d("Field", "onFailure: " + t.getMessage());
                }
            });

            String CallBackUri = itemView.getContext().getString(R.string.CallBackUri);

            submit.setOnClickListener(v -> {
                int amount = Integer.parseInt(amounts.getText().toString());
                //Toast.makeText(itemView.getContext(), price.getText().toString(), Toast.LENGTH_LONG).show();
                getTransID(api_key, order_id[0], amount, CallBackUri, username);
                submit.setEnabled(false);



            });


        }
    }
}
