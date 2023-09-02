package aryan.safary.sinoohe.classes;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MySharedPrefrence {

    private final EncryptedSharedPreferences sp ;
  //  private final SharedPreferences sp;
  SharedPreferences.Editor editor;
    //private final SharedPreferences.Editor editor;
    private static MySharedPrefrence instance;

    // on below line getting data from shared preferences.
    // creating a master key for encryption of shared preferences.
    String masterKeyAlias;
















    public static MySharedPrefrence getInstance(Context context) {
        if (instance == null)
            instance = new MySharedPrefrence(context);

        return instance;
    }

    private MySharedPrefrence(Context context) {
        try {
            masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        try {
            sp=(EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                    // passing a file name to share a preferences
                    "file",
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        //--
        // >sp = context.getSharedPreferences("myapp", Context.MODE_PRIVATE );
        // editor = sp.edit();
        editor=sp.edit();
    }

    public void ClearSharedPrefrence() {
        editor.clear().commit();

    }










    public void setIsLogin(boolean isLogin) {

        editor.putBoolean("isLogin", isLogin).apply();
    }
    public boolean getIsLogin() {
        return sp.getBoolean("isLogin", false);
    }

    public void setWork(boolean work) {

        editor.putBoolean("work", work).apply();
    }
    public boolean getWork() {
        return sp.getBoolean("work", false);
    }




    public int getRequestFilter() {
        return sp.getInt("checkbox_number", 1);
    }

    public void setRequestFilter(int checkbox_number) {
        editor.putInt("checkbox_number", checkbox_number).apply();
    }


    public int getTransActionFilter() {
        return sp.getInt("checkbox_number", 1);
    }

    public void setTransActionFilter(int checkbox_number) {
        editor.putInt("checkbox_number", checkbox_number).apply();
    }

    public void setCompany(String company) {
        editor.putString("company", company).apply();
    }

    public String getCompany() {
        return sp.getString("company", "");
                //securityKey.getAsciiConvertedToText(text);
        //651141211115

    }

    public void setUserStatus(String UserStatus) {
        editor.putString("UserStatus", UserStatus).apply();
    }

    public String getUserStatus() {
        return sp.getString("UserStatus", "");
    }




    public void setUserId(String UserId) {
        editor.putString("UserId", UserId).apply();
    }

    public String getUserId() {
        return sp.getString("UserId", "");
    }



    public void setProductId(String ProductId) {
        editor.putString("ProductId", ProductId).apply();
    }

    public String getProductId() {
        return sp.getString("ProductId", "");
    }



    public void setHoursWork(String hours) {
        editor.putString("hours", hours).apply();
    }

    public String getHoursWork() {
        return sp.getString("hours", "");
    }

    public void setMinutesWork(String minutes) {
        editor.putString("minutes", minutes).apply();
    }

    public String getMinutesWork() {
        return sp.getString("minutes", "");
    }

    public void setSecondsWork(String seconds) {
        editor.putString("seconds", seconds).apply();
    }

    public String getSecondsWork() {
        return sp.getString("seconds", "");
    }


    public void setPage(String page) {
        editor.putString("page", page).apply();
    }

    public String getPage() {
        return sp.getString("page", "");
    }

    public void setSelectedName(String name) {
        editor.putString("name", name).apply();
    }

    public String getSelectedName() {
        return sp.getString("name", "");
    }



    public void setSelectedData(String data) {
        editor.putString("data", data).apply();
    }

    public String getSelectedData() {
        return sp.getString("data", "");
    }




    public void setUser(String username) {
        editor.putString("username", username).apply();
    }

    public String getUser() {
        return sp.getString("username", "");
    }

    public void setName(String Name) {
        editor.putString("Name", Name).apply();
    }

    public String getName() {
        return sp.getString("Name", "");
    }

    public void setSide(String side) {
        editor.putString("side", side).apply();
    }

    public String getSide() {
        return sp.getString("side", "");
    }

    public void setPhone(String Phone) {
            editor.putString("Phone", Phone).apply();
    }

    public String getPhone() {
        return sp.getString("Phone", "");
    }

    public void setToken(String Token) {
        editor.putString("Token", Token).apply();
    }

    public String getToken() {
            return sp.getString("Token", "");
    }

    public void setEmail(String Email) {
        editor.putString("Email", Email).apply();
    }
    public String getEmail() {
        return sp.getString("Email", "");
    }


    public void setImage(String Image) {
        editor.putString("Image", Image).apply();
    }
    public String getImage() {
        return sp.getString("Image", "");
    }

    public void setBarthday(String Barthday) {
        editor.putString("Barthday", Barthday).apply();
    }
    public String getBarthday() {
        return sp.getString("Barthday", "");
    }

    public void setDta(String Data) {
        editor.putString("Barthday", Data).apply();
    }
    public String getData() {
        return sp.getString("Data", "");
    }

    public void setLocation(String Location) {
        editor.putString("Location", Location).apply();
    }
    public String getLocation() {
        return sp.getString("Location", "");
    }






    public void setPharmacyName(String PharmacyName) {
        editor.putString("PharmacyName", PharmacyName).apply();
    }

    public String getPharmacyName() {
        return sp.getString("PharmacyName", "");
    }

    public void setProductName(String ProductName) {
        editor.putString("ProductName", ProductName).apply();
    }

    public String getProductName() {
        return sp.getString("ProductName", "");
    }



    public void setUpdateNameProduct(String product_name) {
        editor.putString("product_name", product_name).apply();
    }
    public String getUpdateNameProduct() {
        return sp.getString("product_name", "");
    }
    public void setUpdateBrandProduct(String product_brand) {
        editor.putString("product_brand", product_brand).apply();
    }
    public String getUpdateBrandProduct() {
        return sp.getString("product_brand", "");
    }
    public void setUpdateDescriptionProduct(String product_description) {
        editor.putString("product_description", product_description).apply();
    }
    public String getUpdateDescriptionProduct() {
        return sp.getString("product_description", "");
    }
    public void setUpdatePriceProduct(String product_price) {
        editor.putString("product_price", product_price).apply();
    }
    public String getUpdatePriceProduct() {
        return sp.getString("product_price", "");
    }


    public void setCompanyAddress(String companyAddress) {
        editor.putString("CompanyAddress", companyAddress).apply();
    }

    public String getCompanyAddress() {
        return sp.getString("CompanyAddress", "");
    }

    public void setCity(String city) {
        editor.putString("City", city).apply();
    }

    public String getCity() {
        return sp.getString("City", "");
    }

    public void setProvince(String Province) {
        editor.putString("Province", Province).apply();
    }

    public String getProvince() {
        return sp.getString("Province", "");
    }





    public void setUpdateNamePharmacy(String pharmacy_name) {
        editor.putString("pharmacy_name", pharmacy_name).apply();
    }
    public String getUpdateNamePharmacy() {
        return sp.getString("pharmacy_name", "");
    }

    public void setUpdateLocationPharmacy(String pharmacy_Location) {
        editor.putString("pharmacy_Location", pharmacy_Location).apply();
    }
    public String getUpdateLocationPharmacy() {
        return sp.getString("pharmacy_Location", "");
    }

    public void setUpdatePhonePharmacy(String pharmacy_Phone) {
        editor.putString("pharmacy_Phone", pharmacy_Phone).apply();
    }
    public String getUpdatePhonePharmacy() {
        return sp.getString("pharmacy_Phone", "");
    }

    public void setUpdateIdPharmacy(String pharmacy_Id) {
        editor.putString("pharmacy_Id", pharmacy_Id).apply();
    }
    public String getUpdateIdPharmacy() {
        return sp.getString("pharmacy_Id", "");
    }



}
