package model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by surender on 2/22/2017.
 */
@IgnoreExtraProperties
public class Update {

    String givenName,familyName,phoneNumber,email,uid;

    public Update() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Update(String givenName, String familyName, String phoneNumber, String email, String uid) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.uid = uid;
    }
}
