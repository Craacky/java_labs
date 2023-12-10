package org.example;

import java.sql.*;

public class SQL {
    public static Connection getNewConnection() throws SQLException {
        String url = "jdbc:mysql://localhost/shop";
        String user = "root";
        String passwd = "";
        return DriverManager.getConnection(url, user, passwd);
    }

    public static void closeConnection(Connection connector) throws SQLException {
        connector.close();
        System.out.println("Connection close");
    }
}
