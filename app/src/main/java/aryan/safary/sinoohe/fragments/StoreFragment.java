package aryan.safary.sinoohe.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.material.tabs.TabLayout;
import java.util.Objects;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.MySharedPrefrence;


public class StoreFragment extends Fragment {

    TabLayout tabLayout;
    private final Context context;
    public StoreFragment(Context context) {
        // Required empty public constructor
        this.context=context;
    }
    private void setObjectId(View view) {
        tabLayout = view.findViewById(R.id.StoreTab);
            requireActivity().getSupportFragmentManager().beginTransaction().add(R.id.StoreContainer, new InventoryFragment(context)).commit();


    }

    private void init(View view) {
        setObjectId(view);
        setSideOption();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {



                    switch (tab.getPosition()) {
                        case 0:
                            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.StoreContainer, new InventoryFragment(context)).commit();
                            Toast.makeText(context, "Inventory", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.StoreContainer, new TransactionFragment()).commit();
                            Toast.makeText(context, "transaction", Toast.LENGTH_LONG).show();
                            break;

                        case 2:
                            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.StoreContainer, new RequestDeductFragment(context)).commit();
                            Toast.makeText(context, "RequestDeducted", Toast.LENGTH_LONG).show();
                            break;
                    }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
private void setSideOption(){
        String side = MySharedPrefrence.getInstance(getContext()).getSide();
        switch (side){
    case "مدیر" :
    Objects.requireNonNull(tabLayout.getTabAt(2)).view.setVisibility(View.GONE);
        break;
    case "بازاریاب":
        Objects.requireNonNull(tabLayout.getTabAt(2)).view.setVisibility(View.GONE);
        Objects.requireNonNull(tabLayout.getTabAt(1)).view.setVisibility(View.GONE);
break;
            case "انباردار":
                Objects.requireNonNull(tabLayout.getTabAt(2)).view.setVisibility(View.VISIBLE);
                Objects.requireNonNull(tabLayout.getTabAt(1)).view.setVisibility(View.VISIBLE);
                Objects.requireNonNull(tabLayout.getTabAt(1)).view.setVisibility(View.VISIBLE);
        break;
            default:
                Objects.requireNonNull(tabLayout.getTabAt(2)).view.setVisibility(View.GONE);
                Objects.requireNonNull(tabLayout.getTabAt(1)).view.setVisibility(View.GONE);
                Objects.requireNonNull(tabLayout.getTabAt(1)).view.setVisibility(View.GONE);
                break;



        }

}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        init(view);
        return view;
    }
}