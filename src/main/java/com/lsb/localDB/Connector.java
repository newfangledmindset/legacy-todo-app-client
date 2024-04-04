package com.lsb.localDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.lsb.Config;

public class Connector {
    static Connection connection = null;

    public static void connect() {
        try {
            connection = DriverManager.getConnection(Config.URL_LOCALDB);
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void close() {
        if (connection != null) {
            try {
                connection.close();
            }
            catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
