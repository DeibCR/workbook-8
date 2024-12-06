package dao;

import model.Vehicle;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class VehicleDAOMysqlJdbc implements VehicleDAO{

    public final DataSource dataSource;

    public VehicleDAOMysqlJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Vehicle findVehicleByPrice(double maxPrice, double minPrice) {
        String vin ="";
        int year=0;
        String make="";
        String model="";
        String vehicleType="";
        String color="";
        int odometer=0;
        boolean sold=false;

        try(Connection connection=dataSource.getConnection()){
            PreparedStatement statement= connection.prepareStatement("""
                    SELECT *
                    FROM vehicles
                    WHERE price BETWEEN ? AND ?;
                    """);
            statement.setDouble(1,minPrice);
            statement.setDouble(2,maxPrice);
            ResultSet rs= statement.executeQuery();

            if (rs.next()){
                vin=rs.getString("vin");
                year=rs.getInt("year");
                make=rs.getString("make");
                model=rs.getString("model");
                vehicleType=rs.getString("vehicleType");
                color=rs.getString("color");

            }

        }catch (SQLException e){
            throw  new RuntimeException(e);
        }



        return new Vehicle(vin,year,make,model,vehicleType,color,odometer,price,sold);
    }

    @Override
    public Vehicle findVehicleByMakeModel(String make, String model) {
        return null;
    }

    @Override
    public Vehicle findVehicleByYear(int maxYear, int minYear) {
        return null;
    }

    @Override
    public Vehicle findVehicleByColor(String color) {
        return null;
    }

    @Override
    public Vehicle findVehicleByOdometer(int maxOdometer, int minOdometer) {
        return null;
    }

    @Override
    public Vehicle findVehicleByType(String vehicleType) {
        return null;
    }

    @Override
    public List<Vehicle> findAllVehicles() {
        return null;
    }
}
