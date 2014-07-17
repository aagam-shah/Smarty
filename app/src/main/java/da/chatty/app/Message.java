package da.chatty.app;

/**
 * Created by Aagam Shah on 17/7/14.
 */
public class Message {
    public String getHandle() {
        return handle;
    }

    public void setHandle(String handler) {
        this.handle = handler;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String handle,text;
}
