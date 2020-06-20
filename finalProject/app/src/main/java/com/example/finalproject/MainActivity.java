package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private Location location;
    private int count=0;

    public TextView text1;
    private String TAG="MainActivityClass";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = findViewById(R.id.textView);
        init();
        checkLocationChange();
        Log.d(TAG, location.aHasCar);

    }

    protected void checkLocationChange(){
        //網路的連接不能放在主thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    count++;
                    location.httpgetLocation();
                    updateMapLocation();
                    Log.d(TAG,"test" );
                    //Log.d(TAG,"location change");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    private void init(){
        this.location = new Location();
    }
    private void getLocation(){
        this.location.get();
        //this.location.printLocation(TAG);
    }

    private void updateMapLocation(){
        GetXMLTask task = new GetXMLTask();
        task.execute();
    }

    //@SuppressLint("NewApi")
    private class GetXMLTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... urls) {
            try {
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);

            if(location.aHasCar == "empty"){
                String tmp= "locationA is empty , tmperature:";
                tmp += location.temperature;
                text1.setText(tmp);
            }
            else if(location.aHasCar == "hasCar"){
                String tmp= "locationA has car , tmperature:";
                tmp += location.temperature;
                text1.setText(tmp);
            }
            Log.d(TAG,"a " );
            Log.d(TAG, location.aHasCar);
        }
    }

}
