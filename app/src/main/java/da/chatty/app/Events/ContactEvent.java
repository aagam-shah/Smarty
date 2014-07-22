package da.chatty.app.Events;

import java.util.HashMap;

/**
 * Created by Aagam Shah on 22/7/14.
 */
public class ContactEvent {
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public HashMap<String, String> getContacts() {
        return contacts;
    }

    public void setContacts(HashMap<String, String> contacts) {
        this.contacts = contacts;
    }

    public String event;
    public HashMap<String,String> contacts;
}
