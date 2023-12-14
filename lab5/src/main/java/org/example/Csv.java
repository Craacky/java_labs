package org.example;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Csv {
    public static List<String[]> orderTableGetter(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM shop.order");
        List<String[]> orderRows = new ArrayList<>();
        while (rs.next()) {
            orderRows.add(new String[]{rs.getString("order_id"),
                    rs.getString("clients_id"),
                    rs.getString("timestamp"),
                    rs.getString("state"),
                    rs.getString("payment_status"),
                    rs.getString("total_price")});

        }
        return orderRows;
    }

    public static String convertToCsvString(String... dataset) {

        String[] newData = new String[dataset.length];
        int i = 0;
        for (String data : dataset) {

            try {
                newData[i++] = escapeSpecialCharacters(data);
            } catch (NullPointerException ignored) {
            }
        }
        return String.join(",", newData);
    }


    public static String escapeSpecialCharacters(String data) {

        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }


    public static void writeStringsToCsv(List<String> csvStrings) throws IOException {

        File csvOutputFile = new File("/home/craacky/Projects/java_labs/lab5/src/" +
                "main/java/org/example/order.csv");
        try (FileWriter csvWriter = new FileWriter(csvOutputFile)) {
            for (String csvString : csvStrings) {
                csvWriter.append(csvString);
                csvWriter.append("\n");

            }
        }
    }
}
