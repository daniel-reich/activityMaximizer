package model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by surender on 2/22/2017.
 */

@IgnoreExtraProperties
public class UserInfo {

    public String givenName,familyName,phoneNumber,email,uid;
    public String contactsAdded,  created, dailyPointAverages,fivePointClients,fivePointRecruits,
            partner_solution_number, partnerUID,profilePictureURL,ref,rvp_solution_number,solution_number,
            state,trainer_solution_number,upline_solution_number;

    HashMap achievements;

    public UserInfo(String givenName, String familyName, String phoneNumber, String email, String uid,
                    HashMap achievements, String contactsAdded, String created, String dailyPointAverages,
                    String fivePointClients, String fivePointRecruits, String partner_solution_number,
                    String partnerUID, String profilePictureURL, String ref, String rvp_solution_number,
                    String solution_number, String state, String trainer_solution_number,
                    String upline_solution_number) {

        this.givenName = givenName;
        this.familyName = familyName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.uid = uid;
        this.achievements = achievements;
        this.contactsAdded = contactsAdded;
        this.created = created;
        this.dailyPointAverages = dailyPointAverages;
        this.fivePointClients = fivePointClients;
        this.fivePointRecruits = fivePointRecruits;
        this.partner_solution_number = partner_solution_number;
        this.partnerUID = partnerUID;
        this.profilePictureURL = profilePictureURL;
        this.ref = ref;
        this.rvp_solution_number = rvp_solution_number;
        this.solution_number = solution_number;
        this.state = state;
        this.trainer_solution_number = trainer_solution_number;
        this.upline_solution_number = upline_solution_number;

    }
}
