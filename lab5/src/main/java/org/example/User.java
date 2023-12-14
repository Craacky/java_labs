package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class User {
    static Logger LOGGER;
    static {
        try(FileInputStream ins = new FileInputStream("/home/craacky/Projects/java_labs/lab5/log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Main.class.getName());
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
    }
    public static int clientId;

    public static double clientBalance;

    public static ArrayList<String> typeComponentsNames = new ArrayList<>();

    public static void clientMenu(Connection connection) throws SQLException, IOException {
        LOGGER.log(Level.INFO,"Client part start");

        Scanner sc = new Scanner(System.in);
        boolean status = false;
        System.out.println("--------------------Main Page--------------------");
        System.out.println("[1] I have an account.");
        System.out.println("[2] I don't have an account.");
        System.out.println("[exit] [back]");
        System.out.print("Shop:/Unrecognized> ");


        String selector = sc.next();
        if (selector.equals("1")) {
            status = checkClientExist(connection);
        } else if (selector.equals("2")) {
            status = clientSignUp(connection);
        } else if (selector.equalsIgnoreCase("back")) {
            SQL.menuGreeter(connection);
        } else if (selector.equalsIgnoreCase("exit")) {
            System.out.println("BYE!");
        }
        if (status) {
            clientShoppingSection(connection);
        }
    }

    public static void clientShoppingSection(Connection connection) throws SQLException, IOException {
        LOGGER.log(Level.INFO,"Client Shopping section start");

        Scanner sc = new Scanner(System.in);
        String selector = null;
        while (!Objects.equals(selector, "exit")) {
            System.out.println("--------------------Shopping--------------------");
            System.out.println("[1] Check balance.");
            System.out.println("[2] Top up your balance.");
            System.out.println("[3] Make order.");
            System.out.println("[4] Pay for my orders.");
            System.out.println("[exit] [back]");
            System.out.print("Shop:/User> ");
            selector = sc.next();

            if (selector.equals("1")) {
                System.out.println("Current balance = " + checkClientBalance(connection));
            } else if (selector.equals("2")) {
                topUpClientBalance(connection);
            } else if (selector.equals("3")) {
                orderFinal(connection);
            } else if (selector.equals("4")) {
                payForOrder(connection);
            } else if (selector.equalsIgnoreCase("back")) {
                clientMenu(connection);
            } else if (selector.equalsIgnoreCase("exit")) {
                System.exit(0);
            }
        }
    }

    public static boolean checkClientExist(Connection connection) throws SQLException {
        LOGGER.log(Level.INFO,"Client check if client exist");

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
        LOGGER.log(Level.INFO,"Client signup");
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
        LOGGER.log(Level.INFO,"Check client balance ");

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
        LOGGER.log(Level.INFO,"top up client balance");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter value of deposit: ");
        double depositValue = scanner.nextDouble();
        clientBalance = depositValue + checkClientBalance(connection);

        String sqlUpdate = "UPDATE client SET balance =" + clientBalance + " WHERE client_id =" + clientId;
        Statement stmt = connection.createStatement();


        stmt.executeUpdate(sqlUpdate);
    }

    public static void orderFinal(Connection connection) throws SQLException, IOException {
        LOGGER.log(Level.INFO,"Complete order");

        Statement statement = connection.createStatement();
        Scanner sc = new Scanner(System.in);
        int componentPointer;
        ArrayList<Integer> componentId = new ArrayList<>();
        int orderPointer = 0;
        int orderID = 0;
        while (orderPointer != 11) {
            orderPointer = orderMenuGreeter(connection);
            if (orderPointer == 10) {
                statement.executeUpdate("INSERT INTO shop.order(clients_id) VALUES (" + clientId + ")");
                for (Integer i : componentId) {
                    orderID = orderIdFinder(connection);
                    statement.executeUpdate("INSERT INTO order_item (order_id,component_id) VALUES (" +
                            orderID + "," + i + ")");
                }

                totalPriceCounter(connection, componentId, orderID);
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
        LOGGER.log(Level.INFO,"Order menu greeter");

        Scanner sc = new Scanner(System.in);
        System.out.println("--------------------Choose one for order--------------------");
        componentTypeNameGetter(connection);
        System.out.println("[10] Complete order");
        System.out.println("[11] BACK");
        System.out.print("Shop:/User/Order> ");
        return sc.nextInt();
    }

    public static void componentTypeNameGetter(Connection connection) throws SQLException {
        LOGGER.log(Level.INFO,"component type name getter");

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
        LOGGER.log(Level.INFO,"show order items table");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT t2.component_id,t2.name, t2.price, t1.name " +
                "FROM component_type t1 " +
                "INNER JOIN component t2 " +
                "ON t1.type_id = t2.type_id where t1.name = '" + typeName + "' order by t2.price");


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
        LOGGER.log(Level.INFO,"Order id finder");

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT max(order_id) FROM shop.order  WHERE clients_id = " + clientId);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public static void payForOrder(Connection connection) throws SQLException {
        LOGGER.log(Level.INFO,"Pay for order");

        Scanner sc = new Scanner(System.in);
        Statement statement = connection.createStatement();
        System.out.println("Your orders:");
        ResultSet rs = statement.executeQuery("""
                SELECT t1.*, t3.address, t4.name
                FROM shop.order t1\s
                INNER JOIN shop.order_item t2 ON t1.order_id = t2.order_id
                INNER JOIN shop.client t3 ON t1.clients_id = t3.client_id
                INNER JOIN shop.component t4 ON t2.component_id = t4.component_id
                INNER JOIN shop.component_type t5 ON t5.type_id = t4.type_id where t1.clients_id =\s""" + clientId + " and t1.payment_status = 0");
        String format = "%-8s|%-10s|%-20s|%-5s|%-7s|%-11s|%-50s|%-50s";
        System.out.println("ORDER ID|CLIENTS ID|TIMESTAMP           |STATE|PAYMENT|TOTAL PRICE|" +
                "ADDRESS                                           |COMPONENT NAME                                    ");

        int orderId = 0;
        while (rs.next()) {
            orderId = rs.getInt("t1.order_id");
            int clientId = rs.getInt("t1.clients_id");
            String timeStamp = rs.getString("t1.timestamp");
            int orderState = rs.getInt("t1.state");
            int paymentStatus = rs.getInt("t1.payment_status");
            double totalPrice = rs.getInt("t1.total_price");
            String status = rs.getString("t3.address");
            String nameComponent = rs.getString("t4.name");
            System.out.println("______________________________________________________________________________________" +
                    "_______________________________________________________________________" +
                    "________________________________");
            System.out.printf((format) + "%n", orderId + "", clientId, timeStamp,
                    orderState, paymentStatus, totalPrice, status, nameComponent);
            System.out.println("______________________________________________________________________________________" +
                    "_________________________________________________________________" +
                    "______________________________________");
        }
        if (orderId > 0){
            System.out.print("Choose by order id>");
            purchaseOrder(connection, sc.nextInt());
        }else {
            System.out.println("Order list empty");
        }

    }

    public static double totalPrice = 0;

    public static void totalPriceCounter(Connection connection, ArrayList<Integer> data, int orderId) throws SQLException {
        LOGGER.log(Level.INFO,"Price counter");

        Statement statement = connection.createStatement();
        String sqlSelect = "SELECT price FROM shop.component where component_id = ";
        for (Object id : data) {
            ResultSet rs = statement.executeQuery(sqlSelect + id);
            if (rs.next()) {
                totalPrice += rs.getDouble("price");
            }
        }
        statement.executeUpdate("UPDATE shop.order SET total_price = " + totalPrice +
                "WHERE order_id = " + orderId);
    }

    public static void purchaseOrder(Connection connection, int orderId) throws SQLException {
        LOGGER.log(Level.INFO,"Order purchase");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT total_price FROM shop.order where order_id = " + orderId);
        double priceOrder = 0;
        if (rs.next()) {
            priceOrder = rs.getDouble("total_price");
        } else {
            System.out.println("wrong order id");
        }
        double clientBalance = checkClientBalance(connection);
        double result;
        if (priceOrder < clientBalance) {
            result = clientBalance - priceOrder;
            statement.executeUpdate("UPDATE shop.order SET payment_status = 1");
            statement.executeUpdate("UPDATE shop.client SET balance = " + result + " WHERE client_id = " + clientId);
        } else {
            System.out.println("Don't enough money=)");
        }


    }
}
