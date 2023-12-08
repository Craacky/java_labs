package org.example;

import java.sql.*;

public class SQLPart {
    public static Connection getNewConnection() throws SQLException {
        String url = "jdbc:mariadb://localhost/shop";
        String user = "root";
        String passwd = "3197375";
        return DriverManager.getConnection(url, user, passwd);
    }

    public static int idSetter(Connection connection, String tableName) throws SQLException {
        Statement statement = connection.createStatement();
        String sql1 = "select * from shop." + tableName + " where id" + tableName + " != 0";
        ResultSet rs = statement.executeQuery(sql1);
        if (rs.next()) {
            rs.last();
            return rs.getInt("id" + tableName) + 1;
        } else {
            return 1;
        }
    }

    public static void closeConnection(Connection connector) throws SQLException {
        connector.close();
        System.out.println("Connection close");
    }


}
