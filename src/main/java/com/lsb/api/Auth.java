package com.lsb.api;

import java.io.IOException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import com.lsb.Config;
import com.lsb.exception.DuplicateIDException;

public class Auth {
    private static String authToken;

    static Connector conn = Connector.instance;

    public static String getAuthToken() {
        return authToken;
    }

    public static void login(String id, String password) {
        try {
            URL url = new URL(Config.URL_LOGIN);
            JSONObject LoginData = createLoginData(id, password);

            authToken = new JSONObject(conn.request(url, LoginData)).getString("authToken");
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void register(String id, String password, String name) throws DuplicateIDException {
        try {
            URL url = new URL(Config.URL_REGISTER);
            JSONObject RegisterData = createRegisterData(id, password, name);
            String _token = new JSONObject(conn.request(url, RegisterData)).getString("authToken");
            authToken = _token;
        }
        catch (IOException e) {
            System.err.println(e);
        }
        catch (JSONException e) {
            throw new DuplicateIDException();
        }
    }

    static JSONObject createLoginData(String id, String password) {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("password", password);

        return obj;
    }

    static JSONObject createRegisterData(String id, String password, String name) {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("password", password);
        obj.put("username", name);

        return obj;
    }

    static JSONObject createIDData(String id) {
        JSONObject obj = new JSONObject();
        obj.put("id", id);

        return obj;
    }
}
