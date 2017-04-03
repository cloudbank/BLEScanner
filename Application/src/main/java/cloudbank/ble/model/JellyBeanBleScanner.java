package cloudbank.ble.model;

/**
 * Created by sabine on 3/30/17.
 */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


public class JellyBeanBleScanner extends BleScanner.BaseBleScanner {
    private final static String TAG = JellyBeanBleScanner.class.getName();



    public BluetoothAdapter mBluetooth = null;
    private SimpleScanCallback mScanCallback = null;

    public JellyBeanBleScanner(Context context, SimpleScanCallback callback) {
        mScanCallback = callback;
        BluetoothManager bluetoothMgr = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetooth = bluetoothMgr.getAdapter();
    }

    //testing
    public JellyBeanBleScanner(SimpleScanCallback callback, BluetoothAdapter adapter) {
        mScanCallback = callback;
        mBluetooth = adapter;
    }

    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mScanCallback.onBleScan(device, rssi, scanRecord);
                }
            });
        }
    };



        @SuppressWarnings(value = {"deprecation"})
        @Override
        public void onStartBleScan(long timeoutMillis) {
            long delay = timeoutMillis == 0 ? defaultTimeout : timeoutMillis;
            if (mBluetooth != null) {
                isScanning = mBluetooth.startLeScan(leScanCallback);
                timeoutHandler.postDelayed(timeoutRunnable, delay);
                Log.d(TAG, "mBluetooth.startLeScan() " + isScanning);
            } else {
                mScanCallback.onBleScanFailed(BleScanState.BLUETOOTH_OFF);//bluetooth is off
            }
        }

        @SuppressWarnings(value = {"deprecation"})
        @Override
        public void onStartBleScan() {//scan always
            if (mBluetooth != null) {
                isScanning = mBluetooth.startLeScan(leScanCallback);
                Log.d(TAG, "mBluetooth.startLeScan() " + isScanning);
            } else {
                mScanCallback.onBleScanFailed(BleScanState.BLUETOOTH_OFF);//bluetooth is off
            }
        }

        @SuppressWarnings(value = {"deprecation"})
        @Override
        public void onStopBleScan() {
            isScanning = false;
            if (mBluetooth != null) {
                mBluetooth.stopLeScan(leScanCallback);
            }
        }

        @Override
        public void onBleScanFailed(BleScanState scanState) {
            mScanCallback.onBleScanFailed(scanState);//
        }
    }