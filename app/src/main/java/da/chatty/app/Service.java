package da.chatty.app;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;

/**
 * Created by Aagam Shah on 17/7/14.
 */
public class Service extends IntentService {
    public WebSocketClient mWebSocketClient;

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("destroyed", "");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e("onStart", "");
    }

    public Service() {
        super("Service");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("onHandleIntent", "");
      //  connectChatty();
        // while (t)

    }
}
