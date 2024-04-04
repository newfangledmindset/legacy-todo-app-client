package com.lsb.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONException;
import org.json.JSONObject;

public class Connector {
    public static final Connector instance = new Connector();

    public String request(URL url, JSONObject obj) throws IOException, JSONException {
        String returnString = "";

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("x-access-token", Auth.getAuthToken());
            connection.setDoOutput(true);

            BufferedWriter req = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8));
            req.write(obj.toString());
            req.flush();
            req.close();

            BufferedReader res = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

            returnString = res.readLine();

            return returnString;
        }
        catch (IOException e) {
            throw new IOException(e);
        }
        catch (JSONException e) {
            throw new JSONException(e);
        }
    }
}
