package da.chatty.app;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.HashMap;

/**
 * Created by Aagam Shah on 22/7/14.
 */
public class GetContacts {


    public HashMap get(Context ctx) {

        Cursor phones = ctx.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        HashMap<String, String> listContacts = new HashMap<String, String>();
        int i = 0;
        while (phones.moveToNext() && i < 5) {
            i++;
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            if (name != null && phoneNumber != null)
                listContacts.put(phoneNumber, name);
        }
        phones.close();

        return listContacts;

    }

}
