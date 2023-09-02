package aryan.safary.sinoohe.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import com.google.android.material.appbar.MaterialToolbar;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.NetworkChanged;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.classes.UserListAdapter;
import aryan.safary.sinoohe.data.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
private void setObjectId(){
    MaterialToolbar toolbar = findViewById(R.id.toolbar);
    recyclerView=findViewById(R.id.userListRecycler);
    recyclerView.setLayoutManager(new LinearLayoutManager(UserListActivity.this));
    setSupportActionBar(toolbar);
    if(getSupportActionBar() !=null) getSupportActionBar().setTitle("لیست کاربران");

}

private void init(){
    setObjectId();
    getUserList();
}

private void getUserList(){
    RetrofitClient.getInstance(UserListActivity.this).getApi().getUserList(
            MySharedPrefrence.getInstance(UserListActivity.this).getCompany(),
            MySharedPrefrence.getInstance(UserListActivity.this).getCompanyAddress()
    ).enqueue(new Callback<UserModel>() {
        @Override
        public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
            if(response.isSuccessful()) {
                assert response.body() != null;
                recyclerView.setAdapter(new UserListAdapter(response.body().getData(),UserListActivity.this));
            }

            else {
                assert response.errorBody() != null;
                Log.d("error", "onResponse: "+response.errorBody().source());
            }
        }

        @Override
        public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
            Log.d("Field", "onFailure: "+t.getMessage());
        }
    });
}

    @Override
    protected void onResume() {
        super.onResume();
        getUserList();
        NetworkChanged.InternetConnection(UserListActivity.this);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        init();
    }
}