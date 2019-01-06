package com.upt.cti.myapplication;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class BluetoothConnection extends Activity {

    private String address = "00:21:13:04:1F:F7"; //adresa MAC a bluetooth-ului
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    private boolean isBtConnected = false;
    private static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static BluetoothSocket btSocket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ConnectBT().execute();
        Toast.makeText(getApplicationContext(), "Am intrat in bluetooth conn.", Toast.LENGTH_LONG).show();
    }

    //public BluetoothConnection() {
    //    new ConnectBT().execute(); //Call the class to connect
    //}

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(getApplicationContext(), "Connecting...", "Please wait!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                Toast.makeText(getApplicationContext(), "Connection failed.", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Connected!", Toast.LENGTH_LONG).show();
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

    public BluetoothSocket getBtSocket(){
        Toast.makeText(getApplicationContext(), "Am trimis btSocket.", Toast.LENGTH_LONG).show();
        return btSocket;
    }
}
