package com.example.android.bluetoothlegatt;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class ConvertMacToVendor {
    Context context;
    Activity deviceScanActivity;
    HashMap<String, String> mac_to_vendor;
    public ConvertMacToVendor(final Context context){
        this.context=context;
        deviceScanActivity = (Activity) context;
        deviceScanActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mac_to_vendor = ReadJsonFile();
                Toast.makeText(context, "json read complete", Toast.LENGTH_SHORT).show();
            }
        });

        Log.i("mac_to_vendor", mac_to_vendor.toString());

    }
    public String convert(String mac) {
        String vendor = "";
        Log.i("mac add", mac);

        String first_six = mac.substring(0, 6);
        String first_seven = mac.substring(0, 7);
        String first_eight = mac.substring(0, 8);
        String first_nine = mac.substring(0, 9);
        String first_ten = mac.substring(0, 10);
        String first_eleven = mac.substring(0, 11);
        Log.i("first_six", first_six);
        Log.i("first_seven", first_seven);
        if (mac_to_vendor.containsKey(first_six)) {
            vendor = mac_to_vendor.get(first_six);
        } else if (mac_to_vendor.containsKey(first_seven)) {
            vendor = mac_to_vendor.get(first_seven);
        } else if (mac_to_vendor.containsKey(first_eight)) {
            vendor = mac_to_vendor.get(first_eight);
        } else if (mac_to_vendor.containsKey(first_nine)) {
            vendor = mac_to_vendor.get(first_nine);
        } else if (mac_to_vendor.containsKey(first_ten)) {
            vendor = mac_to_vendor.get(first_ten);
        } else if (mac_to_vendor.containsKey(first_eleven)) {
            vendor = mac_to_vendor.get(first_eleven);
        }
        /*
        if (!checkVendor(vendor)){
            vendor = "";
        }
         */

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
        HashSet<String> hashSet = new HashSet<String>();
        boolean check = false;
        Collections.addAll(hashSet, "Samsung", "Apple", "Xiaomi", "Huawei", "Sony");
        Collections.addAll(hashSet, "samsung", "apple", "xiaomi", "huawei", "sony");
        for (String sr: hashSet) {
            if (vendor.contains(sr)) {
                check  = true;
                break;
            }
        }
        /*
        Collections.addAll(hashSet, "Condor", "Okapia", "Helio", "Maximus", "Walton", "Symphony",
                "Kogan", "Gradiente", "Multilaser", "Positivo",
                "BlackBerry Limited", "DataWind", "10.Or", "Amoi", "BBK",
                "Coolpad", "Cubot", "Gfive", "Haier", "Hisense", "Honor", "Huawei",
                "Konka", "LeEco", "Meitu", "Meizu", "Ningbo Bird", "OnePlus",
                "Oppo", "iQOO", "Itel Mobile", "Realme", "Smartisan",
                "TCL Corporation", "Technology Happy Life", "Tecno Mobile",
                "Umidigi", "Vivo", "Vsun", "Wasam", "Xiaomi", "ZTE", "ZUK Mobile",
                "Jablotron", "Verzo", "SICO Technology", "Jolla",
                "Nokia Corporation", "HMD Global", "Bittium", "Archos",
                "Alcatel Mobile", "Groupe Bull", "MobiWire", "Wiko", "Gigaset",
                "Medion", "TechniSat", "Tiptel", "MLS", "X-tigi Mobile", "Lenovo",
                "Infinix", "Celkon", "Iball", "Intex Technologies",
                "Karbonn Mobiles", "Lava International", "HCL Technologies", "Jio",
                "LYF", "Micromax Informatics", "Onida Electronics",
                "Spice Digital", "Videocon", "Xolo", "YU Televentures", "Asia",
                "Brondi", "New Generation Mobile", "Olivetti",
                "Onda Mobile Communication", "Akai", "Fujitsu", "Casio", "Hitachi",
                "JRC", "Kyocera", "Mitsubishi Electric", "NEC", "Panasonic",
                "Sansui", "Sharp", "Sony", "Toshiba", "Just5", "M Dot",
                "Ninetology", "Kyoto Electronics", "Lanix", "Zonda", "Fairphone",
                "John\"s Phone", "Philips", "Arirang", "QMobile", "Voice Mobile",
                "Cherry Mobile", "Firefly Mobile", "Starmobile", "Cloudfone",
                "MyPhone", "Torque", "Kruger&Matz", "Manta Multimedia", "myPhone",
                "Allview", "Evolio", "E-Boda", "Myria", "Utok", "Beeline",
                "Explay", "Gresso", "Highscreen", "Megafon", "MTS", "RoverPC",
                "teXet", "Sitronics", "Yotaphone", "Cell C", "MTN", "Mobicel",
                "Telkom", "Vodacom", "KT Tech", "Pantech", "Samsung", "Doro",
                "Acer", "Asus", "BenQ", "DBTel", "Dopod", "Foxconn",
                "Gigabyte Technology", "HTC", "AIS", "DTAC", "True", "Wellcom",
                "I-Mobile", "EvertekTunisie", "ASELSAN", "Vestel", "Thuraya",
                "Bullitt Group", "Wileyfox", "Apple", "BLU Products",
                "Caterpillar", "Firefly", "Garmin", "Google", "HP", "InFocus",
                "InfoSonics", "Motorola Mobility", "Obi Worldphone", "Nextbit",
                "Purism, SPC", "BPhone", "Masstel", "GTel");

         */
        //TODO ... vendor in our list of phone companies ...
        return check;
    }
}
