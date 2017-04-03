package model;

/**
 * Created by Rohan on 3/7/2017.
 */
public class AllDownlines
{
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    String uid,name,fivePointClients,fivePointRecruits;

    public AllDownlines(String uid,String name, String fivePointClients, String fivePointRecruits) {
        this.uid=uid;
        this.name = name;
        this.fivePointClients = fivePointClients;
        this.fivePointRecruits = fivePointRecruits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
