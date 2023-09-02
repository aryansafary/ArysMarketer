package aryan.safary.sinoohe.data;

import com.google.gson.annotations.SerializedName;

public class RequestItem {
    private String sender ;
    private String receiver;
    private String location;
    @SerializedName("name_product")
    private String ProductName;
    private String count;
    private String offer;
    @SerializedName("product_price")
    private String ProductPrice;
    @SerializedName("Total_price")
    private String TotalPrice;
    private String data;
    private String status;

    public String getStatus() {
        return status;
    }
    public String getTotalPrice() {
        return TotalPrice;
    }
    public String getProductPrice() {
        return ProductPrice;
    }
    public String getOffer() {
        return offer;
    }
    public String getCount() {
        return count;
    }
    public String getProductName() {
        return ProductName;
    }
    public String getLocation() {
        return location;
    }
    public String getReceiver() {
        return receiver;
    }
    public String getSender() {
        return sender;
    }
    public String getData() {
        return data;
    }
}
