package aryan.safary.sinoohe.activitys;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.carto.graphics.Color;
import com.carto.styles.LineStyle;
import com.carto.styles.LineStyleBuilder;
import com.carto.styles.MarkerStyle;
import com.carto.styles.MarkerStyleBuilder;
import com.carto.utils.BitmapUtils;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;
import org.neshan.common.model.LatLng;
import org.neshan.mapsdk.MapView;
import org.neshan.mapsdk.model.Marker;
import org.neshan.mapsdk.model.Polyline;
import org.neshan.mapsdk.style.NeshanMapStyle;
import java.util.ArrayList;
import java.util.List;
import aryan.safary.sinoohe.BuildConfig;
import aryan.safary.sinoohe.classes.NetworkChanged;
import aryan.safary.sinoohe.services.LocationService;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.JsonResponseModel;
import aryan.safary.sinoohe.data.LatLonItem;
import aryan.safary.sinoohe.data.LatLonModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MapActivity extends AppCompatActivity {
    private static final String TAG = MapActivity.class.getName();
    private List<LatLonItem> LatLon;
    ArrayList<LatLng> latLngs = new ArrayList<>();
private void SaveLocation(String username , double lat , double lon , String hours  , String minutes  , String seconds  , String region ) {

    RetrofitClient.getInstance(MapActivity.this).getApi().LocationUser(username,lat,lon,hours,minutes,seconds,region)
            .enqueue(new Callback<JsonResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
                    if(response.isSuccessful())
                        Toast.makeText(MapActivity.this, "ok", Toast.LENGTH_SHORT).show();
                    else {
                        assert response.errorBody() != null;
                        Log.d("Error", "onResponse: "+ response.errorBody().source() + response.body() + response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
                    Log.d(TAG, "onFailure: "+t.getMessage());
                }
            });
}
private void getLatLon(){
    RetrofitClient.getInstance(MapActivity.this).getApi().getLocationLatLon(MySharedPrefrence.getInstance(MapActivity.this).getUser()).enqueue(new Callback<LatLonModel>() {
        @Override
        public void onResponse(@NonNull Call<LatLonModel> call, @NonNull Response<LatLonModel> response) {
            if(response.isSuccessful()){
                Toast.makeText(MapActivity.this,"True",Toast.LENGTH_LONG).show();
                assert response.body() != null;
               LatLon=response.body().getData();
            }else {
                assert response.errorBody() != null;
                Log.d("ErrorGetLocation", "onResponse: "+ response.code() + response.errorBody().source());
            }
        }

        @Override
        public void onFailure(@NonNull Call<LatLonModel> call, @NonNull Throwable t) {
            Log.d("FieldGetLocation", "onFailure: "+t.getMessage() + call);
        }
    });


}
private void CloseWork(){

    RetrofitClient.getInstance(MapActivity.this).getApi().CloseLocation(MySharedPrefrence.getInstance(MapActivity.this).getUser()).enqueue(new Callback<JsonResponseModel>() {
        @Override
        public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
            if(response.isSuccessful()){
                Toast.makeText(MapActivity.this,"خسته نباشید",Toast.LENGTH_LONG).show();
            }

            else {
                assert response.errorBody() != null;
                Log.d("Error", "onResponse: "+response.code()+ response.errorBody().source());
            }
        }

        @Override
        public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {

        }
    });
}
private void CheckWorking(){
    RetrofitClient.getInstance(MapActivity.this).getApi().
            CheckWorking(MySharedPrefrence.getInstance(MapActivity.this).getUser()).enqueue(new Callback<JsonResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
                  if(response.isSuccessful())  {
                  switch (response.code()){
                      case 200 :
                        StartWorkBtn.setEnabled(false);
                        Toast.makeText(MapActivity.this,"شما امروز سرکار رفتید",Toast.LENGTH_LONG).show();
                          break;
                      case 201 :
                          StartWorkBtn.setEnabled(true);
                          Toast.makeText(MapActivity.this,"موفق باشید",Toast.LENGTH_LONG).show();
                          assert response.body() != null;
                          NearbyLocText.setText(response.body().getMyMessage());
                          break;
                  }
                }
                else  Log.d("Error", "onResponse: "+response.code());
            }
                @Override
                public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
                    Log.d("Field", "onFailure: "+t.getMessage());
                }
            });
}
private void CheckWorkUser(){
    if(MySharedPrefrence.getInstance(MapActivity.this).getWork()){
             if(MySharedPrefrence.getInstance(MapActivity.this).getSecondsWork().isEmpty() &&
                MySharedPrefrence.getInstance(MapActivity.this).getMinutesWork().isEmpty() &&
                MySharedPrefrence.getInstance(MapActivity.this).getMinutesWork().isEmpty()){
                 s=0;
                 m=0;
                 h=0;
                 MySharedPrefrence.getInstance(MapActivity.this).setSecondsWork(s+"");
                 MySharedPrefrence.getInstance(MapActivity.this).setMinutesWork(m+"");
                 MySharedPrefrence.getInstance(MapActivity.this).setHoursWork(h+"");
             }else {
                s=   Integer.parseInt( MySharedPrefrence.getInstance(MapActivity.this).getSecondsWork()) ;
                m=Integer.parseInt( MySharedPrefrence.getInstance(MapActivity.this).getMinutesWork());
                h=Integer.parseInt( MySharedPrefrence.getInstance(MapActivity.this).getHoursWork());
             }
             countDownTimer.start();
    }
    else {
        countDownTimer.cancel();
        s=0;
        m=0;
        h=0;
        MySharedPrefrence.getInstance(MapActivity.this).setSecondsWork(s+"");
        MySharedPrefrence.getInstance(MapActivity.this).setMinutesWork(m+"");
        MySharedPrefrence.getInstance(MapActivity.this).setHoursWork(h+"");
    }

}
    // used to track request permissions
    final int REQUEST_CODE = 123;
    // location updates interval - 1 sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    // fastest updates interval - 1 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000;


    private int h =0 , m=0,   s=0;

private final CountDownTimer countDownTimer = new CountDownTimer(8640000,1000) {
    @SuppressLint("SetTextI18n")
    @Override
    public void onTick(long millisUntilFinished) {
        MySharedPrefrence.getInstance(MapActivity.this).setSecondsWork(s+"");
        MySharedPrefrence.getInstance(MapActivity.this).setMinutesWork(m+"");
        MySharedPrefrence.getInstance(MapActivity.this).setHoursWork(h+"");
        s++;
        if(MySharedPrefrence.getInstance(MapActivity.this).getSecondsWork().equals("60")){
            s=0;
            MySharedPrefrence.getInstance(MapActivity.this).setSecondsWork(s+"");
            MySharedPrefrence.getInstance(MapActivity.this).setMinutesWork(m+"");
            MySharedPrefrence.getInstance(MapActivity.this).setHoursWork(h+"");
            m++;
        }
        if(MySharedPrefrence.getInstance(MapActivity.this).getMinutesWork().equals("60")){
           m=0;
           h++;
            MySharedPrefrence.getInstance(MapActivity.this).setSecondsWork(s+"");
            MySharedPrefrence.getInstance(MapActivity.this).setMinutesWork(m+"");
            MySharedPrefrence.getInstance(MapActivity.this).setHoursWork(h+"");
        }
        if(MySharedPrefrence.getInstance(MapActivity.this).getHoursWork().equals("60")){
            s=0;
            m=0;
            h=0;
            MySharedPrefrence.getInstance(MapActivity.this).setSecondsWork(s+"");
            MySharedPrefrence.getInstance(MapActivity.this).setMinutesWork(m+"");
            MySharedPrefrence.getInstance(MapActivity.this).setHoursWork(h+"");
        }
        if(Integer.parseInt(MySharedPrefrence.getInstance(MapActivity.this).getSecondsWork())%5==0)
       if(userLocation!=null)
        SaveLocation(
                MySharedPrefrence.getInstance(MapActivity.this).getUser() ,
                userLocation.getLatitude(),
                userLocation.getLongitude(),
                MySharedPrefrence.getInstance(MapActivity.this).getHoursWork(),
                MySharedPrefrence.getInstance(MapActivity.this).getMinutesWork(),
                MySharedPrefrence.getInstance(MapActivity.this).getSecondsWork(),
                NearbyLocText.getText().toString());
 TimeText.setText(MySharedPrefrence.getInstance(MapActivity.this).getHoursWork()+":"+
                MySharedPrefrence.getInstance(MapActivity.this).getMinutesWork()+":"+
                MySharedPrefrence.getInstance(MapActivity.this).getSecondsWork());





    }



    @Override
    public void onFinish() {
        MySharedPrefrence.getInstance(MapActivity.this).setSecondsWork(s+"");
        MySharedPrefrence.getInstance(MapActivity.this).setMinutesWork(m+"");
        MySharedPrefrence.getInstance(MapActivity.this).setHoursWork(h+"");

    }
};

private MaterialTextView NearbyLocText , TimeText;





    // map UI element
    private MapView map;
private MaterialButton StartWorkBtn;
    // User's current location
    private Location userLocation;
private double LatStart , LonStart;
    private FusedLocationProviderClient fusedLocationClient;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    // boolean flag to toggle the ui
    private Marker marker;




    private void setObjectId(){
    map =findViewById(R.id.MapMapview);
    StartWorkBtn=findViewById(R.id.MapStartWork);
    NearbyLocText=findViewById(R.id.MapLocWorkText);
    TimeText=findViewById(R.id.MapTimeWorkText);
    if(MySharedPrefrence.getInstance(MapActivity.this).getWork())
        StartWorkBtn.setText(getString(R.string.stop_work_farsi));
    else StartWorkBtn.setText(getString(R.string.start_work_farsi));


}
//private void MarkerStart (LatLng loc){
    //remove existing marker from map
      //  if (marker1 != null) {
       // map.removeMarker(marker1);
   // }
    // Creating marker style. We should use an object of type MarkerStyleCreator, set all features on it
    // and then call buildStyle method on it. This method returns an object of type MarkerStyle
   // MarkerStyleBuilder markStCr = new MarkerStyleBuilder();
    //    markStCr.setSize(30f);
   //     markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker)));
   // MarkerStyle markSt = markStCr.buildStyle();
    // Creating user marker
  //  marker = new Marker(loc, markSt);

    // Adding user marker to map!
    //    map.addMarker(marker);
//}
//    private Marker createMarker(LatLng loc) {
//        // Creating animation for marker. We should use an object of type AnimationStyleBuilder, set
//        // all animation features on it and then call buildStyle() method that returns an object of type
//        // AnimationStyle
//        AnimationStyleBuilder animStBl = new AnimationStyleBuilder();
//        animStBl.setFadeAnimationType(AnimationType.ANIMATION_TYPE_SMOOTH STEP);
//        animStBl.setSizeAnimationType(AnimationType.ANIMATION_TYPE_SPRING);
//        animStBl.setPhaseInDuration(0.5f);
//        animStBl.setPhaseOutDuration(0.5f);
//        animSt = animStBl.buildStyle();
        // Creating marker style. We should use an object of type MarkerStyleCreator, set all features on it
        // and then call buildStyle method on it. This method returns an object of type MarkerStyle
//        MarkerStyleBuilder markStCr = new MarkerStyleBuilder();
//        markStCr.setSize(30f);
//        markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker)));
//        // AnimationStyle object - that was created before - is used here
//        markStCr.setAnimationStyle(animSt);
//        MarkerStyle markSt = markStCr.buildStyle();
//
//        // Creating marker
//        return new Marker(loc, markSt);
//    }

    @SuppressLint("SuspiciousIndentation")
    private void init(){
    setObjectId();
     map.setMapStyle(NeshanMapStyle.NESHAN);
    findViewById(R.id.MapFindLocation).setOnClickListener(this::focusOnUserLocation);
    findViewById(R.id.MapZoomLocation).setOnClickListener(v -> {
        if(userLocation!=null)
            map.moveCamera(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), 0.25f);
           map.setZoom(map.getZoom()+2,0);
    });
    findViewById(R.id.MapZoomOutLocation).setOnClickListener(v -> {
        if(userLocation!=null)
            map.moveCamera(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), 0.25f);
        map.setZoom(map.getZoom()-2,0);
    });
    StartWorkBtn.setOnClickListener(v -> {
            if (!MySharedPrefrence.getInstance(MapActivity.this).getWork()) {
                MySharedPrefrence.getInstance(MapActivity.this).setWork(true);
                StartWorkBtn.setText(getString(R.string.stop_work_farsi));
                LatStart = userLocation.getLatitude();
                LonStart = userLocation.getLongitude();
                Snackbar.make(v, LatStart + "" + LonStart, BaseTransientBottomBar.LENGTH_LONG).show();
                countDownTimer.start();
                UserTrip();
                CheckWorking();
                ContextCompat.startForegroundService(MapActivity.this, new Intent(MapActivity.this, LocationService.class));

//        }else Toast.makeText(MapActivity.this, "لطفا پیش از کاز از اتصال خود حاصل فرمایید", Toast.LENGTH_SHORT).show();}
            }else{
                    MySharedPrefrence.getInstance(MapActivity.this).setWork(false);
                    StartWorkBtn.setText(getString(R.string.start_work_farsi));
                    countDownTimer.cancel();
                    UserTrip();
                    CloseWork();
                    this.finish();
                    stopService(new Intent(MapActivity.this, LocationService.class));


                }



    });
        CheckWorkUser();

    }



    @SuppressLint("SuspiciousIndentation")
    private void UserTrip(){
        getLatLon();
        if(LatLon!=null)
            for(int i =0;i<LatLon.size();i++)
                latLngs.add(new LatLng(LatLon.get(i).getLat(),LatLon.get(i).getLon()))    ;

        Polyline polyline = new Polyline(latLngs, getLineStyle());
        map.addPolyline(polyline);
        if(userLocation!=null) map.moveCamera(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), 0.25f);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // starting app in full screen
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_map);
init();
    }


 //TODO DRAWING FACT -------------------------------------------------------------

    // Drawing circle on map
   // public Circle drawCircle(View view , ArrayList<LatLng>latLngs){
        // Here we use getLineStyle() method to define line styles
      //  Circle circle = new Circle(new LatLng(35.769368,51.327650), 100, new Color((short) 2, (short) 119, (short) 189, (short)190), getLineStyle1drawCircle());
        // adding the created circle on map
      //  map.addCircle(circle);
        // Focusing camera on center point of circle
      //  map.moveCamera(new LatLng(35.769368,51.327650),0.25f);
      //  map.setZoom(14,0);
      //  return circle;
   // }


    //Todo Draw Line
// Drawing line on map
//public Polyline drawLine(View view , ArrayList<LatLng>latLngs) {
    // Adding some LatLng points to a latLngs
//    latLngs.add(new LatLng(LatStart,LonStart));
//    latLngs.add(new LatLng(userLocation.getLatitude(),userLocation.getLongitude()));
    // Creating a line from LineGeom. here we use getLineStyle() method to define line styles
   // Polyline polyline = new Polyline(latLngs, getLineStyle());
    // adding the created line to lineLayer, showing it on map
//    map.addPolyline(polyline);
//    // focusing camera on first point of drawn line
//    map.moveCamera(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), 0.25f);
//    map.setZoom(14, 0);
//    return polyline;
//}
    // In this method we create a LineStyleCreator, set its features and call buildStyle() method
    // on it and return the LineStyle object (the same routine as crating a marker style)
    private LineStyle getLineStyle() {
        LineStyleBuilder lineStCr = new LineStyleBuilder();
        lineStCr.setColor(new Color((short) 3, (short) 190, (short)200,(short) 210));
        lineStCr.setWidth(12f);
        lineStCr.setStretchFactor(0f);
        return lineStCr.buildStyle();
    }




    // Todo this Blow Thread For Find Location User_____________________________________________________

    @Override
    protected void onStart() {
        super.onStart();
        // everything related to ui is initialized here
        initLayoutReferences();
        // Initializing user location
        initLocation();
        startReceivingLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
        CheckWorking();
        NetworkChanged.InternetConnection(MapActivity.this);





    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check for the integer request code originally supplied to startResolutionForResult().
        if (requestCode == REQUEST_CODE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Log.e(TAG, "User agreed to make required location settings changes.");
                    // Nothing to do. startLocation-updates() gets called in onResume again.
                    break;
                case Activity.RESULT_CANCELED:
                    Log.e(TAG, "User chose not to make required location settings changes.");
                    break;
            }
        }
    }

    private void initLayoutReferences() {
        // Initializing views
        setObjectId();
        // Initializing mapView element
        initMap();



    }
    // Initializing map
    private void initMap() {
        // Setting map focal position to a fixed position and setting camera zoom
        if(userLocation!=null){
        map.moveCamera(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), 0);
        map.setZoom(14, 0);
        }
    }

    @SuppressLint("VisibleForTests")
    private void initLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        settingsClient = LocationServices.getSettingsClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                userLocation = locationResult.getLastLocation();
                onLocationChange();
            }
        };

        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();

    }


    private void startLocationUpdates() {

        settingsClient
                .checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(this, locationSettingsResponse -> {
                    Log.i(TAG, "All location settings are satisfied.");

                    //noinspection MissingPermission
                    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

                    onLocationChange();
                })
                .addOnFailureListener(this, e -> {
                    int statusCode = ((ApiException) e).getStatusCode();
                    switch (statusCode) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                    "location settings ");
                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the
                                // result in onActivityResult().
                                ResolvableApiException rae = (ResolvableApiException) e;
                                rae.startResolutionForResult(MapActivity.this, REQUEST_CODE);
                            } catch (IntentSender.SendIntentException sie) {
                                Log.i(TAG, "PendingIntent unable to execute request.");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            String errorMessage = "Location settings are inadequate, and cannot be " +
                                    "fixed here. Fix in Settings.";
                            Log.e(TAG, errorMessage);

                            Toast.makeText(MapActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    }

                    onLocationChange();
                });
    }

    public void stopLocationUpdates() {
        // Removing location updates
        fusedLocationClient
                .removeLocationUpdates(locationCallback)
                .addOnCompleteListener(this, task -> {
                  //  Toast.makeText(getApplicationContext(), "Location updates stopped!", Toast.LENGTH_SHORT).show();
                });
    }

    public void startReceivingLocationUpdates() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).check();
    }

    private void openSettings() {

        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void onLocationChange() {
        if (userLocation != null) {
            addUserMarker(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()));
        }

    }

    // This method gets a LatLng as input and adds a marker on that position
    private void addUserMarker(LatLng loc) {
        //remove existing marker from map
        if (marker != null) {
            map.removeMarker(marker);
        }
        // Creating marker style. We should use an object of type MarkerStyleCreator, set all features on it
        // and then call buildStyle method on it. This method returns an object of type MarkerStyle
        MarkerStyleBuilder markStCr = new MarkerStyleBuilder();
        markStCr.setSize(30f);
        markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker)));
        MarkerStyle markSt = markStCr.buildStyle();
        // Creating user marker
        marker = new Marker(loc, markSt);

        // Adding user marker to map!
        map.addMarker(marker);
    }

    public void focusOnUserLocation(View view) {
        if (userLocation != null) {
            map.moveCamera(
                    new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), 0.25f);
            map.setZoom(15, 0.25f);
        }
    }

















}


