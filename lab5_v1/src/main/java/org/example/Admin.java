package org.example;

import java.sql.*;
import java.util.Scanner;

public class Admin {
    public static String name = "1";
    public static String passwd = "1";

    public static void adminMenu(Connection connection) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("--------------------Hi Admin!--------------------");
        System.out.print("Nick: ");
        String nick = sc.next();
        System.out.print("Password: ");
        String password = sc.next();

        if (nick.equals(name) && passwd.equals(password)) {
            System.out.println("--------------------Hi " + nick + "--------------------");
            System.out.println("1.View orders.");
            System.out.println("2.Replenish the components.");
            System.out.println("3.Set order status");
            System.out.println("4.Exit");
            System.out.print("> ");
            int answer = sc.nextInt();

            if (answer == 1) {

            } else if (answer == 2) {
                replenishComponents(connection);
            } else if (answer == 3) {

            } else if (answer == 4) {
                System.exit(0);
            }
        }


    }

    public static void replenishComponents(Connection connection) throws SQLException {
        Scanner sc = new Scanner(System.in);
        int pointer;
        do {
            System.out.println("--------------------Which one you want to replenish?--------------------");
            System.out.println("""
                    1| Cooler
                    2| GraphicCard
                    3| Motherboard
                    4| PowerSupply
                    5| Processor \s
                    6| RAM       \s
                    7| Storage   \s
                    8| SystemUnit\s
                    9| Exit""");
            System.out.print("> ");
            pointer = sc.nextInt();
            if (pointer == 1)
                insertData(connection, "Cooler");

            if (pointer == 2)
                insertData(connection, "GraphicCard");

            if (pointer == 3)
                insertData(connection, "Motherboard");

            if (pointer == 4)
                insertData(connection, "PowerSupply");

            if (pointer == 5)
                insertData(connection, "Processor");

            if (pointer == 6)
                insertData(connection, "RAM");

            if (pointer == 7)
                insertData(connection, "Storage");

            if (pointer == 8)
                insertData(connection, "SystemUnit");
        } while (pointer != 9);

    }

    public static void columnsName(Connection connection, String table) throws SQLException {
        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery("select * from shop." + table);

        ResultSetMetaData rsMetaData = rs.getMetaData();
        System.out.println("Column names in the " + table + " table: ");

        int count = rsMetaData.getColumnCount();
        for (int i = 1; i <= count; i++) {
            System.out.println(i + ". " + rsMetaData.getColumnName(i));
        }
    }

    public static void insertData(Connection connection, String tableName) throws SQLException {
        Statement statement = connection.createStatement();
        Scanner sc = new Scanner(System.in);
        System.out.println("--------------------Replenish--------------------");

        System.out.println("Table: " + tableName);
        columnsName(connection, tableName);

        System.out.print("Enter id = ");
        int id = sc.nextInt();

        System.out.print("Enter Name = ");
        String name = sc.next();

        System.out.print("Enter Price = ");
        double price = sc.nextDouble();

        statement.executeUpdate("insert into shop." + tableName + " values (" + id + ",'" + name + "'," + price + ")");
    }

}
