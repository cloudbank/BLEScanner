package cloudbank.ble.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by sabine on 4/3/17.
 */

public class BLEBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "BLEBroadcastReceiver";

    public static final String RECEIVE_UPDATE = "BLE_UPDATES";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Received broadcast");
        String action = intent.getAction();
        if (action.equals(RECEIVE_UPDATE))
        {
            Log.i(TAG, "Received ble update from service!");
        }
    }
}