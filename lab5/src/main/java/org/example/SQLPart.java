package org.example;

import java.sql.*;
import java.util.Scanner;

public class SQLPart {
    public static Connection getNewConnection() throws SQLException {
        String url = "jdbc:mariadb://localhost/shop";
        String user = "root";
        String passwd = "3197375";
        return DriverManager.getConnection(url, user, passwd);
    }

    public static void closeConnection(Connection connector) throws SQLException {
        connector.close();
        System.out.println("Connection close");
    }


}
