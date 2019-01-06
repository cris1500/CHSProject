package com.upt.cti.myapplication;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class Room1Activity extends AppCompatActivity {

    private static boolean LedON;
    private String address = "00:21:13:04:1F:F7"; //adresa MAC a bluetooth-ului
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    private boolean isBtConnected = false;
    private static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    BluetoothSocket btSocket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room1);

        new ConnectBT().execute();
    }

    public void R1LedON(View view) {
        if (LedON == true) {
            Toast.makeText(getBaseContext(), "Lumina este deja pornita.", Toast.LENGTH_LONG).show();
        } else {
            this.LedON = true;

            if (btSocket!=null)
            {
                try
                {
                    btSocket.getOutputStream().write('0');
                }
                catch (IOException e)
                {
                    Toast.makeText(getApplicationContext(), "Eroare!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void R1LedOFF(View view) {
        if (LedON == false) {
            Toast.makeText(getBaseContext(), "Lumina este deja stinsa.", Toast.LENGTH_LONG).show();
        } else {
            this.LedON = false;

            if (btSocket!=null)
            {
                try
                {
                    btSocket.getOutputStream().write('1');
                }
                catch (IOException e)
                {
                    Toast.makeText(getApplicationContext(), "Eroare!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public boolean getLEDStateR1() {
        return LedON;
    }


    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(Room1Activity.this, "Conectare...", "Va rugam sa asteptati!");  //show a progress dialog
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
                Toast.makeText(getApplicationContext(), "Conexiune esuata.", Toast.LENGTH_LONG).show();
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Conectat!", Toast.LENGTH_LONG).show();
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}
