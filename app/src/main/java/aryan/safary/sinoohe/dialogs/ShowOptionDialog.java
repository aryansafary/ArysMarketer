package aryan.safary.sinoohe.dialogs;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.activitys.InsertStoreActivity;
import aryan.safary.sinoohe.activitys.NewProductActivity;
import aryan.safary.sinoohe.classes.MySharedPrefrence;

public class ShowOptionDialog extends Fragment {
    @SuppressLint("ResourceAsColor")
    private void init(View view){
        setOptionSide(view);
        view.findViewById(R.id.showOption_report_form).setOnClickListener(v -> {
startActivity(new Intent(getContext(),ReportActivity2.class));
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        view.findViewById(R.id.showOption_store_form).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), InsertStoreActivity.class));
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        view.findViewById(R.id.showOption_product_form).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), NewProductActivity.class));
            requireActivity().getSupportFragmentManager().popBackStack();
            requireActivity().finish();
        });
        view.findViewById(R.id.showOption_request_form).setOnClickListener(v -> {
            startActivity(new Intent(getContext(),SubmitRequest.class));
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        view.findViewById(R.id.showOption_CloseBtn).setOnClickListener(V-> requireActivity().getSupportFragmentManager().popBackStack());
    }
    public ShowOptionDialog() {

    }


    private void setOptionSide(View view){
        switch (MySharedPrefrence.getInstance(getContext()).getSide()){
            case "انباردار":
                view.findViewById(R.id.showOption_report_form).setVisibility(View.VISIBLE);
                view.findViewById(R.id.showOption_store_form).setVisibility(View.VISIBLE);
                view.findViewById(R.id.showOption_product_form).setVisibility(View.GONE);
                view.findViewById(R.id.showOption_request_form).setVisibility(View.GONE);
                break;
            case "بازاریاب":
                view.findViewById(R.id.showOption_report_form).setVisibility(View.VISIBLE);
                view.findViewById(R.id.showOption_store_form).setVisibility(View.GONE);
                view.findViewById(R.id.showOption_product_form).setVisibility(View.GONE);
                view.findViewById(R.id.showOption_request_form).setVisibility(View.VISIBLE);
                break;
            case "باربری":
                view.findViewById(R.id.showOption_report_form).setVisibility(View.VISIBLE);
                view.findViewById(R.id.showOption_store_form).setVisibility(View.GONE);
                view.findViewById(R.id.showOption_product_form).setVisibility(View.GONE);
                view.findViewById(R.id.showOption_request_form).setVisibility(View.GONE);
                break;
            case "مدیر":
                view.findViewById(R.id.showOption_report_form).setVisibility(View.VISIBLE);
                view.findViewById(R.id.showOption_store_form).setVisibility(View.GONE);
                view.findViewById(R.id.showOption_product_form).setVisibility(View.VISIBLE);
                view.findViewById(R.id.showOption_request_form).setVisibility(View.VISIBLE);
                break;
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_show_option, container, false);
        init(view);
        return view;
    }
}
