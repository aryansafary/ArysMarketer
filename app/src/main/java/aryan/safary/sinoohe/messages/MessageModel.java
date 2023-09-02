package aryan.safary.sinoohe.messages;

public class MessageModel {

    private int id;
    private String sender;
    private String message;
    private String date;

    public MessageModel(int id, String sender, String message, String date) {
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.date = date;
    }

    public MessageModel(String sender, String message, String date) {
        this.sender = sender;
        this.message = message;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
