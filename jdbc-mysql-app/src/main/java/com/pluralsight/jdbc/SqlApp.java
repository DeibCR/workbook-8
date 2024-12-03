package com.pluralsight.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Scanner;

public class SqlApp {



    public static void main(String[] args) {

        BasicDataSource dataSource;
        dataSource=new BasicDataSource();



        String url="jdbc:mysql://localhost:3306/cardealership";
        String user="root";
        String password="yearup";

        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        Scanner scanner= new Scanner(System.in);
        boolean running=true;


        try (Connection connection = dataSource.getConnection()){

            PreparedStatement insertStatement= connection.prepareStatement("INSERT INTO vehicles (vin,year,make,model,vehicleType,color,odometer,price,sold)" +
                    "VALUES (?,?,?,?,?,?,?,?,?);");

            while (running){
                System.out.println("Enter the VIN you are looking to insert: ");
                String vinToInsert= scanner.nextLine();
                System.out.println("Enter the year you are looking to insert: ");
                String yearToInsert= scanner.nextLine();
                System.out.println("Enter the make you are looking to insert: ");
                String makeToInsert= scanner.nextLine();
                System.out.println("Enter the model you are looking to insert: ");
                String modelToInsert= scanner.nextLine();
                System.out.println("Enter the vehicleType you are looking to insert: ");
                String typeToInsert= scanner.nextLine();
                System.out.println("Enter the color you are looking to insert: ");
                String colorToInsert= scanner.nextLine();
                System.out.println("Enter the odometer you are looking to insert: ");
                String odometerToInsert= scanner.nextLine();
                System.out.println("Enter the price you are looking to insert: ");
                String priceToInsert= scanner.nextLine();
                System.out.println("Enter 1 for vehicle sold, 0 for vehicles not sold:  ");
                String soldToInsert= scanner.nextLine();

                insertStatement.setString(1,vinToInsert);
                insertStatement.setString(2,yearToInsert);
                insertStatement.setString(3,makeToInsert);
                insertStatement.setString(4,modelToInsert);
                insertStatement.setString(5,typeToInsert);
                insertStatement.setString(6,colorToInsert);
                insertStatement.setString(7,odometerToInsert);
                insertStatement.setString(8,priceToInsert);
                insertStatement.setString(9,soldToInsert);
                insertStatement.execute();


            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            dataSource.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
