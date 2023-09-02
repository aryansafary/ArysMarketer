package aryan.safary.sinoohe.classes;

import android.content.Context;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.data.api;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private final Retrofit retrofit;
    private static  RetrofitClient Instance;


    public static RetrofitClient getInstance(Context context)
    {
        if(Instance==null)
            Instance=new RetrofitClient(context);


        return Instance;
    }


    private  RetrofitClient(Context context)
    {
//new
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        //
        retrofit=new Retrofit.Builder().baseUrl(context.getString(R.string.BaseUrl)).addConverterFactory(GsonConverterFactory.create(gson)).build();

    }



    public api getApi()
    {
        return retrofit.create(api.class);
    }











}
