package com.example.android.bluetoothlegatt;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class ConvertMacToVendor {
    Context context;
    HashMap<String, String> mac_to_vendor;
    public ConvertMacToVendor(final Context context){
        this.context=context;
        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mac_to_vendor = ReadJsonFile();
                        Toast.makeText(context, "json read complete", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        thread.start();

    }
    public String convert(String mac) {
        String vendor = "";
        String first_six = mac.substring(0, 8);
        if (mac_to_vendor.containsKey(first_six)) {
            vendor = mac_to_vendor.get(first_six);
            if (!checkVendor(vendor)){
                vendor = "";
            }
        }
        return vendor;
    }
    public HashMap<String, String> getMap() {
        return mac_to_vendor;
    }
    public HashMap<String, String> ReadJsonFile() {
        HashMap<String, String> mac_vendor_map = new HashMap<>();
        try {
            AssetManager assetManager = context.getAssets();
            InputStream source = null;
            source = assetManager.open("mac_vendor.json");
            int size = source.available();
            byte[] buffer = new byte[size];
            source.read(buffer);
            source.close();
            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String mac = jsonObject.getString("mac");
                String vendor = jsonObject.getString("vendor");
                mac_vendor_map.put(mac, vendor);
            }

        } catch (IOException | JSONException ioException) {
            ioException.printStackTrace();
        }

        return mac_vendor_map;
    }

    public boolean checkVendor(String vendor){
        //TODO ... vendor in our list of phone companies ...
        return true;
    }
}
