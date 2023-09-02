package aryan.safary.sinoohe.classes;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import java.util.ArrayList;
import java.util.List;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.data.JsonResponseModel;
import aryan.safary.sinoohe.data.UserItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
private List<UserItem>list;
private final Context context;

public UserListAdapter(List<UserItem> list, Context context){
    this.context=context;
    this.list=list;
}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_list , parent , false);
        return new ViewHolder(view);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
holder.id.setText(list.get(position).getId());
holder.name.setText(list.get(position).getName());
holder.phone.setText(list.get(position).getPhone());
holder.city.setText(list.get(position).getCity());
holder.province.setText(list.get(position).getProvince());
holder.location.setText(list.get(position).getLocation());
holder.company.setText(list.get(position).getCompany());
holder.companyAddress.setText(list.get(position).getCompanyAddress());
holder.side.setText(list.get(position).getSide());
holder.barthday.setText(list.get(position).getBarthday());
holder.ItemClick.setOnClickListener(v -> {
        //creating a popup menu
        PopupMenu popup = new PopupMenu(context, v);
        //inflating menu from xml resource
        popup.inflate(R.menu.menu_user_list);
        popup.setForceShowIcon(true);
        //adding click listener
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.UserListDeleteMenu) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("اخراج کارمند");
                builder.setMessage("آیا مطمن هستید برای اخراج    " + holder.name.getText() + "   از لیست کارمندان؟");
                builder.setNegativeButton("نه پشیمان شدم", (dialog, which) -> dialog.dismiss());
                builder.setPositiveButton("بعله اخراج شود", (dialog, which) -> RetrofitClient.getInstance(context).getApi().DeleteUser(
                                holder.id.getText().toString(),
                                holder.company.getText().toString())
                        .enqueue(new Callback<JsonResponseModel>() {
                            @Override
                            public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
                                if (response.isSuccessful()) {
                                    Snackbar.make(v, "کارمند مورد نظر اخراج گردید", Snackbar.LENGTH_SHORT).show();
                                    holder.ItemClick.setVisibility(View.GONE);
                                } else {
                                    assert response.errorBody() != null;
                                    Log.d("error", "onResponse: " + response.errorBody().source());
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
                                Log.d("Field", "onFailure: " + t.getMessage());
                            }
                        }));
                builder.show();
            }
            return false;
        });
        //displaying the popup
        popup.show();


    });
}
    @Override
    public int getItemCount() {
    if(list==null) list=new ArrayList<>();
    return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

    private final ConstraintLayout ItemClick;
private final MaterialTextView id;

        private final MaterialTextView name;
        private final MaterialTextView phone;
        private final MaterialTextView city;
        private final MaterialTextView province;
        private final MaterialTextView location;
        private final MaterialTextView company;
        private final MaterialTextView companyAddress;
        private final MaterialTextView side;
        private final MaterialTextView barthday;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.itemUserListName);
            phone=itemView.findViewById(R.id.itemUserListPhone);
            city=itemView.findViewById(R.id.itemUserListCity);
            province=itemView.findViewById(R.id.itemUserListProvince);
            location=itemView.findViewById(R.id.itemUserListAddress);
            company=itemView.findViewById(R.id.itemUserListCompany);
            companyAddress=itemView.findViewById(R.id.itemUserListCompanyAddress);
            side=itemView.findViewById(R.id.itemUserListSide);
            barthday=itemView.findViewById(R.id.itemUserListBarthday);
            id=itemView.findViewById(R.id.itemUserListId);
            ItemClick=itemView.findViewById(R.id.itemUserListClick);
        }
    }

}
