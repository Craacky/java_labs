package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static int gbClientId = 0;
    public static int gbComputerId = 0;
    public static int[] componentsId = new int[10];

    public static void main(String[] args) throws SQLException {
        Connection connection = getNewConnection();
        menuGreeter(connection);
    }

    public static void menuGreeter(Connection connection) throws SQLException {
        Scanner in = new Scanner(System.in);
        System.out.println("--------------------Who you are ?--------------------");
        System.out.println("1. I am user!");
        System.out.println("2. I am admin!");
        System.out.print("> ");
        int checkPoint = in.nextInt();

        if (checkPoint == 1) {
            System.out.println("--------------------Hi user!--------------------");
            System.out.println("1. Enter id");
            System.out.println("2. Registration");
            System.out.println("3. Exit");
            System.out.print("> ");
            int clientPointer = in.nextInt();
            if (clientPointer == 1) {
                System.out.print("Your id = ");
                int idCheck = in.nextInt();

                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT idClient FROM Client WHERE idClient =" + idCheck);

                if (result.first())
                    System.out.println("Have a good shopping!");
                else {
                    System.out.println("You are not registered!");
                    clientRegistration(connection);
                }

            } else if (clientPointer == 2) {
                clientRegistration(connection);
            }
//SELECT idClient FROM Client WHERE idClient = 1;
        } else if (checkPoint == 2) {
            System.out.println("--------------------Hi admin!--------------------");

        } else {
            System.out.println("You peak broke combination. GoodBye !!!");
        }
        in.close();
    }

    public static void clientGreeter(Connection connection) {
        System.out.println("--------------------Shopping--------------------");
        System.out.println("1.Change balance ");
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
        int result = statement.executeUpdate(
                "insert into Client(idClient,Adress,Balance)" + sql);
        if (result > 0)
            System.out.println("Successfully registration");

        else
            System.out.println("Unsuccessful registration");
    }

    public static Connection getNewConnection() throws SQLException {
        String url = "jdbc:mariadb://localhost/pc_shop";
        String user = "root";
        String passwd = "3197375";
        return DriverManager.getConnection(url, user, passwd);
    }

    public static void closeConnection(Connection connector) throws SQLException {
        connector.close();
        System.out.println("Connection close");
    }

    public static void showTablesConnection(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery("Show tables");
        System.out.println("Tables in the current database: ");
        while (rs.next()) {
            System.out.print(rs.getString(1));
            System.out.println();
        }
    }
    
    public static void columnsName(Connection connection, String table) throws SQLException {
        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery("select * from " + table);

        ResultSetMetaData rsMetaData = rs.getMetaData();
        System.out.println("List of column names in the " + table + " table: ");

        int count = rsMetaData.getColumnCount();
        for (int i = 1; i <= count; i++) {
            System.out.println(rsMetaData.getColumnName(i));
        }
    }


}
