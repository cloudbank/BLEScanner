package cloudbank.ble;


/**
 * Created by sabine on 4/1/17.
 */


import android.bluetooth.BluetoothAdapter;
import android.support.compat.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowBluetoothAdapter;

import cloudbank.ble.activity.DeviceScanActivity;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


@Config(constants = BuildConfig.class, shadows = ShadowBluetoothAdapter.class )
@RunWith(RobolectricTestRunner.class)
public class BluetoothAdapterTest {
    private BluetoothAdapter shadowBluetoothAdapter;
    private DeviceScanActivity activity;

    @Before
    public void setUp() throws Exception {
        //bluetoothAdapter = Robolectric.newInstanceOf(BluetoothAdapter.class);
        //activity = Robolectric.setupActivity(DeviceScanActivity.class);
        shadowBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Test
    public void testAdapterDefaultsDisabled() {
        assertFalse(shadowBluetoothAdapter.isEnabled());
    }

    @Test
    public void testAdapterCanBeEnabled() {
        shadowBluetoothAdapter.enable();
        assertTrue(shadowBluetoothAdapter.isEnabled());
    }


}