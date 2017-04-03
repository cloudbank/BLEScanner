
Android BluetoothLeScanner Sample
===================================

This sample demonstrates how to use the Bluetooth LE 
to scan for devices.

Introduction
------------

This sample shows a list of available Bluetooth LE devices.

It uses the Bluetooth LE API to scan for available low energy devices for 15 seconds;  it also provides a service with local broadcast for notification during and after the scan.

Since the scan only lasts 15 seconds(and services are meant for long running tasks) the scan call is stopped with a handler thread and the callback is moved to the UI thread with another Handler thread.  During the 15 second scan, devices are updated as their RSSI data changes.  Device id, RSSI data, and the hexidecimal string representation for the scan record are presented in a RecyclerView, which has the viewholder pattern built-in for efficiency.

The "Start Scan" button becomes un-clickable until the scan stops.
The target SDK is 25 with a minimum of 18, when this code was introduced.  Backward compatability is provided via the suppport library.

A factory pattern is used to increase extensibility for plugging in versions of the scanner as the API continues to change.
Butterknife is utilized for field and method binding for views via annotations.

https://github.com/googlesamples/android-BluetoothLeGatt

Timing: it would take about 20 minutes to get the android sample working exactly as you wish with minimal modifications( using the built in listview etc). It took minutes to grok the code and a few hours to get it just right with the lastest material design and api updates ( recylerview, toolbar)

 Despite the changes in the API the deprecated code still works on L devices.  I addressed this with a factory for scanners.
Things I did not addess:  java 1.8, the service and broadcast.  I added  a service and localbroadcast for notification because you asked for it.  The 15 sec scan really only needs a couple of handler threads; I think a service is overkill and not intended for short lived tasks.  .

I also just used MVC/google lifecycle patterns.  In the future as the app grows it might need to be changed to MVP or MVVM in places.

I also added minimal tests with roboeletric, which gives back the classes in the android jar using Shadows. I just added a small example for a sample.
