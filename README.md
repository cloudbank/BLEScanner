
Android BluetoothLeScanner Sample
===================================

This sample demonstrates how to use the Bluetooth LE 
to scan for devices.

Introduction
------------

This sample shows a list of available Bluetooth LE devices.

It uses the [Bluetooth LE API] to scan for available low energy devices for 15 seconds;  it also provides a service with local broadcast for notification during and after the scan.

Since the scan only lasts 15 seconds(and services are meant for long running tasks) the scan call is stopped with a handler thread and the callback is moved to the UI thread with another Handler thread.  During the 15 second scan, devices are updated as their RSSI data changes.  Device id, RSSI data, and the hexidecimal string representation for the scan record are presented in a recylcerview, which has the viewholder pattern built-in for efficiency.

The "Start Scan" button becomes un-clickable until the scan stops.
The target SDK is 25 with a minimum of 18, when this code was introduced.  Backward compatability is provided via the suppport library.

A factory pattern is used to increase extensibility for plugging in versions of the scanner as the API continues to change.
Butterknife is utilized for field and method binding for views via annotations.

[1]:http://developer.android.com/reference/android/app/Service.html
[2]:https://developer.android.com/reference/android/bluetooth/BluetoothGatt.html

Pre-requisites
--------------

- Android SDK 25
- Android Build Tools v25.0.2
- Android Support Repository


