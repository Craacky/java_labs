package org.example;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
   public static void main(String[] args) throws SQLException {
        Connection connection = SQL.getNewConnection();
        SQL.menuGreeter(connection);
        SQL.closeConnection(connection);

    }
}
