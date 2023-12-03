package org.example;

import java.sql.*;
import java.util.Scanner;

public class User {
    public static int clientId = 0;
    public static void clientMenu(Connection connection) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("--------------------Hi user!--------------------");
        System.out.println("1. I am have account.");
        System.out.println("2. I don't have account.");
        System.out.println("3. Exit.");
        System.out.print("> ");
        int selector = 0;
            selector = sc.nextInt();
            if (selector == 1){
                checkClientId(connection);
            } else if (selector == 2) {
                clientRegistration(connection);
            }else {
                System.out.println("BYE!");
            }
        System.out.println("1.Check balance");
            checkBalance(connection);
    }

    public static void clientRegistration(Connection connection) throws SQLException {
        Scanner in = new Scanner(System.in);
        System.out.println("--------------------Registration--------------------");

        System.out.print("Enter your id = ");
        int id = in.nextInt();

        System.out.print("Enter your address = ");
        String address = in.next();

        System.out.print("Enter your Balance = ");
        double balance = in.nextDouble();

        String sql = "values('" + id + "','" + address + "','" + balance + "')";
        Statement statement = connection.createStatement();

        int result = statement.executeUpdate("insert into Client(idClient,Address,Balance)" + sql);
        if (result > 0)
            System.out.println("Successfully registration");
        else
            System.out.println("Unsuccessful registration");

    }

    public static void checkClientId(Connection connection) throws SQLException {
        Scanner in = new Scanner(System.in);
        System.out.print("Your id = ");
        int idCheck = in.nextInt();

        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT idClient FROM Client WHERE idClient =" + idCheck);

        if (result.first()){
            System.out.println("Have a good shopping!");
            clientId = idCheck;
        }
        else {
            System.out.println("You are not registered!");
            clientRegistration(connection);
        }
    }

    public static void checkBalance(Connection connection) throws SQLException{
        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery("select Balance from Client where idClient =" + clientId);
        while (rs.next()){
            System.out.println("Balance " +  rs.getString("Balance"));
        }
    }
}
