package da.chatty.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import static java.lang.Thread.sleep;

/**
 * Created by Aagam Shah on 17/7/14.
 */
public class BGService extends android.app.Service {

    public WebSocketClient mWebSocketClient;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        connectChatty();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void connectChatty() {
        Log.e("start chatty", "");


        mWebSocketClient = newClient();
        mWebSocketClient.connect();

    }

    private WebSocketClient newClient() {
        URI uri;
        try {


            uri = new URI("ws://backendtest-appxtest.rhcloud.com:8000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.e("Websocket", "Opened");

                Gson gson = new Gson();
                Message message = new Message();
                message.setHandle("My phone");
                message.setText(Build.MANUFACTURER + " " + Build.MODEL);
                mWebSocketClient.send(gson.toJson(message));
            }

            @Override
            public void onMessage(String s) {



                final String message = s;

                Gson gson = new Gson();
                Message message1 = gson.fromJson(s, Message.class);
                Log.e("onMessage", "" + s);


                SharedPreferences prefs = getSharedPreferences("chatty", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("text", message1.getText());
                editor.putString("name", message1.getHandle());
                Date date = new Date();
                editor.putString("date", "" + date.toString());
                editor.commit();


                Notification notification = new Notification(R.drawable.ic_launcher,
                        "Chatty", System.currentTimeMillis());

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                notification.setLatestEventInfo(getApplicationContext(), message1.getHandle(),
                        message1.getText(),
                        PendingIntent.getActivity(getApplicationContext(), 1, intent, 0));


                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                ;
                notificationManager.notify(123, notification);
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.e("Websocket", "Closed " + s);
//                try {//
//                    while (!mWebSocketClient.connectBlocking()){}
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                mWebSocketClient.getConnection().close();

                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mWebSocketClient.connect();
            }

            @Override
            public void onError(Exception e) {
                Log.e("Websocket", "Error " + e.getMessage());
                mWebSocketClient.getConnection().close();
                try {
                    sleep(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                mWebSocketClient.connect();
            }
        };

        return mWebSocketClient;
    }

}
