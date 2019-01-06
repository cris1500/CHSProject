package com.upt.cti.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Set;

public class MainPanel extends AppCompatActivity {

    //Bluetooth
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_panel);

        //if the device has bluetooth
        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        if(myBluetooth == null)
        {
            Toast.makeText(getApplicationContext(), "Bluetooth device not available", Toast.LENGTH_LONG).show();

            //finish apk
            //finish();
        }
        else if(!myBluetooth.isEnabled())
        {
            //Ask to the user turn the bluetooth on
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon,1);
        }
        pairedDevicesList();
    }

    private void pairedDevicesList()
    {
        pairedDevices = myBluetooth.getBondedDevices();

        if (pairedDevices.size()>0)
        {
            for(BluetoothDevice bt : pairedDevices)
            {
                if(bt.getName().contains("HC")){
                    address = bt.getAddress();
                }
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

    }

    public void gateClicked(View view) {

        startActivity(new Intent(MainPanel.this, GateActivity.class));
    }

    public void room4Clicked(View view) {
        startActivity(new Intent(MainPanel.this, Room4Activity.class));
    }

    public void room3Clicked(View view) {
        startActivity(new Intent(MainPanel.this, Room3Activity.class));
    }

    public void room2Clicked(View view) {
        startActivity(new Intent(MainPanel.this, Room2Activity.class));
    }

    public void room1Clicked(View view) {
        startActivity(new Intent(MainPanel.this, Room1Activity.class));
    }
}
