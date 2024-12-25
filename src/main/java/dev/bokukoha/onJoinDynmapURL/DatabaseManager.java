package dev.bokukoha.onJoinDynmapURL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseManager {

    private final String url = "jdbc:sqlite:plugins/On-Join-Dynmap-Url/playerdata.db";

    public DatabaseManager() {
        try (Connection conn = DriverManager.getConnection(url)) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS player_settings ("
                    + "uuid TEXT PRIMARY KEY, "
                    + "name TEXT, "
                    + "send_url BOOLEAN DEFAULT true)";
            try (PreparedStatement stmt = conn.prepareStatement(createTableQuery)) {
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getSendURLSetting(UUID uuid) {
        try (Connection conn = DriverManager.getConnection(url)) {
            String query = "SELECT send_url FROM player_settings WHERE uuid = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, uuid.toString());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getBoolean("send_url");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true; // デフォルト値
    }

    public void setSendURLSetting(UUID uuid, String name, boolean sendURL) {
        try (Connection conn = DriverManager.getConnection(url)) {
            String insertOrUpdateQuery = "INSERT INTO player_settings (uuid, name, send_url) VALUES (?, ?, ?) "
                    + "ON CONFLICT(uuid) DO UPDATE SET send_url = excluded.send_url, name = excluded.name";
            try (PreparedStatement stmt = conn.prepareStatement(insertOrUpdateQuery)) {
                stmt.setString(1, uuid.toString());
                stmt.setString(2, name);
                stmt.setBoolean(3, sendURL);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
