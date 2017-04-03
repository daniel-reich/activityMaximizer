package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by surender on 2/22/2017.
 */
@IgnoreExtraProperties
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    public String givenName;
    public String familyName;
    public String phoneNumber;
    public String email;
    public String uid;
    public String state;
    public String achievementToShow;
    public String ref;

    public String profilePictureURL;
    public String solution_number;
}
