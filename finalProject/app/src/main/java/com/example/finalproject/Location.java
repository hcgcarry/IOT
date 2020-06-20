package com.example.finalproject;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Location {

    private static final String TAG = "LocationClass";
    private String parkInfo;
    public String aHasCar ="empty";
    public String temperature = "0" ;


    protected void get(){
        //httpget();
        //網路的連接不能放在主thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    httpgetLocation();
                    printLocation(TAG);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    public void printLocation(String TAG){
    }

    /**
     * Converts the contents of an InputStream to a String.
     */
    public void handleJson(String str){
        try {
            JSONObject jsonObject = new JSONObject(str);
            String a = jsonObject.getString("a");
            String splitArray[] =a.split(",");
            String hasCar = splitArray[0];
            temperature = splitArray[1];
            if (hasCar .equals( "1")) {
                aHasCar = "hasCar";
            }
            else{
                aHasCar = "empty";
            }
            Log.d(TAG, a);
            Log.d(TAG, "a Has Car ");
            Log.d(TAG, aHasCar);
        } catch (Exception e) {
            Log.e(TAG,Log.getStackTraceString(e));
        } finally {
        }
}

    public void httpgetLocation(){
        String urlString = "http://192.168.127.103/park/get_park_info.php";
        String respondMessage;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();

            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");//設定訪問方式為“GET”
            connection.setConnectTimeout(8000);//設定連線伺服器超時時間為8秒
            connection.setReadTimeout(8000);//設定讀取伺服器資料超時時間為8秒
            respondMessage= connection.getResponseMessage();
            Log.d(TAG, respondMessage);
            Log.d(TAG, Integer.toString(connection.getResponseCode()));

            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                Log.d(TAG, "connect success");
                //從伺服器獲取響應並把響應資料轉為字串列印
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder response = new StringBuilder();
                String line;
                while (null != (line = reader.readLine())) {
                    response.append(line);
                }
                String responseString= response.toString();
                Log.d(TAG, responseString);
                handleJson(responseString);


                /*
                String col1="latitude",col2="longitude";
                String splitArray[] =responseString.split(",");
                Double resultLongitude= Double.parseDouble(splitArray[0]);
                Double resultLatitude= Double.parseDouble(splitArray[1]);
                if(resultLatitude != this.latitude || resultLongitude != this.longitude){
                    this.locationHasChange=1;
                }
                this.longitude= Double.parseDouble(splitArray[0]);
                this.latitude= Double.parseDouble(splitArray[1]);
                */
                //int[] result=new int[2];
            }
        } catch (Exception e) {
            Log.e(TAG,Log.getStackTraceString(e));
        } finally {
            if (null!= connection) {
                connection.disconnect();
            }
        }
    }

}
