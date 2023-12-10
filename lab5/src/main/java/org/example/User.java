package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class User {
    public static int clientId;

    public static double clientBalance;

    public static ArrayList<String> typeComponentsNames = new ArrayList<>();

    public static void clientMenu(Connection connection) throws SQLException {
        Scanner sc = new Scanner(System.in);
        boolean status = false;
        System.out.println("--------------------Main Page--------------------");
        System.out.println("[1] I have an account.");
        System.out.println("[2] I don't have an account.");
        System.out.println("[exit]");
        System.out.print("Shop:/Unrecognized> ");

        String selector = sc.next();
        if (selector.equals("1")) {
            status = checkClientExist(connection);
        } else if (selector.equals("2")) {
            status = clientSignUp(connection);
        } else if (selector.equalsIgnoreCase("exit")) {
            System.out.println("BYE!");
        }
        if (status) {
            clientShoppingSection(connection);
        }
    }

    public static void clientShoppingSection(Connection connection) throws SQLException {
        Scanner sc = new Scanner(System.in);
        String selector = null;
        while (!Objects.equals(selector, "exit")) {
            System.out.println("--------------------Shopping--------------------");
            System.out.println("[1] Check balance.");
            System.out.println("[2] Top up your balance.");
            System.out.println("[3] Make order.");
            System.out.println("[exit]");
            System.out.print("Shop:/User> ");
            selector = sc.next();

            if (selector.equals("1")) {
                System.out.println("Current balance = " + checkClientBalance(connection));
            } else if (selector.equals("2")) {
                topUpClientBalance(connection);
            } else if (selector.equals("3")) {
                orderFinal(connection);
            }else if (selector.equalsIgnoreCase("exit")){
                System.exit(0);
            }
        }
    }

    public static boolean checkClientExist(Connection connection) throws SQLException {
        Scanner in = new Scanner(System.in);
        System.out.println("--------------------Sign in--------------------");
        System.out.print("Nick: ");
        String clientName = in.next();
        System.out.print("Password: ");
        String clientPassword = in.next();

        String sqlSelect = "SELECT client_id FROM client WHERE " +
                "name = " + "'" + clientName + "'" +
                " and " +
                "password = " + "'" + clientPassword + "'";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sqlSelect);

        if (rs.next()) {
            System.out.println("Have a good shopping!");
            clientId = rs.getInt("client_id");
            return true;
        } else {
            System.out.println("You are not registered!");
            clientSignUp(connection);
        }
        return false;
    }

    public static boolean clientSignUp(Connection connection) throws SQLException {
        Scanner in = new Scanner(System.in);
        System.out.println("--------------------Sign Up--------------------");

        System.out.print("Enter your nick: ");
        String clientName = in.nextLine();
        System.out.print("Enter your password: ");
        String clientPassword = in.nextLine();
        System.out.print("Enter your address: ");
        String clientAddress = in.nextLine();

        String sqlInsert = "INSERT INTO client(name,password,address) " +
                "VALUES( " +
                "'" + clientName + "'," +
                "'" + clientPassword + "'," +
                "'" + clientAddress + "')";

        Statement statement = connection.createStatement();

        int result = statement.executeUpdate(sqlInsert);
        if (result > 0) {
            System.out.println("Successfully registration");
            return true;
        } else {
            System.out.println("Unsuccessful registration");
        }
        return false;
    }


    public static double checkClientBalance(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        String sqlSelect = "SELECT balance FROM client WHERE client_id =" +
                clientId;

        ResultSet rs = stmt.executeQuery(sqlSelect);
        if (rs.next()) {
            return rs.getDouble("balance");
        }
        return 0;
    }

    public static void topUpClientBalance(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter value of deposit: ");
        double depositValue = scanner.nextDouble();
        clientBalance = depositValue + checkClientBalance(connection);

        String sqlUpdate = "UPDATE client SET balance =" + clientBalance + " WHERE client_id =" + clientId;
        Statement stmt = connection.createStatement();

        stmt.executeUpdate(sqlUpdate);
    }

    public static void orderFinal(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        Scanner sc = new Scanner(System.in);
        int typePointer = 0;
        int componentPointer;
        ArrayList<Integer> componentId = new ArrayList<>();
        int orderPointer = 0;

        while (orderPointer != 11) {
            orderPointer = orderMenuGreeter(connection);
            if (orderPointer == 10) {
                for (Integer i : componentId) {
                    statement.executeUpdate("INSERT INTO shop.order(clients_id) VALUES (" + clientId + ")");
                    statement.executeUpdate("INSERT INTO order_item (order_id,component_id) VALUES (" +
                            orderIdFinder(connection) + "," + i + ")");
                }
            } else if (orderPointer > 0 && orderPointer < 10) {
                System.out.println("--------------------Choose by id--------------------");
                showTable(connection, typeComponentsNames.get(orderPointer - 1));
                System.out.print("Shop:/User/Order/Components> ");
                componentPointer = sc.nextInt();
                componentId.add(componentPointer);

            }
        }
        clientShoppingSection(connection);
    }

    public static int orderMenuGreeter(Connection connection) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("--------------------Choose one for order--------------------");
        componentTypeNameGetter(connection);
        System.out.println("[10] Complete order");
        System.out.println("[11] BACK");
        System.out.print("Shop:/User/Order> ");
        return sc.nextInt();
    }

    public static void componentTypeNameGetter(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT name FROM component_type ORDER BY type_id");
        int index = 1;
        while (rs.next()) {
            typeComponentsNames.add(rs.getString(1));
            System.out.println("[" + index + "] " + rs.getString(1));
            ++index;
        }
    }

    public static void showTable(Connection connection, String typeName) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT t2.component_id,t2.name, t2.price, t1.name " +
                "FROM component_type t1 " +
                "INNER JOIN component t2 " +
                "ON t1.type_id = t2.type_id where t1.name = '" + typeName + "'");


        String format = "%-8s|%-37s|%-16s|%-21s ";
        System.out.println("ID      |NAME COMPONENT                       |PRICE           |TYPE NAME            ");

        while (rs.next()) {
            int id = rs.getInt("t2.component_id");
            String nameComponent = rs.getString("t2.name");
            Double price = rs.getDouble("t2.price");
            String nameType = rs.getString("t1.name");
            System.out.printf((format) + "%n", id + "", nameComponent, price, nameType);
        }
    }


    public static int orderIdFinder(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT order_id FROM shop.order WHERE clients_id = " + clientId);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
}
