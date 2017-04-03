package cloudbank.ble.fragment;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cloudbank.ble.R;
import cloudbank.ble.adapter.ScanListAdapter;
import cloudbank.ble.model.BLEDevice;
import cloudbank.ble.model.BleScanState;
import cloudbank.ble.model.BleScanner;
import cloudbank.ble.model.SimpleScanCallback;
import cloudbank.ble.receiver.BLEBroadcastReceiver;
import cloudbank.ble.service.BroadcastService;

/**
 * Created by sabine on 3/30/17.
 */

public class ScanListFragment extends Fragment implements SimpleScanCallback {
    private final static String TAG = ScanListFragment.class.getName();

    ScanListAdapter sAdapter;
    List<BLEDevice> mDevices = new ArrayList<BLEDevice>();
    Map<String, BLEDevice> deviceMap = new HashMap<>();
    View view;
    private Handler mHandler;
    private BleScanner mBleScanner;
    private static final int REQUEST_ENABLE_BT = 1;
    // Stops scanning after 15 seconds.
    private static final long SCAN_PERIOD = 15000;//
    private boolean mScanning = false;
    @BindView(R.id.scanButton)
    Button scanButton;
    @BindView(R.id.loading_spinner)
    ProgressBar pBar;
    @BindView(R.id.rvDevices)
    RecyclerView rvDevices;
    @BindView(R.id.empty_view)
    TextView emptyView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onBleScanFailed(BleScanState scanState) {

    }

    private BLEBroadcastReceiver mMessageReceiver = new BLEBroadcastReceiver();


    @Override
    public void onBleScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
        Log.d("TAG", "onBleScan callback reached on UI thread :" + "device: " + device + " rssi: " + rssi + " scanR: " + scanRecord);
        BLEDevice d = new BLEDevice(device.toString(), rssi, scanRecord);
        if (!deviceMap.containsKey(device.toString())) {

            deviceMap.put(device.toString(), d);
            mDevices.add(d);
            sAdapter.notifyDataSetChanged();
        } else {
            //in 15 seconds the rssi can change
            mDevices.remove(deviceMap.get(device.toString()));
            deviceMap.put(device.toString(), d);
            mDevices.add(d);
            sAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        sAdapter = new ScanListAdapter(getActivity().getApplicationContext(), mDevices);
        setRetainInstance(true);

    }


    private void scanLeDevice(final boolean enable) {

        // Stops scanning after a pre-defined scan period.
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScanning = false;
                updateStopScanButton();
                mBleScanner.stopBleScan();
                mBleScanner = null;
                getActivity().stopService(new Intent(getActivity().getApplicationContext(), BroadcastService.class));


            }
        }, SCAN_PERIOD);


        mScanning = true;
        if (mBleScanner == null) {
            mBleScanner = new BleScanner(getActivity().getApplicationContext(), this);
        }

        mBleScanner.startBleScan(); // factory for scanner version
        getActivity().startService(new Intent(getActivity().getApplicationContext(), BroadcastService.class));


    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_scanlist, container,
                false);
        ButterKnife.bind(this, view);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setLogo(R.drawable.ic_launcher);
        activity.getSupportActionBar().setDisplayUseLogoEnabled(true);
        activity.getSupportActionBar().setTitle((R.string.title_devices));
        activity.getSupportActionBar().setSubtitle("sample");

        rvDevices.setAdapter(sAdapter);
        rvDevices.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        // emptyList(mDevices.isEmpty());
        return view;

    }

    @OnClick(R.id.scanButton)
    public void onClick() {
        if (!mScanning) {
            Log.d("TAG", "scanning");
            mDevices.clear();
            deviceMap.clear();
            updateStartScanButton();
            scanLeDevice(true);
            mScanning = true;
        }

    }

    private void updateStartScanButton() {
        pBar.setVisibility(View.VISIBLE);
        scanButton.setText(R.string.Scanning);
        scanButton.setBackground(getResources().getDrawable(R.drawable.circle_button2));
        scanButton.setTextColor(getResources().getColor(R.color.gray));
        scanButton.setClickable(false);
    }


    private void updateStopScanButton() {
        pBar.setVisibility(View.INVISIBLE);
        scanButton.setText(R.string.StartScan);
        scanButton.setClickable(true);
        scanButton.setBackground(getResources().getDrawable(R.drawable.circle_button));
    }

    private void emptyList(boolean empty) {
        if (empty) {
            rvDevices.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            rvDevices.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);

        }
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(
                mMessageReceiver, new IntentFilter(BLEBroadcastReceiver.RECEIVE_UPDATE));
    }


    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(mMessageReceiver);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
