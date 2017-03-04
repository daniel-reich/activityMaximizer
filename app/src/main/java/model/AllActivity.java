package model;

/**
 * Created by Rohan on 3/4/2017.
 */
public class AllActivity
{
    String contactName,contactRef,created,date,eventKitID,ref,type,userName,userRef;

    public AllActivity(String contactName, String contactRef, String created, String date, String eventKitID, String ref, String type, String userName, String userRef) {
        this.contactName = contactName;
        this.contactRef = contactRef;
        this.created = created;
        this.date = date;
        this.eventKitID = eventKitID;
        this.ref = ref;
        this.type = type;
        this.userName = userName;
        this.userRef = userRef;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactRef() {
        return contactRef;
    }

    public void setContactRef(String contactRef) {
        this.contactRef = contactRef;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEventKitID() {
        return eventKitID;
    }

    public void setEventKitID(String eventKitID) {
        this.eventKitID = eventKitID;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRef() {
        return userRef;
    }

    public void setUserRef(String userRef) {
        this.userRef = userRef;
    }
}
