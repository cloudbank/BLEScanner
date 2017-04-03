package cloudbank.ble.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import cloudbank.ble.R;
import cloudbank.ble.receiver.BLEBroadcastReceiver;

/**
 * Created by sabine on 4/2/17.
 */

public class BroadcastService extends Service{
        private NotificationManager mNM;

        // Unique Identification Number for the Notification.
        // We use it on Notification start, and to cancel it.
        private int NOTIFICATION = R.string.local_service_started;

        /**
         * Class for clients to access.  Because we know this service always
         * runs in the same process as its clients, we don't need to deal with
         * IPC.
         */
        public class LocalBinder extends Binder {
            BroadcastService getService() {
                return BroadcastService.this;
            }
        }

        @Override
        public void onCreate() {
            mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

            // Display a notification about us starting.  We put an icon in the status bar.
            showNotification();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.i("BroadcastService", "Received start id " + startId + ": " + intent);
            Intent i = new Intent(BLEBroadcastReceiver.RECEIVE_UPDATE);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
            return START_NOT_STICKY;
        }

        @Override
        public void onDestroy() {
            // Cancel the persistent notification.
            mNM.cancel(NOTIFICATION);

            // Tell the user we stopped.
            Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show();
        }

        @Override
        public IBinder onBind(Intent intent) {
            return mBinder;
        }

        // This is the object that receives interactions from clients.  See
        // RemoteService for a more complete example.
        private final IBinder mBinder = new LocalBinder();

        /**
         * Show a notification while this service is running.
         */
        private void showNotification() {
            // In this sample, we'll use the same text for the ticker and the expanded notification
            CharSequence text = getText(R.string.local_service_started);


            // Set the info for the views that show in the notification panel.
            Notification notification = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher)  // the status icon
                    .setTicker(text)  // the status text
                    .setWhen(System.currentTimeMillis())  // the time stamp
                    .setContentTitle(getText(R.string.local_service_label))  // the label of the entry
                    .setContentText(text)  // the contents of the entry
                    //.setContentIntent(contentIntent)  // The intent to send when the entry is clicked
                    .build();

            // Send the notification.
            mNM.notify(NOTIFICATION, notification);
        }
    }
