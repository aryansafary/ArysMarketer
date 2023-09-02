package aryan.safary.sinoohe.data;

public class SubscriptionItem {
     private String id ,
             username ,
             company ,
             day_start ,
             day_end ,
             count_day ,
            gone_day,
             hours,
             minutes,
             seconds,
             day_data,
             month_data,
             year_data,
             status,
             active;

    public String getActive() {
        return active;
    }

    public String getStatus() {
        return status;
    }

    public String getYear_data() {
        return year_data;
    }

    public String getMonth_data() {
        return month_data;
    }

    public String getDay_data() {
        return day_data;
    }

    public String getGone_day() {
        return gone_day;
    }

    public String getCount_day() {
        return count_day;
    }

    public String getSeconds() {
        return seconds;
    }

    public String getMinutes() {
        return minutes;
    }

    public String getHours() {
        return hours;
    }

    public String getDay_end() {
        return day_end;
    }

    public String getDay_start() {
        return day_start;
    }

    public String getCompany() {
        return company;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }
}
