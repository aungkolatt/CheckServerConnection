package com.example.divine_it.checkserverconnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {


    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        String url = "https://www.google.com/";

        TextView showtext = (TextView) findViewById(R.id.text);
        flag = isInternetOn();
        if (flag) {
            Toast.makeText(this, " Network connection is ok ", Toast.LENGTH_LONG).show();

            boolean flag1 = IsReachable(getApplicationContext(), url);
            if (flag1) {
                showtext.setText("Congrates your server is connected");
            } else {
                showtext.setText("opps your server connection fail. plz check your server");

            }

        } else {
            Toast.makeText(this, "Sorry your net connection is fail", Toast.LENGTH_LONG).show();
        }


    }


    public static boolean IsReachable(Context context, String check_url) {
        // First, check we have any sort of connectivity
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = connMgr.getActiveNetworkInfo();
        boolean isReachable = false;

        if (netInfo != null && netInfo.isConnected()) {
            // Some sort of connection is open, check if server is reachable
            try {

                URL url = new URL(check_url);

                // URL url = new URL("http://192.168.100.93/office_com/www/api.php/office_com/Logins/SignUp?api_user=endpoint");
                //URL url = new URL("http://10.0.2.2");
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setRequestProperty("User-Agent", "Android Application");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(10 * 1000);
                try {
                    urlc.connect();
                    System.out.println("-----fffff");
                } catch (Exception e) {

                    System.out.println("-----fffff  " + e);

                }
                isReachable = (urlc.getResponseCode() == 200);
            } catch (IOException e) {

            }
        }

        return isReachable;
    }

    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {


            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {


            return false;
        }
        return false;
    }

}
