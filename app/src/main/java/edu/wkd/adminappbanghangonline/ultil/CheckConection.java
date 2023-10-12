package edu.wkd.adminappbanghangonline.ultil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class CheckConection {
    public static boolean HaveConnection(Context context){
        boolean wifi = false, moblie = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] infos = cm.getAllNetworkInfo();
        for (NetworkInfo networkInfo : infos){
            if (networkInfo.getTypeName().equals("WIFI"))
                if (networkInfo.isConnected())
                    wifi = true;

            if (networkInfo.getTypeName().equals("MOBILE"))
                if (networkInfo.isConnected())
                    moblie = true;
        }
        return wifi || moblie;
    }

    public static void ShowToast(Context context, String tb){
        Toast.makeText(context, tb, Toast.LENGTH_SHORT).show();
    }
}
