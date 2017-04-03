package cloudbank.ble.model;


/**
 * Created by sabine on 3/31/17.
 */


public class BLEDevice {


    public String name;
    public int rssi;
    public String scanRecord;

    public BLEDevice(String name, int rssi, byte[] scanRecord) {
        this.name = name;
        this.rssi = rssi;
        this.scanRecord = BLEDevice.byteArrayToString(scanRecord);
    }




    public static String byteArrayToString(byte[] ba)
    {
        StringBuilder hex = new StringBuilder(ba.length * 2);
        for (byte b : ba)
            hex.append(b).append(" ");

        return hex.toString();
    }
}


