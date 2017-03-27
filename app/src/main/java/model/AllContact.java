package model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by Rohan on 3/2/2017.
 */
@IgnoreExtraProperties
public class AllContact implements Serializable
{
    String competitive,created,credible,familyName,givenName,hasKids,homeowner,hungry,incomeOver40k,
        married,motivated,ofProperAge,peopleSkills,phoneNumber,ref;
    int rating,recruitRating;

    public AllContact(String competitive, String created, String credible, String familyName, String givenName, String hasKids, String homeowner, String hungry, String incomeOver40k, String married, String motivated, String ofProperAge, String peopleSkills, String phoneNumber, String rating, String recruitRating, String ref) {
        this.competitive = competitive;
        this.created = created;
        this.credible = credible;
        this.familyName = familyName;
        this.givenName = givenName;
        this.hasKids = hasKids;
        this.homeowner = homeowner;
        this.hungry = hungry;
        this.incomeOver40k = incomeOver40k;
        this.married = married;
        this.motivated = motivated;
        this.ofProperAge = ofProperAge;
        this.peopleSkills = peopleSkills;
        this.phoneNumber = phoneNumber;
        if(rating.equalsIgnoreCase("")|rating.equalsIgnoreCase(null))
        {
            this.rating = Integer.parseInt("0");
        }
        else {
            this.rating = Integer.parseInt(rating);
        }
        if(recruitRating.equalsIgnoreCase("")|recruitRating.equalsIgnoreCase(null))
        {
            this.recruitRating = Integer.parseInt("0");
        }
        else {
            this.recruitRating = Integer.parseInt(recruitRating);
        }
        this.ref = ref;
    }

    public String getCompetitive() {
        return competitive;
    }

    public void setCompetitive(String competitive) {
        this.competitive = competitive;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCredible() {
        return credible;
    }

    public void setCredible(String credible) {
        this.credible = credible;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getHasKids() {
        return hasKids;
    }

    public void setHasKids(String hasKids) {
        this.hasKids = hasKids;
    }

    public String getHomeowner() {
        return homeowner;
    }

    public void setHomeowner(String homeowner) {
        this.homeowner = homeowner;
    }

    public String getHungry() {
        return hungry;
    }

    public void setHungry(String hungry) {
        this.hungry = hungry;
    }

    public String getIncomeOver40k() {
        return incomeOver40k;
    }

    public void setIncomeOver40k(String incomeOver40k) {
        this.incomeOver40k = incomeOver40k;
    }

    public String getMarried() {
        return married;
    }

    public void setMarried(String married) {
        this.married = married;
    }

    public String getMotivated() {
        return motivated;
    }

    public void setMotivated(String motivated) {
        this.motivated = motivated;
    }

    public String getOfProperAge() {
        return ofProperAge;
    }

    public void setOfProperAge(String ofProperAge) {
        this.ofProperAge = ofProperAge;
    }

    public String getPeopleSkills() {
        return peopleSkills;
    }

    public void setPeopleSkills(String peopleSkills) {
        this.peopleSkills = peopleSkills;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRecruitRating() {
        return recruitRating;
    }

    public void setRecruitRating(int recruitRating) {
        this.recruitRating = recruitRating;
    }
}
