package com.upt.cti.myapplication;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class GateActivity extends AppCompatActivity {

    private static boolean GateON;
    private String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    //SPP UUID. Look for it
    private static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate);

        address = "00:21:13:04:1F:F7";
        new ConnectBT().execute(); //Call the class to connect
    }

    public void openGate(View view) {
        if(GateON == true){
            Toast.makeText(getBaseContext(), "Gate is already open.", Toast.LENGTH_LONG).show();
        }
        else {
            this.GateON = true;
            //TODO bluetooth connection
            if (btSocket!=null)
            {
                try
                {
                    btSocket.getOutputStream().write('8');
                }
                catch (IOException e)
                {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void closeGate(View view) {
        if(GateON == false){
            Toast.makeText(getBaseContext(), "Gate is already closed.", Toast.LENGTH_LONG).show();
        }
        else {
            this.GateON = false;
            //TODO bluetooth connection
            if (btSocket!=null)
            {
                try
                {
                    btSocket.getOutputStream().write('9');
                }
                catch (IOException e)
                {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public boolean getLEDStateR1(){
        return GateON;
    }
    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(GateActivity.this, "Connecting...", "Please wait!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                //Toast.makeText(getApplicationContext(), "Am ajuns in try", Toast.LENGTH_LONG).show();
                if (btSocket == null || !isBtConnected)
                {
                    //Toast.makeText(getApplicationContext(), "Am ajuns in background", Toast.LENGTH_LONG).show();
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    //Toast.makeText(getApplicationContext(), "Am ajuns in background", Toast.LENGTH_LONG).show();
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                    //Toast.makeText(getApplicationContext(), "Am ajuns in background", Toast.LENGTH_LONG).show();
                }
            }
            catch (IOException e)
            {
                //Toast.makeText(getApplicationContext(), "Am ajuns in exceptie", Toast.LENGTH_LONG).show();
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
                //msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                //finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Connected!!!!!!!", Toast.LENGTH_LONG).show();
                //msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}
