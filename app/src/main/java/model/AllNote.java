package model;

/**
 * Created by Rohan on 3/3/2017.
 */
public class AllNote
{
    String content,created,ref;

    public AllNote(String content, String created, String ref) {
        this.content = content;
        this.created = created;
        this.ref = ref;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
