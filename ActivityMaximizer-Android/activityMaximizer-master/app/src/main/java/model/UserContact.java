package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

/**
 * Created by LucianVictor on 4/3/2017.
 */
@IgnoreExtraProperties
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserContact {

    public String familyName;
    public boolean married;
    public String givenName;
    public boolean hasKids;
    public float created;

    public Date getCreated() {
        return new Date((long) created * 100);
    }

    @Override
    public String toString() {
        return "UserContact{" +
                "familyName='" + familyName + '\'' +
                ", married=" + married +
                ", givenName='" + givenName + '\'' +
                ", hasKids=" + hasKids +
                ", created=" + created +
                '}';
    }
}
