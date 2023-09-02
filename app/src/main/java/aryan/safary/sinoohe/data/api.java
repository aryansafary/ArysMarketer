package aryan.safary.sinoohe.data;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;



public interface api {

    @FormUrlEncoded
    @POST("ChangePassword.php")
    Call<JsonResponseModel> CheckPassword(@Field("username")  String username ,
                                          @Field("this_pass") String this_pass ,
                                          @Field("new_pass")  String new_pass );




    @FormUrlEncoded
    @POST("checkUser.php")
    Call<JsonResponseModel> CheckUser(@Field("username") String username);

    @FormUrlEncoded
    @POST("getTransaction.php")
    Call<trans_id_model> getTransactionList(@Field("username") String username);

    @FormUrlEncoded
    @POST("getSubscriptionList.php")
    Call<SubscriptionModel> getSubscriptionList(@Field("company") String company);



    @FormUrlEncoded
    @POST("DeleteUser.php")
    Call<JsonResponseModel> DeleteUser( @Field("id") String id,
                                        @Field("company") String company);



    @FormUrlEncoded
    @POST("getUserList.php")
    Call<UserModel> getUserList( @Field("company") String company,
                                         @Field("companyAddress") String companyAddress);




    @FormUrlEncoded
    @POST("EditCenter.php")
    Call<JsonResponseModel> EditCenterPharmacy( @Field("name") String name,
                                            @Field("location") String location,
                                            @Field("phone") String phone ,
                                            @Field("id") String id );




    @FormUrlEncoded
    @POST("EditProduct.php")
    Call<JsonResponseModel> EditProduct(    @Field("name") String name,
                                            @Field("brand") String brand,
                                            @Field("description") String description ,
                                            @Field("id") String id ,
                                            @Field("price") String price);


    @FormUrlEncoded
    @POST("DeleteCenter.php")
    Call<JsonResponseModel> DeleteCenter(
                                            @Field("company") String company ,
                                            @Field("id") String id);





    @FormUrlEncoded
    @POST("getSubscription.php")
    Call<SubscriptionModel> getSubscription(@Field("username") String username,
                                            @Field("company") String company ,
                                            @Field("side") String side);


    @FormUrlEncoded
    @POST("DeleteProduct.php")
    Call<JsonResponseModel> DeleteProduct(@Field("username") String username,
                                            @Field("company") String company ,
                                            @Field("name") String name,
                                            @Field("id") String  id);




    @FormUrlEncoded
    @POST("SignUp.php")
    Call<JsonResponseModel> SignUp(@Field("name") String name,
                                   @Field("username") String username,
                                   @Field("password") String password,
                                   @Field("email") String email,
                                   @Field("phone") String phone,
                                   @Field("token") String token,
                                   @Field("company") String company,
                                   @Field("companyAddress") String companyAddress,
                                   @Field("barthday") String barthday,
                                   @Field("city") String city,
                                   @Field("province") String province,
                                   @Field("location") String location

                                   );





    @FormUrlEncoded
    @POST("message.php")
    Call<JsonResponseModel> sendMessage(@Field("text") String text,
                                        @Field("username") String username);


    @FormUrlEncoded
    @POST("NewUser.php")
    Call<JsonResponseModel> NewUser(@Field("side") String side,
                                      @Field("username") String username ,
                                      @Field("password") String password ,
                                      @Field("user") String user
    );


    @FormUrlEncoded
    @POST("Login.php")
    Call<JsonResponseModel> loginUser(@Field("username") String username ,
                                       @Field("password") String password ,
                                      @Field("token") String token);


    @FormUrlEncoded
    @POST("InsertStore.php")
    Call<JsonResponseModel> InsertStore(@Field("name") String name ,
                                        @Field("count") String count ,
                                        @Field("username") String username
                                      );
//TODO _________________________USER

    @FormUrlEncoded
    @POST("Location.php")
    Call<JsonResponseModel> LocationUser(@Field("username") String username ,
                                         @Field("lat") double lat ,
                                         @Field("lon") double lon ,
                                         @Field("hours") String hours ,
                                         @Field("minutes") String minutes ,
                                         @Field("seconds") String seconds ,
                                         @Field("region") String region);



    @FormUrlEncoded
    @POST("NewProduct.php")
    Call<JsonResponseModel> NewProduct(@Field("name") String name ,
                                         @Field("brand") String brand ,
                                         @Field("description") String description ,
                                         @Field("Ingredients") String Ingredients ,
                                         @Field("price") String price ,
                                         @Field("image") String image,
                                         @Field("username") String username
                                        );



    @FormUrlEncoded
    @POST("transaction_payment.php")
    Call<JsonResponseModel> InsertTransactionPayment(@Field("username") String username ,
                                              @Field("amount") int amount ,
                                              @Field("order_id") String order_id ,
                                              @Field("code") int code ,
                                              @Field("trans_id") String trans_id);





    @FormUrlEncoded
    @POST("CheckWorking.php")
    Call<JsonResponseModel> CheckWorking(@Field("username")String username);


    @FormUrlEncoded
    @POST("Check_Order_id.php")
    Call<JsonResponseModel> getOrderId(@Field("username")String username);



    @FormUrlEncoded
    @POST("https://nextpay.org/nx/gateway/token")
    Call<trans_id_item> getNexPayToken(
            @Field("api_key")String api_key,
            @Field("order_id")String order_id,
            @Field("amount")int amount,
            @Field("callback_uri")String callback_uri);









    @SerializedName("getPayment.php")
    @GET("getPayment.php")
    Call<PaySubscriptionModel> getPayment();





    @FormUrlEncoded
    @POST("getDataLocation.php")
    Call<DataModel> getDataLocation(@Field("username")String username);


    @FormUrlEncoded
    @POST("getLocation.php")
    Call<LatLonModel> getLocationLatLon(@Field("username")String username);


    @FormUrlEncoded
    @POST("ShowLocation.php")
    Call<LatLonModel> ShowLocation(@Field("username")String username ,@Field("data")String data  );




    @FormUrlEncoded
    @POST("CloseLocation.php")
    Call<JsonResponseModel> CloseLocation(@Field("username")String username);





    @FormUrlEncoded
    @POST("EditProfile.php")
    Call<JsonResponseModel> EditProfile(
                                      @Field("id") String id ,
                                      @Field("name") String name ,
                                      @Field("username") String username ,
                                      @Field("city") String city ,
                                      @Field("province") String province ,
                                      @Field("location") String location ,
                                      @Field("phone") String phone ,
                                      @Field("email") String email ,
                                      @Field("barthday") String barthday
                                        );




    @FormUrlEncoded
    @POST("InsertPharmacyAnd.php")
    Call<JsonResponseModel> InsertPharmacy(
            @Field("name") String name ,
            @Field("user_id") String user_id ,
            @Field("city") String city ,
            @Field("province") String province ,
            @Field("location") String location ,
            @Field("phone") String phone ,
            @Field("activity") String activity ,
            @Field("company") String company
    );




    @FormUrlEncoded
    @POST("getUser.php")
    Call<UserModel> getUser(@Field("username") String username);






@FormUrlEncoded
@SerializedName("DeleteRequestDeducted.php")
@POST("DeleteRequestDeducted.php")
Call<JsonResponseModel> DeleteRequestDeducted(@Field("id") String id ,
                                              @Field("username") String username);
//TODO ________________________USER
    @FormUrlEncoded
    @SerializedName("updateRequest.php")
    @POST("updateRequest.php")
    Call<JsonResponseModel> updateFright(@Field("id")String id );
@FormUrlEncoded
    @SerializedName("getFreight.php")
    @POST("getFreight.php")
    Call<FreightModel> getFright(@Field("username") String username );
//TODO _________________________________________________USER

@FormUrlEncoded
    @SerializedName("getStore.php")
    @POST("getStore.php")
    Call<StoreModel> getStore(@Field("filter_number") int filter_number ,
                              @Field("username") String username );
//TODO __________________________________USER
    @FormUrlEncoded
    @SerializedName("getReport.php")
    @POST("getReport.php")
    Call<ReportModel> getReport(@Field("sender") String sender );

    @FormUrlEncoded
    @SerializedName("getCalculator.php")
    @POST("getCalculator.php")
    Call<CalculatorModel> getCalculator(@Field("username") String username );

    @FormUrlEncoded
    @SerializedName("Calculator.php")
    @POST("Calculator.php")
    Call<JsonResponseModel> setCalculator(
            @Field("name") String name,

            @Field("sat_region") String sat_region,
            @Field("sat_des") String sat_des,

            @Field("sun_region") String sun_region,
            @Field("sun_des") String sun_des,

            @Field("mon_region") String mon_region,
            @Field("mon_des") String mon_des,

            @Field("tus_region") String tus_region,
            @Field("tus_des") String tus_des,

            @Field("wed_region") String wed_region,
            @Field("wed_des") String wed_des,

            @Field("thu_region") String thu_region,
            @Field("thu_des") String thu_des

    );





@FormUrlEncoded
@SerializedName("getStoreInventory.php")
@POST("getStoreInventory.php")
Call<InventoryModel> getInventory(@Field("username") String username );
//TODO _____________________________USER








    @FormUrlEncoded
    @SerializedName("getRequestDeducted.php")
    @POST("getRequestDeducted.php")
    Call<RequestDeductedModel> getDeducted(@Field("username") String username );




@FormUrlEncoded
@SerializedName("getproduct.php")
@POST("getproduct.php")
Call<ProductsModel> getProducts(@Field("username") String username );
//TODO ________________________________________USER
    @FormUrlEncoded
    @SerializedName("getName.php")
    @POST("getName.php")
    Call<NameModel> getName(@Field("username") String username );
//TODO ______________________________________USER
@FormUrlEncoded
@SerializedName("getPharmacyName.php")
@POST("getPharmacyName.php")Call<PharmacyNameModel>getPharmacyName(
        @Field("username")String username
        );



@FormUrlEncoded
@SerializedName("getProductName.php")
@POST("getProductName.php")
Call<ProductNameModel> getProductName(@Field("username") String username );
    //TODO __________________________________USER
    @FormUrlEncoded
    @POST("getRequest.php")
    Call<RequestModel> getRequest(@Field("filter_number") int filter_number,
                                  @Field("username") String username);
//TODO ________________________USER
@FormUrlEncoded
    @POST("request.php")
    Call<JsonResponseModel> setRequest(
            @Field("sender") String sender,
            @Field("receiver") String receiver,
            @Field("name_product") String name_product,
            @Field("count") String count,
            @Field("offer") String offer

    );
    @FormUrlEncoded
    @POST("report.php")
    Call<JsonResponseModel> setReport(
            @Field("sender") String sender,
            @Field("description") String description
    );


}
