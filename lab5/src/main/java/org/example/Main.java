package org.example;

import java.sql.*;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = SQL.getNewConnection();
        menuGreeter(connection);
        SQL.closeConnection(connection);
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
}
