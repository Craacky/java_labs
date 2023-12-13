package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        System.out.println("[3] Change order status.");
        System.out.println("[exit] [back]");
        System.out.print("Shop:/Admin> ");
        String answer = sc.next();
        switch (answer) {
            case "1" -> outputOrder(connection);
            case "2" -> {
                autoAddComponentsType(connection);
                topUpComponents(connection);
            }
            case "3" -> changeOrderStatus(connection);
            case "back" -> SQL.menuGreeter(connection);
            case "exit" -> System.exit(0);
        }
    }

    public static void changeOrderStatus(Connection connection) throws SQLException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();


        Scanner sc = new Scanner(System.in);
        outputOrder(connection);
        System.out.println("Current date " + dtf.format(now));
        System.out.println("Choose deprecated order:");
        System.out.print("Shop:/Admin/SetDeprecated>");
        int orderId = sc.nextInt();
        Statement statement = connection.createStatement();
        statement.executeUpdate("UPDATE shop.order SET state = 1 where order_id = " + orderId);
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

    public static void outputOrder(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("""
                SELECT t1.*, t3.address, t4.name
                FROM shop.order t1\s
                INNER JOIN shop.order_item t2 ON t1.order_id = t2.order_id
                INNER JOIN shop.client t3 ON t1.clients_id = t3.client_id
                INNER JOIN shop.component t4 ON t2.component_id = t4.component_id
                INNER JOIN shop.component_type t5 ON t5.type_id = t4.type_id ORDER BY t1.timestamp;""");
        String format = "%-8s|%-10s|%-20s|%-5s|%-7s|%-11s|%-50s|%-50s";
        System.out.println("ORDER ID|CLIENTS ID|TIMESTAMP           |STATE|PAYMENT|TOTAL PRICE|" +
                "ADDRESS                                           |COMPONENT NAME                                    ");

        while (rs.next()) {
            int orderId = rs.getInt("t1.order_id");
            int clientId = rs.getInt("t1.clients_id");
            String timeStamp = rs.getString("t1.timestamp");
            int orderState = rs.getInt("t1.state");
            int paymentStatus = rs.getInt("t1.payment_status");
            double totalPrice = rs.getInt("t1.total_price");
            String status = rs.getString("t3.address");
            String nameComponent = rs.getString("t4.name");
            System.out.printf((format) + "%n", orderId + "", clientId, timeStamp, orderState, paymentStatus, totalPrice, status, nameComponent);
        }

    }
}