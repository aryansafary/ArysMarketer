package aryan.safary.sinoohe.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import aryan.safary.sinoohe.BuildConfig;
import aryan.safary.sinoohe.R;
import aryan.safary.sinoohe.classes.Const;
import aryan.safary.sinoohe.classes.MySharedPrefrence;
import aryan.safary.sinoohe.classes.NetworkChanged;
import aryan.safary.sinoohe.classes.RetrofitClient;
import aryan.safary.sinoohe.data.JsonResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewProductActivity extends AppCompatActivity {
    private MaterialButton submit;
    private ImageView imageView;
    private TextInputEditText name , brand , description  , price ;

    private String path;
    private Bitmap bitmap;

    private LottieAnimationView lottieAnimationView;
private void setObjectId(){
    submit=findViewById(R.id.newProduct_submit_btn);
    imageView=findViewById(R.id.newProduct_image);
    name=findViewById(R.id.newProduct_name);
    brand=findViewById(R.id.newProduct_brand);
    description=findViewById(R.id.newProduct_description);
    price=findViewById(R.id.newProduct_price);
    lottieAnimationView=findViewById(R.id.newProductLottie);
}
@RequiresApi(api = Build.VERSION_CODES.N)
private void init(){
    setObjectId();
    getPermission();
    imageView.setOnClickListener(v -> {
if(GalleryPermission || CameraPermission ) {
//    new MaterialAlertDialogBuilder(NewProductActivity.this).
//            setTitle(getString(R.string.new_product))
//            .setMessage(getString(R.string.new_product_quiz))
//            .setPositiveButton(getString(R.string.gallery_farsi), (dialog, which) -> {
//                Toast.makeText(NewProductActivity.this, "gallery", Toast.LENGTH_LONG).show();
                SelectImageFromGallery();
//
//            }).setNegativeButton(getString(R.string.camera_farsi), (dialog, which) -> {
//                Toast.makeText(NewProductActivity.this, "camera", Toast.LENGTH_LONG).show();
//                getFromCamera();
//            }).setNeutralButton(getString(R.string.cancel), (dialog, which) -> Toast.makeText(NewProductActivity.this, "Cancel ", Toast.LENGTH_LONG).show()).show();
}else getPermission();
    });
    submit.setOnClickListener(v -> {
            if(bitmap==null)
                Toast.makeText(this, "لطفا یک تصویر انتخاب کنید", Toast.LENGTH_LONG).show();
            else setProduct();
    });
}
private  void setProduct(){
if(!Objects.requireNonNull(name.getText()).toString().isEmpty()  &&
        !Objects.requireNonNull(brand.getText()).toString().isEmpty()  &&
        !Objects.requireNonNull(description.getText()).toString().isEmpty()  &&
        !Objects.requireNonNull(price.getText()).toString().isEmpty()  &&
        !toBase64(bitmap).isEmpty() )
    RetrofitClient.getInstance(NewProductActivity.this).getApi().
            NewProduct(name.getText().toString() ,
            brand.getText().toString(),
            description.getText().toString(),
            "",
            price.getText().toString(),
            toBase64(bitmap) ,
                    MySharedPrefrence.getInstance(NewProductActivity.this).getUser()).enqueue(new Callback<JsonResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<JsonResponseModel> call, @NonNull Response<JsonResponseModel> response) {
                    if(response.isSuccessful()){
                        new CountDownTimer(2000, 2000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                lottieAnimationView.playAnimation();
                                lottieAnimationView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onFinish() {
                                Toast.makeText(NewProductActivity.this,"محصول با موفقیت به لیست محصولات اضافه شد",Toast.LENGTH_LONG).show();
                                NewProductActivity.this.finish();
                                startActivity(new Intent(NewProductActivity.this,HomeActivity.class));
                            }
                        }.start();




                    }else {
                       Toast.makeText(NewProductActivity.this,"Error",Toast.LENGTH_LONG).show();
                        assert response.errorBody() != null;
                        Log.d("Error_New", "onResponse: "+response.code()+ response.errorBody().source());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonResponseModel> call, @NonNull Throwable t) {
                    Log.d("Field", "onFailure: "+t.getMessage());
                }
            });
else {
    Toast.makeText(NewProductActivity.this,"لطفا اطلاعات را تکمیل کنید",Toast.LENGTH_LONG).show();
}

}
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        init();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            assert data != null;
            if (data.getData()!=null && requestCode == Const.GalleryPermissionRequestCode) {
                    Picasso.get().load(data.getData()).fit().centerCrop().placeholder(R.drawable.ic_new_product_image_form).into(imageView);

                    try {
                        bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }}
            else {
                if (requestCode == Const.CameraPermissionRequestCode) {
                    if (path != null) {
                        Picasso.get().load(Uri.parse(path)).fit().centerCrop().placeholder(R.drawable.ic_new_product_image_form).into(imageView);
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(path));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            }
        }


    @Override
    protected void onResume() {
        super.onResume();
        NetworkChanged.InternetConnection(NewProductActivity.this);
    }

    private String toBase64(Bitmap bitmap) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);

    }






    // used to track request permissions
    private  boolean GalleryPermission=false;
    private  boolean CameraPermission=false;
    //  final int REQUEST_CODE = 123;
    // location updates interval - 1 sec
    // fastest updates interval - 1 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle

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
    public void getPermission() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        GalleryPermission = true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            finish();
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

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        CameraPermission = true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            finish();
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    private File CreateFile() throws IOException {
        String data =new SimpleDateFormat("_yyyyMMdd_HHmmss", Locale.US).format(new Date());
        File a=  File.createTempFile(MySharedPrefrence.getInstance(NewProductActivity.this).getUser()+data,".JPG", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        path=a.getAbsolutePath();
        return a;
    }




    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getFromCamera() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(NewProductActivity.this,"com.example.android.fileprovider",CreateFile()));
            startActivityForResult(intent,Const.CameraPermissionRequestCode);
        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    private  void SelectImageFromGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"عکس محصول را انتخاب کنید"),Const.GalleryPermissionRequestCode);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NewProductActivity.this,HomeActivity.class));
    }
}