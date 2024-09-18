package org.menu.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;
import org.menu.Settings;

import java.util.logging.Logger;


public class ConnectionManager {
    Logger log = Logger.getLogger(ConnectionManager.class.getName());
    private String url;
    private String user;
    private String password;
    public ConnectionManager() {
        Dotenv dotenv = Dotenv.configure()
                .directory(Settings.ENV_PATH.toString())
                .load();
        this.url = dotenv.get("URL");
        this.user = dotenv.get("USER");
        this.password = dotenv.get("PASSWORD");
    }
    public ConnectionManager(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public static void init() {
        Logger log = Logger.getLogger(ConnectionManager.class.getName());
        try {
            // Register PostgreSQL Driver
            Class.forName("org.postgresql.Driver");
            log.info("Driver loaded");
        } catch (ClassNotFoundException e) {
            log.severe("Unable to load Driver class\n" + e.getMessage());
            System.exit(1);
        }
    }
    public Connection getConnection() throws SQLException {
        if (url == null || user == null || password == null) {
            log.severe("Error loading env");
            throw new IllegalStateException("Error loading env");
        }
            return DriverManager.getConnection(url, user, password);
    }
    public static Connection getTestConnection(String url, String username, String password) {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
