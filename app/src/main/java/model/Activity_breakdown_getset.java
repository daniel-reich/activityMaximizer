package model;

/**
 * Created by surender on 3/8/2017.
 */

public class Activity_breakdown_getset {

    String activity_name;
    int points,hatch_value,total;

    public Activity_breakdown_getset(String activity_name, int points, int hatch_value, int total) {
        this.activity_name = activity_name;
        this.points = points;
        this.hatch_value = hatch_value;
        this.total = total;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getHatch_value() {
        return hatch_value;
    }

    public void setHatch_value(int hatch_value) {
        this.hatch_value = hatch_value;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
