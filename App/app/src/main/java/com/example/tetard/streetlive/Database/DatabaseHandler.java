package com.example.tetard.streetlive.Database;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

/**
 * Created by nicolas on 12/03/2018.
 */

public class DatabaseHandler {
    public void connect(String link) {
        try {
            Log.wtf("HANDLER", "entered Connect Handler");

            // connect ?
            URL url = new URL(link);
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));

            // response
            HttpResponse response = client.execute(request);
            Log.wtf("URL", response.toString());
            BufferedReader in = new BufferedReader
                    (new InputStreamReader(response.getEntity().getContent()));
        }
        catch (Exception e) {
            Log.wtf("EEEEEEEEEEEEEEEERRRRRRRRRRRRRRR", e.getStackTrace().toString());
        }
    }
}
