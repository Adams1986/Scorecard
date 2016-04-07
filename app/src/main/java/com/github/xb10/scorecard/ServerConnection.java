package com.github.xb10.scorecard;

import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class for connecting to PHP server.
 */
public class ServerConnection {

    public static String executePost(String targetURL, String urlParameters){

        HttpURLConnection connection = null;
        try {
            //adding identifier
            urlParameters = "message="+urlParameters;

            //This bit is bad for performance. Network operations should run as an asynchronous task in background
            /*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);*/

            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "" + Integer.toString(urlParameters.getBytes().length));

            //Neccessary or not?
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send the request
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(urlParameters);
            outputStream.flush();
            outputStream.close();

            //Get Response from server
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null){
                response.append(line);
                //basically the same as "\n"
                response.append("\r");
            }
            reader.close();

            return response.toString();

            //connection.connect();

        }
        catch (Exception e) {
            e.printStackTrace();

            return null;

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
