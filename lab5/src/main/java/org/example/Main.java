package org.example;
import java.sql.*;

public class Main {
    static final String DB_URL = "jdbc:mariadb://localhost/pc_shop";
    static final String USER = "root";
    static final String PASS = "3197375";

    public static void main(String[] args){
        connector();

    }

    public static void connector(){
        System.out.println("Connecting to a selected database...");
        // Open a connection
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.println("Connected database successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}