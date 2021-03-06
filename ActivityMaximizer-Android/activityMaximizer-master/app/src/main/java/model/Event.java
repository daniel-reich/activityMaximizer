package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

/**
 * Created by LucianVictor on 4/1/2017.
 */
@IgnoreExtraProperties
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

    public String contactName;
    public String type;
    public String userName;
    public float created;
    public float date;
    public int amount;
    public String userRef;
    public String ref;

    public Date getCreated() {
        return new Date((long) created * 1000);
    }

    public Date getDate() {
        return new Date((long) date * 1000);
    }

    @Override
    public String toString() {
        return "Event{" +
                "contactName='" + contactName + '\'' +
                ", type='" + type + '\'' +
                ", userName='" + userName + '\'' +
                ", created=" + created +
                ", date=" + date +
                ", amount=" + amount +
                ", userRef='" + userRef + '\'' +
                ", ref='" + ref + '\'' +
                '}';
    }
}
