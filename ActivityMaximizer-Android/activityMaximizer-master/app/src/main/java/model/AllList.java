package model;

/**
 * Created by Rohan on 3/2/2017.
 */
public class AllList
{
    String name,ref;

    public AllList(String name, String ref) {
        this.name = name;
        this.ref = ref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
