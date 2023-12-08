package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = SQLPart.getNewConnection();
        menuGreeter(connection);
        SQLPart.closeConnection(connection);
    }

    public static void menuGreeter(Connection connection) throws SQLException {
        Scanner in = new Scanner(System.in);
        System.out.println("--------------------Who you are ?--------------------");
        System.out.println("1. I am user!");
        System.out.println("2. I am admin!");
        System.out.println("3. EXIT!");
        System.out.print("> ");

        int checkPoint = in.nextInt();

        if (checkPoint == 1) {
            User.clientMenu(connection);
        } else if (checkPoint == 2) {
            Admin.adminMenu(connection);
        } else if (checkPoint == 3) {
            System.exit(0);
        }
    }

}