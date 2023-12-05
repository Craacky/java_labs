package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
