package com.lsb.localDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONObject;

public class Memo {
    static JSONArray memosOffline;

    public static JSONArray get() {
        return memosOffline;
    }

    public static void refresh() {
        try {
            memosOffline = new JSONArray();
            Connector.connect();
            Statement st = Connector.connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM memos");

            while(rs.next()) {
                String _id = rs.getString("_id");
                String title = rs.getString("title");
                String article = rs.getString("article");
                String color = rs.getString("color");
                long timestamp = rs.getLong("timestamp");

                JSONObject body = new JSONObject();
                JSONObject timestampObj = new JSONObject();

                timestampObj.put("_seconds", timestamp);

                body.put("_id", _id);
                body.put("title", title);
                body.put("article", article);
                body.put("color", color);
                body.put("timestamp", timestampObj);

                memosOffline.put(body);
            }
        }
        catch (SQLException e) {
            System.err.println(e);
        }
        finally {
            Connector.close();
        }
    }

    public static void update(JSONObject body) {
        try {
            String _id = body.getString("_id");
            String title = body.getString("title");
            String article = body.getString("article");
            String color = body.getString("color");
            long timestamp = body.getJSONObject("timestamp").getLong("_seconds");

            Connector.connect();
            String sql =
                "INSERT INTO memos (_id, title, article, color, timestamp) " + 
                "VALUES ('" + _id + "', '" + title + "', '" + article + "', '" + color + "', '" + timestamp + "') " +
                "ON CONFLICT (_id) " + 
                "DO UPDATE SET _id = '" + _id + "', title = '" + title + "', article = '" + article + "', color = '" + color + "', timestamp = '" + timestamp + "'";

            PreparedStatement st = Connector.connection.prepareStatement(sql);
            st.executeUpdate();
            
        }
        catch (SQLException e) {
            System.err.println(e);
        }
        finally {
            Connector.close();
        }
    }

    public static void delete(String _id) {
        try {
            Connector.connect();
            String sql = 
                "DELETE FROM memos " + 
                "WHERE _id = '" + _id + "';";

            PreparedStatement st = Connector.connection.prepareStatement(sql);
            st.executeUpdate();
        }
        catch (SQLException e) {
            System.err.println(e);
        }
        finally {
            Connector.close();
        }
    }
}
