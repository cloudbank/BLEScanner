package cloudbank.ble.adapter;

/**
 * Created by sabine on 3/31/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cloudbank.ble.R;
import cloudbank.ble.model.BLEDevice;

/**
 * Created by sabine on 9/26/16.
 */

public class ScanListAdapter extends RecyclerView.Adapter<ScanListAdapter.ViewHolder> {


    private OnItemClickListener listener;


    public OnItemClickListener getListener() {
        return this.listener;

    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.device_name) TextView name;
        @BindView(R.id.rssi) TextView rssi;
        @BindView(R.id.record) TextView record;

        public ViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView);

            ButterKnife.bind(this, itemView);


        }
    }

    private List<BLEDevice> mDevices;
    private Context mContext;


    public ScanListAdapter(Context context, List<BLEDevice> devices) {

        mDevices = devices;
        mContext = context;

    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public ScanListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View deviceView = inflater.inflate(R.layout.listitem_device, parent, false);

        ViewHolder viewHolder = new ViewHolder(deviceView, getListener());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ScanListAdapter.ViewHolder viewHolder, int position) {
        BLEDevice device = mDevices.get(position);
        if (device != null) {
            TextView name = viewHolder.name;
            name.setText(device.name);
            TextView record = viewHolder.record;
            record.setText(device.scanRecord);

            TextView rssi = viewHolder.rssi;
            rssi.setText(String.valueOf(device.rssi));
        }


    }

    @Override
    public int getItemCount() {
        return mDevices.size();
    }


}
