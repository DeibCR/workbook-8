package com.pluralsight.jdbc;

import java.sql.*;

public class SqlApp {



    public static void main(String[] args) {

        String url="jdbc:mysql://localhost:3306/cardealership";
        String user="root";
        String password="yearup";

        try (Connection connection = DriverManager.getConnection(url,user,password)){

            Statement statement= connection.createStatement();

            ResultSet resultSet= statement.executeQuery("SELECT * FROM dealerships");
          //  ResultSet resultSet1= statement.executeQuery("SELECT vehicle_make, vehicle_model,vehicle_price FROM vehicles");
            ResultSet resultSet2= statement.executeQuery("""
    SELECT 
    v.make AS vehicle_make,
    v.model AS vehicle_model,
    v.price AS vehicle_price,
    d.name AS dealership_name,
    c.customerName AS customer_name,
    c.customerEmail AS customer_email
FROM vehicles v
JOIN inventory i ON v.vin = i.vin
JOIN dealerships d ON i.dealershipID = d.dealershipID
JOIN contract c ON v.vin = c.vin
WHERE v.sold = TRUE;
                    """);
            while (resultSet2.next()) {

                //System.out.println(resultSet.getString("name")+ ": "+ resultSet.getString("address")+". "+ resultSet.getString("phone"));
                System.out.println( resultSet2.getString(1)+ " " + resultSet2.getString(2)+" "+resultSet2.getString(3));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
