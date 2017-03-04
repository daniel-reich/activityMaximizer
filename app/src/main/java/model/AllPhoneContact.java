package model;

/**
 * Created by Rohan on 3/4/2017.
 */
public class AllPhoneContact
{
    int status;
    String AllContactList,AllContactNameList;

    public AllPhoneContact(int status, String allContactList, String allContactNameList) {
        this.status = status;
        this.AllContactList = allContactList;
        this.AllContactNameList = allContactNameList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAllContactList() {
        return AllContactList;
    }

    public void setAllContactList(String allContactList) {
        AllContactList = allContactList;
    }

    public String getAllContactNameList() {
        return AllContactNameList;
    }

    public void setAllContactNameList(String allContactNameList) {
        AllContactNameList = allContactNameList;
    }
}
