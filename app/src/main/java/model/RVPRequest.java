package model;

/**
 * Created by Surbhi on 09-03-2017.
 */
public class RVPRequest
{
 String key,ref,solution_number,Rvpsolutionnumber,uid,uplinesolutionnumber,username;

    public RVPRequest(String key,String ref, String solution_number, String rvpsolutionnumber, String uid, String uplinesolutionnumber, String username) {
         this.key=key;
        this.ref = ref;
        this.solution_number = solution_number;
        Rvpsolutionnumber = rvpsolutionnumber;
        this.uid = uid;
        this.uplinesolutionnumber = uplinesolutionnumber;
        this.username = username;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getSolution_number() {
        return solution_number;
    }

    public void setSolution_number(String solution_number) {
        this.solution_number = solution_number;
    }

    public String getRvpsolutionnumber() {
        return Rvpsolutionnumber;
    }

    public void setRvpsolutionnumber(String rvpsolutionnumber) {
        Rvpsolutionnumber = rvpsolutionnumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUplinesolutionnumber() {
        return uplinesolutionnumber;
    }

    public void setUplinesolutionnumber(String uplinesolutionnumber) {
        this.uplinesolutionnumber = uplinesolutionnumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
