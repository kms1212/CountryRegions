package com.kms1212.mcplugin.countryregions;

import org.bukkit.ChatColor;

import java.sql.*;

public class DataControl {
    private Connection conn;
    private PreparedStatement stmt;
    private CountryRegions plugin;
    
    public DataControl(CountryRegions plugin) {
        this.plugin = plugin;
        String host = plugin.getConfig().getString("sql.host");
        String port = plugin.getConfig().getString("sql.port");
        String database = plugin.getConfig().getString("sql.database");
        String username = plugin.getConfig().getString("sql.username");
        String password = plugin.getConfig().getString("sql.password");
        String tableName = plugin.getConfig().getString("sql.table");

        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            plugin.getLogger().info(ChatColor.RED + "JDBC Driver not found.");
            return;
        }
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            plugin.getLogger().info(ChatColor.RED + "SQL connection error.");
            return;
        }
    }

    public void initializeTables() {
        try {
            stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS CR_CONFIG_TABLES ( TableName CHAR(20), PRIMARY KEY (TableName));");
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
