package org.example;

import java.sql.*;

public class SQLPart {
    public static Connection getNewConnection() throws SQLException {
        String url = "jdbc:mysql://localhost/shop";
        String user = "root";
        String passwd = "";
        return DriverManager.getConnection(url, user, passwd);
    }

    public static int idSetter(Connection connection, String tableName) throws SQLException {
        Statement statement = connection.createStatement();
        String sql1 = "select max(id" + tableName + ")" + "from shop." + tableName;
        ResultSet rs = statement.executeQuery(sql1);
        while (rs.next()) {
            return rs.getInt("id" + tableName) + 1;
        }

        return 1;
    }

    public static void closeConnection(Connection connector) throws SQLException {
        connector.close();
        System.out.println("Connection close");
    }


}
