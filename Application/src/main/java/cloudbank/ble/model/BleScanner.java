package cloudbank.ble.model;

import android.content.Context;
import android.os.Build;
import android.os.Handler;

/**
 * Created by sabine on 3/30/17.
 */

public class BleScanner {
    private final static String TAG = BleScanner.class.getName();
    public BaseBleScanner bleScanner;

    public BleScanner(Context context, final SimpleScanCallback callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bleScanner = new LollipopBleScanner(callback);
        } else {
            bleScanner = new JellyBeanBleScanner(context, callback);
        }
    }




    public boolean isScanning(){
        return bleScanner.isScanning;
    }

    public void startBleScan(){
        bleScanner.onStartBleScan();
    }

    public void startBleScan(long timeoutMillis){
        bleScanner.onStartBleScan(timeoutMillis);
    }
    public void stopBleScan(){
        bleScanner.onStopBleScan();
    }

    public abstract static class BaseBleScanner {
        public final static long defaultTimeout = 15 *1000;
        public boolean isScanning;

        public abstract void onStartBleScan();
        public abstract void onStartBleScan(long timeoutMillis);

        public abstract void onStopBleScan();

        public abstract void onBleScanFailed(BleScanState scanState);

        protected Handler timeoutHandler  = new Handler();
        protected Runnable timeoutRunnable = new Runnable() {
            @Override
            public void run() {
                onStopBleScan();
                onBleScanFailed(BleScanState.SCAN_TIMEOUT);
            }
        };
    }
}