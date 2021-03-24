package com.codecool.database;


import java.sql.*;

public class RadioCharts {
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;

    public RadioCharts(String db_url, String db_user, String db_password) {
        DB_URL = db_url;
        DB_USER = db_user;
        DB_PASSWORD = db_password;
    }

    public String getMostPlayedSong() {
        String result = "";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = " SELECT song FROM music_broadcast GROUP BY song ORDER BY SUM(times_aired) DESC, artist ASC LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = rs.getString("song");
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public String getMostActiveArtist() {
        String result = "";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT artist, COUNT(DISTINCT song) as songs FROM music_broadcast GROUP BY artist ORDER BY songs DESC";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = rs.getString("artist");
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}

