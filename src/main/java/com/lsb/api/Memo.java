package com.lsb.api;

import java.io.IOException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import com.lsb.Config;

public class Memo {
    private static JSONArray memosOrigin = new JSONArray();
    private static JSONArray memos = new JSONArray();

    static Connector conn = Connector.instance;

    public static JSONArray get() {
        return memos;
    }

    public static void reset() {
        memos = memosOrigin;
    }

    public static void set(JSONObject memo) {
        try {
            URL url = new URL(Config.URL_SET_MEMO);
            memo.remove("timestamp");
            conn.request(url, memo);
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void refresh() {
        try {
            URL url = new URL(Config.URL_GET_MEMO);
            JSONObject nullData = new JSONObject();

            memosOrigin = new JSONArray(conn.request(url, nullData));
            memos = memosOrigin;
            backup();
        }
        catch (IOException e) {
            com.lsb.localDB.Memo.refresh();
            memosOrigin = com.lsb.localDB.Memo.get();
            memos = memosOrigin;
        }
        catch (JSONException e) {
            System.err.println(e);
        }
    }

    public static void delete(String _id) {
        try {
            URL url = new URL(Config.URL_DELETE_MEMO);
            JSONObject body = new JSONObject();
            body.put("_id", _id);

            conn.request(url, body);
        }
        catch (IOException e) {
            System.err.println(e);
        }
        catch (JSONException e) {
            System.err.println(e);
        }
    }

    public static JSONObject findByID(String _id) {
        for (Object obj : memosOrigin) {
            JSONObject body = (JSONObject) obj;
            if (body.getString("_id").equals(_id)) return body;
        }

        return null;
    }

    public static void search(String keyword) {
        JSONArray result = new JSONArray();
        keyword = keyword.toUpperCase();

        for (Object obj : memos) {
            JSONObject body = (JSONObject) obj;
            String title = body.getString("title").toUpperCase();
            String article = Jsoup.parse(body.getString("article")).text().toUpperCase();

            if (title.contains(keyword) || article.contains(keyword)) {
                result.put(body);
            }
        }

        memos = result;
    }

    private static void backup() {
        for (Object obj : memosOrigin) {
            JSONObject body = (JSONObject) obj;
            com.lsb.localDB.Memo.update(body);
        }
    }
}
