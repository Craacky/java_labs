package org.example;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class SQL {
    public static Connection getNewConnection() throws SQLException {
        String url = "jdbc:mariadb://localhost/shop";
        String user = "root";
        String passwd = "3197375";
        return DriverManager.getConnection(url, user, passwd);
    }

    public static void menuGreeter(Connection connection) throws SQLException {
        Scanner in = new Scanner(System.in);
        System.out.println("--------------------Who you are ?--------------------");
        System.out.println("[1] I am user!");
        System.out.println("[2] I am admin!");
        System.out.println("[exit] ");
        System.out.print("Shop:/home> ");

        String checkPoint = in.next();

        if (checkPoint.equals("1")) {
            User.clientMenu(connection);
        } else if (checkPoint.equals("2")) {
            Admin.adminMenu(connection);
        } else if (checkPoint.equalsIgnoreCase("exit")) {
            System.exit(0);
        }
    }

    public static void closeConnection(Connection connector) throws SQLException {
        connector.close();
        System.out.println("Connection close");
    }
}
