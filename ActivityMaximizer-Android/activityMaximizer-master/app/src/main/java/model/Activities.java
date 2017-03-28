package model;

/**
 * Created by jattin on 3/6/2017.
 */
public class Activities {

    public Activities(long time, String json) {
        this.time = time;
        this.json = json;
    }

    public  long time;
   public  String json="";

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
