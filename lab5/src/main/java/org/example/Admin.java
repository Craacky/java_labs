package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin {
    public static String name = "1";
    public static String passwd = "1";
    public static ArrayList<Integer> typeComponentsId = new ArrayList<>();

    public static void adminMenu(Connection connection) throws SQLException {
        boolean status = adminSignIn();
        if (status) {
            serverPartMenu(connection);
        }

    }

    public static boolean adminSignIn() {
        Scanner sc = new Scanner(System.in);
        System.out.println("--------------------Hi Admin!--------------------");
        System.out.print("Nick: ");
        String nick = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        return nick.equals(name) && passwd.equals(password);
    }

    public static void serverPartMenu(Connection connection) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("--------------------Server Part--------------------");
        System.out.println("[1] View orders.");
        System.out.println("[2] Top up the components.");
        System.out.println("[exit]");
        System.out.print("Shop:/Admin> ");
        String answer = sc.next();
        if (answer.equals("1")) {

        } else if (answer.equals("2")) {
            autoAddComponentsType(connection);
            topUpComponents(connection);
        }
    }

    public static void autoAddComponentsType(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM component_type");
        if (result.next()) {
            System.out.println("Component type uploaded!");
        } else {
            insertTypeComponent(connection, "Cooler");
            insertTypeComponent(connection, "GPU");
            insertTypeComponent(connection, "Motherboard");
            insertTypeComponent(connection, "PSU");
            insertTypeComponent(connection, "CPU");
            insertTypeComponent(connection, "RAM");
            insertTypeComponent(connection, "Storage");
            insertTypeComponent(connection, "SystemUnit");
            insertTypeComponent(connection, "PC");
            System.out.println("Component type loaded!");
        }
    }

    public static void insertTypeComponent(Connection connection, String name) throws SQLException {
        Statement statement = connection.createStatement();

        String sqlInsert = "INSERT INTO component_type(name) values(" + "'" + name + "')";
        int resultSet = statement.executeUpdate(sqlInsert);

        if (resultSet != 0) {
            System.out.println(name + " added !");
        } else {
            System.out.println("Insert Error!");
        }
    }

    public static void componentTypeNameGetter(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT name FROM component_type ORDER BY type_id");
        int index = 1;
        while (rs.next()) {
            typeComponentsId.add(index);
            System.out.println("[" + index + "] " + rs.getString(1));
            ++index;
        }
    }

    public static void topUpComponents(Connection connection) throws SQLException {
        Scanner sc = new Scanner(System.in);

        int pointer;
        do {
            System.out.println("--------------------Which one you want to top up?--------------------");
            componentTypeNameGetter(connection);
            System.out.println("[10]BACK");
            System.out.print("Shop:/Admin/TopUP> ");
            pointer = sc.nextInt();

            if (pointer > 0 && pointer <= 9) {
                insertData(connection, pointer);
            }

        } while (pointer != 10);
        serverPartMenu(connection);
    }

    public static void insertData(Connection connection, int id) throws SQLException {
        Statement statement = connection.createStatement();
        Scanner sc = new Scanner(System.in);
        System.out.println("--------------------Replenish--------------------");

        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Price: ");
        double price = sc.nextDouble();

        String sqlUpdate = "INSERT INTO component(type_id,name,price)" +
                " VALUES (" + id + ",'" + name + "'," + price + ")";
        statement.executeUpdate(sqlUpdate);
    }
}