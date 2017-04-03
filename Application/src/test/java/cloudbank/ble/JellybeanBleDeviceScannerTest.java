package cloudbank.ble;
/**
 * Created by sabine on 4/1/17.
 */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowBluetoothAdapter;

import cloudbank.ble.model.BleScanState;
import cloudbank.ble.model.JellyBeanBleScanner;
import cloudbank.ble.model.SimpleScanCallback;

@Config(constants = BuildConfig.class, shadows = {ShadowBluetoothAdapter.class})
@RunWith(RobolectricTestRunner.class)
public class JellybeanBleDeviceScannerTest {
    private BluetoothAdapter mAdapter;
    private SimpleScanCallback callback;


    @Before
    public void setUp() throws Exception {
        callback = new SimpleScanCallback() {
            @Override
            public void onBleScan(BluetoothDevice device, int rssi, byte[] scanRecord) {

            }

            @Override
            public void onBleScanFailed(BleScanState scanState) {

            }
        };
        mAdapter = BluetoothAdapter.getDefaultAdapter();

    }

    @Test
    public void isScanning_afterCreation_shouldReturnFalse() {
        JellyBeanBleScanner bleScanner = new JellyBeanBleScanner(callback, mAdapter);
        Assert.assertFalse(bleScanner.isScanning);
    }

    @Test
    public void isScanning_afterStart_shouldReturnTrue() {
        JellyBeanBleScanner bleScanner = new JellyBeanBleScanner(callback, mAdapter);
        bleScanner.onStartBleScan();
        Assert.assertTrue(bleScanner.isScanning);
    }


    @Test
    public void isScanning_afterStartAndStop_shouldReturnFalse() {
        JellyBeanBleScanner bleScanner = new JellyBeanBleScanner(callback, mAdapter);
        bleScanner.onStartBleScan();
        bleScanner.onStopBleScan();
        Assert.assertFalse(bleScanner.isScanning);

    }


}