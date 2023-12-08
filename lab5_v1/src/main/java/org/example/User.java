package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
    public static int clientId = 0;
    public static int pcIdentify = 0;

    public static boolean status = false;
    public static double clientBalance = 0.0;

    public static ArrayList<String> tablesName = new ArrayList<>();
    public static ArrayList<Integer> pcId = new ArrayList<>();

    public static void clientMenu(Connection connection) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("--------------------Hi user!--------------------");
        System.out.println("1. I have an account.");
        System.out.println("2. I don't have an account.");
        System.out.println("3. Exit.");
        System.out.print("> ");

        int selector = sc.nextInt();
        if (selector == 1) {
            status = checkClientId(connection);
        } else if (selector == 2) {
            status = clientRegistration(connection);
        } else {
            System.out.println("BYE!");
        }
        selector = 0;
        if (clientId != 0 && status) {
            while (selector < 4) {
                System.out.println("--------------------Shopping--------------------");
                System.out.println("1. Check balance.");
                System.out.println("2. Deposit cash.");
                System.out.println("3. Make order.");
                System.out.println("4. Exit");
                System.out.print("> ");
                selector = sc.nextInt();

                if (selector == 1) {
                    System.out.println("Current balance = " + checkBalance(connection));
                } else if (selector == 2) {
                    depositBalance(connection);
                } else if (selector == 3) {
                    makeOrder(connection);
                    insertPcData(connection);
                }
            }
        }
    }

    public static boolean checkClientId(Connection connection) throws SQLException {
        Scanner in = new Scanner(System.in);
        System.out.print("Your id = ");
        int idCheck = in.nextInt();

        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT idClient FROM shop.Client WHERE idClient =" + idCheck);

        if (result.next()) {
            System.out.println("Have a good shopping!");
            clientId = idCheck;
            return true;
        } else {
            System.out.println("You are not registered!");
            clientRegistration(connection);
        }
        return false;
    }

    public static boolean clientRegistration(Connection connection) throws SQLException {
        Scanner in = new Scanner(System.in);
        System.out.println("--------------------Registration--------------------");

        int id = SQLPart.idSetter(connection, "Client");
        clientId = id;

        System.out.print("Enter your address = ");
        String address = in.next();

        System.out.print("Enter your Balance = ");
        double balance = in.nextDouble();

        String sql = "values('" + id + "','" + address + "','" + balance + "')";
        Statement statement = connection.createStatement();

        int result = statement.executeUpdate("insert into Client(idClient,Address,Balance)" + sql);
        if (result > 0) {
            System.out.println("Successfully registration");
            System.out.println("Your id = " + clientId);
            System.out.println("Your address = " + address);
            System.out.println("Your balance = " + balance);
            return true;
        } else {
            System.out.println("Unsuccessful registration");
        }
        return false;
    }


    public static double checkBalance(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select Balance from shop.Client where idClient =" + clientId);
        if (rs.next()) {
            return rs.getDouble("Balance");
        }
        return 0;
    }

    public static void depositBalance(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter value of deposit = ");
        double depositValue = scanner.nextDouble();
        clientBalance = depositValue + checkBalance(connection);

        Statement stmt = connection.createStatement();
        stmt.executeQuery("update Client set Balance =" + clientBalance + " where idClient =" + clientId);
    }

    public static void tablesName(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("show tables");
        System.out.println("All tables name");
        while (rs.next()) {
            if (!rs.getString(1).equals("Client") &&
                    !rs.getString(1).equals("Order") &&
                    !rs.getString(1).equals("PC")
            ) {
                tablesName.add(rs.getString(1));
            }
        }
    }

    public static void showTable(Connection connection, String tableName) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM shop." + tableName);
        String format = "|%5s\t| %5s\t | %5s |";
        System.out.printf((format) + "%n", "ID", "NAME", "PRICE");
        while (rs.next()) {
            int id = rs.getInt("id" + tableName);
            String name = rs.getString("Name");
            Double price = rs.getDouble("Price");
            System.out.printf((format) + "%n", id + "", name, price);
        }
    }

    public static void insertPcData(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String sql1 = "insert into shop.PC values";
        pcIdentify = SQLPart.idSetter(connection, "PC");
        String sql2 = "(" + pcIdentify + "," + pcId.get(0) + "," + pcId.get(1) + "," + pcId.get(2) + "," +
                pcId.get(3) + "," + pcId.get(4) + "," + pcId.get(5) + "," +
                pcId.get(6) + "," + pcId.get(7) + ")";
        statement.executeQuery(sql1 + sql2);
    }


    public static void makeOrder(Connection connection) throws SQLException {
        tablesName(connection);
        Scanner sc = new Scanner(System.in);
        System.out.println(SQLPart.idSetter(connection, "Cooler"));
        for (String s : tablesName) {
            System.out.println("Current table " + s);
            showTable(connection, s);
            System.out.println("Choose one by id number");
            System.out.print("> ");
            pcId.add(sc.nextInt());

        }

    }

    public static void completeOrder(Connection connection) throws SQLException {

    }

}
