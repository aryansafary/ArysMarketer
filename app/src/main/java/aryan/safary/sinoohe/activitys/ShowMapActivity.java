package aryan.safary.sinoohe.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.carto.graphics.Color;
import com.carto.styles.LineStyle;
import com.carto.styles.LineStyleBuilder;
import com.google.android.material.textview.MaterialTextView;
import org.neshan.common.model.LatLng;
import org.neshan.mapsdk.MapView;
import org.neshan.mapsdk.model.Polyline;
import org.neshan.mapsdk.style.NeshanMapStyle;

import java.util.ArrayList;
import java.util.List;

import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.NetworkChanged;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.LatLonItem;
import aryan.safary.sinoohe.data.LatLonModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowMapActivity extends AppCompatActivity {
    private MapView mapView ;
    private Double lat , lon ;
    private MaterialTextView region , times , startTime , endTime , status;
    private List<LatLonItem>list;
    private final ArrayList<LatLng>latLngs=new ArrayList<>();
private void setObjectId(){
    mapView=findViewById(R.id.ShowMapMapview);
    region=findViewById(R.id.ShowMapRegionText);
    times=findViewById(R.id.ShowMapTimeWorkText);
    startTime=findViewById(R.id.ShowMapTimeStartText);
    endTime=findViewById(R.id.ShowMapTimeEndText);
    status=findViewById(R.id.ShowMapStatusText);
}
private void init(){
    setObjectId();
    drawPolygon();
    mapView.setMapStyle(NeshanMapStyle.STANDARD_DAY);
    findViewById(R.id.ShowMapFindLocation).setOnClickListener(v -> drawPolygon());
    findViewById(R.id.ShowMapZoomLocation).setOnClickListener(v -> mapView.setZoom(mapView.getZoom()+14,mapView.getZoom()+14));
    findViewById(R.id.ShowMapZoomOutLocation).setOnClickListener(v -> mapView.setZoom(mapView.getZoom()-14,mapView.getZoom()-14));
}

    @SuppressLint("SuspiciousIndentation")
    private void getMap(){

String name = MySharedPrefrence.getInstance(ShowMapActivity.this).getSelectedName();
String data = MySharedPrefrence.getInstance(ShowMapActivity.this).getSelectedData();
if(name.isEmpty())
name=MySharedPrefrence.getInstance(ShowMapActivity.this).getName();



RetrofitClient.getInstance(ShowMapActivity.this).getApi().ShowLocation(name,data)
                .enqueue(new Callback<LatLonModel>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NonNull Call<LatLonModel> call, @NonNull Response<LatLonModel> response) {
                        if(response.isSuccessful()){
                            assert response.body() != null;
                            Toast.makeText(ShowMapActivity.this,"True",Toast.LENGTH_LONG).show();
                            list = response.body().getData();
                            LatLonItem latLonItem = response.body().getData().get(response.body().getData().size()-1);
                            region.setText(latLonItem.getRegion());
                            times.setText(latLonItem.getHours()+":"+latLonItem.getMinutes()+":"+latLonItem.getSeconds());
                            startTime.setText(latLonItem.getStart_time());
                            endTime.setText(latLonItem.getEnd_time());
                            lat=latLonItem.getLat();
                            lon=latLonItem.getLon();
                          String s= latLonItem.getStatus();
                          if(s.equals("0"))
                          status.setText("درحال کار");
                          else status.setText("کار به اتمام رسیده");
                        }else Log.d("Error", "onResponse: "+response.message()+response.code());
                    }

                    @Override
                    public void onFailure(@NonNull Call<LatLonModel> call, @NonNull Throwable t) {
                        Log.d("Field", "onFailure: "+t.getMessage());
                    }
                });


    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // starting app in full screen
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_map);
        init();
    }


    @Override
    protected void onResume() {
        super.onResume();
        NetworkChanged.InternetConnection(ShowMapActivity.this);
    }

    @SuppressLint("SetTextI18n")
    public void drawPolygon(){
        getMap();
        if(list!=null)
            for(int i =0;i<list.size();i++) {
               LatLonItem latLonItem = list.get(i);
               lat=latLonItem.getLat();
               lon=latLonItem.getLon();
               latLngs.add(new LatLng(lat,lon));
            }
        Polyline polyline = new Polyline(latLngs, getLineStyle());
        mapView.addPolyline(polyline);
        Toast.makeText(ShowMapActivity.this, lat+"__"+lon,Toast.LENGTH_LONG).show();
        if(lat !=null && lon!= null)
            mapView.moveCamera(new LatLng(lat,lon), 0.25f);
    }





    private LineStyle getLineStyle(){
        LineStyleBuilder lineStCr = new LineStyleBuilder();
        lineStCr.setColor(new Color((short) 2, (short) 119, (short) 189, (short)190));
        lineStCr.setWidth(12f);
        lineStCr.setStretchFactor(0f);
        return lineStCr.buildStyle();
    }






}