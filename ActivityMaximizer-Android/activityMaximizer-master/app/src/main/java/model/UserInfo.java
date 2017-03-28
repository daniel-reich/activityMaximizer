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

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContactsAdded() {
        return contactsAdded;
    }

    public void setContactsAdded(String contactsAdded) {
        this.contactsAdded = contactsAdded;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDailyPointAverages() {
        return dailyPointAverages;
    }

    public void setDailyPointAverages(String dailyPointAverages) {
        this.dailyPointAverages = dailyPointAverages;
    }

    public String getFivePointClients() {
        return fivePointClients;
    }

    public void setFivePointClients(String fivePointClients) {
        this.fivePointClients = fivePointClients;
    }

    public String getFivePointRecruits() {
        return fivePointRecruits;
    }

    public void setFivePointRecruits(String fivePointRecruits) {
        this.fivePointRecruits = fivePointRecruits;
    }

    public String getPartner_solution_number() {
        return partner_solution_number;
    }

    public void setPartner_solution_number(String partner_solution_number) {
        this.partner_solution_number = partner_solution_number;
    }

    public String getPartnerUID() {
        return partnerUID;
    }

    public void setPartnerUID(String partnerUID) {
        this.partnerUID = partnerUID;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getRvp_solution_number() {
        return rvp_solution_number;
    }

    public void setRvp_solution_number(String rvp_solution_number) {
        this.rvp_solution_number = rvp_solution_number;
    }

    public String getSolution_number() {
        return solution_number;
    }

    public void setSolution_number(String solution_number) {
        this.solution_number = solution_number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTrainer_solution_number() {
        return trainer_solution_number;
    }

    public void setTrainer_solution_number(String trainer_solution_number) {
        this.trainer_solution_number = trainer_solution_number;
    }

    public String getUpline_solution_number() {
        return upline_solution_number;
    }

    public void setUpline_solution_number(String upline_solution_number) {
        this.upline_solution_number = upline_solution_number;
    }

    public HashMap getAchievements() {
        return achievements;
    }

    public void setAchievements(HashMap achievements) {
        this.achievements = achievements;
    }
}
