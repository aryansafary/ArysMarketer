package aryan.safary.sinoohe.classes;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textview.MaterialTextView;
import java.util.ArrayList;
import java.util.List;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.activitys.EditCenterPharmacyActivity;
import aryan.safary.sinoohe.data.JsonResponseModel;
import aryan.safary.sinoohe.data.NamePharmacyItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CenterListPharmacyAdapter extends RecyclerView.Adapter<CenterListPharmacyAdapter.ViewHolder> {
    private final Context context;
    private List<NamePharmacyItem> list;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pharmacy_center_list,parent,false);
        return new ViewHolder(view);
    }


    public CenterListPharmacyAdapter(Context context , List<NamePharmacyItem> list){
        this.list=list;
        this.context=context;
    }
    // method for filtering our recyclerview items.
    @SuppressLint("NotifyDataSetChanged")
    public void filterList(ArrayList<NamePharmacyItem> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        list = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }



    @SuppressLint("NonConstantResourceId")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
holder.name.setText(list.get(position).getName());
holder.province.setText(list.get(position).getProvince());
holder.city.setText(list.get(position).getCity());
holder.location.setText(list.get(position).getLoc());
holder.phone.setText(list.get(position).getPhone());
holder.activity.setText(list.get(position).getActivity());
holder.id.setText(list.get(position).getId());
if(MySharedPrefrence.getInstance(context).getSide().equals("مدیر")){
 holder.itemClick.setOnClickListener(v -> {
     //creating a popup menu
    PopupMenu popup = new PopupMenu(v.getContext(), v);
     popup.inflate(R.menu.mnute_center_item);
    popup.setForceShowIcon(true);
     //adding click listener
     popup.setOnMenuItemClickListener(item -> {
         switch (item.getItemId()) {
             case R.id.EditCenterMenu:
MySharedPrefrence.getInstance(context).setUpdateNamePharmacy(holder.name.getText().toString());
MySharedPrefrence.getInstance(context).setUpdateLocationPharmacy(holder.location.getText().toString());
MySharedPrefrence.getInstance(context).setUpdatePhonePharmacy(holder.phone.getText().toString());
MySharedPrefrence.getInstance(context).setUpdateIdPharmacy(holder.id.getText().toString());
                 context.startActivity(new Intent(context, EditCenterPharmacyActivity.class));
                 break;
             case R.id.DeleteCenterMenu:
                 AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                 builder.setTitle("حذف مرکز");
                 builder.setIcon(R.drawable.ic_delete);
                 builder.setMessage("آیا مطمن هستید برای حذف    "+holder.name.getText()+"   از لیست مراکز؟");
                 builder.setNegativeButton("نه حذف نشود",(dialog, which) -> dialog.dismiss());
                 builder.setPositiveButton("بعله حذف شود",(dialog, which) -> RetrofitClient.getInstance(context).getApi().DeleteCenter(
                         MySharedPrefrence.getInstance(context).getCompany(),holder.id.getText().toString()).enqueue(new Callback<JsonResponseModel>() {
                     @Override
                     public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
                         if(response.isSuccessful()){
                             Toast.makeText(context, "مرکز مورد نظر با موفقیت حذف شد.", Toast.LENGTH_SHORT).show();
                             holder.itemClick.setVisibility(View.GONE);
                         }else {
                             assert response.errorBody() != null;
                             Log.d("error", "onResponse: "+ response.message());
                         }
                     }

                     @Override
                     public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
                         Log.d("Field", "onFailure: "+t.getMessage());
                     }
                 }));
                 builder.show();
                 break;
         }
         return false;
     });
     //displaying the popup
     popup.show();







         });





}
    }

    @Override
    public int getItemCount() {
        if(list==null)
            list=new ArrayList<>();

        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView name,province,city,location,phone,activity , id;
        private final ConstraintLayout itemClick;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.item_centerListName);
            province=itemView.findViewById(R.id.item_centerList_province);
            city=itemView.findViewById(R.id.item_centerList_city);
            location=itemView.findViewById(R.id.item_centerList_location);
            phone=itemView.findViewById(R.id.item_centerList_phone);
            activity=itemView.findViewById(R.id.item_centerList_activity);
            itemClick=itemView.findViewById(R.id.CenterListItemClick);
            id=itemView.findViewById(R.id.item_centerList_id);

        }
    }
}
